package com.zmsoft.ccd.lib.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 数量帮助类
 *
 * @author DangGui
 * @create 2017/2/23.
 */

public class NumberUtils {
    /**
     * 将originFee根据precision（精度）转化为需要的字符串
     *
     * @param originFee 原始金额
     * @param precision 精度
     * @return
     */
    public static String getDecimalFee(double originFee, int precision) {
        BigDecimal bigDecimal = new BigDecimal(originFee);
        bigDecimal = bigDecimal.setScale(precision, BigDecimal.ROUND_HALF_UP);//设置精度
        return bigDecimal.toString();
    }

    /**
     * @param originFee
     * @return
     */
    public static String getDecimalFee(double originFee) {
        return getDecimalFee(originFee, 2); // 默认精度为2
    }

    /**
     * 将originFee根据precision（精度）转化为需要的字符串
     *
     * @param originFee 原始金额
     * @param precision 精度
     * @return
     */
    public static String getDecimalFee(String originFee, int precision) {
        if (TextUtils.isEmpty(originFee)) {
            return originFee;
        }
        BigDecimal bigDecimal = new BigDecimal(originFee);
        bigDecimal = bigDecimal.setScale(precision, BigDecimal.ROUND_HALF_UP);//设置精度
        return bigDecimal.toString();
    }

    /**
     * 判断double是否是整型
     *
     * @param number double类型
     * @return
     */
    public static boolean doubleIsInteger(double number) {
        return !Double.isNaN(number) && !Double.isInfinite(number) && number == Math.rint(number);
    }

    /**
     * 获取数量显示
     *
     * @param number 数量
     * @return
     */
    public static String doubleToStr(double number) {
        String result;
        if (doubleIsInteger(number)) {
            result = String.valueOf((int) number);
        } else {
            result = String.valueOf(number);
        }
        return result;
    }


    public static String trimPointIfZero(double d, int scale) {
        if (d - (int) d == 0) {
            return String.valueOf((int) d);
        }
        return NumberUtils.getDecimalFee(d, scale);
    }

    public static String trimPointIfZero(double d) {
        if (d - (int) d == 0) {
            return String.valueOf((int) d);
        }
        BigDecimal bigDecimal = new BigDecimal(d);
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);//设置精度
        d = bigDecimal.doubleValue();
        if (d - (int) d == 0) {
            return String.valueOf((int) d);
        }
        return String.valueOf(d);
    }

}

