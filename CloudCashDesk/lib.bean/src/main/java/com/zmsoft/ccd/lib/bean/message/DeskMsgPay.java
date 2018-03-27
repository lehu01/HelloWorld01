package com.zmsoft.ccd.lib.bean.message;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/20 10:42
 */
public class DeskMsgPay extends Base {

    private String customerRegisterId; // 会员id
    private String customerName; // 会员姓名
    /**
     * 支付类型。1表示支付宝，2表示块钱，3表示会员卡，4表示银联，6
     */
    private short type;
    /**
     * 支付流水号
     */
    private String code;
    /**
     * 支付金额
     */
    private double fee;
    /**
     * 付款类型
     */
    private String kindPayId;
    /**
     * 支付时间
     */
    private long payTime;
    /**
     * 收银员
     */
    private String operator;

    /**
     * 现收金额
     */
    private double pay;
    /**
     * 找零
     */
    private double charge;
    /**
     * 相关付款信息
     */
    private String totalPayId;
    /**
     * 会员卡ID
     */
    private String cardId;
    /**
     * 处理标识(对挂账收款的支持)
     */
    private Short isDealed;
    /**
     * 会员卡系统Id
     */
    private String cardEntityId;
    /**
     * 在线支付订单Id
     */
    private String onlineBillId;

    /**
     * 网上支付相关Id
     */
    private String waitingPayId;
    /**
     * type_name对应的字段
     */
    public String typeName;

    /**
     * 代金券面额
     */
    public double couponFee;

    /**
     * 代金券实际购买金额
     */
    public double couponCost;
    /**
     * 代金券使用张数
     */
    public int couponNum;

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public String getCustomerRegisterId() {
        return customerRegisterId;
    }

    public void setCustomerRegisterId(String customerRegisterId) {
        this.customerRegisterId = customerRegisterId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getKindPayId() {
        return kindPayId;
    }

    public void setKindPayId(String kindPayId) {
        this.kindPayId = kindPayId;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public String getTotalPayId() {
        return totalPayId;
    }

    public void setTotalPayId(String totalPayId) {
        this.totalPayId = totalPayId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Short getIsDealed() {
        return isDealed;
    }

    public void setIsDealed(Short isDealed) {
        this.isDealed = isDealed;
    }

    public String getCardEntityId() {
        return cardEntityId;
    }

    public void setCardEntityId(String cardEntityId) {
        this.cardEntityId = cardEntityId;
    }

    public String getOnlineBillId() {
        return onlineBillId;
    }

    public void setOnlineBillId(String onlineBillId) {
        this.onlineBillId = onlineBillId;
    }

    public String getWaitingPayId() {
        return waitingPayId;
    }

    public void setWaitingPayId(String waitingPayId) {
        this.waitingPayId = waitingPayId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(Double couponFee) {
        this.couponFee = couponFee;
    }

    public Double getCouponCost() {
        return couponCost;
    }

    public void setCouponCost(Double couponCost) {
        this.couponCost = couponCost;
    }

    public Integer getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }
}
