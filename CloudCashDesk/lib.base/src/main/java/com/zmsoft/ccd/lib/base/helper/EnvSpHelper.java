package com.zmsoft.ccd.lib.base.helper;

import android.content.Context;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/15 14:27
 *     desc  : 环境存储自定义的环境
 * </pre>
 */
public class EnvSpHelper {

    private static final String KEY_PREF = "key_env_pref";
    private static final String KEY_CUSTOM_ENV = "key_custom_env";

    public static void saveCustomEnv(Context context, String env) {
        context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)
                .edit().putString(KEY_CUSTOM_ENV, env)
                .apply();
    }

    public static String getCustomEnv(Context context) {
        return context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)
                .getString(KEY_CUSTOM_ENV, "");
    }

}
