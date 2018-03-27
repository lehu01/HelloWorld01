package com.zmsoft.ccd.module.menu.menu.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Description：套餐计价规则（撞餐）
 * <br/>
 * Created by kumu on 2017/5/23.
 */

public class SuitMenuHitRule implements Parcelable {


    private String ruleId;

    private double floatPrice;

    private String entityId;

    private String suitMenuId;

    private String name;

    private int sortCode;

    private int isValid;


    private long createTime;

    private long opTime;

    private long lastVer;

    private List<SuitMenuChange> items;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public double getFloatPriceYuan() {
        return floatPrice / 100d;
    }

    public double getFloatPrice() {
        return floatPrice;
    }

    public void setFloatPrice(double floatPrice) {
        this.floatPrice = floatPrice;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getSuitMenuId() {
        return suitMenuId;
    }

    public void setSuitMenuId(String suitMenuId) {
        this.suitMenuId = suitMenuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }

    public long getLastVer() {
        return lastVer;
    }

    public void setLastVer(long lastVer) {
        this.lastVer = lastVer;
    }

    public List<SuitMenuChange> getItems() {
        return items;
    }

    public void setItems(List<SuitMenuChange> items) {
        this.items = items;
    }

    public static class SuitMenuChange implements Parcelable {
        private String menuName;
        private String menuId;

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public String getMenuId() {
            return menuId;
        }

        public void setMenuId(String menuId) {
            this.menuId = menuId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.menuName);
            dest.writeString(this.menuId);
        }

        public SuitMenuChange() {
        }

        protected SuitMenuChange(Parcel in) {
            this.menuName = in.readString();
            this.menuId = in.readString();
        }

        public static final Parcelable.Creator<SuitMenuChange> CREATOR = new Parcelable.Creator<SuitMenuChange>() {
            @Override
            public SuitMenuChange createFromParcel(Parcel source) {
                return new SuitMenuChange(source);
            }

            @Override
            public SuitMenuChange[] newArray(int size) {
                return new SuitMenuChange[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ruleId);
        dest.writeDouble(this.floatPrice);
        dest.writeString(this.entityId);
        dest.writeString(this.suitMenuId);
        dest.writeString(this.name);
        dest.writeInt(this.sortCode);
        dest.writeInt(this.isValid);
        dest.writeLong(this.createTime);
        dest.writeLong(this.opTime);
        dest.writeLong(this.lastVer);
        dest.writeTypedList(this.items);
    }

    public SuitMenuHitRule() {
    }

    protected SuitMenuHitRule(Parcel in) {
        this.ruleId = in.readString();
        this.floatPrice = in.readDouble();
        this.entityId = in.readString();
        this.suitMenuId = in.readString();
        this.name = in.readString();
        this.sortCode = in.readInt();
        this.isValid = in.readInt();
        this.createTime = in.readLong();
        this.opTime = in.readLong();
        this.lastVer = in.readLong();
        this.items = in.createTypedArrayList(SuitMenuChange.CREATOR);
    }

    public static final Parcelable.Creator<SuitMenuHitRule> CREATOR = new Parcelable.Creator<SuitMenuHitRule>() {
        @Override
        public SuitMenuHitRule createFromParcel(Parcel source) {
            return new SuitMenuHitRule(source);
        }

        @Override
        public SuitMenuHitRule[] newArray(int size) {
            return new SuitMenuHitRule[size];
        }
    };
}
