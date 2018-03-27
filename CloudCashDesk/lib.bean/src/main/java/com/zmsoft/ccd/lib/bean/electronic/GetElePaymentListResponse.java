package com.zmsoft.ccd.lib.bean.electronic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/8/19.
 */

public class GetElePaymentListResponse implements Parcelable {
    /**
     * 电子支付明细列表
     */
    private List<ElePayment> elePayments;

    public List<ElePayment> getElePayments() {
        return elePayments;
    }

    public void setElePayments(List<ElePayment> elePayments) {
        this.elePayments = elePayments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.elePayments);
    }

    public GetElePaymentListResponse() {
    }

    protected GetElePaymentListResponse(Parcel in) {
        this.elePayments = in.createTypedArrayList(ElePayment.CREATOR);
    }

    public static final Parcelable.Creator<GetElePaymentListResponse> CREATOR = new Parcelable.Creator<GetElePaymentListResponse>() {
        @Override
        public GetElePaymentListResponse createFromParcel(Parcel source) {
            return new GetElePaymentListResponse(source);
        }

        @Override
        public GetElePaymentListResponse[] newArray(int size) {
            return new GetElePaymentListResponse[size];
        }
    };
}
