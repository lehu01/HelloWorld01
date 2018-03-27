package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 云收银收款对象
 *
 * @author DangGui
 * @create 2017/6/13.
 */
public class Fund implements Parcelable {
    //收款类型，对应tc的ChannelType
    private short type;
    private String className;
    //收款钱(分)
    private int fee;
    //找零(元)
    private double charge;
    //支付详情列表
    private List<PayDetail> payDetails;
    //以下字段是请求接口返回实体里需要的
    //核销失败原因
    private String verifyMessage;
    //核销状态，1表示成功，-1表示失败
    private int status;
    private String kindPayId;
    /**
     * 通联支付使用，交易流水编号
     */
    private String outTradeNo;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public List<PayDetail> getPayDetails() {
        return payDetails;
    }

    public void setPayDetails(List<PayDetail> payDetails) {
        this.payDetails = payDetails;
    }

    public String getVerifyMessage() {
        return verifyMessage;
    }

    public void setVerifyMessage(String verifyMessage) {
        this.verifyMessage = verifyMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKindPayId() {
        return kindPayId;
    }

    public void setKindPayId(String kindPayId) {
        this.kindPayId = kindPayId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Fund() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.className);
        dest.writeInt(this.fee);
        dest.writeDouble(this.charge);
        dest.writeTypedList(this.payDetails);
        dest.writeString(this.verifyMessage);
        dest.writeInt(this.status);
        dest.writeString(this.kindPayId);
        dest.writeString(this.outTradeNo);
    }

    protected Fund(Parcel in) {
        this.type = (short) in.readInt();
        this.className = in.readString();
        this.fee = in.readInt();
        this.charge = in.readDouble();
        this.payDetails = in.createTypedArrayList(PayDetail.CREATOR);
        this.verifyMessage = in.readString();
        this.status = in.readInt();
        this.kindPayId = in.readString();
        this.outTradeNo = in.readString();
    }

    public static final Creator<Fund> CREATOR = new Creator<Fund>() {
        @Override
        public Fund createFromParcel(Parcel source) {
            return new Fund(source);
        }

        @Override
        public Fund[] newArray(int size) {
            return new Fund[size];
        }
    };
}
