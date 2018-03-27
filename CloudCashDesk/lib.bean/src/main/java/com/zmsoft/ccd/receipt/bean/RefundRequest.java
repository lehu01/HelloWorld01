package com.zmsoft.ccd.receipt.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 云收银退款接口 参数
 *
 * @author DangGui
 * @create 2017/6/13.
 */

public class RefundRequest implements Serializable {
    private String orderId;
    private String entityId;
    private String opUserId;
    private List<Refund> refunds;
    private boolean checkLimit;

    public RefundRequest(String orderId, String entityId, String opUserId, List<Refund> refunds, boolean checkLimit) {
        this.orderId = orderId;
        this.entityId = entityId;
        this.opUserId = opUserId;
        this.refunds = refunds;
        this.checkLimit = checkLimit;
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

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public boolean isCheckLimit() {
        return checkLimit;
    }

    public void setCheckLimit(boolean checkLimit) {
        this.checkLimit = checkLimit;
    }
}
