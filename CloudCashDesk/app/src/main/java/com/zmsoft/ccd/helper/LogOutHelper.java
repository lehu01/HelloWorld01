package com.zmsoft.ccd.helper;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chiclaim.modularization.utils.RouterActivityManager;
import com.sobot.chat.SobotApi;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.login.login.LoginActivity;

/**
 * 退出登录 帮助类<br/>
 * 退出登录前需要的一些初始化操作，比如清空用户信息、清空缓存、还原推送相关配置等
 *
 * @author DangGui
 * @create 2017/2/23.
 */

public class LogOutHelper {

    /**
     * 退出登录
     *
     * @param context
     */
    public static void logOut(Context context) {
        logOut(context, "");
    }

    /**
     * 退出登录
     *
     * @param context
     * @param errorCode
     */
    public static void logOut(Context context, String errorCode) {
        logOut(context, 0);
        // 注销在线客服
        SobotApi.exitSobotChat(context);
        //退出到登录界面
        gotoLoginActivity(context, errorCode);
        //关闭所有的ACTIVITY
        RouterActivityManager.get().finishAllActivityExcept(LoginActivity.class);
    }

    /**
     * 退出登录,不需要退出到登录页，仅仅是清楚老用户信息
     *
     * @param context
     */
    public static void logOut(Context context, int type) {
        // 清除统一登录的缓存
//        TDFACache.get(context).remove(TDFJpushLoginConstants.CLEAR_MEMBER_SESSION_ID);
        //清空用户信息
        UserLocalPrefsCacheSource.logout();
        //清空SharedPreferences设置信息
        SPUtils.getInstance(context).clear();
        //反注册推送消息
        PushSettingHelper.unRegisterPush();
        //clear通知栏notification
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        //清空推送消息内存缓存
        LocalPushMsgHelper.clearCache();
    }

    private static void gotoLoginActivity(Context context, String errorCode) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (!StringUtils.isEmpty(errorCode)) {
            Bundle bundle = new Bundle();
            bundle.putString(RouterPathConstant.Login.EXTRA_CODE, errorCode);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

}

