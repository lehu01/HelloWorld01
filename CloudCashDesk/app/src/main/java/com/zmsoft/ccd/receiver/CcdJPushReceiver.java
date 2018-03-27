package com.zmsoft.ccd.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.helper.PushMsgDispatchHelper;

import cn.jpush.android.api.JPushInterface;

/**
 * JPush自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class CcdJPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (null == bundle) {
            return;
        }
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Logger.d("[CcdJPushReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.d("[CcdJPushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Logger.d("[CcdJPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.d("[CcdJPushReceiver] 用户点击打开了通知");
            PushMsgDispatchHelper.handleNotificationOpened(context, bundle);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Logger.d("[CcdJPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Logger.w("[CcdJPushReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Logger.d("[CcdJPushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 处理接收到的自定义消息
     *
     * @param bundle 自定义消息
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Logger.d("[CcdJPushReceiver] 接收到推送下来的自定义消息:message---> " + message);
        Logger.d("[CcdJPushReceiver] 接收到推送下来的自定义消息:extras---> " + extras);
        PushMsgDispatchHelper.processCustomMessage(context, title, message, extras);
    }
}
