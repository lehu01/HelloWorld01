package com.zmsoft.ccd.module.receipt.receipt.model;

import com.zmsoft.ccd.receipt.bean.Promotion;

import java.util.List;

/**
 * 账单优惠（整单打折，卡优惠）
 *
 * @author DangGui
 * @create 2017/6/19.
 */

public class CashPromotionBillRequest {
    private String orderId;
    private String entityId;
    private String opUserId;
    private List<Promotion> promotions;

    public CashPromotionBillRequest(String orderId, String entityId, String opUserId, List<Promotion> promotions) {
        this.orderId = orderId;
        this.entityId = entityId;
        this.opUserId = opUserId;
        this.promotions = promotions;
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

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
