package com.zmsoft.ccd.lib.base.helper;

import android.content.Context;

import com.zmsoft.ccd.lib.base.constant.BaseSpKey;
import com.zmsoft.ccd.lib.bean.Base;

import static com.zmsoft.ccd.lib.utils.SPUtils.getInstance;


/**
 * Description：公共配置工具类
 * <br/>
 * 
 * Created by kumu on 2017/7/17.
 */

public class ConfigHelper {

    public static void saveReceiveCarryoutPhoneSetting(Context context, boolean setting) {
        getInstance(context).putBoolean(BaseSpKey.KEY_CASH_CARRYOUT_PHONE_SETTING, setting);
    }

    public static boolean getReceiveCarryoutPhoneSetting(Context context) {
        return getInstance(context).getBoolean(BaseSpKey.KEY_CASH_CARRYOUT_PHONE_SETTING, false);
    }

    public static boolean hasOpenCashClean(Context context) {
        return isHybrid(context) &&
                Base.STRING_TRUE.equals(getInstance(context).getString(BaseSpKey.KEY_TOGGLE_CASH_CLEAN));
    }

    public static boolean hasOpenCarryoutPhoneCall(Context context) {
        return Base.STRING_TRUE.equals(getInstance(context).getString(BaseSpKey.KEY_TOGGLE_CARRYOUT_PHONE_CALL));
    }

    public static void saveWorkMode(Context context, boolean isHybrid) {
        getInstance(context).putBoolean(BaseSpKey.KEY_HYBRID, isHybrid);
    }

    public static boolean isHybrid(Context context) {
        return getInstance(context).getBoolean(BaseSpKey.KEY_HYBRID);
    }

}
