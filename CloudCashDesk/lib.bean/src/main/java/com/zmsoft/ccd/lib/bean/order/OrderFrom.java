package com.zmsoft.ccd.lib.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huaixi on 2017/10/31.
 */

public class OrderFrom implements Parcelable {
    short code;
    String desc;

    public OrderFrom(Parcel source) {
        this.code = (short) source.readInt();
        this.desc = source.readString();
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
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
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(this.code);
        dest.writeString(this.desc);
    }

    public static final Creator<OrderFrom> CREATOR = new Creator<OrderFrom>() {
        @Override
        public OrderFrom createFromParcel(Parcel source) {
            return new OrderFrom(source);
        }

        @Override
        public OrderFrom[] newArray(int size) {
            return new OrderFrom[size];
        }
    };
}
