package com.zmsoft.ccd.lib.bean.pay;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/20 10:42
 */
public class Pay extends Base {

    private String customerRegisterId; // 会员id
    private String customerName; // 会员姓名
    private int payType; // 支付类型。1表示支付宝，2表示块钱，3表示会员卡，4表示银联，6
    private double payAmount; // 支付金额
    private String payNo; // 支付流水号
    private long payTime; // 支付时间
    private String kindPayName; // 支付方式名称
    private String cardId; // 会员卡id
    private String code; // 会员卡号
    private String id; // 订单id
    private boolean isEndPay; // 是否结账完毕：本地
    private boolean isTakeOutOrder; // 是否外卖单：本地

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getKindPayName() {
        return kindPayName;
    }

    public void setKindPayName(String kindPayName) {
        this.kindPayName = kindPayName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isEndPay() {
        return isEndPay;
    }

    public void setEndPay(boolean endPay) {
        isEndPay = endPay;
    }

    public boolean isTakeOutOrder() {
        return isTakeOutOrder;
    }

    public void setTakeOutOrder(boolean takeOutOrder) {
        isTakeOutOrder = takeOutOrder;
    }
}
