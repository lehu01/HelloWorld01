package com.zmsoft.ccd.lib.bean.message;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/8/29.
 */

public class TakeoutInstanceVo implements Parcelable {
    private String name;
    private String makeName;
    private double num;
    private double ratioFee;
    private short kind;
    private short status;
    private String memo;
    private String unit;
    private String accountUnit;
    private String specDetailName;
    /**
     * 商品类型
     * 0, "食物"
     * 1, "打包盒"
     */
    private int type;
    /**
     * 条形码
     */
    private String code;
    private double accountNum;

    /**
     * 双单位标识，用以判断零售称重商品
     */
    private int doubleUnits;

    private List<TakeoutInstanceVo> childTakeoutInstances;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public double getRatioFee() {
        return ratioFee;
    }

    public void setRatioFee(double ratioFee) {
        this.ratioFee = ratioFee;
    }

    public short getKind() {
        return kind;
    }

    public void setKind(short kind) {
        this.kind = kind;
    }

    public List<TakeoutInstanceVo> getChildTakeoutInstances() {
        return childTakeoutInstances;
    }

    public void setChildTakeoutInstances(List<TakeoutInstanceVo> childTakeoutInstances) {
        this.childTakeoutInstances = childTakeoutInstances;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAccountUnit() {
        return accountUnit;
    }

    public void setAccountUnit(String accountUnit) {
        this.accountUnit = accountUnit;
    }

    public String getSpecDetailName() {
        return specDetailName;
    }

    public void setSpecDetailName(String specDetailName) {
        this.specDetailName = specDetailName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String barCode) {
        this.code = barCode;
    }

    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }

    public int getDoubleUnits() {
        return doubleUnits;
    }

    public void setDoubleUnits(int doubleUnits) {
        this.doubleUnits = doubleUnits;
    }

    public TakeoutInstanceVo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.makeName);
        dest.writeDouble(this.num);
        dest.writeDouble(this.ratioFee);
        dest.writeInt(this.kind);
        dest.writeInt(this.status);
        dest.writeString(this.memo);
        dest.writeString(this.unit);
        dest.writeString(this.accountUnit);
        dest.writeString(this.specDetailName);
        dest.writeInt(this.type);
        dest.writeString(this.code);
        dest.writeDouble(this.accountNum);
        dest.writeInt(this.doubleUnits);
        dest.writeTypedList(this.childTakeoutInstances);
    }

    protected TakeoutInstanceVo(Parcel in) {
        this.name = in.readString();
        this.makeName = in.readString();
        this.num = in.readDouble();
        this.ratioFee = in.readDouble();
        this.kind = (short) in.readInt();
        this.status = (short) in.readInt();
        this.memo = in.readString();
        this.unit = in.readString();
        this.accountUnit = in.readString();
        this.specDetailName = in.readString();
        this.type = in.readInt();
        this.code = in.readString();
        this.accountNum = in.readDouble();
        this.doubleUnits = in.readInt();
        this.childTakeoutInstances = in.createTypedArrayList(TakeoutInstanceVo.CREATOR);
    }

    public static final Creator<TakeoutInstanceVo> CREATOR = new Creator<TakeoutInstanceVo>() {
        @Override
        public TakeoutInstanceVo createFromParcel(Parcel source) {
            return new TakeoutInstanceVo(source);
        }

        @Override
        public TakeoutInstanceVo[] newArray(int size) {
            return new TakeoutInstanceVo[size];
        }
    };
}
