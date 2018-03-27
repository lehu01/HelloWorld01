package com.zmsoft.ccd.lib.utils;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/2 18:20
 */
public class IpUtils {

    public static final String IP_ADDRESS = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." +
            "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
            "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
            "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            if (text.matches(IP_ADDRESS)) {
                return true;
            }
        }
        return false;
    }
}
