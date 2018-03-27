package com.zmsoft.ccd.lib.bean.shortcutreceipt;

/**
 * @author DangGui
 * @create 2017/8/3.
 */

public class ShortCutReceiptRequest {
    /**
     * 快捷收款金额(单位为分)
     */
    private int fee;

    private String entityId;

    private String opUserId;

    public ShortCutReceiptRequest(int fee, String entityId, String opUserId) {
        this.fee = fee;
        this.entityId = entityId;
        this.opUserId = opUserId;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
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
