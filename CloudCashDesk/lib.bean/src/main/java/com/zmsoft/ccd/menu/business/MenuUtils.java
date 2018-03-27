package com.zmsoft.ccd.menu.business;

import android.text.TextUtils;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/9/12.
 */

public class MenuUtils {


    /**
     * 是否是双单位
     */
    public static boolean isTwoAccount(String unit, String accountUnit) {
        return !TextUtils.isEmpty(accountUnit) && (!accountUnit.equals(unit));
    }

}
