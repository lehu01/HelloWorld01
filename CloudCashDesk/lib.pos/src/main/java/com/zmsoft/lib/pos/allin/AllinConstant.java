package com.zmsoft.lib.pos.allin;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/28 16:52
 *     desc  : 通联rejcode
 * </pre>
 */
public class AllinConstant {

    /**
     * 交易请求type
     */
    public class TransType {
        // 交易成功
        public static final String SUCCESS = "00";
        // 交易已撤销
        public static final String CANCEL = "XP";
    }

    /**
     * 支付类型type
     */
    public class PayType {
        public static final String ALIPAY = "ALP";
        public static final String WEXIN = "WXP";
    }

    /**
     * code返回码解释
     */
    public class RejCode {
        // 无记录
        public static final String NO_RECORD = "X2";
        // 无交易
        public static final String NO_PAY_RECORD = "X3";
    }


}
