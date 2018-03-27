package com.zmsoft.ccd.module.menu.menu.bean;

/**
 * Created by huaixi on 2017/11/9.
 */

public class MenuRetailQuery {

    String entityId;  //菜编码
    String code;      //菜编码
    Integer pageSize; //页面大小

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
