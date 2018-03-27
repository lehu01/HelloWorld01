package com.zmsoft.ccd.module.receipt.receipt.model;

/**
 * 云收银收款 参数
 *
 * @author DangGui
 * @create 2017/6/13.
 */

public class BaseCloudCashCollectPayRequest {
    private String customerRegisterId;
    private String orderId;
    private String entityId;
    private String opUserId;
    private boolean notCheckPromotion;

    public BaseCloudCashCollectPayRequest(String customerRegisterId, String orderId, String entityId, String opUserId) {
        this.customerRegisterId = customerRegisterId;
        this.orderId = orderId;
        this.entityId = entityId;
        this.opUserId = opUserId;
    }

    public BaseCloudCashCollectPayRequest(String customerRegisterId, String orderId, String entityId, String opUserId, boolean notCheckPromotion) {
        this.customerRegisterId = customerRegisterId;
        this.orderId = orderId;
        this.entityId = entityId;
        this.opUserId = opUserId;
        this.notCheckPromotion = notCheckPromotion;
    }

    public boolean isNotCheckPromotion() {
        return notCheckPromotion;
    }

    public void setNotCheckPromotion(boolean notCheckPromotion) {
        this.notCheckPromotion = notCheckPromotion;
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

    public String getCustomerRegisterId() {
        return customerRegisterId;
    }

    public void setCustomerRegisterId(String customerRegisterId) {
        this.customerRegisterId = customerRegisterId;
    }
}
