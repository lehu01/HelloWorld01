package com.zmsoft.ccd.receipt.bean;

/**
 * @author DangGui
 * @create 2017/8/3.
 */

public class OrderPayListRequest {
    private String orderId;
    private String entityId;
    private String opUserId;

    public OrderPayListRequest(String orderId, String entityId, String opUserId) {
        this.orderId = orderId;
        this.entityId = entityId;
        this.opUserId = opUserId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }
}
