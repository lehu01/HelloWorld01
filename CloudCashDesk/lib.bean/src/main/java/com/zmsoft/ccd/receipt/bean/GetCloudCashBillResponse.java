package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DangGui
 * @create 2017/6/13.
 */

public class GetCloudCashBillResponse implements Parcelable {
    /**
     * 云收银账单金额详情，不可为null
     */
    private CloudCashBill cloudCashBill;
    /**
     * 云收银账单优惠详情列表，可以为空
     */
    private List<Promotion> promotions;
    /**
     * 云收银账单支付详情列表，可以为空
     */
    private List<Pay> pays;
    /**
     * 云收银账单支付方式列表，可以为空
     */
    private List<KindPay> kindPays;

    public CloudCashBill getCloudCashBill() {
        return cloudCashBill;
    }

    public void setCloudCashBill(CloudCashBill cloudCashBill) {
        this.cloudCashBill = cloudCashBill;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Pay> getPays() {
        return pays;
    }

    public void setPays(List<Pay> pays) {
        this.pays = pays;
    }

    public List<KindPay> getKindPays() {
        return kindPays;
    }

    public void setKindPays(List<KindPay> kindPays) {
        this.kindPays = kindPays;
    }

    public GetCloudCashBillResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.cloudCashBill, flags);
        dest.writeTypedList(this.promotions);
        dest.writeList(this.pays);
        dest.writeTypedList(this.kindPays);
    }

    protected GetCloudCashBillResponse(Parcel in) {
        this.cloudCashBill = in.readParcelable(CloudCashBill.class.getClassLoader());
        this.promotions = in.createTypedArrayList(Promotion.CREATOR);
        this.pays = new ArrayList<Pay>();
        in.readList(this.pays, Pay.class.getClassLoader());
        this.kindPays = in.createTypedArrayList(KindPay.CREATOR);
    }

    public static final Creator<GetCloudCashBillResponse> CREATOR = new Creator<GetCloudCashBillResponse>() {
        @Override
        public GetCloudCashBillResponse createFromParcel(Parcel source) {
            return new GetCloudCashBillResponse(source);
        }

        @Override
        public GetCloudCashBillResponse[] newArray(int size) {
            return new GetCloudCashBillResponse[size];
        }
    };
}
