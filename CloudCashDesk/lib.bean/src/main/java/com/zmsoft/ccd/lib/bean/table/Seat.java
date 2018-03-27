package com.zmsoft.ccd.lib.bean.table;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/16 15:11
 */
public class Seat extends Base {

    public static final int TYPE_SEAT = 0;

    private int type; // 类型
    private int adviseNum; // 推荐人数
    private int seatKind; // 座位类型
    private String seatCode; // 桌位编码
    private int status; // 桌位状态
    private int peopleCount; // 就餐人数
    private int orderNum; // 订单编号
    private String orderId; // 订单id
    private long openTime; // 开单时间
    private int isLimitTime; // 是否限时用餐
    private int payStatus; // 支付状态
    private String entityId; // 店铺id
    private String seatName; // 桌位名称
    private int overTime; // 是否用餐超时
    private int printCount; // 打印客户联次数
    private String areaId; // 区域id

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrintCount() {
        return printCount;
    }

    public void setPrintCount(int printCount) {
        this.printCount = printCount;
    }

    public int getAdviseNum() {
        return adviseNum;
    }

    public void setAdviseNum(int adviseNum) {
        this.adviseNum = adviseNum;
    }

    public int getSeatKind() {
        return seatKind;
    }

    public void setSeatKind(int seatKind) {
        this.seatKind = seatKind;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public int getIsLimitTime() {
        return isLimitTime;
    }

    public void setIsLimitTime(int isLimitTime) {
        this.isLimitTime = isLimitTime;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public int getOverTime() {
        return overTime;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
