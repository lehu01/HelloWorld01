package com.zmsoft.ccd.receipt.bean;

import java.io.Serializable;

/**
 * 云收银获取账单 参数
 *
 * @author DangGui
 * @create 2017/6/13.
 */

public class GetCloudCashBillRequest implements Serializable {
    private String orderId;
    private String entityId;
    private String opUserId;
    /**
     * 支付平台，0表示通用，1表示通联
     */
    private short platform;

    public GetCloudCashBillRequest(String orderId, String entityId, String opUserId) {
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

    public short getPlatform() {
        return platform;
    }

    public void setPlatform(short platform) {
        this.platform = platform;
    }
}
