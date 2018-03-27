package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 云收银账单支付详情
 *
 * @author DangGui
 * @create 2017/6/13.
 */
public class Pay implements Parcelable {
    //支付id (payId)
    private String id;
    //支付类型
    private short type;
    //支付类型
    private String kindPayId;
    //支付名称（展示内容）
    private String name;
    //支付金额（分）
    private int fee;
    /**
     * 外部支付流水号，如果是通联支付则为支付凭证,退款时候时候
     */
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getKindPayId() {
        return kindPayId;
    }

    public void setKindPayId(String kindPayId) {
        this.kindPayId = kindPayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getCode() {
        return code;
    }

    public Pay() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.kindPayId);
        dest.writeString(this.name);
        dest.writeInt(this.fee);
        dest.writeString(this.code);
    }

    protected Pay(Parcel in) {
        this.id = in.readString();
        this.type = (short) in.readInt();
        this.kindPayId = in.readString();
        this.name = in.readString();
        this.fee = in.readInt();
        this.code = in.readString();
    }

    public static final Creator<Pay> CREATOR = new Creator<Pay>() {
        @Override
        public Pay createFromParcel(Parcel source) {
            return new Pay(source);
        }

        @Override
        public Pay[] newArray(int size) {
            return new Pay[size];
        }
    };
}
