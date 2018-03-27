package com.zmsoft.ccd.module.menu.menu.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 菜肴做法
 */
public class Recipe implements Parcelable {

    private String makeId;
    /**
     * 烧法名称
     */
    private String name;
    /**
     * 烧法加价
     */
    private double makePrice;
    /**
     * 调价模式,0表示不加价，1表示一次性加价，2表示每点菜单位加价，3表示每结账单位加价
     */
    private int makePriceMode;

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMakePrice() {
        return makePrice;
    }

    public void setMakePrice(double makePrice) {
        this.makePrice = makePrice;
    }

    public int getMakePriceMode() {
        return makePriceMode;
    }

    public void setMakePriceMode(int makePriceMode) {
        this.makePriceMode = makePriceMode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.makeId);
        dest.writeString(this.name);
        dest.writeDouble(this.makePrice);
        dest.writeInt(this.makePriceMode);
    }

    public Recipe() {
    }

    public Recipe(String makeId, String name, double makePrice, int makePriceMode) {
        this.makeId = makeId;
        this.name = name;
        this.makePrice = makePrice;
        this.makePriceMode = makePriceMode;
    }

    protected Recipe(Parcel in) {
        this.makeId = in.readString();
        this.name = in.readString();
        this.makePrice = in.readDouble();
        this.makePriceMode = in.readInt();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}