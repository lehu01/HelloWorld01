package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author DangGui
 * @create 2017/6/14.
 */

public class Refund implements Parcelable {
    private short type;
    /**
     * 支付id
     */
    private String payId;
    private String message;
    /**
     * 核销状态，1表示成功，-1表示失败，0表示退款中
     */
    private int status;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.payId);
        dest.writeString(this.message);
        dest.writeInt(this.status);
    }

    public Refund() {
    }

    protected Refund(Parcel in) {
        this.type = (short) in.readInt();
        this.payId = in.readString();
        this.message = in.readString();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<Refund> CREATOR = new Parcelable.Creator<Refund>() {
        @Override
        public Refund createFromParcel(Parcel source) {
            return new Refund(source);
        }

        @Override
        public Refund[] newArray(int size) {
            return new Refund[size];
        }
    };
}
