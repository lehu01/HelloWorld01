package com.zmsoft.ccd.helper;

import com.zmsoft.ccd.lib.utils.NumberUtils;

/**
 * @author DangGui
 * @create 2017/2/22.
 */

public final class MessageDetailHelper {

    /**
     * 商品如果有做法、规格、备注、加料在商品的下一行显示，超过一行时换行显示，例如大杯，加冰，椰果1份
     * 加料需展示每份加料的数量
     * 依次显示规格、做法、加料、备注，用逗号隔开
     */
    public static final String FOOD_MAKENAME_SEPARATOR = "，";

    public static class FoodType {
        public static final int TYPE_NORMAL_FOOD = 1; //普通菜品
        public static final int TYPE_COMBO_FOOD = 2; //套餐
        public static final int TYPE_CUSTOM_FOOD = 3; //自定义菜
        public static final int TYPE_CUSTOM_COMBO_FOOD = 4; //自定义套菜
        public static final int TYPE_CHARGE_FOOD = 5; //加料菜
    }

    public static class FoodState {
        public static final int STATE_SEND_READY = 0; //待发送
        public static final int STATE_CHECK_READY = 1; //已发送待审核;
        public static final int STATE_TIMEOUT = 2; //下单超时
        public static final int STATE_FAIL = 3; //下单失败
        public static final int STATE_SUCCESS = 9; //下单成功
    }

    public static class OrderState {
        public static final int STATE_CHECK_PENDING = 0; //未处理状态 ， 待审核
        public static final int STATE_CHECK_HANDLING = 1; //处理中
        public static final int STATE_AGREED = 2; //处理成功， 已同意
        public static final int STATE_TIMEOUT = 3; //超时 ，   已超时
        public static final int STATE_HANDLE_FAIL = 4; //处理失败
        public static final int STATE_REJECTED = 5; //已拒绝
        public static final int STATE_NO_NEED_HANDLE = 6; //不需要处理状态
    }

    /**
     * 加菜的状态
     */
    public static class FoodStatus {
        public static final int STATUS_SEND_PENDING = 0; //待发送
        public static final int STATUS_CHECK_PENDING = 1; //已发送待审核
        public static final int STATUS_ORDER_TIMEOUT = 2; //下单超时
        public static final int STATUS_ORDER_FAIL = 3; //下单失败
        public static final int STATUS_ORDER_SUCCESS = 9; //下单成功
    }

    /**
     * 支付方式
     */
    public static class PaymentMethod {
        public static final int PAYMENT_METHOD_CASH_MONEY0 = 0; // 现金
        public static final int PAYMENT_METHOD_ALIPAY = 1; // 支付宝
        public static final int PAYMENT_METHOD_KUAI_QIAN = 2; // 块钱
        public static final int PAYMENT_METHOD_VIP_CARD = 3; // 会员卡
        public static final int PAYMENT_METHOD_YIN_LIAN = 4; // 银联
        public static final int PAYMENT_METHOD_YIN_LIAN_NET = 6; // 银联网上支付
        public static final int PAYMENT_METHOD_XUNLIAN = 7; // 讯联支付
        public static final int PAYMENT_METHOD_CASH_MONEY = 8; // 在线优惠
        public static final int PAYMENT_METHOD_WEIXIN = 9; // 微信
        public static final int PAYMENT_METHOD_RED_BAG = 10; // 红包支付
        public static final int PAYMENT_METHOD_QQ = 16; // QQ支付
        public static final int PAYMENT_METHOD_MEITUAN_PAY = 101; // 美团支付
        public static final int PAYMENT_METHOD_MEITUAN_DISCOUNT_PAY = 1101; // 美团优惠
    }

    /**
     * 加菜的状态
     */
    public static class TakeFoodType {
        public static final int FOOD = 0; //食物
        public static final int DOGGY_BOX = 1; //打包盒
    }

    /**
     * 距离转换，小于1KM，显示M
     *
     * @param distance
     * @return
     */
    public static String formatDistance(double distance) {
        if (distance < 1) {
            return NumberUtils.trimPointIfZero(distance * 1000) + "m";
        }
        return NumberUtils.trimPointIfZero(distance) + "km";
    }

}
