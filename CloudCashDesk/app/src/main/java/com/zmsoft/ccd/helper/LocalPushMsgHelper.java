package com.zmsoft.ccd.helper;

import android.content.Context;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.zmsoft.ccd.data.local.PushMessage;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 存储在本地的推送消息工具类
 *
 * @author DangGui
 * @create 2017/6/10.
 */
public class LocalPushMsgHelper {
    /**
     * 由于云收银同时接入了极光推送和内部的TCP通道推送，需要将消息缓存到内存以及本地，做去重处理
     */
    private static LruCache<String, String> mPushMessageLruCache = new LruCache<>(1000);
    /**
     * 数据库中消息的过期时间，暂定位24小时
     */
    private static final long LOACL_MSG_EXPIRED_TIME = 24 * 60 * 60 * 1000;

    private static Executor mExecutor = Executors.newSingleThreadExecutor();

    public static void saveMsg(final Context context, final PushMessage pushMessage, final int type
            , final String title, final String message) {
        if (null == pushMessage) {
            return;
        }
        if (TextUtils.isEmpty(pushMessage.getPushMsgId())) {
            return;
        }
        if (null != mPushMessageLruCache.get(pushMessage.getPushMsgId())) {
            //内存已存在该msg，清除内存缓存以及本地缓存
//            Logger.w("推送消息--->消息id**" + pushMessage.getPushMsgId() + "**在内存中已存在");
            deleteLocal(pushMessage.getPushMsgId());
            return;
        }
        //内存以及本地存储都不存在该msg，存储到内存缓存以及本地缓存
        RxUtils.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                if (null == mPushMessageLruCache.get(pushMessage.getPushMsgId())) {
                    Long id = selectPushMsgId(pushMessage.getPushMsgId());
                    if (id < 0) {
//                        Logger.d("推送消息--->消息id**" + pushMessage.getPushMsgId() + "**不存在，notification提示");
                        saveToLocal(pushMessage);
                        mPushMessageLruCache.put(pushMessage.getPushMsgId(), pushMessage.getPushMsgId());
                        return true;
                    } else {
//                        Logger.w("推送消息--->消息id**" + pushMessage.getPushMsgId() + "**在本地数据库中已存在");
                        deleteLocal(pushMessage.getPushMsgId());
                        return false;
                    }
                } else {
                    //内存已存在该msg，清除内存缓存以及本地缓存
//                    Logger.w("推送消息--->消息id**" + pushMessage.getPushMsgId() + "**在内存中已存在");
                    deleteLocal(pushMessage.getPushMsgId());
                    return false;
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.from(mExecutor))
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean needNotification) {
                        if (needNotification) {
//                            Logger.d("推送消息--->Show notification");
                            PushMsgDispatchHelper.handlePushMsgWithType(context, type, title, message);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    public static void clearCache() {
        mPushMessageLruCache.trimToSize(0);
    }

    /**
     * 删除本地数据库中存留时间超过一天的消息
     */
    public static void trimLocalMsg() {
        long currentTime = System.currentTimeMillis();
        long expiredTime = currentTime - LOACL_MSG_EXPIRED_TIME;
        deleteLocalByCreateTime(expiredTime);
    }

    /**
     * 保存一条记录
     *
     * @param pushMessage
     */
    public static void saveToLocal(final PushMessage pushMessage) {
        if (pushMessage == null)
            return;
        //如果对应的消息已存在，则更新，否则插入
        long id = selectPushMsgId(pushMessage.getPushMsgId());
        if (id > 0) {
            pushMessage.update(id);
        } else {
            pushMessage.save();
        }
    }

    /**
     * 删除一条记录，如果pushMsgId对应的id存在，则删除
     *
     * @param pushMsgId
     */

    public static void deleteLocal(final String pushMsgId) {
        if (TextUtils.isEmpty(pushMsgId))
            return;

        RxUtils.fromCallable(new Callable<Void>() {
            @Override
            public Void call() {
                long id = selectPushMsgId(pushMsgId);
                if (id > 0) {
                    DataSupport.deleteAll(PushMessage.class, "pushMsgId = ?", pushMsgId);
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void v) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    /**
     * 删除一条记录，如果消息创建时间早于expiredTime，则删除
     *
     * @param expiredTime
     */
    private static void deleteLocalByCreateTime(final long expiredTime) {
        if (expiredTime <= 0)
            return;

        RxUtils.fromCallable(new Callable<Void>() {
            @Override
            public Void call() {
                DataSupport.deleteAll(PushMessage.class, "createTime < ?", expiredTime + "");
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void v) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    /**
     * 根据pushMsgId查询主键id值
     *
     * @param pushMsgId
     * @return
     */
    private static long selectPushMsgId(String pushMsgId) {
        List<PushMessage> infos = DataSupport.where("pushMsgId=?", pushMsgId).find(PushMessage.class);
        if (infos != null && !infos.isEmpty()) {
            return infos.get(0).getId();
        }
        return -1;
    }

    /**
     * 更新已有的pushMsg
     */
    public static int updateLocal(PushMessage pushMessage) {
        if (pushMessage == null)
            return 0;
        return pushMessage.updateAll("pushMsgId=?", pushMessage.getPushMsgId());

    }

    /**
     * 通过pushMsgId查询指定pushMsg
     */
    public static PushMessage selectByPushMsgId(String pushMsgId) {
        List<PushMessage> infos = DataSupport.where("pushMsgId=?", pushMsgId).find(PushMessage.class);
        if (infos != null && !infos.isEmpty()) {
            return infos.get(0);
        }
        return null;
    }

    /**
     * 获取PushMsg表中所有数据
     */
    public static List<PushMessage> findAllPushMsg() {
        return DataSupport.where("userId = ?", UserHelper.getUserId())
                .find(PushMessage.class);
    }
}
