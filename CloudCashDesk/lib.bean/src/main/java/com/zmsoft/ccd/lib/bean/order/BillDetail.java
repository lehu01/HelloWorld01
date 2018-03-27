package com.zmsoft.ccd.lib.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huaixi on 2017/11/1.
 */

public class BillDetail implements Parcelable {

    String orderId;  //订单id
    int code;        //订单编号
    short orderFrom; //外卖时表示订单来源
    String seatCode; //座位编号（可以为null）
    String orderNum; //订单流水号
    long endTime;    //订单结账时间
    double fee;      //订单的应收金额（单位元）
    double realFee;  //订单实收金额（单位元）
    String cashier;  //收银员姓名

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public short getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(short orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getRealFee() {
        return realFee;
    }

    public void setRealFee(double realFee) {
        this.realFee = realFee;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeInt(this.code);
        dest.writeInt(this.orderFrom);
        dest.writeString(this.seatCode);
        dest.writeString(this.orderNum);
        dest.writeLong(this.endTime);
        dest.writeDouble(this.fee);
        dest.writeDouble(this.realFee);
        dest.writeString(this.cashier);
    }

    protected BillDetail(Parcel in) {
        this.orderId = in.readString();
        this.code = in.readInt();
        this.orderFrom = (short) in.readInt();
        this.seatCode = in.readString();
        this.orderNum = in.readString();
        this.endTime = in.readLong();
        this.fee = in.readDouble();
        this.realFee = in.readDouble();
        this.cashier = in.readString();
    }

    public static final Creator<BillDetail> CREATOR = new Creator<BillDetail>() {
        @Override
        public BillDetail createFromParcel(Parcel source) {
            return new BillDetail(source);
        }

        @Override
        public BillDetail[] newArray(int size) {
            return new BillDetail[size];
        }
    };
}
