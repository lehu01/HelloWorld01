package com.zmsoft.ccd.lib.base.helper;

import android.content.Context;

import com.zmsoft.ccd.lib.base.R;

/**
 * @author DangGui
 * @create 2017/6/21.
 */

public class BusinessHelper {

    /**
     * 获取支付类型icon
     *
     * @param type 支付类型
     * @return 返回结果
     */
    public static int getPayTypeIcon(int type) {
        int payType;
        switch (type) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_CASH:
                payType = R.drawable.icon_pay_from_cash_money;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP:
                payType = R.drawable.icon_pay_from_vip_car;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_SIGN_BILL:
                payType = R.drawable.icon_pay_from_onaccount;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_FREE_BILL:
                payType = R.drawable.icon_pay_from_free;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_COUPON:
                payType = R.drawable.icon_pay_from_coupon;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_PART_PAY:
                payType = R.drawable.icon_pay_from_part;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VOUCHER:
                payType = R.drawable.icon_pay_from_voucher;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_WEIXIN:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT:
                payType = R.drawable.icon_wechat;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY:
                payType = R.drawable.icon_alipay;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_BANK:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK:
                payType = R.drawable.icon_pay_from_bank;
                break;
            default:
                payType = R.drawable.icon_pay_from_default;
                break;
        }
        return payType;
    }


    /**
     * 删除支付；提示文案
     *
     * @param context 上下文
     * @param type    支付类型
     * @return 返回结果
     */
    public static String alertDeletePayItem(Context context, short type) {
        switch (type) {
            case ReceiptMethod.RECEIPT_METHOD_VIP:
            case ReceiptMethod.RECEIPT_METHOD_WEIXIN:
            case ReceiptMethod.RECEIPT_METHOD_ALIPAY:
            case ReceiptMethod.RECEIPT_METHOD_QQ:
            case ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
            case ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
            case ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
            case ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY:
            case ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT:
            case ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK:
                return context.getString(R.string.module_receipt_delete_card_pay_alert);
            case ReceiptMethod.RECEIPT_METHOD_PROMOTION_MARKET_EXCHANGE_SINGLE_COUPON:
                return context.getString(R.string.module_receipt_delete_single_coupon_pay_alert);
            case ReceiptMethod.RECEIPT_METHOD_PROMOTION_MARKET_CENTER:
                return context.getString(R.string.module_receipt_delete_market_center_pay_alert);
            case ReceiptMethod.RECEIPT_METHOD_MEMBER_POINTS:
                return context.getString(R.string.module_receipt_delete_member_points_pay_alert);
            case ReceiptMethod.RECEIPT_METHOD_PROMOTION_WEIXIN:
                return String.format(context.getString(R.string.module_receipt_delete_weixin_discount_pay_alert)
                        , context.getString(R.string.weixin));
            case ReceiptMethod.RECEIPT_METHOD_PROMOTION_ALIPAY:
                return String.format(context.getString(R.string.module_receipt_delete_weixin_discount_pay_alert)
                        , context.getString(R.string.alipay));
            case ReceiptMethod.RECEIPT_METHOD_PROMOTION_QQ:
                return String.format(context.getString(R.string.module_receipt_delete_weixin_discount_pay_alert)
                        , context.getString(R.string.QQ));
            default:
                return context.getString(R.string.module_receipt_delete_received_item_alert);
        }
    }

    /**
     * 是否是电子支付
     *
     * @param payType 支付类型
     * @return
     */
    public static boolean isElectronicPay(int payType) {
        return payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_WEIXIN
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALIPAY
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_QQ
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK;
    }

    /**
     * 收款方式
     */
    public static class ReceiptMethod {
        //收款方式需要展示出来的十种方式
        public static final int RECEIPT_METHOD_CASH = 10000; // 现金支付
        public static final int RECEIPT_METHOD_BANK = 10001; // 银行卡支付
        public static final int RECEIPT_METHOD_ALIPAY = 1; // 支付宝支付
        public static final int RECEIPT_METHOD_WEIXIN = 9; // 微信支付
        public static final int RECEIPT_METHOD_COUPON = 10010; // 代金券支付
        public static final int RECEIPT_METHOD_VIP = 3; // 会员卡支付
        public static final int RECEIPT_METHOD_FREE_BILL = 10004; // 免单支付
        public static final int RECEIPT_METHOD_SIGN_BILL = 10002; // 挂账支付
        public static final int RECEIPT_METHOD_PART_PAY = 10006; // 由客房结算支付
        public static final int RECEIPT_METHOD_VOUCHER = 10003; // 优惠券支付

        //其他收款方式
        public static final int RECEIPT_METHOD_CLICKLIKE = 15; // 点赞支付
        public static final int RECEIPT_METHOD_DP = 11; // 闪惠支付
        public static final int RECEIPT_METHOD_QQ = 16; // QQ钱包支付
        public static final int RECEIPT_METHOD_MEMBER_POINTS = 17; // 会员积分支付
        public static final int RECEIPT_METHOD_PROMOTION_CARD = -1; // 会员卡优惠
        public static final int RECEIPT_METHOD_PROMOTION_WEIXIN = 1009; // 微信优惠
        public static final int RECEIPT_METHOD_PROMOTION_QQ = 1016; // QQ钱包优惠
        public static final int RECEIPT_METHOD_PROMOTION_ALIPAY = 1001; // 支付宝优惠
        public static final int RECEIPT_METHOD_PROMOTION_DP = 12; // 闪惠优惠
        public static final int RECEIPT_METHOD_PROMOTION_MARKET_CENTER = 8; // 本店在线优惠
        public static final int RECEIPT_METHOD_PROMOTION_MARKET_EXCHANGE_SINGLE_COUPON = 13; // 单品兑换券在线优惠

        //通联支付收款方式
        public static final int RECEIPT_METHOD_ALLIN_ALIPAY = 21; // 通联支付宝支付
        public static final int RECEIPT_METHOD_ALLIN_WEICHAT = 22; // 通联微信支付
        public static final int RECEIPT_METHOD_ALLIN_BANK = 24; // 通联银行卡支付

        // 新大陆收款方式
        public static final int RECEIPT_METHOD_NEW_LAND_ALIPAY = 25; // 通联支付宝支付
        public static final int RECEIPT_METHOD_NEW_LAND_WEICHAT = 26; // 通联微信支付
        public static final int RECEIPT_METHOD_NEW_LAND_BANK = 28; // 通联银行卡支付
    }

    /**
     * 退款状态
     */
    public static class RefundStatus {
        /**
         * 成功
         */
        public static final int SUCCESS = 1;
        /**
         * 失败
         */
        public static final int FAILURE = -1;
        /**
         * 退款中
         */
        public static final int REFUNDING = 0;
    }
}
