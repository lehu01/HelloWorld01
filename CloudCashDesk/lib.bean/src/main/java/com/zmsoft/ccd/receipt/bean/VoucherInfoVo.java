package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 代金券信息
 *
 * @author DangGui
 * @create 2017/6/16.
 */
public class VoucherInfoVo extends VoucherFund {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public VoucherInfoVo() {
    }

    protected VoucherInfoVo(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<VoucherInfoVo> CREATOR = new Parcelable.Creator<VoucherInfoVo>() {
        @Override
        public VoucherInfoVo createFromParcel(Parcel source) {
            return new VoucherInfoVo(source);
        }

        @Override
        public VoucherInfoVo[] newArray(int size) {
            return new VoucherInfoVo[size];
        }
    };
}
