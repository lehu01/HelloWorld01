package com.zmsoft.lib.newland;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/23 15:57
 *     desc  : 新大陆key
 * </pre>
 */
public class NewLandConstant {

    /**
     * 交易code：和PayType保持一致
     */
    public static class Code {
        // 银行卡
        public static final String PAY_BANK = "000000";
        // 扫码支付
        public static final String PAY_SCAN = "660000";
        // 银行卡撤销
        public static final String CANCEL_PAY_BANK = "200000";
        // 扫码撤销
        public static final String CANCEL_PAY_SCAN = "680000";
        // 交易请求
        public static final String PAY_REQUEST = "0200";
        // 交易响应
        public static final String PAY_REPLY = "0210";
    }

    /**
     * 支付类型：和code保持一致
     */
    public static class Type {
        // 银行卡
        public static final int BANK = 0;
        // 微信支付
        public static final int WEIXIN = 11;
        // 支付宝支付
        public static final int ALIPAY = 12;
        // 微信支付
        public static final int WEIXIN_REPLY = 1;
        // 支付宝支付
        public static final int ALIPAY_REPLY = 2;
    }

    /**
     * Key
     */
    public static class Key {
        // 新大陆
        public static final String MSG_TP = "msg_tp";
        public static final String PROC_TP = "proc_tp";
        public static final String APPID = "appid";
        public static final String PAY_TP = "pay_tp";
        public static final String AMT = "amt";
        public static final String SYSTRACENO = "systraceno";
        public static final String PROC_CD = "proc_cd";
        public static final String SYS_TRACE_NO = "sysoldtraceno";
        public static final String REASON = "reason";
        public static final String TXNDETAIL = "txndetail";
    }
}
