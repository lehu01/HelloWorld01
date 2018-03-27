package com.zmsoft.ccd.lib.bean.order.create;

import java.io.Serializable;

/**
 * 开单的参数：传递给点菜界面
 *
 * @email：danshen@2dfire.com
 * @time : 2017/4/17 10:15
 */
public class OrderParam implements Serializable {

    private String seatName; // 座位名称
    private String seatCode; // 座位code
    private int number; // 就餐人数
    private int originNumber; // 原来就餐人数
    private String memo; // 备注
    private boolean isWait; // 是否待菜
    private String orderId; // 订单id：加菜的时候才有
    private long modifyTime; // 修改时间
    private String originSeatCode; // 原座位code
    private boolean isMustMenu; // 是否有必选菜

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOriginSeatCode() {
        return originSeatCode;
    }

    public void setOriginSeatCode(String originSeatCode) {
        this.originSeatCode = originSeatCode;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isWait() {
        return isWait;
    }

    public void setWait(boolean wait) {
        isWait = wait;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isMustMenu() {
        return isMustMenu;
    }

    public void setMustMenu(boolean mustMenu) {
        isMustMenu = mustMenu;
    }

    public int getOriginNumber() {
        return originNumber;
    }

    public void setOriginNumber(int originNumber) {
        this.originNumber = originNumber;
    }
}
