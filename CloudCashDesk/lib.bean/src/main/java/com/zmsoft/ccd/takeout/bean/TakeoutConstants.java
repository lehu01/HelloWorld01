package com.zmsoft.ccd.takeout.bean;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public interface TakeoutConstants {

    String ORDER_TYPE = "type";
    String DATE_TYPE = "dateType";
    String ORDER_SOURCE = "source";
    String CURSOR_MARK = "*";

    int PAGE_COUNT = 10;


    interface OrderStatus {

        /**
         * 未下厨
         */
        int UN_COOK = 11;

        /**
         * 待配送
         */
        int WAITING_DISPATCH = 0;

        /**
         * 等候配送
         */
        int WAITING_DELIVERY = 4;

        /**
         * 配送中
         */
        int DELIVERING = 1;

        /**
         * 已送达
         */
        int ARRIVED = 2;

        /**
         * 配送异常
         */
        int DELIVERY_EXCEPTION = 6;

    }


    interface ReserveStatus {
        /**
         * 全部
         */
        int ALL = -1;
        /**
         * 立即送货
         */
        int DELIVERY_IMMEDIATELY = 0;
        /**
         * 预约指定时间送货
         */
        int APPOINTMENT = 1;
    }

    interface OrderFrom {
        /**
         * 全部
         */
        int ALL = -1;
        /**
         * 小二外卖
         */
        int XIAOER = 112;
        /**
         * 美团外卖
         */
        int MEITUAN = 101;

        /**
         * 百度外卖
         */
        int BAIDU = 100;
        /**
         * 饿了么
         */
        int ERLEME = 102;
        /**
         * 微店
         */
        int WEIDIAN = 70;
    }

    interface SendType {
        /**
         * 商家配送
         */
        int MERCHANT = 0;
        /**
         * 第三方配送
         */
        int THIRD_PARTY = 1;
        /**
         * 自提
         */
        int SELF_TAKE = 2;

    }


    /**
     * 下单到厨房 - 1
     * 配送 - 2
     * 取消配送 - 3
     * 已送达 - 4
     * 已自取 - 5
     * 结账完毕 - 6
     */
    interface OperationType {

        /**
         * 下单到厨房
         */
        short ORDER_TO_KITCHEN = 1;
        /**
         * 配送
         */
        short ORDER_DISPATCH = 2;
        /**
         * 取消配送
         */
        short ORDER_CANCEL_DISPATCH = 3;
        /**
         * 已送达
         */
        short ORDER_ARRIVED = 4;
        /**
         * 已自取
         */
        short ORDER_SELF_TAKE = 5;
        /**
         * 结账完毕
         */
        short ORDER_CHECK_OUT = 6;
    }

    interface DateType {
        /**
         * 所有
         */
        short ALL = -1;
        /**
         * 今天
         */
        short TODAY = 0;
        /**
         * 昨天
         */
        short YESTERDAY = 1;
        /**
         * 自定义
         */
        short CUSTOM = 2;
    }

    interface DeliveryPlatformCode {
        /**
         * 自配送
         */
        String SELF_DELIVERY = "P000";
        /**
         * 顺丰配送
         */
        String SHUNFENG_DELIVERY = "P001";
    }


    interface TakeoutErrorCode {

        /**
         * 结账完毕失败，支付金额小于应付金额
         */
        String ACTUAL_PAY_LESS_NEED_PAY = "20023";
        /**
         * 支付金额大于应付金额
         */
        String ACTUAL_PAY_GREATER_NEED_PAY = "20022";

    }
}
