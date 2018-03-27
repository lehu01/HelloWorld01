package com.zmsoft.ccd.module.takeout.delivery.adapter.items;

/**
 * @author DangGui
 * @create 2017/9/1.
 */

public class DeliverySelectAllItem {
    /**
     * 选中的ITEM总数
     */
    private int totalNum;
    /**
     * 是否全选
     */
    private boolean checkAll;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public boolean isCheckAll() {
        return checkAll;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }
}
