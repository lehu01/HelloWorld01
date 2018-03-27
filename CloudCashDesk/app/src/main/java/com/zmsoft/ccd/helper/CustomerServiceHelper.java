package com.zmsoft.ccd.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import com.sobot.chat.SobotApi;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.utils.ZhiChiConstant;
import com.zmsoft.ccd.BuildConfig;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.constant.AppConfigure;
import com.zmsoft.ccd.event.customerservice.CustomerServiceUnreadMessageEvent;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/30 10:45.
 */

public class CustomerServiceHelper {

    public static void openCustomerService(Context context) {
        Information info = new Information();
        info.setAppkey(AppConfigure.CUSTOMER_SERVICE.APP_KEY);
        info.setColor(AppConfigure.CUSTOMER_SERVICE.TITLE_COLOR);

        Map<String, String> customInfo = new HashMap<>();
        customInfo.put(context.getString(R.string.customer_service_app_version), BuildConfig.VERSION_NAME);
        customInfo.put(context.getString(R.string.customer_service_phone_version), new Build().MODEL + " " + String.format(context.getString(R.string.response_mail_os_format), android.os.Build.VERSION.RELEASE));
        info.setCustomInfo(customInfo);

        User user = UserLocalPrefsCacheSource.getUser();
        if (null != user.getUserId()) {
            info.setUid(user.getUserId());
        } else {
            info.setUid("");
        }
        if (!StringUtils.isEmpty(user.getMemberName())) {
            info.setUname(user.getMemberName());
        } else {
            info.setUname(context.getString(R.string.customer_service_not_login_user) + user.getMobile());
        }
        if (null != user.getMobile()) {
            info.setTel(user.getMobile());
        } else {
            info.setTel("-");
        }
        if (null != user.getPicFullPath()) {
            info.setFace(user.getPicFullPath());
        } else {
            info.setFace(AppConfigure.CUSTOMER_SERVICE.DEFAULT_FACE);
        }
        info.setShowSatisfaction(false);

        SobotApi.startSobotChat(context, info);
    }


    //================================================================================
    // customer service
    //=================================================================================
    public static void registerCustomerService(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ZhiChiConstant.sobot_unreadCountBrocast);

        MessageReceiver messageReceiver = new MessageReceiver();
        SobotApi.initSobotChannel(context);

        context.registerReceiver(messageReceiver, intentFilter);
        SobotApi.setNotificationFlag(context, true, R.drawable.icon_app, R.drawable.icon_app);
    }

    private static class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (ZhiChiConstant.sobot_unreadCountBrocast.equals(action)) {
                if (SobotApi.getUnreadMsg(context) > 0) {
                    EventBusHelper.post(new CustomerServiceUnreadMessageEvent());
                }
            }
        }
    }
}
