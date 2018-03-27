package com.zmsoft.ccd.lib.bean.message;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author DangGui
 * @create 2017/8/29.
 */

public class TakeoutMsgDetailResponse implements Parcelable {
    private TakeoutOrderVo takeoutOrderVo;
    private int msgState;

    public TakeoutOrderVo getTakeoutOrderVo() {
        return takeoutOrderVo;
    }

    public void setTakeoutOrderVo(TakeoutOrderVo takeoutOrderVo) {
        this.takeoutOrderVo = takeoutOrderVo;
    }

    public int getMsgState() {
        return msgState;
    }

    public void setMsgState(int msgState) {
        this.msgState = msgState;
    }

    public TakeoutMsgDetailResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.takeoutOrderVo, flags);
        dest.writeInt(this.msgState);
    }

    protected TakeoutMsgDetailResponse(Parcel in) {
        this.takeoutOrderVo = in.readParcelable(TakeoutOrderVo.class.getClassLoader());
        this.msgState = in.readInt();
    }

    public static final Creator<TakeoutMsgDetailResponse> CREATOR = new Creator<TakeoutMsgDetailResponse>() {
        @Override
        public TakeoutMsgDetailResponse createFromParcel(Parcel source) {
            return new TakeoutMsgDetailResponse(source);
        }

        @Override
        public TakeoutMsgDetailResponse[] newArray(int size) {
            return new TakeoutMsgDetailResponse[size];
        }
    };
}
