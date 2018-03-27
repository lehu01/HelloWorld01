package com.zmsoft.ccd.takeout.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/9/1.
 */

public class GetDeliveryOrderListResponse implements Parcelable {
    private List<DeliveryOrderVo> deliveryOrderVos;

    public List<DeliveryOrderVo> getDeliveryOrderVos() {
        return deliveryOrderVos;
    }

    public void setDeliveryOrderVos(List<DeliveryOrderVo> deliveryOrderVos) {
        this.deliveryOrderVos = deliveryOrderVos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.deliveryOrderVos);
    }

    public GetDeliveryOrderListResponse() {
    }

    protected GetDeliveryOrderListResponse(Parcel in) {
        this.deliveryOrderVos = in.createTypedArrayList(DeliveryOrderVo.CREATOR);
    }

    public static final Parcelable.Creator<GetDeliveryOrderListResponse> CREATOR = new Parcelable.Creator<GetDeliveryOrderListResponse>() {
        @Override
        public GetDeliveryOrderListResponse createFromParcel(Parcel source) {
            return new GetDeliveryOrderListResponse(source);
        }

        @Override
        public GetDeliveryOrderListResponse[] newArray(int size) {
            return new GetDeliveryOrderListResponse[size];
        }
    };
}
