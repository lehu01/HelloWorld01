package com.zmsoft.ccd.module.receipt.receipt.helper;

import com.zmsoft.ccd.lib.base.helper.BusinessHelper;

/**
 * @author DangGui
 * @create 2017/6/13.
 */

public class ReceiptHelper {
    /**
     * 优惠类型，目前版本只有“会员卡”这种类型
     */
    public static class PromotionType {
        /**
         * <code>会员卡</code>.
         */
        public static final int VIP_CARD = 3;
    }

    /**
     * 优惠金额的优惠类型(会员卡)
     * 如果是会员卡，则根据不同的会员卡优惠类型显示不同的内容：
     * 如果是折扣，则显示：会员卡：{会员卡名称} {折扣率}
     * 如果是会员价，则显示：会员卡：{会员卡名称} -¥{优惠金额}
     * 如果是打折方案，则显示：会员卡：{会员卡名称} -¥{优惠金额}
     */
    public static class DiscountMode {
        /**
         * <code>优惠方式:使用会员价</code>.
         */
        public static final int MODE_MEMBERPRICE = 1;
        /**
         * <code>优惠方式:使用打折方案</code>.
         */
        public static final int MODE_DISCOUNTPLAN = 2;
        /**
         * <code>优惠方式：打折</code>.
         */
        public static final int MODE_RATIO = 3;
    }

    /**
     * 支付状态
     */
    public static class PayStatus {
        /**
         * 支付成功
         */
        public static final int SUCCESS = 1;
        /**
         * 支付失败
         */
        public static final int FAILURE = -1;
        /**
         * 正在支付
         */
        public static final int PAYING = 2;
    }

    /**
     * 优惠状态
     */
    public static class PromotionStatus {
        /**
         * 成功
         */
        public static final int SUCCESS = 1;
        /**
         * 失败
         */
        public static final int FAILURE = -1;
        /**
         * 正在核销
         */
        public static final int PROMOTIONING = 0;
    }

    /**
     * 收款类型
     */
    public static class ReceiptFund {
        /**
         * 卡支付
         */
        public static final int TYPE_CARD = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP;
        public static final String CLASS_CARD_FUND = "com.dfire.tp.client.cloudcash.vo.fund.CardFund";
        /**
         * 第三方支付
         */
        public static final int TYPE_THIRD_ALIPAY = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALIPAY; //支付宝支付
        public static final int TYPE_THIRD_WEIXIN = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_WEIXIN; //微信支付
        public static final int TYPE_THIRD_QQ = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_QQ; //QQ钱包支付
        public static final String CLASS_THIRD_FUND = "com.dfire.tp.client.cloudcash.vo.fund.ThirdFund";
        /**
         * 代金券支付
         */
        public static final int TYPE_COUPON = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_COUPON;
        public static final String CLASS_COUPON_FUND = "com.dfire.tp.client.cloudcash.vo.fund.VoucherFund";
        /**
         * 普通支付
         */
        public static final int TYPE_NORMAL_CASH = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_CASH; //现金支付
        public static final int TYPE_NORMAL_BANK = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_BANK; //银行卡支付
        public static final int TYPE_NORMAL_ONACCOUNT = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_SIGN_BILL; //挂账支付
        public static final int TYPE_NORMAL_VOUCHER = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VOUCHER; //优惠券支付
        public static final int TYPE_NORMAL_FREE = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_FREE_BILL; //免单支付
        public static final int TYPE_NORMAL_PART = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_PART_PAY; //由客房结算支付
        public static final String CLASS_NORMAL_FUND = "com.dfire.tp.client.cloudcash.vo.fund.Fund";
    }

    /**
     * 优惠类型
     */
    public static class ReceiptPromotion {
        /**
         * 卡优惠
         */
        public static final int TYPE_CARD = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_PROMOTION_CARD;
        public static final String CLASS_CARD_PROMOTION = "com.dfire.tp.client.cloudcash.vo.promotion.CardPromotion";
        /**
         * 整单打折优惠
         */
        public static final int TYPE_DISCOUNT = -2;
        public static final String CLASS_DISCOUNT = "com.dfire.tp.client.cloudcash.vo.promotion.RatioOrderPromotion";
    }

    /**
     * startActivityForResult requestCode
     */
    public static class ACTIVITY_REQUEST_CODE {
        /**
         * 收款界面跳转到各种收款方式界面
         */
        public static final int CODE_RECEIPT_WAY = 100;
    }

    /**
     * KindDetail模式<br />
     * 0自由录入/1从列表中选择2可选择可录入
     */
    public static class KindDetailMode {
        /**
         * 自由录入
         */
        public static final int INPUT = 0;
        /**
         * 从列表中选择
         */
        public static final int LIST_OPTION = 1;
        /**
         * 可选择可录入
         */
        public static final int INPUT_OR_OPTION = 2;
    }

    /**
     * 收款 RecyclerAdapter中ViewHolder 子view点击事件类型
     */
    public static class RecyclerViewHolderClickType {
        /**
         * 整单打折
         */
        public static final int DISCOUNT = 1;
        /**
         * 优惠券核销
         */
        public static final int VERIFICATION = 2;
    }

    /**
     * 订单的收款模式，0表示普通收款，1表示快捷收款
     */
    public static class CollectPayMode {
        /**
         * 普通收款
         */
        public static final int NORMAL = 0;
        /**
         * 快捷收款
         */
        public static final int SHORTCUT = 1;
    }

    /**
     * 第三方收款超时
     */
    public static class CollectThirdPayOuttime {
        /**
         * 第三方收款超时
         */
        public static final String ERROR_CODE_OUTTIME = "ERROR_THIRD_OUTTIME";
    }
}
