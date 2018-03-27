package com.zmsoft.ccd.lib.bean.electronic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 电子支付对象
 *
 * @author DangGui
 * @create 2017/8/19.
 */

public class ElePayment implements Parcelable {
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
    }

    public ElePayment() {
    }

    protected ElePayment(Parcel in) {
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
    }

    public static final Parcelable.Creator<ElePayment> CREATOR = new Parcelable.Creator<ElePayment>() {
        @Override
        public ElePayment createFromParcel(Parcel source) {
            return new ElePayment(source);
        }

        @Override
        public ElePayment[] newArray(int size) {
            return new ElePayment[size];
        }
    };
}
