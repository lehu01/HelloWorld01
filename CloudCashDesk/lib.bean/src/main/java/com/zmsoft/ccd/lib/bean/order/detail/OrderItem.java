package com.zmsoft.ccd.lib.bean.order.detail;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.order.detail.servicebill.ServiceBill;

/**
 * Description：本地拼装数据：支付信息+支付金额+订单来源等
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2016/12/20 14:03
 */
public class OrderItem extends Base {

    // 补充额外信息
    private int orderDetailStatus; // 订单状态（详情外层）必须结合两个做判断
    private String seatName; // 座位名称
    private boolean isUpdateServiceFee; // 否修改附加费
    private String innerCode; //订单流水号
    private int minuteSinceOpen; // 已开单时间（单位为分钟）
    private boolean limitTime; // 是否限时用餐
    private boolean overTime; // 限时用餐时，是否已经超时
    private int minuteToOverTime; //  限时用餐时，距离超时的时间(单位为分钟)
    private boolean isPayCount; // 是否有支付记录
    private boolean isEndPay; // 是否结账完毕
    // 信息
    private PromotionVo promotionVo; // 优惠明细
    private OrderVo orderVo; // 订单相关数据
    private ServiceBill serviceBill; // 付款信息

    public int getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(int orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public boolean isUpdateServiceFee() {
        return isUpdateServiceFee;
    }

    public void setUpdateServiceFee(boolean updateServiceFee) {
        isUpdateServiceFee = updateServiceFee;
    }

    public int getMinuteSinceOpen() {
        return minuteSinceOpen;
    }

    public void setMinuteSinceOpen(int minuteSinceOpen) {
        this.minuteSinceOpen = minuteSinceOpen;
    }

    public boolean isLimitTime() {
        return limitTime;
    }

    public void setLimitTime(boolean limitTime) {
        this.limitTime = limitTime;
    }

    public boolean isOverTime() {
        return overTime;
    }

    public void setOverTime(boolean overTime) {
        this.overTime = overTime;
    }

    public int getMinuteToOverTime() {
        return minuteToOverTime;
    }

    public void setMinuteToOverTime(int minuteToOverTime) {
        this.minuteToOverTime = minuteToOverTime;
    }

    public boolean isPayCount() {
        return isPayCount;
    }

    public void setPayCount(boolean payCount) {
        isPayCount = payCount;
    }

    public boolean isEndPay() {
        return isEndPay;
    }

    public void setEndPay(boolean endPay) {
        isEndPay = endPay;
    }

    public PromotionVo getPromotionVo() {
        return promotionVo;
    }

    public void setPromotionVo(PromotionVo promotionVo) {
        this.promotionVo = promotionVo;
    }

    public OrderVo getOrderVo() {
        return orderVo;
    }

    public void setOrderVo(OrderVo orderVo) {
        this.orderVo = orderVo;
    }

    public ServiceBill getServiceBill() {
        return serviceBill;
    }

    public void setServiceBill(ServiceBill serviceBill) {
        this.serviceBill = serviceBill;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }
}
