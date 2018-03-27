package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/9/5.
 */

public class DeliverTakeoutRequest {
    private List<String> orderIds;
    private short type;
    private String platformCode;
    private String carrierName;
    private String carrierPhone;
    private String entityId;
    private String opUserId;
    private String expressCode;

    public DeliverTakeoutRequest(List<String> orderIds, short type, String platformCode, String carrierName
            , String carrierPhone, String entityId, String opUserId, String expressCode) {
        this.orderIds = orderIds;
        this.type = type;
        this.platformCode = platformCode;
        this.carrierName = carrierName;
        this.carrierPhone = carrierPhone;
        this.entityId = entityId;
        this.opUserId = opUserId;
        this.expressCode = expressCode;
    }

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierPhone() {
        return carrierPhone;
    }

    public void setCarrierPhone(String carrierPhone) {
        this.carrierPhone = carrierPhone;
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
