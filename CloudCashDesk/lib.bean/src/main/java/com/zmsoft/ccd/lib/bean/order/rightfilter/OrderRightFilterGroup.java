package com.zmsoft.ccd.lib.bean.order.rightfilter;

import java.util.List;

public class OrderRightFilterGroup {

    private int type;           // OrderRightFilterItem.Type
    private String groupName;
    private List<OrderRightFilterItem> itemList;

    public OrderRightFilterGroup() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setItemList(List<OrderRightFilterItem> itemList) {
        this.itemList = itemList;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<OrderRightFilterItem> getItemList() {
        return itemList;
    }
}
