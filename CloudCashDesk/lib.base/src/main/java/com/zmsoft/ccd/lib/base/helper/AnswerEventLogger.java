package com.zmsoft.ccd.lib.base.helper;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;
import com.zmsoft.ccd.app.AppEnv;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.lib.bean.user.ChannelInfoRequest;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.DeviceUtil;

import java.util.Map;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 11/08/2017.
 *
 * 埋点记录器
 */
public final class AnswerEventLogger {

    private ICommonSource iCommonSource;

    public AnswerEventLogger(){
        iCommonSource = new CommonRemoteSource();
    }

    /**
     * 埋点记录
     * @param contentName 行为名称
     */
    public static void log(String contentName){
        log(contentName, null, null);
    }

    /**
     * 埋点记录
     * @param contentName 行为名称
     * @param contentType 行为分类类型
     */
    public static void log(String contentName, String contentType){
        log(contentName, contentType, null);
    }

    /**
     * 埋点记录
     * @param contentName 行为名称
     * @param contentType 行为分类类型
     * @param map         自定义参数map合集
     */
    public static void log(String contentName, String contentType, ArrayMap<String, String> map){
        if (!AppEnv.isProduction()) {
            return;
        }
        ContentViewEvent contentViewEvent = new ContentViewEvent();
        contentViewEvent.putContentName(contentName);


        if(!TextUtils.isEmpty(contentType)){
            contentViewEvent.putContentType(contentType);
        }

        if(map != null && !map.isEmpty()){
            for (Map.Entry<String, String> entry : map.entrySet()){
                contentViewEvent.putCustomAttribute(entry.getKey(), entry.getValue());
            }
        }
        Answers.getInstance().logContentView(contentViewEvent);
    }

    public void logChannelInfo(@ChannelInfoRequest.EventCode int event, @Nullable User user, String VersionName){
        String ipAddress = DeviceUtil.getIPAddress(true);
        String macAddress = DeviceUtil.getMACAddress(null);
        final ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(GlobalVars.context);
        String channel = null;
        if(channelInfo != null){
            channel = channelInfo.getChannel();
        }
        String ua = String.format("%s-%s-%s", Build.MANUFACTURER, Build.BRAND, Build.MODEL);

        iCommonSource.uploadChannelInfo(new ChannelInfoRequest.Builder()
                .ip(ipAddress)
                .mac(macAddress)
                .mobile(user == null ? "" : user.getMobile())
                .entityId(user == null ? "" : user.getEntityId())
                .channelString(channel)
                .event(event)
                .ua(ua)
                .app_code(AppEnv.getApiKey())
                .version(VersionName)
                .build());
    }
}
