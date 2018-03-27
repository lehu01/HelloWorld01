package com.zmsoft.lib.pos.icbc;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/29 10:33
 *     desc  :
 * </pre>
 */
public class ICBCConstant {

    /**
     * 交易类型
     */
    public class TransType {
        // 今日退货
        public static final String CANCEL_PAY_TODAY = "POS_VOID";
        // 隔日退货
        public static final String CANCEL_PAY_MORE_DAY = "REFUND";
        // 支付
        public static final String PAY = "MULTI_PURCHASE";
        // 扫码退货
        public static final String CANCEL_ERR = "QRREFUND";
    }


}
