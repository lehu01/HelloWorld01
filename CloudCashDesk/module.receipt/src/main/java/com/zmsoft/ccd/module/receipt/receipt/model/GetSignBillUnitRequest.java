package com.zmsoft.ccd.module.receipt.receipt.model;

/**
 * @author DangGui
 * @create 2017/6/17.
 */

public class GetSignBillUnitRequest {
    private String kindPayId;
    private String entityId;
    private String keyword;
    private int pageIndex;
    private int pageSize;

    public GetSignBillUnitRequest(String kindPayId, String entityId, String keyword, int pageIndex, int pageSize) {
        this.kindPayId = kindPayId;
        this.entityId = entityId;
        this.keyword = keyword;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
}
