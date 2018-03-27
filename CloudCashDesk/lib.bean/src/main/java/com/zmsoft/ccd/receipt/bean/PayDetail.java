package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 云收银收款详细信息
 *
 * @author DangGui
 * @create 2017/6/13.
 */
public class PayDetail implements Parcelable {
    //详细信息id
    private String kindPayDetailId;
    //详细信息选项id
    private String kindPayDetailOptionId;
    //详细信息填写的内容
    private String memo;

    public String getKindPayDetailId() {
        return kindPayDetailId;
    }

    public void setKindPayDetailId(String kindPayDetailId) {
        this.kindPayDetailId = kindPayDetailId;
    }

    public String getKindPayDetailOptionId() {
        return kindPayDetailOptionId;
    }

    public void setKindPayDetailOptionId(String kindPayDetailOptionId) {
        this.kindPayDetailOptionId = kindPayDetailOptionId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public PayDetail() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kindPayDetailId);
        dest.writeString(this.kindPayDetailOptionId);
        dest.writeString(this.memo);
    }

    protected PayDetail(Parcel in) {
        this.kindPayDetailId = in.readString();
        this.kindPayDetailOptionId = in.readString();
        this.memo = in.readString();
    }

    public static final Creator<PayDetail> CREATOR = new Creator<PayDetail>() {
        @Override
        public PayDetail createFromParcel(Parcel source) {
            return new PayDetail(source);
        }

        @Override
        public PayDetail[] newArray(int size) {
            return new PayDetail[size];
        }
    };
}
