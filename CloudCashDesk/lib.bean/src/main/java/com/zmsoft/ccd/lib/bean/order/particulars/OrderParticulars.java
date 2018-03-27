package com.zmsoft.ccd.lib.bean.order.particulars;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 14:32.
 */

public class OrderParticulars extends Base {
    private String orderId;                 // 订单id,999325945f47220f015f473832055907
    private int code;                       // 订单编号,10001
    private short orderFrom;                // 外卖时表示订单来源，112表示小二外卖，100百度外卖，101表示美团外卖，102表示饿了吗外卖
    private String seatCode;                // 座位编号（可以为null）
    private String seatName;                // 桌位名称
    private String orderNum;                // 订单流水号
    private long endTime;                   // 订单结账时间
    private double fee;                     // 订单的应收金额

    public String getOrderId() {
        return orderId;
    }

    public int getCode() {
        return code;
    }

    public short getOrderFrom() {
        return orderFrom;
    }

    public String getSeatName() {
        return seatName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public long getEndTime() {
        return endTime;
    }

    public double getFee() {
        return fee;
    }
}
