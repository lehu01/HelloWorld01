package com.zmsoft.ccd.lib.bean.order;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：订单列表数据[交易中心]
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2016/12/19 10:37
 */
public class Order extends Base {

    public static final int TYPE_ORDER = 0;

    private int type; // item显示类型
    private String orderId; // 订单id
    private String customerId; // 用户ID
    private String seatCode; // 座位号
    private double totalPrice; // 原价
    private double realPrice; //　 实价
    private int orderFrom; // 订单来源
    private double outFee; //  外送费
    private int status; // 订单状态
    private int payStatus; // 支付状态
    private String outId; // 外部订单号
    private String name; // 联系人
    private String mobile; // 联系人电话
    private String address; // 外卖地址
    private long reserveDate; // 预定时间
    private int peopleCount; // 就餐人数
    private int code; // 单号
    private String globalCode; // 全码
    private String memo; // 备注
    private long createTime; // 创建时间
    private long openTime; // 开单时间
    private long opTime; // 操作时间
    private long endTime; // 结账时间
    private String entityId; // 店铺id
    private String areaId; // 区域id
    private String cashier; //收银员姓名
    private String innerCode; //订单流水号

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(double realPrice) {
        this.realPrice = realPrice;
    }

    public int getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(int orderFrom) {
        this.orderFrom = orderFrom;
    }

    public double getOutFee() {
        return outFee;
    }

    public void setOutFee(double outFee) {
        this.outFee = outFee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(long reserveDate) {
        this.reserveDate = reserveDate;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }
}
