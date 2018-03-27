package com.zmsoft.ccd.lib.bean.message;

/**
 * @author DangGui
 * @create 2017/8/29.
 */

public class TakeoutMsgDetailRequest {
    private String orderId;
    private String entityId;
    private String msgId;

    public TakeoutMsgDetailRequest(String orderId, String entityId, String msgId) {
        this.orderId = orderId;
        this.entityId = entityId;
        this.msgId = msgId;
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
}
