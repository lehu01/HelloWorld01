package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 云收银账单优惠详情
 *
 * @author DangGui
 * @create 2017/6/13.
 */
public class KindPay implements Parcelable {
    //支付类型id
    private String id;
    //支付类型名称
    private String name;
    //支付类型
    private short type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.type);
    }

    public KindPay() {
    }

    protected KindPay(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.type = (short) in.readInt();
    }

    public static final Creator<KindPay> CREATOR = new Creator<KindPay>() {
        @Override
        public KindPay createFromParcel(Parcel source) {
            return new KindPay(source);
        }

        @Override
        public KindPay[] newArray(int size) {
            return new KindPay[size];
        }
    };
}
