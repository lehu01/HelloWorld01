package com.zmsoft.ccd.module.takeout.delivery.adapter.items;

/**
 * @author DangGui
 * @create 2017/9/1.
 */

public class DeliveryOrderItem {
    private String userName;
    private String userPhone;
    private String userAddress;
    private short orderFromType;
    private String orderFrom;
    /**
     * 外部订单号(比如美团自己的订单号)
     */
    private String outId;
    /**
     * 共计N件
     */
    private String foodsTotalNum;
    /**
     * 所有商品名称拼接的字符串
     */
    private String foodsName;
    /**
     * 订单CODE
     */
    private String orderCode;
    /**
     * 配送方式
     */
    private String deliveryMethod;

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

    public short getOrderFromType() {
        return orderFromType;
    }

    public void setOrderFromType(short orderFromType) {
        this.orderFromType = orderFromType;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
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

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getFoodsTotalNum() {
        return foodsTotalNum;
    }

    public void setFoodsTotalNum(String foodsTotalNum) {
        this.foodsTotalNum = foodsTotalNum;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }
}
