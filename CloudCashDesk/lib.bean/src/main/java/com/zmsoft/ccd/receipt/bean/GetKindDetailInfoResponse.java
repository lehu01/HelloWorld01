package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/6/19.
 */

public class GetKindDetailInfoResponse implements Parcelable {
    private List<KindPayDetailDto> kindPayDetailDtoList;

    public List<KindPayDetailDto> getKindPayDetailDtoList() {
        return kindPayDetailDtoList;
    }

    public void setKindPayDetailDtoList(List<KindPayDetailDto> kindPayDetailDtoList) {
        this.kindPayDetailDtoList = kindPayDetailDtoList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.kindPayDetailDtoList);
    }

    public GetKindDetailInfoResponse() {
    }

    protected GetKindDetailInfoResponse(Parcel in) {
        this.kindPayDetailDtoList = in.createTypedArrayList(KindPayDetailDto.CREATOR);
    }

    public static final Parcelable.Creator<GetKindDetailInfoResponse> CREATOR = new Parcelable.Creator<GetKindDetailInfoResponse>() {
        @Override
        public GetKindDetailInfoResponse createFromParcel(Parcel source) {
            return new GetKindDetailInfoResponse(source);
        }

        @Override
        public GetKindDetailInfoResponse[] newArray(int size) {
            return new GetKindDetailInfoResponse[size];
        }
    };
}
