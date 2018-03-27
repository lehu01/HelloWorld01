/************************* 版权声明 **********************************
 * 版权所有：Copyright (c) 2011, ${year} 大米
 *
 * 工程名称：	item-soa
 * 创建者： 大米 创建日期： ${date}
 * 创建记录：	创建类结构。
 *
 **/
package com.zmsoft.ccd.lib.bean.menubalance;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MenuVO implements Parcelable {

    private static final long serialVersionUID = 1L;

    private String kindMenuName;

    private List<Menu> menuList;

    public String getKindMenuName() {
        return kindMenuName;
    }

    public void setKindMenuName(String kindMenuName) {
        this.kindMenuName = kindMenuName;
    }

    public List<Menu> getMenu() {
        return menuList;
    }

    public void setMenu(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kindMenuName);
        dest.writeTypedList(this.menuList);
    }

    public MenuVO() {
    }

    protected MenuVO(Parcel in) {
        this.kindMenuName = in.readString();
        this.menuList = in.createTypedArrayList(Menu.CREATOR);
    }

    public static final Creator<MenuVO> CREATOR = new Creator<MenuVO>() {
        @Override
        public MenuVO createFromParcel(Parcel source) {
            return new MenuVO(source);
        }

        @Override
        public MenuVO[] newArray(int size) {
            return new MenuVO[size];
        }
    };
}
