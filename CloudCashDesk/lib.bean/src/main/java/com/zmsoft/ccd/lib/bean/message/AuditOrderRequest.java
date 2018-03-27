package com.zmsoft.ccd.lib.bean.message;

import java.io.Serializable;

/**
 * @author DangGui
 * @create 2017/4/25.
 */

public class AuditOrderRequest implements Serializable {
    private String entityId;
    private boolean pass;
    private String messageId;
    private String opUserId;

    public AuditOrderRequest(String entityId, boolean pass, String messageId, String opUserId) {
        this.entityId = entityId;
        this.pass = pass;
        this.messageId = messageId;
        this.opUserId = opUserId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }
}
