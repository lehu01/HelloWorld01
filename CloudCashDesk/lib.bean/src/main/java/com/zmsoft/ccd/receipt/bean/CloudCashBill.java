package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 云收银账单金额详情
 *
 * @author DangGui
 * @create 2017/6/13.
 */
public class CloudCashBill implements Parcelable {
    private String orderId;                  //订单ID
    private int fee;                         //应收金额
    private int originFee;                   //消费金额
    private int paidFee;                     //订单已支付金额（分）
    private int needFee;                     //还需收款
    private int discountFee;                 //优惠金额（分）
    private int serviceFee;                  //订单服务费（分）
    private int taxFee;                      //税费（分）
    private int leastFee;                    //最低消费（分）
    private boolean usePromotion;            //是否使用了优惠（卡优惠和收银优惠）
    /**
     * 订单最后修改时间
     */
    private long modifyTime;

    private int outFee;                        //外送费（分）

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getOriginFee() {
        return originFee;
    }

    public void setOriginFee(int originFee) {
        this.originFee = originFee;
    }

    public int getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(int paidFee) {
        this.paidFee = paidFee;
    }

    public int getNeedFee() {
        return needFee;
    }

    public void setNeedFee(int needFee) {
        this.needFee = needFee;
    }

    public int getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(int discountFee) {
        this.discountFee = discountFee;
    }

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getTaxFee() {
        return taxFee;
    }

    public void setTaxFee(int taxFee) {
        this.taxFee = taxFee;
    }

    public int getLeastFee() {
        return leastFee;
    }

    public void setLeastFee(int leastFee) {
        this.leastFee = leastFee;
    }

    public boolean isUsePromotion() {
        return usePromotion;
    }

    public void setUsePromotion(boolean usePromotion) {
        this.usePromotion = usePromotion;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getOutFee() {
        return outFee;
    }

    public void setOutFee(int outFee) {
        this.outFee = outFee;
    }

    public CloudCashBill() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeInt(this.fee);
        dest.writeInt(this.originFee);
        dest.writeInt(this.paidFee);
        dest.writeInt(this.needFee);
        dest.writeInt(this.discountFee);
        dest.writeInt(this.serviceFee);
        dest.writeInt(this.taxFee);
        dest.writeInt(this.leastFee);
        dest.writeByte(this.usePromotion ? (byte) 1 : (byte) 0);
        dest.writeLong(this.modifyTime);
        dest.writeInt(this.outFee);
    }

    protected CloudCashBill(Parcel in) {
        this.orderId = in.readString();
        this.fee = in.readInt();
        this.originFee = in.readInt();
        this.paidFee = in.readInt();
        this.needFee = in.readInt();
        this.discountFee = in.readInt();
        this.serviceFee = in.readInt();
        this.taxFee = in.readInt();
        this.leastFee = in.readInt();
        this.usePromotion = in.readByte() != 0;
        this.modifyTime = in.readLong();
        this.outFee = in.readInt();
    }

    public static final Creator<CloudCashBill> CREATOR = new Creator<CloudCashBill>() {
        @Override
        public CloudCashBill createFromParcel(Parcel source) {
            return new CloudCashBill(source);
        }

        @Override
        public CloudCashBill[] newArray(int size) {
            return new CloudCashBill[size];
        }
    };
}
