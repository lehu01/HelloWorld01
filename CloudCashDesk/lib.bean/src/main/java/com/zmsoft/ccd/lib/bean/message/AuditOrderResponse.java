package com.zmsoft.ccd.lib.bean.message;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/6/22.
 */

public class AuditOrderResponse {
    /**
     * 本次修改时间
     */
    private long modifyTime;
    /**
     * 菜id列表
     */
    private List<String> instanceIds;

    private String orderId;

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<String> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<String> instanceIds) {
        this.instanceIds = instanceIds;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
