package com.zmsoft.ccd.lib.bean.electronic;

/**
 * 收款明细凭证
 *
 * @author DangGui
 * @create 2017/8/24.
 */

public class ElePaymentDetail {
    private String orderId;
    /**
     * 单号
     */
    private String orderNo;
    private String seatCode;
    /**
     * 支付编号
     */
    private String payCode;
    /**
     * 支付类型
     */
    private short payType;
    private String receiptName;
    private String time;
    /**
     * 支付状态0表示支付成功，1表示退款成功
     */
    private int payStatus;
    /**
     * 支付金额
     */
    private String payFee;
    /**
     * 会员姓名
     */
    private String memberName;
    /**
     * 如果是会员卡支付表示会员卡号
     */
    private String cardNo;
    /**
     * 是否能够退款
     */
    private boolean canRefund;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public short getPayType() {
        return payType;
    }

    public void setPayType(short payType) {
        this.payType = payType;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayFee() {
        return payFee;
    }

    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public boolean isCanRefund() {
        return canRefund;
    }

    public void setCanRefund(boolean canRefund) {
        this.canRefund = canRefund;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
