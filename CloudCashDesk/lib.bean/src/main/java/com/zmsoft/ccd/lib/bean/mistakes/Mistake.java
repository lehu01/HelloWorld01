package com.zmsoft.ccd.lib.bean.mistakes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jihuo on 2016/12/24.
 */

public class Mistake implements Parcelable {

    private int orderNO;

    private long mistakeTime;

    private String seatName;

    private String mistakeContent;

    public long getMistakeTime() {
        return mistakeTime;
    }

    public void setMistakeTime(long mistakeTime) {
        this.mistakeTime = mistakeTime;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getMistakeContent() {
        return mistakeContent;
    }

    public void setMistakeContent(String mistakeContent) {
        this.mistakeContent = mistakeContent;
    }

    public int getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(int orderNO) {
        this.orderNO = orderNO;
    }

    public Mistake(long mistakeTime, int orderNO, String seatName,String mistakeContent) {
        this.mistakeTime = mistakeTime;
        this.seatName = seatName;
        this.orderNO = orderNO;
        this.mistakeContent = mistakeContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mistakeTime);
        dest.writeString(this.seatName);
        dest.writeString(this.mistakeContent);
        dest.writeInt(this.orderNO);
    }

    protected Mistake(Parcel in) {
        this.mistakeTime = in.readLong();
        this.seatName = in.readString();
        this.mistakeContent = in.readString();
        this.orderNO = in.readInt();
    }

    public static final Creator<Mistake> CREATOR = new Creator<Mistake>() {
        @Override
        public Mistake createFromParcel(Parcel source) {
            return new Mistake(source);
        }

        @Override
        public Mistake[] newArray(int size) {
            return new Mistake[size];
        }
    };
}
