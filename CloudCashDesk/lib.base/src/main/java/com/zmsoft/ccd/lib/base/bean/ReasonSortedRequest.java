package com.zmsoft.ccd.lib.base.bean;

/**
 * @author DangGui
 * @create 2017/6/29.
 */

public class ReasonSortedRequest {
    private String entityId;
    private String dicCode;
    private int systemType;
    private String orderBy;

    public ReasonSortedRequest(String entityId, String dicCode, int systemType, String orderBy) {
        this.entityId = entityId;
        this.dicCode = dicCode;
        this.systemType = systemType;
        this.orderBy = orderBy;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public int getSystemType() {
        return systemType;
    }

    public void setSystemType(int systemType) {
        this.systemType = systemType;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
