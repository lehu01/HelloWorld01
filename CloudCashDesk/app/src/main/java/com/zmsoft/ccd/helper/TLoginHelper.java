//package com.zmsoft.ccd.helper;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.openshop.common.expose.ExternalParams;
//import com.zmsoft.ccd.app.AppEnv;
//import com.zmsoft.ccd.lib.utils.StringUtils;
//import com.zmsoft.ccd.module.login.TLoginActivity;
//
//import tdf.zmsoft.core.constants.TDFProjectParams;
//import tdf.zmsoft.login.manager.LoginUtils;
//
///**
// * <pre>
// *     author: danshen@2dfire.com
// *     time  : 2017/9/18 17:59
// *     desc  :
// * </pre>
// */
//public class TLoginHelper {
//
//    /**
//     * 跳转登录
//     * @param context
//     */
//    public static void gotoLoginActivity(Context context) {
//        gotoLoginActivity(context, "");
//    }
//
//    /**
//     * 跳转登录
//     * @param context
//     * @param errorCode
//     */
//    public static void gotoLoginActivity(Context context, String errorCode) {
//        Intent intent = new Intent(context, TLoginActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(LoginUtils.APP_ID, "");
//        bundle.putString(LoginUtils.APP_SECRET, "");
//        bundle.putInt(LoginUtils.APP_TYPE, TDFProjectParams.APPTYPE_CCD);
//        if (!StringUtils.isEmpty(errorCode)) {
//            bundle.putString(TLoginActivity.EXTRA_CODE, errorCode);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        intent.putExtras(bundle);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 登录环境切换
//     * @return
//     */
//    public static int getEnvType() {
//        if (AppEnv.isProduction()) {
//            return ExternalParams.TYPE_RELEASE;
//        }
//        int env = AppEnv.getEnv();
//        if (env == AppEnv.PUB) {
//            return ExternalParams.TYPE_RELEASE;
//        } else if (env == AppEnv.PRE) {
//            return ExternalParams.TYPE_RELEASEPRE;
//        } else if (env == AppEnv.DEV) {
//            return ExternalParams.TYPE_DEBUG;
//        }
//        return ExternalParams.TYPE_RELEASEPRE;
//    }
//}
