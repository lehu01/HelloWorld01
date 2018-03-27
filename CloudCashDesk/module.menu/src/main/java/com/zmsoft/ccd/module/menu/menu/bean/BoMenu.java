package com.zmsoft.ccd.module.menu.menu.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huaixi on 2017/11/9.
 */

public class BoMenu implements Parcelable {

    private String imagePath;   //图片下载地址
    private String name;       //商品名称
    private String code;       //商品编码
    private String account;    //结账单位
    private String buyAccount; //点菜单位
    private double price;      //商品价格
    private int isTwoAccount;  //是否双单位
    private String id;         //菜id

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBuyAccount() {
        return buyAccount;
    }

    public void setBuyAccount(String buyAccount) {
        this.buyAccount = buyAccount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIsTwoAccount() {
        return isTwoAccount;
    }

    public void setIsTwoAccount(int isTwoAccount) {
        this.isTwoAccount = isTwoAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BoMenu() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imagePath);
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeString(this.account);
        dest.writeString(this.buyAccount);
        dest.writeDouble(this.price);
        dest.writeInt(this.isTwoAccount);
        dest.writeString(this.id);
    }

    protected BoMenu(Parcel in) {
        this.imagePath = in.readString();
        this.name = in.readString();
        this.code = in.readString();
        this.account = in.readString();
        this.buyAccount = in.readString();
        this.price = in.readDouble();
        this.isTwoAccount = in.readInt();
        this.id = in.readString();
    }

    public static final Creator<BoMenu> CREATOR = new Creator<BoMenu>() {
        @Override
        public BoMenu createFromParcel(Parcel source) {
            return new BoMenu(source);
        }

        @Override
        public BoMenu[] newArray(int size) {
            return new BoMenu[size];
        }
    };
}
