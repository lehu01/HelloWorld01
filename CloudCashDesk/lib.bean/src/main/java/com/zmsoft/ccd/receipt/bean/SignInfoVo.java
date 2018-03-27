package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;

/**
 * 挂账单位、签字人信息
 *
 * @author DangGui
 * @create 2017/6/16.
 */
public class SignInfoVo extends PayDetail {

    //顺序码
    private int sortCode;

    //选项名称(签字人)
    private String name;

    //所属实体
    private String entityId;

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        super.writeToParcel(dest, flags);
        dest.writeInt(this.sortCode);
        dest.writeString(this.name);
        dest.writeString(this.entityId);
    }

    public SignInfoVo() {
    }

    protected SignInfoVo(Parcel in) {
        super(in);
        this.sortCode = in.readInt();
        this.name = in.readString();
        this.entityId = in.readString();
    }

    public static final Creator<SignInfoVo> CREATOR = new Creator<SignInfoVo>() {
        @Override
        public SignInfoVo createFromParcel(Parcel source) {
            return new SignInfoVo(source);
        }

        @Override
        public SignInfoVo[] newArray(int size) {
            return new SignInfoVo[size];
        }
    };
}
