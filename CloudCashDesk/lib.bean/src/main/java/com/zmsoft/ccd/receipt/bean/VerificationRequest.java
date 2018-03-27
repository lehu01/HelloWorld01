package com.zmsoft.ccd.receipt.bean;

/**
 * @author DangGui
 * @create 2017/8/3.
 */

public class VerificationRequest {
    private String entityId;
    /**
     * 优惠券code
     */
    private String promotionCode;
    private String orderId;
    private String opUserId;
    /**
     * 操作来源，0表示云收银，1表示本地收银
     */
    private short from;

    public VerificationRequest(String entityId, String promotionCode, String orderId, String opUserId, short from) {
        this.entityId = entityId;
        this.promotionCode = promotionCode;
        this.orderId = orderId;
        this.opUserId = opUserId;
        this.from = from;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public short getFrom() {
        return from;
    }

    public void setFrom(short from) {
        this.from = from;
    }
}
