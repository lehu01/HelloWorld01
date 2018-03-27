package com.ccd.lib.print.util.printer;

import android.os.Build;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/2 10:43
 *     desc  : 本机打印工具类：自带打印机功能的机器
 * </pre>
 */
public class LocalPrinterUtils {

    /**
     * 非凡打印机
     */
    private static final String DEVICE_BRAND_FEIFAN = "FFAN";

    /**
     * 根据型号来检查是不是非凡打印机
     *
     * @return
     */
    public static boolean isFeiFanPrinter() {
        return Build.BRAND.equals(DEVICE_BRAND_FEIFAN);
    }

    /**
     * 是否支持本机打印
     */
    public static boolean isSupportLocalPrinter() {
        return isFeiFanPrinter();
    }

}
