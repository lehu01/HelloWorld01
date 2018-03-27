package com.zmsoft.ccd.takeout.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zmsoft.ccd.lib.bean.message.TakeoutInstanceVo;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/9/1.
 */

public class DeliveryOrderVo implements Parcelable {
    private String entityId;
    private String orderId;
    private String name;
    private String mobile;
    private int code;
    /**
     * 商品总计
     */
    private int instanceNum;
    private List<TakeoutInstanceVo> takeoutInstanceVos;
    private String address;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getInstanceNum() {
        return instanceNum;
    }

    public void setInstanceNum(int instanceNum) {
        this.instanceNum = instanceNum;
    }

    public List<TakeoutInstanceVo> getChildTakeoutInstances() {
        return takeoutInstanceVos;
    }

    public void setChildTakeoutInstances(List<TakeoutInstanceVo> childTakeoutInstances) {
        this.takeoutInstanceVos = childTakeoutInstances;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DeliveryOrderVo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.entityId);
        dest.writeString(this.orderId);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeInt(this.code);
        dest.writeInt(this.instanceNum);
        dest.writeTypedList(this.takeoutInstanceVos);
        dest.writeString(this.address);
    }

    protected DeliveryOrderVo(Parcel in) {
        this.entityId = in.readString();
        this.orderId = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.code = in.readInt();
        this.instanceNum = in.readInt();
        this.takeoutInstanceVos = in.createTypedArrayList(TakeoutInstanceVo.CREATOR);
        this.address = in.readString();
    }

    public static final Creator<DeliveryOrderVo> CREATOR = new Creator<DeliveryOrderVo>() {
        @Override
        public DeliveryOrderVo createFromParcel(Parcel source) {
            return new DeliveryOrderVo(source);
        }

        @Override
        public DeliveryOrderVo[] newArray(int size) {
            return new DeliveryOrderVo[size];
        }
    };
}
