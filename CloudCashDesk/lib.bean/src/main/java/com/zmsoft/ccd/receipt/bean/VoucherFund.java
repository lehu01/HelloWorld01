package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;

/**
 * 云收银收款-代金券
 *
 * @author DangGui
 * @create 2017/6/13.
 */
public class VoucherFund extends Fund {
    //代金券id
    private String voucherId;
    //代金券使用张数
    private int couponNum;
    //代金券实际购买金额(45.00 eg:45抵50)
    private double couponCost;
    //代金券面额(50.00 eg:45抵50)
    private double couponFee;
    //签名，防止篡改代金券的代金券实际购买金额和代金券面额 , 根据 @Encrypt 顺序生成签名
    private String sign;

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public int getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(int couponNum) {
        this.couponNum = couponNum;
    }

    public double getCouponCost() {
        return couponCost;
    }

    public void setCouponCost(double couponCost) {
        this.couponCost = couponCost;
    }

    public double getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(double couponFee) {
        this.couponFee = couponFee;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.voucherId);
        dest.writeInt(this.couponNum);
        dest.writeDouble(this.couponCost);
        dest.writeDouble(this.couponFee);
        dest.writeString(this.sign);
    }

    public VoucherFund() {
    }

    protected VoucherFund(Parcel in) {
        super(in);
        this.voucherId = in.readString();
        this.couponNum = in.readInt();
        this.couponCost = in.readDouble();
        this.couponFee = in.readDouble();
        this.sign = in.readString();
    }

    public static final Creator<VoucherFund> CREATOR = new Creator<VoucherFund>() {
        @Override
        public VoucherFund createFromParcel(Parcel source) {
            return new VoucherFund(source);
        }

        @Override
        public VoucherFund[] newArray(int size) {
            return new VoucherFund[size];
        }
    };
}
