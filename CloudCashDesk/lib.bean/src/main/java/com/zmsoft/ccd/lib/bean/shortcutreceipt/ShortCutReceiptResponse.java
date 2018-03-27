package com.zmsoft.ccd.lib.bean.shortcutreceipt;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/8/3.
 */

public class ShortCutReceiptResponse {
    private String orderId;
    /**
     * 菜id列表
     */
    private List<String> instanceIds;
    /**
     * 订单最后修改时间
     */
    private long modifyTime;

    /**
     * 开单数量限制，超过50单，给予提示
     */
    private boolean numOutOfLimit;
    /**
     * 开单数量限制，超过50单，给予提示
     */
    private String numOutOfLimitTip;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<String> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<String> instanceIds) {
        this.instanceIds = instanceIds;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public boolean isNumOutOfLimit() {
        return numOutOfLimit;
    }

    public void setNumOutOfLimit(boolean numOutOfLimit) {
        this.numOutOfLimit = numOutOfLimit;
    }

    public String getNumOutOfLimitTip() {
        return numOutOfLimitTip;
    }

    public void setNumOutOfLimitTip(String numOutOfLimitTip) {
        this.numOutOfLimitTip = numOutOfLimitTip;
    }
}
