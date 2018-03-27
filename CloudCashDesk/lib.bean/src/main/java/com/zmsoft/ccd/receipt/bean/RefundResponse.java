package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/6/14.
 */

public class RefundResponse implements Parcelable {
    private List<Refund> refunds;

    private double userCollectLimit;

    private double userCurrAmount;

    /**
     * 订单最后修改时间
     */
    private long modifyTime;

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public double getUserCollectLimit() {
        return userCollectLimit;
    }

    public void setUserCollectLimit(double userCollectLimit) {
        this.userCollectLimit = userCollectLimit;
    }

    public double getUserCurrAmount() {
        return userCurrAmount;
    }

    public void setUserCurrAmount(double userCurrAmount) {
        this.userCurrAmount = userCurrAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.refunds);
        dest.writeDouble(userCollectLimit);
        dest.writeDouble(userCurrAmount);
        dest.writeLong(this.modifyTime);
    }

    public RefundResponse() {
    }

    protected RefundResponse(Parcel in) {
        this.refunds = in.createTypedArrayList(Refund.CREATOR);
        this.userCollectLimit = in.readDouble();
        this.userCurrAmount = in.readDouble();
        this.modifyTime = in.readLong();
    }

    public static final Creator<RefundResponse> CREATOR = new Creator<RefundResponse>() {
        @Override
        public RefundResponse createFromParcel(Parcel source) {
            return new RefundResponse(source);
        }

        @Override
        public RefundResponse[] newArray(int size) {
            return new RefundResponse[size];
        }
    };
}
