package com.zmsoft.ccd.module.receipt.receipt.model;

/**
 * @author DangGui
 * @create 2017/6/19.
 */

public class GetKindDetailInfoRequest {
    private String kindPayId;
    private String entityId;
    private String name;
    private int mode;

    public GetKindDetailInfoRequest(String kindPayId, String entityId, String name, int mode) {
        this.kindPayId = kindPayId;
        this.entityId = entityId;
        this.name = name;
        this.mode = mode;
    }

    public String getKindPayId() {
        return kindPayId;
    }

    public void setKindPayId(String kindPayId) {
        this.kindPayId = kindPayId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
