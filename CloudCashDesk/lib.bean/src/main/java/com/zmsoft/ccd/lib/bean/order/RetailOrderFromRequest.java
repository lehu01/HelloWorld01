package com.zmsoft.ccd.lib.bean.order;

/**
 * Created by huaixi on 2017/10/31.
 */

public class RetailOrderFromRequest {

    String entityId;
    String opUserId;
    String opUserName;

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
}
