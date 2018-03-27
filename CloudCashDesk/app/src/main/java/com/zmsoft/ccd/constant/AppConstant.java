package com.zmsoft.ccd.constant;

/**
 * 全局常量管理类
 *
 * @author DangGui
 * @create 2016/12/16.
 * @see com.zmsoft.ccd.network.CommonConstant
 */
@Deprecated
public class AppConstant {

    public static final String APP_LOGGER_TAG = "CCD-2dfire";

    public static final String ANDROID = "android";
    public static final String MD5 = "md5";
    public static final String JSON = "json";

    //在应用商城里的app_code  后续确定了再修改
    public static final String APP_CODE = "1017";
    //检查更新的时间间隔
    public static final long CHECK_UPDATE_TIME_GAP = 12 * 60 * 60 * 1000;
    // 发送验证码时间
    public static final long SEND_SMS_TIME = 60 * 1000;
}
