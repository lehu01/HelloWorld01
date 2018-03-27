package com.zmsoft.ccd.lib.base.constant;

/**
 * 公用的静态常量
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class BaseConstant {
    public static class PayType {
        public static final int PAY_TYPE_ALIPAY = 1;//阿里支付
        public static final int PAY_TYPE_WEIXIN = 2;//微信支付
        public static final int PAY_TYPE_CLUB_CARD = 3;//会员卡支付
        public static final int PAY_TYPE_BANK_CARD = 4;//银行卡支付
    }


    /**
     * 是否开启云收银（系统提下线时间）
     */
    public static final long TIME_TURN_CLOUD_CASH = 3 * 24 * 60 * 60 * 1000;

    /**
     * 获取自动打印相关开关的check的同步时间：点菜单，客户联，财务联
     */
    public static final long TIME_SELF_PRINT_ORDER = 30 * 60 * 1000;
}
