package com.zmsoft.ccd.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import com.ccd.lib.print.data.IPrintSource;
import com.ccd.lib.print.data.PrintSource;
import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.model.PrintData;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.google.gson.Gson;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.bean.Extra;
import com.zmsoft.ccd.bean.MessageContent;
import com.zmsoft.ccd.bean.PrintMessageContent;
import com.zmsoft.ccd.bean.PushPrintInfo;
import com.zmsoft.ccd.bean.TCPMsg;
import com.zmsoft.ccd.constant.SPConstants;
import com.zmsoft.ccd.data.local.PushMessage;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.event.JoinTableEvent;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.tts.TTSUtils;
import com.zmsoft.ccd.module.main.MainActivity;
import com.zmsoft.ccd.module.splash.SplashActivity;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.zmsoft.ccd.app.GlobalVars.context;


/**
 * 推送消息分发器
 * <p>
 * 根据消息类型，分别进行相应的处理
 * </p>
 *
 * @author DangGui
 * @create 2017/3/31.
 */

public class PushMsgDispatchHelper {
    /**
     * 用来打开notification跳转activity时，通过intent传参告诉activity该intent的来源
     */
    public static final String EXTRA_NOTIFICATION_TO_MSGCENTER = "extra_notification_to_msgcenter";

    /**
     * 推送的额外字段，消息类型 type
     */
    private static final String MESSAGE_KEY_CONTENT = "configContent";
    private static final String EXTRA_KEY_TYPE = "type";
    private static IPrintSource mPrintSource = new PrintSource();
    /**
     * 当前是否正在处理默认语言提示
     */
    private static boolean isNotifyingDefaultMsg = false;

    /**
     * 处理接收到的自定义消息
     *
     * @param message 接收到的推送消息
     * @param extras  接收到的额外字段消息
     */
    public static void processCustomMessage(Context context, String title, String message, String extras) {
        if (!TextUtils.isEmpty(extras)) {
            try {
                Extra extra = new Gson().fromJson(extras, Extra.class);
                //0代表未处理
                if (!extra.getSu().equals("0")) {
                    return;
                }
                PushMessage pushMessage = new PushMessage();
                pushMessage.setUserId(UserHelper.getUserId());
                pushMessage.setPushMsgId(extra.getMid());
                pushMessage.setCreateTime(System.currentTimeMillis());
                LocalPushMsgHelper.saveMsg(context, pushMessage, extra.getType(), title, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理接收到的TCP通道自定义消息
     *
     * @param message 接收到的推送消息
     */
    public static void processTCPCustomMessage(Context context, String message) {
        if (!TextUtils.isEmpty(message)) {
            try {
                TCPMsg tcpMsg = new Gson().fromJson(message, TCPMsg.class);
                //0代表未处理
                if (!tcpMsg.getSu().equals("0")) {
                    return;
                }
                PushMessage pushMessage = new PushMessage();
                pushMessage.setUserId(UserHelper.getUserId());
                pushMessage.setPushMsgId(tcpMsg.getMid());
                pushMessage.setCreateTime(System.currentTimeMillis());
                LocalPushMsgHelper.saveMsg(context, pushMessage, tcpMsg.getTy(), tcpMsg.getTl(), tcpMsg.getCot());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static MessageContent getMessageContent(String msg) {
        try {
            return new Gson().fromJson(msg, MessageContent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * type = 1226，自动审核打印
     *
     * @param msg
     * @return
     */
    private static PrintMessageContent getPrintMessageContent(String msg) {
        try {
            return new Gson().fromJson(msg, PrintMessageContent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据给的type值，分别做相应的处理
     *
     * @param type 推送来的消息type
     */
    public static void handlePushMsgWithType(Context context, int type, String title, String msg) {
        switch (type) {
            //推送消息来自消息中心的4种消息，比如服务铃消息、加菜消息等，需要notification展示
            case PushMsgType.TYPE_MSGCENTER_SERVICE_CALL:
            case PushMsgType.TYPE_MSGCENTER_PAY:
            case PushMsgType.TYPE_MSGCENTER_ADD_FOOD_SCAN_ORDER:
            case PushMsgType.TYPE_MSGCENTER_ADD_FOOD_SCAN_DESK:
            case PushMsgType.TYPE_MSGCENTER_AUTO_CHECK:
            case PushMsgType.TYPE_MSGCENTER_AUTO_CHECK_PRE_PAY:
            case PushMsgType.TYPE_MSGCENTER_PREPAY_SCAN_DESK:
            case PushMsgType.TYPE_PREPAY_DOUBLE_UNIT:
                MessageContent mc = getMessageContent(msg);
                if (mc != null) {
                    handleMsgCenterPushMsg(type, title
                            , mc.getConfigContent());
                }
                handleMsgRefreshOrderAbout();
                break;
            //外卖消息
            case PushMsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK:
            case PushMsgType.TYPE_TAKEOUT_THIRD_MIXTURE:
            case PushMsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT:
            case PushMsgType.TYPE_TAKEOUT_ORDER_CANCLE_INDEPENDENT:
            case PushMsgType.TYPE_TAKEOUT_ORDER_REMINDER:
            case PushMsgType.TYPE_TAKEOUT_DELIVERY_REFRESH:
                if (UserHelper.getWorkModeIsMixture()) {
                    return;
                }
                MessageContent mc2 = getMessageContent(msg);
                if (mc2 != null) {
                    handleMsgCenterPushMsg(type, title
                            , mc2.getConfigContent());
                }
                handleMsgRefreshOrderAbout();
                handleMsgRefreshTakeoutAbout();
                break;
            case PushMsgType.TYPE_OPEN_ORDER:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_ADD_FOOD:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_MODIFY_FOOD:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_MODIFY_ORDER:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_COMBINE_ORDER:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_SETTLE_ACCOUNTS:
                MessageContent mc5 = getMessageContent(msg);
                //如果操作人是自己，就不需要处理该消息
                if (mc5 == null || TextUtils.isEmpty(mc5.getOpUserId())
                        || TextUtils.isEmpty(UserHelper.getUserId())
                        || !mc5.getOpUserId().equals(UserHelper.getUserId())) {
                    handleMsgRefreshOrderAbout();
                }
                break;
            case PushMsgType.TYPE_CANCLE_ORDER:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_REJECT_ORDER:
                break;
            case PushMsgType.TYPE_COUNTER_SETTLING_ACCOUNTS:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_BACK_FOOD:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_FOOD_STANDBY:
                break;
            case PushMsgType.TYPE_CALL_TAKE_FOOD:
                break;
            case PushMsgType.TYPE_ORDER_STANDBY:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_MODIFY_SEAT:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_PRE_PAY_ADD_FOOD:
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_SAME_SEAT_JOIN:
                //通知购物车有新消息
                EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_NOTIFY_CART_NEW);
                //同桌加入点菜
                MessageContent sc = getMessageContent(msg);
                if (sc != null) {
                    EventBusHelper.post(new JoinTableEvent(sc.getName(), sc.getPicFullPath()));
                }
                break;
            case PushMsgType.TYPE_SEAT_CHANGE: // 桌位变动
                handleMsgRefreshOrderAbout();
                break;
            case PushMsgType.TYPE_AUTO_PRINT_ALL: //打印消息：推送所有用户
                PrintMessageContent printMessageContent = getPrintMessageContent(msg);
                printSelf(printMessageContent);
                break;
            default:
                break;
        }
    }


    //================================================================
    // 自动打印：标签和小票
    //================================================================
    private static void printSelf(PrintMessageContent printMessageContent) {
        if (printMessageContent != null) {
            if (printMessageContent.getPrintTaskList() != null && printMessageContent.getPrintTaskList().size() > 0) {
                for (PushPrintInfo info : printMessageContent.getPrintTaskList()) {
                    if (info == null) {
                        continue;
                    }
                    lockPrintTask(info);
                }
            }
        }
    }

    private static void lockPrintTask(final PushPrintInfo info) {
        mPrintSource.lockPrintTask(UserHelper.getEntityId()
                , UserHelper.getUserId()
                , info.getId())
                .retryWhen(new RetryWithDelay(1, 1000))
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean data) {
                        if (data) {
                            doPrint(info);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof ServerException) {
                            ServerException e = (ServerException) throwable;
                            ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                        }
                    }
                });
    }

    private static void doPrint(PushPrintInfo info) {
        switch (info.getDocumentType()) {
            case PrintData.BIZ_TYPE_PRINT_DISHES_ORDER:
            case PrintData.BIZ_TYPE_PRINT_ACCOUNT_ORDER:
            case PrintData.BIZ_TYPE_PRINT_FINANCE_ORDER:
            case PrintData.BIZ_TYPE_PRINT_ADD_INSTANCE:
            case PrintData.BIZ_TYPE_PRINT_RETAIL_FINANCE_ORDER:
            case PrintData.BIZ_TYPE_PRINT_RETAIL_ORDER:
                // 小票打印
                CcdPrintHelper.printOrderByLoadFilePath(context
                        , info.getFilePath()
                        , info.getSeatCode()
                        , info.getOrderId()
                        , info.getOrderKind()
                        , info.getDocumentType()
                        , info.getIp()
                        , info.getId());
                break;
            case PrintData.BIZ_TYPE_PRINT_LABEL:
                // 标签打印
                CcdPrintHelper.printLabelByLoadFilePath(context
                        , info.getFilePath()
                        , info.getSeatCode()
                        , info.getOrderId()
                        , info.getOrderKind());
                break;
        }
    }

    /**
     * 通知刷新:订单相关改变
     * 1.订单列表
     * 2.座位列表
     * 3.订单详情
     */
    private static void handleMsgRefreshOrderAbout() {
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_ORDER_DETAIL);
    }

    /**
     *  外卖列表
     */
    private static void handleMsgRefreshTakeoutAbout() {
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_NOTIFY_TAKEOUT_LIST);
    }

    /**
     * 处理来自消息中心的推送消息
     *
     * @param type    消息类型
     * @param title   消息title
     * @param message 消息正文
     */
    private static void handleMsgCenterPushMsg(int type, String title, String message) {
        showNotification(context, title, message, type);
        //如果应用是打开状态，则需要通知主界面消息中心
        if (RouterActivityManager.get().getActivityCount() > 0) {
            //通知消息列表刷新数据
            EventBusHelper.post(BaseEvents.CommonEvent.EVENT_MSG_CENTER_REFRESH);
            //通知主界面消息中心显示小红点
            BaseEvents.CommonEvent event = BaseEvents.CommonEvent.EVENT_MSG_CENTER_UNREAD;
            event.setObject(true);
            EventBusHelper.post(event);
        }
    }

    /**
     * 处理用户点击notification之后的页面跳转逻辑
     *
     * @param context mContext
     * @param bundle  推送携带过来的参数bundle
     */
    public static void handleNotificationOpened(Context context, Bundle bundle) {
        //打开自定义的Activity
        Intent i = new Intent(context, MainActivity.class);
        i.putExtras(bundle);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }

    private static void showNotification(Context context, String title, String content, int type) {
        //如果关闭了消息设置里的“接收推送消息”，不弹出notification
        if (!SPUtils.getInstance(context).getBoolean(SPConstants.MsgSetting.SHOW_NEW_MSG, true)) {
            return;
        }
        int nId = 1001;
        if (TextUtils.isEmpty(title)) {
            title = context.getString(R.string.app_name);
        }
        Intent intent;
        //如果应用是关闭状态，则等同于启动应用，否则，直接启动主页面
        if (RouterActivityManager.get().getActivityCount() == 0) {
            intent = new Intent(context, SplashActivity.class);
        } else {
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_NOTIFICATION_TO_MSGCENTER, true);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, nId, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.logo_48x48)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true);
        if (!needSpeakCustomTts(type)) {
//            notificationBuilder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message));
            speakDefaultMsg(context);
        }
        notificationBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        notificationManager.notify(nId, notificationBuilder.build());
        if (needSpeakCustomTts(type)) {
            speakTts(content);
        }
    }

    /**
     * 是否需要播放自定义语音提示（比如支付消息，下单消息）
     *
     * @param type 消息类型
     * @return
     */
    private static boolean needSpeakCustomTts(int type) {
        if (!SPUtils.getInstance(context).getBoolean(SPConstants.MsgSetting.CUSTOM_TTS, true)) {
            return false;
        }
        switch (type) {
            //推送消息来自消息中心的4种消息，比如服务铃消息、加菜消息等，需要notification展示
            case PushMsgType.TYPE_MSGCENTER_ADD_FOOD_SCAN_ORDER:
            case PushMsgType.TYPE_MSGCENTER_ADD_FOOD_SCAN_DESK:
            case PushMsgType.TYPE_MSGCENTER_AUTO_CHECK:
            case PushMsgType.TYPE_MSGCENTER_AUTO_CHECK_PRE_PAY:
                return SPUtils.getInstance(context).getBoolean(SPConstants.MsgSetting.CUSTOM_TTS_SCAN_ORDER, true);
            case PushMsgType.TYPE_MSGCENTER_PAY:
            case PushMsgType.TYPE_MSGCENTER_PREPAY_SCAN_DESK:
                return SPUtils.getInstance(context).getBoolean(SPConstants.MsgSetting.CUSTOM_TTS_PAY, true);
            //外卖下单消息
            case PushMsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK:
            case PushMsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT:
                return SPUtils.getInstance(context).getBoolean(SPConstants.MsgSetting.CUSTOM_TTS_TAKE_OUT, true);
            default:
                return false;
        }
    }

    /**
     * 播放语音提示
     *
     * @param text 消息文本内容
     */
    private static void speakTts(String text) {
        TTSUtils.speak(text);
    }

    /**
     * 播放默认语音
     * <p>目前对语音提示的处理是：<br />
     * 1、默认语音使用notification.setSound(URI uri)播报 <br />
     * 2、其他类型（比如支付消息）使用百度语音（TTS）播报自定义声音
     * </p>
     * <p>第一种情况会出现同一时间接收到N条默认语音消息时，出现播报重叠的问题，现在的优化方案是：<br />
     * Optimize 1、收到默认语音时，延时3秒后再播报，并且将这3秒内接收到的默认语音整合，只播报一次。
     * </p>
     *
     * @param context
     */
    private static void speakDefaultMsg(final Context context) {
        if (isNotifyingDefaultMsg) {
            return;
        }
        isNotifyingDefaultMsg = true;
        RxUtils.fromCallable(new Callable<Void>() {
            @Override
            public Void call() {
                return null;
            }
        }).delay(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        speakTts(context.getString(R.string.notification_default_msg));
                        isNotifyingDefaultMsg = false;
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        isNotifyingDefaultMsg = false;
                    }
                });
    }

    /**
     * 推送消息类型，包括“服务铃、支付消息、加菜消息(非预付款的下单消息)、预付款消息”等
     */
    public static class PushMsgType {
        //服务铃
        public static final int TYPE_MSGCENTER_SERVICE_CALL = MessageHelper.MsgType.TYPE_SERVICE_CALL;
        //支付消息
        public static final int TYPE_MSGCENTER_PAY = MessageHelper.MsgType.TYPE_PAY;
        //加菜消息(非预付款的下单消息),扫单
        public static final int TYPE_MSGCENTER_ADD_FOOD_SCAN_ORDER = MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER;
        //加菜消息(非预付款的下单消息)，扫桌/扫码
        public static final int TYPE_MSGCENTER_ADD_FOOD_SCAN_DESK = MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK;
        //自动审核的非预付款消息,扫码下单，｛数量｝个菜
        public static final int TYPE_MSGCENTER_AUTO_CHECK_PRE_PAY = MessageHelper.MsgType.TYPE_AUTO_CHECK_PRE_PAY;
        //自动审核的非预付款消息,扫码下单，｛数量｝个菜
        public static final int TYPE_MSGCENTER_AUTO_CHECK = MessageHelper.MsgType.TYPE_AUTO_CHECK;
        //云收银预付款支付消息，收到的通知消息:扫码下单，｛数量｝个菜，已支付{支付金额}元，是否付清。
        public static final int TYPE_MSGCENTER_PREPAY_SCAN_DESK = MessageHelper.MsgType.TYPE_PREPAY_SCAN_DESK;
        //云收银开单
        public static final int TYPE_OPEN_ORDER = 1201;
        //云收银加菜
        public static final int TYPE_ADD_FOOD = 1202;
        //云收银修改菜信息
        public static final int TYPE_MODIFY_FOOD = 1203;
        //云收银修改订单信息
        public static final int TYPE_MODIFY_ORDER = 1204;
        //云收银并单
        public static final int TYPE_COMBINE_ORDER = 1205;
        //云收银结账完毕
        public static final int TYPE_SETTLE_ACCOUNTS = 1206;
        //云收银撤单
        public static final int TYPE_CANCLE_ORDER = 1207;
        //云收银拒绝订单
        public static final int TYPE_REJECT_ORDER = 1208;
        //云收银反结账
        public static final int TYPE_COUNTER_SETTLING_ACCOUNTS = 1209;
        //云收银退菜
        public static final int TYPE_BACK_FOOD = 1212;
        //云收银暂不上菜(菜)
        public static final int TYPE_FOOD_STANDBY = 1213;
        //云收银呼叫取餐
        public static final int TYPE_CALL_TAKE_FOOD = 1214;
        //云收银暂不上菜(订单)
        public static final int TYPE_ORDER_STANDBY = 1215;
        //云收银桌位修改
        public static final int TYPE_MODIFY_SEAT = 1216;
        //预付款开单、加菜
        public static final int TYPE_PRE_PAY_ADD_FOOD = 1217;
        //同桌加入点菜
        public static final int TYPE_SAME_SEAT_JOIN = 1301;
        //购物车提交
        public static final int TYPE_CART_COMMIT = 1302;
        //桌位变动[二维火收银机/云收银/服务生]
        public static final int TYPE_SEAT_CHANGE = 301;
        //自动审核新消息后打印：旧版本使用，新版本使用1236
        public static final int TYPE_AUTO_CHECK_PRINT = 1226;
        //打印消息：推送所有用户
        public static final int TYPE_AUTO_PRINT_ALL = 1236;
        //外卖单消息，小二外卖手动审核消息
        public static final int TYPE_TAKEOUT_XIAOER_MANUAL_CHECK = MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK;
        //外卖单消息，混合模式模式下，通知消息(第三方外卖)
        public static final int TYPE_TAKEOUT_THIRD_MIXTURE = MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE;
        //外卖单消息，外卖下单自动审核通知消息----独立模式下第三方外卖通知消息，小二自动审核通知消息
        public static final int TYPE_TAKEOUT_THIRD_INDEPENDENT = MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT;
        //外卖单消息，外卖撤单——独立使用云收银
        public static final int TYPE_TAKEOUT_ORDER_CANCLE_INDEPENDENT = MessageHelper.MsgType.TYPE_TAKEOUT_ORDER_CANCLE_INDEPENDENT;
        //外卖单消息，外卖催单
        public static final int TYPE_TAKEOUT_ORDER_REMINDER = MessageHelper.MsgType.TYPE_TAKEOUT_ORDER_REMINDER;
        //外卖单消息，配送更新（1.待接单2.待取货..等）
        public static final int TYPE_TAKEOUT_DELIVERY_REFRESH = MessageHelper.MsgType.TYPE_TAKEOUT_DELIVERY_REFRESH;
        //双单位预付款非自动审核消息
        public static final int TYPE_PREPAY_DOUBLE_UNIT = MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT;
    }
}
