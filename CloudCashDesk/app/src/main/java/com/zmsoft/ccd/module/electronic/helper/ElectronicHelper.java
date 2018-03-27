package com.zmsoft.ccd.module.electronic.helper;

/**
 * @author DangGui
 * @create 2017/8/19.
 */

public class ElectronicHelper {
    public class ReceiptStatus {
        /**
         * 电子明细支付状态-支付成功
         */
        public static final int PAY_SUCCESS = 0;
        /**
         * 电子明细支付状态-退款成功
         */
        public static final int REFUND_SUCCESS = 1;
        /**
         * 电子明细支付状态-支付成功但已退款
         */
        public static final int PAY_SUCCESS_BUT_REFUNDED = 2;
    }

    public class ExtraParams {
        public static final String PARAM_PAY_ID = "payId";
        public static final String PARAM_CODE = "code";
    }

    public class RequestCode {
        public static final int CODE_TO_DETAIL = 1;
    }
}
