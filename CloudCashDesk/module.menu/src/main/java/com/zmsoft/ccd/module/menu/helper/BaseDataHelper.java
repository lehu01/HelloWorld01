package com.zmsoft.ccd.module.menu.helper;

/**
 * @author DangGui
 * @create 2017/4/20.
 */

public class BaseDataHelper {
    /**
     * 判断double是否是整型
     *
     * @param number double类型
     * @return
     */
    protected static boolean doubleIsInteger(double number) {
        return !Double.isNaN(number) && !Double.isInfinite(number) && number == Math.rint(number);
    }
}
