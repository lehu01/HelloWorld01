package com.zmsoft.ccd.lib.bean.order;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/27 19:15.
 */

public interface OrderConstants {
    // 订单状态
    interface OrderStatus {
        String ORDER_STATUS_NOT_PAID = "NOT_PAID"; // 未支付 1
        String ORDER_STATUS_FULL_PAID = "FULL_PAID"; // 已付清 2
        String ORDER_STATUS_PARTIAL_PAID = "PARTIAL_PAID"; // 部分支付 3
    }

    // 订单类型
    interface OrderType {
        String ORDER_TYPE_BY_TAKEOUT = "TAKEOUT"; // 外卖
        String ORDER_TYPE_BY_SEAT = "DINING"; // 座位
        String ORDER_TYPE_BY_RETAIL = "RETAIL"; // 零售
    }

    //订单类型
    interface OrderKind {
        short ORDER_CATERING = 1;  //餐饮
        short ORDER_RETAIL = 7;  //零售
    }

    //订单来源
    interface OrderFrom {
        short ALL = -1;         //全部
        short YUNSHOUYIN = 60;  //云收银
        short WEIDIAN = 70;     //微店
        short BAIDU = 100;      //百度
        short MEITUAN = 101;    //美团
        short ELEME = 102;      //饿了么
    }
}
