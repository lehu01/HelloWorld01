package com.zmsoft.ccd.lib.bean.order.op;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/14 14:32
 */
public class ChangeOrderByTrade extends Base {

    private String orderId; // 改单新orderId

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
