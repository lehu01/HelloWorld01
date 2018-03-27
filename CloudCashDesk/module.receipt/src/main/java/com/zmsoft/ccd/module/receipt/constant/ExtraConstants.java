package com.zmsoft.ccd.module.receipt.constant;

/**
 * activity跳转传参统一管理
 *
 * @author DangGui
 * @create 2017/6/15.
 */

public class ExtraConstants {
    public static class NormalReceipt {
        //应收金额
        public static final String EXTRA_FEE = "fee";
        //还需收款
        public static final String EXTRA_NEED_FEE = "needFee";
        //普通付款方式的类型（现金支付、银行卡支付、免单支付等）
        public static final String EXTRA_TYPE = "type";
        //普通付款方式的类型名称（现金支付、银行卡支付、免单支付等）
        public static final String EXTRA_TITLE = "title";
    }

    public static class CouponReceipt {
        //支付类型id
        public static final String EXTRA_KIND_PAY_ID = "kindPayId";
    }

    public static class OnAccountReceipt {
        //选中的挂账单位
        public static final String EXTRA_SELECT_UNIT = "unit";
    }

    public static class CompleteReceipt {
        //修改时间
        public static final String EXTRA_MODIFY_TIME = "modifyTime";
    }
}
