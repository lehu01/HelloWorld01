package com.zmsoft.ccd.lib.bean.message;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author DangGui
 * @create 2017/8/29.
 */

public class TakeoutPayVo implements Parcelable {
    private int type;
    private double fee;
    private String name;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TakeoutPayVo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeDouble(this.fee);
        dest.writeString(this.name);
    }

    protected TakeoutPayVo(Parcel in) {
        this.type = in.readInt();
        this.fee = in.readDouble();
        this.name = in.readString();
    }

    public static final Creator<TakeoutPayVo> CREATOR = new Creator<TakeoutPayVo>() {
        @Override
        public TakeoutPayVo createFromParcel(Parcel source) {
            return new TakeoutPayVo(source);
        }

        @Override
        public TakeoutPayVo[] newArray(int size) {
            return new TakeoutPayVo[size];
        }
    };
}
