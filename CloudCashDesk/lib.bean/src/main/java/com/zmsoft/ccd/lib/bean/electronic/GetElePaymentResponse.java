package com.zmsoft.ccd.lib.bean.electronic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 电子支付凭证
 *
 * @author DangGui
 * @create 2017/8/24.
 */

public class GetElePaymentResponse implements Parcelable {
    /**
     * 支付id即waitingPayId
     */
    private String payId;
    /**
     * 支付编号
     */
    private String code;
    /**
     * 订单id（waitingOrderId或者orderId）
     */
    private String orderId;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 座位编号
     */
    private String seatCode;
    /**
     * 支付金额
     */
    private int fee;
    /**
     * 支付类型
     */
    private short type;
    /**
     * 支付名称
     */
    private String name;
    /**
     * 支付状态0表示支付成功，1表示退款成功
     */
    private int payStatus;
    /**
     * 支付时间
     */
    private long payTime;

    private String memberName;

    private String mobile;

    private String cardNo;
    /**
     * 是否能够退款
     */
    private boolean canRefund;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public boolean isCanRefund() {
        return canRefund;
    }

    public void setCanRefund(boolean canRefund) {
        this.canRefund = canRefund;
    }

    public GetElePaymentResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payId);
        dest.writeString(this.code);
        dest.writeString(this.orderId);
        dest.writeString(this.orderCode);
        dest.writeString(this.seatCode);
        dest.writeInt(this.fee);
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeInt(this.payStatus);
        dest.writeLong(this.payTime);
        dest.writeString(this.memberName);
        dest.writeString(this.mobile);
        dest.writeString(this.cardNo);
        dest.writeByte(this.canRefund ? (byte) 1 : (byte) 0);
    }

    protected GetElePaymentResponse(Parcel in) {
        this.payId = in.readString();
        this.code = in.readString();
        this.orderId = in.readString();
        this.orderCode = in.readString();
        this.seatCode = in.readString();
        this.fee = in.readInt();
        this.type = (short) in.readInt();
        this.name = in.readString();
        this.payStatus = in.readInt();
        this.payTime = in.readLong();
        this.memberName = in.readString();
        this.mobile = in.readString();
        this.cardNo = in.readString();
        this.canRefund = in.readByte() != 0;
    }

    public static final Creator<GetElePaymentResponse> CREATOR = new Creator<GetElePaymentResponse>() {
        @Override
        public GetElePaymentResponse createFromParcel(Parcel source) {
            return new GetElePaymentResponse(source);
        }

        @Override
        public GetElePaymentResponse[] newArray(int size) {
            return new GetElePaymentResponse[size];
        }
    };
}
