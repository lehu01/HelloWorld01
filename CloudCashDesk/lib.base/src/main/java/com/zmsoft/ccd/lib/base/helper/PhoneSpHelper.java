package com.zmsoft.ccd.lib.base.helper;

import android.content.Context;

import com.zmsoft.ccd.lib.base.constant.PhoneSpKey;
import com.zmsoft.ccd.lib.utils.PhoneSPUtils;

/**
 * 账号登出时，不会会被全部清除
 * 本地sp存储管理
 * @author : heniu@2dfire.com
 * @time : 2017/10/23 14:25.
 */

public class PhoneSpHelper {

    /**
     * 新手引导版本号，main activity
     */
    public static void saveGreenHandVersionMainActivity(Context context, int value) {
        PhoneSPUtils.getInstance(context).putInt(PhoneSpKey.KEY_GREEN_HAND_VERSION_MAIN_ACTIVITY, value);
    }

    public static int getGreenHandVersionMainActivity(Context context) {
        return PhoneSPUtils.getInstance(context).getInt(PhoneSpKey.KEY_GREEN_HAND_VERSION_MAIN_ACTIVITY);
    }

    /**
     * 新手引导版本号，order detail
     */
    public static void saveGreenHandVersionOrderDetail(Context context, int value) {
        PhoneSPUtils.getInstance(context).putInt(PhoneSpKey.KEY_GREEN_HAND_VERSION_ORDER_DETAIL, value);
    }

    public static int getGreenHandVersionOrderDetail(Context context) {
        return PhoneSPUtils.getInstance(context).getInt(PhoneSpKey.KEY_GREEN_HAND_VERSION_ORDER_DETAIL);
    }
}
