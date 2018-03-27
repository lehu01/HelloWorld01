package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 获取代金券面额列表
 *
 * @author DangGui
 * @create 2017/6/16.
 */

public class GetVoucherInfoResponse implements Parcelable {
    private List<VoucherInfoVo> voucherFundList;

    public List<VoucherInfoVo> getVoucherFundList() {
        return voucherFundList;
    }

    public void setVoucherFundList(List<VoucherInfoVo> voucherFundList) {
        this.voucherFundList = voucherFundList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.voucherFundList);
    }

    public GetVoucherInfoResponse() {
    }

    protected GetVoucherInfoResponse(Parcel in) {
        this.voucherFundList = in.createTypedArrayList(VoucherInfoVo.CREATOR);
    }

    public static final Creator<GetVoucherInfoResponse> CREATOR = new Creator<GetVoucherInfoResponse>() {
        @Override
        public GetVoucherInfoResponse createFromParcel(Parcel source) {
            return new GetVoucherInfoResponse(source);
        }

        @Override
        public GetVoucherInfoResponse[] newArray(int size) {
            return new GetVoucherInfoResponse[size];
        }
    };
}
