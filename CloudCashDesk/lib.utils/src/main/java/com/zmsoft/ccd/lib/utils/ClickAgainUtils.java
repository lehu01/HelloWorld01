package com.zmsoft.ccd.lib.utils;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/21 15:41
 *     desc  : 多次点击
 * </pre>
 */
public class ClickAgainUtils {

    private static long lastClickTime;
    private static int clickCount;

    public static boolean switchClick(int counts) {
        long curTime = System.currentTimeMillis();
        if (lastClickTime == 0 || curTime - lastClickTime <= 2000) {
            clickCount++;
            lastClickTime = curTime;
        } else {
            clickCount = 0;
            lastClickTime = 0;
        }
        if (clickCount >= counts) {
            clickCount = 0;
            lastClickTime = 0;
            return true;
        }
        return false;
    }
}
