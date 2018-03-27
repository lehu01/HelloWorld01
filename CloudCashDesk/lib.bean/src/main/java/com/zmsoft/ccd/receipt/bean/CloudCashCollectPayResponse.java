package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 云收银收款 返回的实体类
 *
 * @author DangGui
 * @create 2017/6/13.
 */

public class CloudCashCollectPayResponse implements Parcelable {
    /**
     * 支付列表
     */
    private List<Fund> funds;
    /**
     * 快照id，不可为空。
     */
    private String snapshotId;
    /**
     * 支付状态，不可为空 -1表示支付失败，1表示支付成功，2表示正在支付。
     */
    private int status;

    /**
     * 订单最后修改时间
     */
    private long modifyTime;

    private double userCollectLimit;

    private double userCurrAmount;

    /**
     * 支付流水号
     */
    private String outTradeNo;

    public List<Fund> getFunds() {
        return funds;
    }

    public void setFunds(List<Fund> funds) {
        this.funds = funds;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public CloudCashCollectPayResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.funds);
        dest.writeString(this.snapshotId);
        dest.writeInt(this.status);
        dest.writeLong(this.modifyTime);
        dest.writeDouble(this.userCollectLimit);
        dest.writeDouble(this.userCurrAmount);
        dest.writeString(this.outTradeNo);
    }

    protected CloudCashCollectPayResponse(Parcel in) {
        this.funds = in.createTypedArrayList(Fund.CREATOR);
        this.snapshotId = in.readString();
        this.status = in.readInt();
        this.modifyTime = in.readLong();
        this.userCollectLimit = in.readDouble();
        this.userCurrAmount = in.readDouble();
        this.outTradeNo = in.readString();
    }

    public static final Creator<CloudCashCollectPayResponse> CREATOR = new Creator<CloudCashCollectPayResponse>() {
        @Override
        public CloudCashCollectPayResponse createFromParcel(Parcel source) {
            return new CloudCashCollectPayResponse(source);
        }

        @Override
        public CloudCashCollectPayResponse[] newArray(int size) {
            return new CloudCashCollectPayResponse[size];
        }
    };
}
