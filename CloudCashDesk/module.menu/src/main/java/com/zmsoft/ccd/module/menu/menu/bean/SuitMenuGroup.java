package com.zmsoft.ccd.module.menu.menu.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SuitMenuGroup implements Parcelable {
    /**
     * 分组id
     */
    private String suitMenuDetailId;
    /**
     * 分组名称
     */
    private String name;
    /**
     * 可点分数
     */
    private double num;
    /**
     * 是否必选
     */
    private int isRequired;
    /**
     * 是否售完
     */
    private boolean soldOut;
    /**
     * 分组下的子菜
     */
    private List<Menu> menus;


    public String getSuitMenuDetailId() {
        return suitMenuDetailId;
    }

    public void setSuitMenuDetailId(String suitMenuDetailId) {
        this.suitMenuDetailId = suitMenuDetailId;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.suitMenuDetailId);
        dest.writeString(this.name);
        dest.writeDouble(this.num);
        dest.writeInt(this.isRequired);
        dest.writeByte(this.soldOut ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.menus);
    }

    public SuitMenuGroup() {
    }

    protected SuitMenuGroup(Parcel in) {
        this.suitMenuDetailId = in.readString();
        this.name = in.readString();
        this.num = in.readDouble();
        this.isRequired = in.readInt();
        this.soldOut = in.readByte() != 0;
        this.menus = in.createTypedArrayList(Menu.CREATOR);
    }

    public static final Parcelable.Creator<SuitMenuGroup> CREATOR = new Parcelable.Creator<SuitMenuGroup>() {
        @Override
        public SuitMenuGroup createFromParcel(Parcel source) {
            return new SuitMenuGroup(source);
        }

        @Override
        public SuitMenuGroup[] newArray(int size) {
            return new SuitMenuGroup[size];
        }
    };
}