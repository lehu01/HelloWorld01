package com.zmsoft.ccd.module.receipt.receipt.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 获取代金券面额列表 参数
 *
 * @author DangGui
 * @create 2017/6/13.
 */

public class GetVoucherInfoRequest implements Parcelable {
    private String kindPayId;
    private String entityId;

    public GetVoucherInfoRequest(String kindPayId, String entityId) {
        this.kindPayId = kindPayId;
        this.entityId = entityId;
    }

    public String getKindPayId() {
        return kindPayId;
    }

    public void setKindPayId(String kindPayId) {
        this.kindPayId = kindPayId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kindPayId);
        dest.writeString(this.entityId);
    }

    protected GetVoucherInfoRequest(Parcel in) {
        this.kindPayId = in.readString();
        this.entityId = in.readString();
    }

    public static final Creator<GetVoucherInfoRequest> CREATOR = new Creator<GetVoucherInfoRequest>() {
        @Override
        public GetVoucherInfoRequest createFromParcel(Parcel source) {
            return new GetVoucherInfoRequest(source);
        }

        @Override
        public GetVoucherInfoRequest[] newArray(int size) {
            return new GetVoucherInfoRequest[size];
        }
    };
}
