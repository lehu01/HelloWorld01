package com.zmsoft.lib.tonglian.helper;

import android.content.Context;


/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/28 16:07
 *     desc  : 通联pos开关保存类
 * </pre>
 */
public class AllInSpHelper {

    public static final String KEY_ALL_IN_POS = "key_all_in_pos";

    /**
     * 存储pos开关
     */
    public static void saveAllInPos(Context context, boolean value) {
        context.getSharedPreferences(KEY_ALL_IN_POS, Context.MODE_PRIVATE)
                .edit().putBoolean(KEY_ALL_IN_POS, value)
                .apply();
    }

    /**
     * 获取通联pos开关
     */
    public static boolean isAllInPos(Context context) {
        return context.getSharedPreferences(KEY_ALL_IN_POS, Context.MODE_PRIVATE)
                .getBoolean(KEY_ALL_IN_POS, false);
    }

}
