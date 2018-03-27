package com.zmsoft.ccd.lib.bean.settings;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jihuo on 2017/2/28.
 */

public class MessageGroupSettingVO implements Parcelable {

    private String groupName;
    private String value;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupName);
        dest.writeString(this.value);
    }

    public MessageGroupSettingVO() {
    }

    protected MessageGroupSettingVO(Parcel in) {
        this.groupName = in.readString();
        this.value = in.readString();
    }

    public static final Creator<MessageGroupSettingVO> CREATOR = new Creator<MessageGroupSettingVO>() {
        @Override
        public MessageGroupSettingVO createFromParcel(Parcel source) {
            return new MessageGroupSettingVO(source);
        }

        @Override
        public MessageGroupSettingVO[] newArray(int size) {
            return new MessageGroupSettingVO[size];
        }
    };
}
