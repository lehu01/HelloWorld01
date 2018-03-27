package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 选择挂账单位（人）ITEM
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class UnitRecyclerItem implements Parcelable {
    public static final int ITEM_TYPE = 1;

    private int itemType = ITEM_TYPE;
    /**
     * 挂账单位（人）名称（比如 火小二科技有限公司）
     */
    private String unitName;

    /**
     * item是否被选中（单选）
     */
    private boolean checked;

    //详细信息id
    private String kindPayDetailId;
    //详细信息选项id
    private String kindPayDetailOptionId;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getKindPayDetailId() {
        return kindPayDetailId;
    }

    public void setKindPayDetailId(String kindPayDetailId) {
        this.kindPayDetailId = kindPayDetailId;
    }

    public String getKindPayDetailOptionId() {
        return kindPayDetailOptionId;
    }

    public void setKindPayDetailOptionId(String kindPayDetailOptionId) {
        this.kindPayDetailOptionId = kindPayDetailOptionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemType);
        dest.writeString(this.unitName);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
        dest.writeString(this.kindPayDetailId);
        dest.writeString(this.kindPayDetailOptionId);
    }

    public UnitRecyclerItem() {
    }

    protected UnitRecyclerItem(Parcel in) {
        this.itemType = in.readInt();
        this.unitName = in.readString();
        this.checked = in.readByte() != 0;
        this.kindPayDetailId = in.readString();
        this.kindPayDetailOptionId = in.readString();
    }

    public static final Parcelable.Creator<UnitRecyclerItem> CREATOR = new Parcelable.Creator<UnitRecyclerItem>() {
        @Override
        public UnitRecyclerItem createFromParcel(Parcel source) {
            return new UnitRecyclerItem(source);
        }

        @Override
        public UnitRecyclerItem[] newArray(int size) {
            return new UnitRecyclerItem[size];
        }
    };
}
