package com.zmsoft.ccd.lib.bean.message;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TakeoutOrderDetailVo implements Parcelable {

    //用餐人数
    private int peopleCount;

    //备注
    private String memo;

    //下单时间
    private String createTime;

    //接单时间
    private String openTime;

    //订单流水号
    private String globalCode;

    //配送费
    private double outFee;

    //外卖送货人姓名
    private String courierName;

    //外卖送货人电话
    private String courierPhone;

    //配送时间
    private String deliveryTime;

    //配送平台
    private String deliveryPlatform;
    //菜品数量
    private int instanceNum;
    //外部订单号(比如美团自己的订单号)
    private String daySeq ;
    //商品信息
    private List<TakeoutInstanceVo> takeoutInstanceVoList;

    //支付信息
    private List<TakeoutPayVo> takeoutPayVoList;

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

    public double getOutFee() {
        return outFee;
    }

    public void setOutFee(double outFee) {
        this.outFee = outFee;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryPlatform() {
        return deliveryPlatform;
    }

    public void setDeliveryPlatform(String deliveryPlatform) {
        this.deliveryPlatform = deliveryPlatform;
    }

    public int getInstanceNum() {
        return instanceNum;
    }

    public void setInstanceNum(int instanceNum) {
        this.instanceNum = instanceNum;
    }

    public String getOutId() {
        return daySeq ;
    }

    public void setOutId(String outId) {
        this.daySeq  = outId;
    }

    public List<TakeoutInstanceVo> getTakeoutInstanceVoList() {
        return takeoutInstanceVoList;
    }

    public void setTakeoutInstanceVoList(List<TakeoutInstanceVo> takeoutInstanceVoList) {
        this.takeoutInstanceVoList = takeoutInstanceVoList;
    }

    public List<TakeoutPayVo> getTakeoutPayVoList() {
        return takeoutPayVoList;
    }

    public void setTakeoutPayVoList(List<TakeoutPayVo> takeoutPayVoList) {
        this.takeoutPayVoList = takeoutPayVoList;
    }

    public TakeoutOrderDetailVo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.peopleCount);
        dest.writeString(this.memo);
        dest.writeString(this.createTime);
        dest.writeString(this.openTime);
        dest.writeString(this.globalCode);
        dest.writeDouble(this.outFee);
        dest.writeString(this.courierName);
        dest.writeString(this.courierPhone);
        dest.writeString(this.deliveryTime);
        dest.writeString(this.deliveryPlatform);
        dest.writeInt(this.instanceNum);
        dest.writeString(this.daySeq);
        dest.writeTypedList(this.takeoutInstanceVoList);
        dest.writeTypedList(this.takeoutPayVoList);
    }

    protected TakeoutOrderDetailVo(Parcel in) {
        this.peopleCount = in.readInt();
        this.memo = in.readString();
        this.createTime = in.readString();
        this.openTime = in.readString();
        this.globalCode = in.readString();
        this.outFee = in.readDouble();
        this.courierName = in.readString();
        this.courierPhone = in.readString();
        this.deliveryTime = in.readString();
        this.deliveryPlatform = in.readString();
        this.instanceNum = in.readInt();
        this.daySeq = in.readString();
        this.takeoutInstanceVoList = in.createTypedArrayList(TakeoutInstanceVo.CREATOR);
        this.takeoutPayVoList = in.createTypedArrayList(TakeoutPayVo.CREATOR);
    }

    public static final Creator<TakeoutOrderDetailVo> CREATOR = new Creator<TakeoutOrderDetailVo>() {
        @Override
        public TakeoutOrderDetailVo createFromParcel(Parcel source) {
            return new TakeoutOrderDetailVo(source);
        }

        @Override
        public TakeoutOrderDetailVo[] newArray(int size) {
            return new TakeoutOrderDetailVo[size];
        }
    };
}