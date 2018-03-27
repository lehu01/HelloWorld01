package com.zmsoft.ccd.takeout.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author DangGui
 * @create 2017/9/9.
 */

public class TakeoutDeliveryResult implements Parcelable {
    private String orderId;
    /**
     * 0 失败，1 成功
     */
    private int result;
    private String msg;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeInt(this.result);
        dest.writeString(this.msg);
    }

    public TakeoutDeliveryResult() {
    }

    protected TakeoutDeliveryResult(Parcel in) {
        this.orderId = in.readString();
        this.result = in.readInt();
        this.msg = in.readString();
    }

    public static final Parcelable.Creator<TakeoutDeliveryResult> CREATOR = new Parcelable.Creator<TakeoutDeliveryResult>() {
        @Override
        public TakeoutDeliveryResult createFromParcel(Parcel source) {
            return new TakeoutDeliveryResult(source);
        }

        @Override
        public TakeoutDeliveryResult[] newArray(int size) {
            return new TakeoutDeliveryResult[size];
        }
    };
}
