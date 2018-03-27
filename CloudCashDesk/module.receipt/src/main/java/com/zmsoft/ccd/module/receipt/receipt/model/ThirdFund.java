package com.zmsoft.ccd.module.receipt.receipt.model;

import android.os.Parcel;

import com.zmsoft.ccd.receipt.bean.Fund;

/**
 * 云收银收款-第三方支付（微信，支付宝）
 *
 * @author DangGui
 * @create 2017/6/13.
 */
public class ThirdFund extends Fund {
    //第三方支付授权码
    private String authCode;
    //客户端的ip地址
    private String remoteAddr;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.authCode);
        dest.writeString(this.remoteAddr);
    }

    public ThirdFund() {
    }

    protected ThirdFund(Parcel in) {
        super(in);
        this.authCode = in.readString();
        this.remoteAddr = in.readString();
    }

    public static final Creator<ThirdFund> CREATOR = new Creator<ThirdFund>() {
        @Override
        public ThirdFund createFromParcel(Parcel source) {
            return new ThirdFund(source);
        }

        @Override
        public ThirdFund[] newArray(int size) {
            return new ThirdFund[size];
        }
    };
}
