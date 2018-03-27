package com.zmsoft.ccd.takeout.bean;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/31.
 */

public class DeliveryInfoRequest extends BaseRequest {

    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
