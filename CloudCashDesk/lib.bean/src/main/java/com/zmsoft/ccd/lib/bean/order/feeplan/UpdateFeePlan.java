package com.zmsoft.ccd.lib.bean.order.feeplan;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/12 10:39
 */
public class UpdateFeePlan extends Base {

    private String orderId; // 订单id

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
