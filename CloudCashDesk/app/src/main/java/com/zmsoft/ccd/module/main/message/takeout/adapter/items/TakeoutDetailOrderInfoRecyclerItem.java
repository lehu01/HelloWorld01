package com.zmsoft.ccd.module.main.message.takeout.adapter.items;

/**
 * 桌位消息详情——订单信息
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class TakeoutDetailOrderInfoRecyclerItem {
    private short orderFrom;
    /**
     * 订单来源 eg: 美团
     */
    private String source;
    /**
     * 外部订单号(比如美团自己的订单号)
     */
    private String outId;
    private short reserveStatus;
    /**
     * 送货方式 eg: “送货上门”
     */
    private String deliveryMethod;
    /**
     * 配送方式
     */
    private short sendType;

    /**
     * 订单状态 eg: 待审核、已同意
     */
    private int status;
    /**
     * 送达时间(立即送出/今日17:30)
     */
    private String sendTime;
    /**
     * 收货人
     */
    private String receiverName;
    /**
     * 收货人电话
     */
    private String receiverPhone;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 距离
     */
    private String distance;
    /**
     * 用餐人数
     */
    private int peopleCount;
    /**
     * 商品总件数
     */
    private int foodsTotalCount;
    /**
     * 备注
     */
    private String remark;

    //收货地址经度
    private double longitude;

    //收货地址纬度
    private double latitude;

    public short getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(short orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public short getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(short reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public int getFoodsTotalCount() {
        return foodsTotalCount;
    }

    public void setFoodsTotalCount(int foodsTotalCount) {
        this.foodsTotalCount = foodsTotalCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public short getSendType() {
        return sendType;
    }

    public void setSendType(short sendType) {
        this.sendType = sendType;
    }
}
