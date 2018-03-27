package com.zmsoft.ccd.lib.bean.electronic;

/**
 * 电子收款明细凭证
 *
 * @author DangGui
 * @create 2017/8/24.
 */

public class GetElePaymentRequest {
    private String payId;
    private String entityId;
    private String opUserId;

    public GetElePaymentRequest(String payId, String entityId, String opUserId) {
        this.payId = payId;
        this.entityId = entityId;
        this.opUserId = opUserId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
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
