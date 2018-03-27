package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 账单优惠（整单打折，卡优惠） response
 *
 * @author DangGui
 * @create 2017/6/19.
 */

public class CashPromotionBillResponse implements Parcelable {
    private String orderId;
    private String entityId;
    private List<Promotion> promotions;
    /**
     * 订单最后修改时间
     */
    private long modifyTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.entityId);
        dest.writeTypedList(this.promotions);
        dest.writeLong(this.modifyTime);
    }

    public CashPromotionBillResponse() {
    }

    protected CashPromotionBillResponse(Parcel in) {
        this.orderId = in.readString();
        this.entityId = in.readString();
        this.promotions = in.createTypedArrayList(Promotion.CREATOR);
        this.modifyTime = in.readLong();
    }

    public static final Creator<CashPromotionBillResponse> CREATOR = new Creator<CashPromotionBillResponse>() {
        @Override
        public CashPromotionBillResponse createFromParcel(Parcel source) {
            return new CashPromotionBillResponse(source);
        }

        @Override
        public CashPromotionBillResponse[] newArray(int size) {
            return new CashPromotionBillResponse[size];
        }
    };
}
