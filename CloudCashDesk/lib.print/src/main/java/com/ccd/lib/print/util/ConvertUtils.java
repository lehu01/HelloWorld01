package com.ccd.lib.print.util;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/6 15:46
 */
public class ConvertUtils {

    public static int toInteger(Object value, int defaultValue) {
        try {
            if (value == null || "".equals(value.toString().trim())) {
                return defaultValue;
            }
            return Integer.parseInt(value.toString().trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
