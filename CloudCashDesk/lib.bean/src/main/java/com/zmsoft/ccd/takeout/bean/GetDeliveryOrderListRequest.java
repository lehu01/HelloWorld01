package com.zmsoft.ccd.takeout.bean;

/**
 * @author DangGui
 * @create 2017/9/1.
 */

public class GetDeliveryOrderListRequest {
    private String entityId;
    private int pageSize;
    private int pageIndex;

    public GetDeliveryOrderListRequest(String entityId, int pageSize, int pageIndex) {
        this.entityId = entityId;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
