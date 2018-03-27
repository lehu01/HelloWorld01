package com.zmsoft.ccd.network;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/15.
 */

public class CommonConstant {

    //-----后面逐步替换AppConstant
    public static final String APP_LOGGER_TAG = "CCD-2dfire";
    public static final String ANDROID = "android";
    public static final String MD5 = "md5";
    public static final String JSON = "json";
    public static final String UNKNOWN = "unknown";
    //-----

    public static final String KEY_SP_TOKEN = "token";
    public static final String KEY_SP_USER_ID = "user_id";
    public static final String KEY_SP_USER_NAME = "user_name";
    public static final String KEY_SP_ENTITY_ID = "entity_id";
    public static final String KEY_SP_MEMBER_ID = "memberId";
    public static final String KEY_SP_MOBILE = "mobile";
    public static final String KEY_SP_WORK_MODE = "workmode";
    public static final String KEY_SP_INDUSTRY = "industry";
    public static final String KEY_SP_CURRENCY_SYMBOL = "currencySymbol";


    public static final String PARA_SIGN = "sign"; // 参数sign：签名
    public static final String PARA_METHOD = "method"; // 参数method：方法key
    public static final String PARA_TOKEN = "token"; // token
    public static final String PARA_ENTITY_ID = "entity_id"; // 参数entity_id：商铺id
    public static final String PARA_USER_ID = "user_id"; //当前账户ID


    public static final String KEY_SP_WORK_STATUS = "workStatus"; // 工作状态

    /**
     * 检测更新
     */
    public static final long CHECK_UPDATE_TIME_GAP = 12 * 60 * 60 * 1000;
    public static final String APP_CODE = "1017";

    public static final String PATCH_URL = "/app_store/v1/check_app_update";
}
