package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/11/17.
 */

public class GetDeliveryTypeRequest {
    private String entityId;
    private String opUserId;
    private String opUserName;
    private List<String> orderIds;

    public GetDeliveryTypeRequest(String entityId, String opUserId, String opUserName, List<String> orderIds) {
        this.entityId = entityId;
        this.opUserId = opUserId;
        this.opUserName = opUserName;
        this.orderIds = orderIds;
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

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }
}
