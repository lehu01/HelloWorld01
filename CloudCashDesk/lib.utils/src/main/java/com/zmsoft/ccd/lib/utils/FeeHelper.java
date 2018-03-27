package com.zmsoft.ccd.lib.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 金额帮助类，设置需要的精度
 *
 * @author DangGui
 * @create 2017/2/23.
 */

public class FeeHelper {
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
     * 分转元
     *
     * @param feeFen 金额，单位分
     * @return
     */
    public static String getDecimalFeeByFen(int feeFen) {
        BigDecimal feeYuan = BigDecimal.valueOf(feeFen).divide(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        return feeYuan.toString(); // 默认精度为2
    }

    /**
     * 元转分
     *
     * @param feeYuan 金额，单位元
     * @return
     */
    public static String getDecimalFeeByYuan(double feeYuan) {
        return BigDecimal.valueOf(feeYuan).multiply(new BigDecimal(100)).toString();
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
     * 拼接货币符号和金额，eg ￥100.00 $100.00
     *
     * @param currencySymbol 货币符号 eg ￥ $
     * @param money
     * @return
     */
    public static String jointMoneyWithCurrencySymbol(String currencySymbol, String money) {
        return StringUtils.appendStr(currencySymbol, money);
    }

    /**
     * 拼接货币符号和金额，eg ￥100.00 $100.00
     *
     * @param currencySymbol 货币符号 eg ￥ $
     * @param money
     * @param negative       是否是负数
     * @return
     */
    public static String jointMoneyWithCurrencySymbol(String currencySymbol, String money, boolean negative) {
        if (negative) {
            return StringUtils.appendStr("-", currencySymbol, money);
        } else {
            return StringUtils.appendStr(currencySymbol, money);
        }
    }

    public static String jointMoneyWithCurrencySymbol(String currencySymbol, String format, int moneyInt) {
        String moneyString = String.format(format, moneyInt);
        boolean negative = moneyInt < 0;
        return jointMoneyWithCurrencySymbol(currencySymbol, moneyString, negative);
    }

    public static String jointMoneyWithCurrencySymbol(String currencySymbol, String format, float moneyFloat) {
        String moneyString = String.format(format, moneyFloat);
        boolean negative = moneyFloat < 0.0f;
        return jointMoneyWithCurrencySymbol(currencySymbol, moneyString, negative);
    }

    public static String jointMoneyWithCurrencySymbol(String currencySymbol, String format, double moneyDouble) {
        String moneyString = String.format(format, moneyDouble);
        boolean negative = moneyDouble < 0.0f;
        return jointMoneyWithCurrencySymbol(currencySymbol, moneyString, negative);
    }

    public static String jointMoneyWithCurrencySymbol(String currencySymbol, String format, BigDecimal moneyBigDecimal) {
        String moneyString = String.format(format, moneyBigDecimal);
        boolean negative = moneyBigDecimal.compareTo(BigDecimal.ZERO) < 0;
        return jointMoneyWithCurrencySymbol(currencySymbol, moneyString, negative);
    }

    /**
     * 转换人民币符号￥-> ¥
     *
     * @param sourceSymbol
     * @return
     */
    public static String transferRMBSymbol(String sourceSymbol) {
        if (!TextUtils.isEmpty(sourceSymbol)) {
            if (sourceSymbol.contains("￥")) {
                sourceSymbol = sourceSymbol.replace("￥", "¥");
            } else if (sourceSymbol.contains("¥")) {
                sourceSymbol = sourceSymbol.replace("¥", "¥");
            }
        }
        return sourceSymbol;
    }

}

