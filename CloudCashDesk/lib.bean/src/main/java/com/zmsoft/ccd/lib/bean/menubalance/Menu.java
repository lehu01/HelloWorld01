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

/**
 * @author <a href="mailto:dami@2dfire.com">大米</a>.
 * @version $Revision$
 */
public class Menu implements Parcelable {

    private String kindMenuId;
    private String kindMenuName;
    private String menuId;
    private String menuName;

    public String getKindMenuId() {
        return kindMenuId;
    }

    public void setKindMenuId(String kindMenuId) {
        this.kindMenuId = kindMenuId;
    }

    public String getKindMenuName() {
        return kindMenuName;
    }

    public void setKindMenuName(String kindMenuName) {
        this.kindMenuName = kindMenuName;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kindMenuId);
        dest.writeString(this.kindMenuName);
        dest.writeString(this.menuId);
        dest.writeString(this.menuName);
    }

    public Menu() {
    }

    protected Menu(Parcel in) {
        this.kindMenuId = in.readString();
        this.kindMenuName = in.readString();
        this.menuId = in.readString();
        this.menuName = in.readString();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel source) {
            return new Menu(source);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}