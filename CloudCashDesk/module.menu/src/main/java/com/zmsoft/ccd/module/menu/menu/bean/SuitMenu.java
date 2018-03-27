package com.zmsoft.ccd.module.menu.menu.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Description：套餐Bean
 * <br/>
 * Created by kumu on 2017/4/21.
 */

public class SuitMenu implements Parcelable {
    /**
     * 套菜id
     */
    private String menuId;
    /**
     * 套菜名称（如两人套餐）
     */
    private String name;
    /**
     * 套菜价格
     */
    private double price;

    /**
     * 子菜总数(套菜) 0表示不限
     */
    private int childCount;

    /**
     * 菜类id
     */
    private String kindMenuId;
    /**
     * 菜类名称
     */
    private String kindMenuName;

    /**
     * 分组列表
     */
    private List<SuitMenuGroup> suitMenuGroupVos;
    /**
     * 描述
     */
    private String desc;
    /**
     * 图片路径
     */
    private List<String> imagePaths;
    /**
     * 是否售完
     */
    private boolean soldOut;
    /**
     * 结账单位
     */
    private String account;
    /**
     * 点菜单位
     */
    private String buyAccount;

    /**
     * 是否设置套餐
     */
    private int isSetMenu;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

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

    public List<SuitMenuGroup> getSuitMenuGroupVos() {
        return suitMenuGroupVos;
    }

    public void setSuitMenuGroupVos(List<SuitMenuGroup> suitMenuGroupVos) {
        this.suitMenuGroupVos = suitMenuGroupVos;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
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

    public int getIsSetMenu() {
        return isSetMenu;
    }

    public void setIsSetMenu(int isSetMenu) {
        this.isSetMenu = isSetMenu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.menuId);
        dest.writeString(this.name);
        dest.writeDouble(this.price);
        dest.writeInt(this.childCount);
        dest.writeString(this.kindMenuId);
        dest.writeString(this.kindMenuName);
        dest.writeTypedList(this.suitMenuGroupVos);
        dest.writeString(this.desc);
        dest.writeStringList(this.imagePaths);
        dest.writeByte(this.soldOut ? (byte) 1 : (byte) 0);
        dest.writeString(this.account);
        dest.writeString(this.buyAccount);
        dest.writeInt(this.isSetMenu);


    }

    public SuitMenu() {
    }

    protected SuitMenu(Parcel in) {
        this.menuId = in.readString();
        this.name = in.readString();
        this.price = in.readDouble();
        this.childCount = in.readInt();
        this.kindMenuId = in.readString();
        this.kindMenuName = in.readString();
        this.suitMenuGroupVos = in.createTypedArrayList(SuitMenuGroup.CREATOR);
        this.desc = in.readString();
        this.imagePaths = in.createStringArrayList();
        this.soldOut = in.readByte() != 0;
        this.account = in.readString();
        this.buyAccount = in.readString();
        this.isSetMenu = in.readInt();
    }

    public static final Parcelable.Creator<SuitMenu> CREATOR = new Parcelable.Creator<SuitMenu>() {
        @Override
        public SuitMenu createFromParcel(Parcel source) {
            return new SuitMenu(source);
        }

        @Override
        public SuitMenu[] newArray(int size) {
            return new SuitMenu[size];
        }
    };
}
