package com.zmsoft.ccd.lib.bean.order.detail;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：订单详情数据源 - 订单
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/21 14:11
 */
public class OrderVo extends Base {

    private int peopleCount; // 就餐人数
    private int code; // 订单号
    private String seatCode; // 座位编码
    private long openTime; // 开单时间
    private long endTime; // 结账时间
    private String memo; // 备注
    private int status; // 订单状态
    private int orderFrom; // 订单来源
    private String id; // 订单id
    private short isWait;  // 是否待菜
    private String feePlanId; // 服务费方案id
    private String areaId; // 区域id

    private String innerCode; // 订单流水号

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

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(int orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getIsWait() {
        return isWait;
    }

    public void setIsWait(short isWait) {
        this.isWait = isWait;
    }

    public String getFeePlanId() {
        return feePlanId;
    }

    public void setFeePlanId(String feePlanId) {
        this.feePlanId = feePlanId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }
}
