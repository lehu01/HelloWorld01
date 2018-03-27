package com.zmsoft.ccd.takeout.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/31.
 */

public class DeliveryInfo implements Parcelable {

    private String date;
    private String desc;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.desc);
    }

    public DeliveryInfo() {
    }

    protected DeliveryInfo(Parcel in) {
        this.date = in.readString();
        this.desc = in.readString();
    }

    public static final Parcelable.Creator<DeliveryInfo> CREATOR = new Parcelable.Creator<DeliveryInfo>() {
        @Override
        public DeliveryInfo createFromParcel(Parcel source) {
            return new DeliveryInfo(source);
        }

        @Override
        public DeliveryInfo[] newArray(int size) {
            return new DeliveryInfo[size];
        }
    };
}
