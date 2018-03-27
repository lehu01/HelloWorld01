package com.zmsoft.ccd.app;

import android.content.Context;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/1/3 14:46
 */
public class GlobalVars {

    public static Context context;

    public static void init(Context context) {
        GlobalVars.context = context;
    }

}