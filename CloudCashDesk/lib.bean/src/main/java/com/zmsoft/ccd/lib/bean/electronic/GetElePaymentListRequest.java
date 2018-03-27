package com.zmsoft.ccd.lib.bean.electronic;

/**
 * 电子收款明细列表 请求参数
 *
 * @author DangGui
 * @create 2017/8/19.
 */

public class GetElePaymentListRequest {
    private int pageIndex;
    private int pageSize;
    private String entityId;
    private String opUserId;

    public GetElePaymentListRequest(int pageIndex, int pageSize, String entityId, String opUserId) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.entityId = entityId;
        this.opUserId = opUserId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
