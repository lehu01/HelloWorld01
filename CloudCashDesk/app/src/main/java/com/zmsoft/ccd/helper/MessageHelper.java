package com.zmsoft.ccd.helper;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 16:40
 */
public final class MessageHelper {

    /**
     * 消息种类
     */
    public static class MsgCategory {
        public static final int MESSAGE_NEW = 0; // 顾客新消息
        public static final int MESSAGE_HANDLED = 2; // 已处理消息
        public static final int MESSAGE_ALL = 7; // 所有消息
    }

    /**
     * 消息类型，包括“服务铃、支付消息、加菜消息(非预付款的下单消息)、预付款消息”
     * 需处理的消息：
     * 1.加菜消息审核。如果5分钟内未对消息进行处理，则消息超时，变为已超时状态，且自动拒绝点菜。
     * 不需要处理的消息:
     * 1.服务铃
     * 2.预付款消息
     * 3.支付消息
     */
    public static class MsgType {
        public static final int TYPE_SERVICE_CALL = 111; //服务铃
        public static final int TYPE_PAY = 141; //支付消息:xx支付多少元,未/已付清
        public static final int TYPE_ADD_FOOD_SCAN_ORDER = 101; // 扫码加菜
        public static final int TYPE_ADD_FOOD_SCAN_DESK = 102; //扫码点菜
        public static final int TYPE_AUTO_CHECK_PRE_PAY = 1210; //自动审核情况下 扫码点菜
        public static final int TYPE_AUTO_CHECK = 1211; //自动审核情况下，扫码加菜
        public static final int TYPE_PREPAY_SCAN_DESK = 1221; //扫码点菜,云收银预付款支付消息，收到的通知消息:扫码下单，｛数量｝个菜，已支付{支付金额}元，是否付清。
        public static final int TYPE_PREPAY_DOUBLE_UNIT = 123; //双单位预付款非自动审核消息
        /**
         * 外卖单消息
         * 只有 type = 501 需要手动审核，其他类型都“我知道了”
         */
        public static final int TYPE_TAKEOUT_XIAOER_MANUAL_CHECK = 501; //小二外卖手动审核消息
        public static final int TYPE_TAKEOUT_THIRD_MIXTURE = 401; //混合模式模式下，通知消息(第三方外卖)
        public static final int TYPE_TAKEOUT_THIRD_INDEPENDENT = 1229; //外卖下单自动审核通知消息----独立模式下第三方外卖通知消息，小二自动审核通知消息
        public static final int TYPE_TAKEOUT_ORDER_CANCLE_INDEPENDENT = 1235; //外卖撤单——独立使用云收银
        public static final int TYPE_TAKEOUT_ORDER_REMINDER = 1052; //外卖催单
        public static final int TYPE_TAKEOUT_DELIVERY_REFRESH = 1231; //配送更新（1.待接单2.待取货..等）
    }

    /**
     * 消息处理状态（对应服务端返回的状态）
     */
    public static class MsgState {
        public static final int STATE_UN_HANDLE = 0; //未处理
        public static final int STATE_HANDLING = 1; //处理中
        public static final int STATE_HANDLED_SUCCESS = 2; //处理成功
        public static final int STATE_TIMEOUT = 3; //已超时
        public static final int STATE_HANDLE_FAIL = 4; //处理失败
        public static final int STATE_HANDLE_REJECTED = 5; //已拒绝
    }
}
