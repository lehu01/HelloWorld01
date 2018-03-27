package com.zmsoft.ccd.lib.bean.permission;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author DangGui
 * @create 2017/6/21.
 */

public class BatchPermission implements Parcelable {
    /**
     * 自定义菜权限
     */
    private boolean hasCustomFoodPermisson;
    /**
     * 赠送这个菜
     */
    private boolean hasPresentFoodPermisson;
    /**
     * 退菜
     */
    private boolean hasCancelInstancePermisson;
    /**
     * 撤单
     */
    private boolean hasCancelOrderPermisson;
    /**
     * 修改菜肴价格
     */
    private boolean hasUpdateInstancePricePermisson;
    /**
     * 修改菜肴重量
     */
    private boolean hasUpdateInstanceWeightPermisson;
    /**
     * 改单[下单]购物车改单不需要
     */
    private boolean hasChangeOrderPermisson;
    /**
     * 沽清添加
     */
    private boolean hasMenuBalancePermisson;

    /**
     * 清空优惠的权限
     */
    private boolean hasEmptyDiscountPermisson;
    /**
     * 挂账权限
     */
    private boolean hasOnAccountPermisson;
    /**
     * 免单权限
     */
    private boolean hasFreeOrderPermisson;
    /**
     * 对不允许打折的商品进行打折
     */
    private boolean hasForceDiscountPermisson;
    /**
     * 对允许可打折的商品进行打折
     */
    private boolean hasForceDiscountAllowPermisson;
    /**
     * 结账权限
     */
    private boolean hasCheckOutPermisson;

    public boolean isHasCustomFoodPermisson() {
        return hasCustomFoodPermisson;
    }

    public void setHasCustomFoodPermisson(boolean hasCustomFoodPermisson) {
        this.hasCustomFoodPermisson = hasCustomFoodPermisson;
    }

    public boolean isHasPresentFoodPermisson() {
        return hasPresentFoodPermisson;
    }

    public void setHasPresentFoodPermisson(boolean hasPresentFoodPermisson) {
        this.hasPresentFoodPermisson = hasPresentFoodPermisson;
    }

    public boolean isHasCancelInstancePermisson() {
        return hasCancelInstancePermisson;
    }

    public void setHasCancelInstancePermisson(boolean hasCancelInstancePermisson) {
        this.hasCancelInstancePermisson = hasCancelInstancePermisson;
    }

    public boolean isHasCancelOrderPermisson() {
        return hasCancelOrderPermisson;
    }

    public void setHasCancelOrderPermisson(boolean hasCancelOrderPermisson) {
        this.hasCancelOrderPermisson = hasCancelOrderPermisson;
    }

    public boolean isHasUpdateInstancePricePermisson() {
        return hasUpdateInstancePricePermisson;
    }

    public void setHasUpdateInstancePricePermisson(boolean hasUpdateInstancePricePermisson) {
        this.hasUpdateInstancePricePermisson = hasUpdateInstancePricePermisson;
    }

    public boolean isHasUpdateInstanceWeightPermisson() {
        return hasUpdateInstanceWeightPermisson;
    }

    public void setHasUpdateInstanceWeightPermisson(boolean hasUpdateInstanceWeightPermisson) {
        this.hasUpdateInstanceWeightPermisson = hasUpdateInstanceWeightPermisson;
    }

    public boolean isHasChangeOrderPermisson() {
        return hasChangeOrderPermisson;
    }

    public void setHasChangeOrderPermisson(boolean hasChangeOrderPermisson) {
        this.hasChangeOrderPermisson = hasChangeOrderPermisson;
    }

    public boolean isHasMenuBalancePermisson() {
        return hasMenuBalancePermisson;
    }

    public void setHasMenuBalancePermisson(boolean hasMenuBalancePermisson) {
        this.hasMenuBalancePermisson = hasMenuBalancePermisson;
    }

    public boolean isHasEmptyDiscountPermisson() {
        return hasEmptyDiscountPermisson;
    }

    public void setHasEmptyDiscountPermisson(boolean hasEmptyDiscountPermisson) {
        this.hasEmptyDiscountPermisson = hasEmptyDiscountPermisson;
    }

    public boolean isHasOnAccountPermisson() {
        return hasOnAccountPermisson;
    }

    public void setHasOnAccountPermisson(boolean hasOnAccountPermisson) {
        this.hasOnAccountPermisson = hasOnAccountPermisson;
    }

    public boolean isHasFreeOrderPermisson() {
        return hasFreeOrderPermisson;
    }

    public void setHasFreeOrderPermisson(boolean hasFreeOrderPermisson) {
        this.hasFreeOrderPermisson = hasFreeOrderPermisson;
    }

    public boolean isHasForceDiscountPermisson() {
        return hasForceDiscountPermisson;
    }

    public void setHasForceDiscountPermisson(boolean hasForceDiscountPermisson) {
        this.hasForceDiscountPermisson = hasForceDiscountPermisson;
    }

    public boolean isHasForceDiscountAllowPermisson() {
        return hasForceDiscountAllowPermisson;
    }

    public void setHasForceDiscountAllowPermisson(boolean hasForceDiscountAllowPermisson) {
        this.hasForceDiscountAllowPermisson = hasForceDiscountAllowPermisson;
    }

    public boolean isHasCheckOutPermisson() {
        return hasCheckOutPermisson;
    }

    public void setHasCheckOutPermisson(boolean hasCheckOutPermisson) {
        this.hasCheckOutPermisson = hasCheckOutPermisson;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.hasCustomFoodPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasPresentFoodPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasCancelInstancePermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasCancelOrderPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasUpdateInstancePricePermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasUpdateInstanceWeightPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasChangeOrderPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasMenuBalancePermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasEmptyDiscountPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasOnAccountPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasFreeOrderPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasForceDiscountPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasForceDiscountAllowPermisson ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasCheckOutPermisson ? (byte) 1 : (byte) 0);
    }

    public BatchPermission() {
    }

    protected BatchPermission(Parcel in) {
        this.hasCustomFoodPermisson = in.readByte() != 0;
        this.hasPresentFoodPermisson = in.readByte() != 0;
        this.hasCancelInstancePermisson = in.readByte() != 0;
        this.hasCancelOrderPermisson = in.readByte() != 0;
        this.hasUpdateInstancePricePermisson = in.readByte() != 0;
        this.hasUpdateInstanceWeightPermisson = in.readByte() != 0;
        this.hasChangeOrderPermisson = in.readByte() != 0;
        this.hasMenuBalancePermisson = in.readByte() != 0;
        this.hasEmptyDiscountPermisson = in.readByte() != 0;
        this.hasOnAccountPermisson = in.readByte() != 0;
        this.hasFreeOrderPermisson = in.readByte() != 0;
        this.hasForceDiscountPermisson = in.readByte() != 0;
        this.hasForceDiscountAllowPermisson = in.readByte() != 0;
        this.hasCheckOutPermisson = in.readByte() != 0;
    }

    public static final Creator<BatchPermission> CREATOR = new Creator<BatchPermission>() {
        @Override
        public BatchPermission createFromParcel(Parcel source) {
            return new BatchPermission(source);
        }

        @Override
        public BatchPermission[] newArray(int size) {
            return new BatchPermission[size];
        }
    };
}
