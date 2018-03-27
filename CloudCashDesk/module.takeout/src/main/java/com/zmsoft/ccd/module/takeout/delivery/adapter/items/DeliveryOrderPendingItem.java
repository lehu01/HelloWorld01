package com.zmsoft.ccd.module.takeout.delivery.adapter.items;

/**
 * @author DangGui
 * @create 2017/9/1.
 */

public class DeliveryOrderPendingItem {
    private String userName;
    private String userPhone;
    /**
     * 所有商品名称拼接的字符串
     */
    private String foodsName;
    /**
     * 共计N件
     */
    private String foodsTotalNum;
    /**
     * 订单CODE
     */
    private String orderCode;
    /**
     * ITEM当前是否被选中
     */
    private boolean checked;
    private String orderId;
    private String address;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getFoodsName() {
        return foodsName;
    }

    public void setFoodsName(String foodsName) {
        this.foodsName = foodsName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getFoodsTotalNum() {
        return foodsTotalNum;
    }

    public void setFoodsTotalNum(String foodsTotalNum) {
        this.foodsTotalNum = foodsTotalNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
