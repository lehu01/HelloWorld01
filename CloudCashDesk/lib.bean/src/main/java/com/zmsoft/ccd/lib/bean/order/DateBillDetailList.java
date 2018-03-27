package com.zmsoft.ccd.lib.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by huaixi on 2017/11/1.
 */

public class DateBillDetailList implements Parcelable {
    String date;                  //日期（格式化后的）
    long longDate;                //时间的毫秒数
    List<BillDetail> billDetails; //账单明细列表

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getLongDate() {
        return longDate;
    }

    public void setLongDate(long longDate) {
        this.longDate = longDate;
    }

    public List<BillDetail> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetail> billDetails) {
        this.billDetails = billDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeLong(this.longDate);
        dest.writeTypedList(this.billDetails);
    }

    public DateBillDetailList() {
    }

    protected DateBillDetailList(Parcel in) {
        this.date = in.readString();
        this.longDate = in.readLong();
        this.billDetails = in.createTypedArrayList(BillDetail.CREATOR);
    }

    public static final Creator<DateBillDetailList> CREATOR = new Creator<DateBillDetailList>() {
        @Override
        public DateBillDetailList createFromParcel(Parcel source) {
            return new DateBillDetailList(source);
        }

        @Override
        public DateBillDetailList[] newArray(int size) {
            return new DateBillDetailList[size];
        }
    };
}
