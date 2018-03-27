package com.zmsoft.ccd.module.receipt.receipt.model;

/**
 * @author DangGui
 * @create 2017/6/20.
 */

public class GetPayStatusRequest {
    private String snapshotId;
    private String entityId;
    private String outTradeNo;

    public GetPayStatusRequest(String snapshotId, String entityId, String outTradeNo) {
        this.snapshotId = snapshotId;
        this.entityId = entityId;
        this.outTradeNo = outTradeNo;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
