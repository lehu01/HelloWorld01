package com.zmsoft.ccd.lib.bean.user;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 30/10/2017.
 */
public class ChannelInfoRequest implements Parcelable {
    @Retention(SOURCE)
    @IntDef({EVENT_CODE_ACTIVE, EVENT_CODE_LOGIN, EVENT_CODE_REGISTER})
    public @interface EventCode {
    }
    public static final int EVENT_CODE_ACTIVE = 2;
    public static final int EVENT_CODE_LOGIN = 3;
    public static final int EVENT_CODE_REGISTER = 4;

    public static final Creator<ChannelInfoRequest> CREATOR = new Creator<ChannelInfoRequest>() {
        @Override
        public ChannelInfoRequest createFromParcel(Parcel source) {
            return new ChannelInfoRequest(source);
        }

        @Override
        public ChannelInfoRequest[] newArray(int size) {
            return new ChannelInfoRequest[size];
        }
    };
    private String ip;
    private String mac;
    private int channel;
    private String channelString;
    private String ua;
    private String app_name;
    private String app_code;
    private String version;
    //系统类型:android-1
    private int system_type;
    //事件类型:激活-2
    private int event;
    private String entity_id;
    private String mobile;

    private ChannelInfoRequest(Builder builder) {
        ip = builder.ip;
        mac = builder.mac;
        initChannel(builder.channelString);
        channelString = builder.channelString;
        ua = builder.ua;
        app_name = builder.app_name;
        app_code = builder.app_code;
        version = builder.version;
        system_type = builder.system_type;
        event = builder.event;
        entity_id = builder.entity_id;
        mobile = builder.mobile;
    }

    public ChannelInfoRequest() {
    }

    protected ChannelInfoRequest(Parcel in) {
        this.ip = in.readString();
        this.mac = in.readString();
        this.channel = in.readInt();
        this.channelString = in.readString();
        this.ua = in.readString();
        this.app_name = in.readString();
        this.app_code = in.readString();
        this.version = in.readString();
        this.system_type = in.readInt();
        this.event = in.readInt();
        this.entity_id = in.readString();
        this.mobile = in.readString();
    }

    private void initChannel(String channelString) {
        if (TextUtils.isEmpty(channelString)) {
            this.channel = 1;
            return;
        }
        switch (channelString) {
            case "2dfire":
                this.channel = 1;
                break;
            case "toutiao":
                this.channel = 2;
                break;
            case "xiaomi":
                this.channel = 3;
                break;
            case "wandoujia":
                this.channel = 4;
                break;
            case "baidu":
                this.channel = 5;
                break;
            case "360cn":
                this.channel = 6;
                break;
            case "myapp":
                this.channel = 7;
                break;
            case "huawei":
                this.channel = 8;
                break;
            case "tonglian":
                this.channel = 9;
                break;
            case "coolApk":
                this.channel = 10;
                break;
            case "vivo":
                this.channel = 11;
                break;
            case "oppo":
                this.channel = 12;
                break;
            default:
                this.channel = 1;
                break;
        }
    }

    public String getIpAddress() {
        return ip;
    }

    public void setIpAddress(String ipAddress) {
        this.ip = ipAddress;
    }

    public String getMacAddress() {
        return mac;
    }

    public void setMacAddress(String macAddress) {
        this.mac = macAddress;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getChannelString() {
        return channelString;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getAppName() {
        return "CloudCash";
    }

    public String getAppode() {
        return app_code;
    }

    public String getVersion() {
        return version;
    }

    public int getSystemType() {
        return 1;
    }

    public String getEndityId() {
        return entity_id;
    }

    public void setEndityId(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ip);
        dest.writeString(this.mac);
        dest.writeInt(this.channel);
        dest.writeString(this.channelString);
        dest.writeString(this.ua);
        dest.writeString(this.app_name);
        dest.writeString(this.app_code);
        dest.writeString(this.version);
        dest.writeInt(this.system_type);
        dest.writeInt(this.event);
        dest.writeString(this.entity_id);
        dest.writeString(this.mobile);
    }



    public static final class Builder {
        private String ip;
        private String mac;
        private int channel = -1;
        private String channelString;
        private String ua;
        private String app_name = "二维火手机收银";
        private String app_code;
        private String version;
        private int system_type = 1;
        private int event = 2;
        private String entity_id;
        private String mobile;

        public Builder() {
        }

        public Builder(ChannelInfoRequest copy) {
            this.channel = copy.getChannel();
            this.channelString = copy.getChannelString();
            this.ua = copy.getUa();
            this.version = copy.getVersion();
            this.mobile = copy.getMobile();
        }

        public Builder ip(String val) {
            ip = val;
            return this;
        }

        public Builder mac(String val) {
            mac = val;
            return this;
        }

        public Builder channel(int val) {
            channel = val;
            return this;
        }

        public Builder channelString(String val) {
            channelString = val;
            return this;
        }

        public Builder ua(String val) {
            ua = val;
            return this;
        }

        public Builder app_name(String val) {
            app_name = val;
            return this;
        }

        public Builder app_code(String val) {
            app_code = val;
            return this;
        }

        public Builder version(String val) {
            version = val;
            return this;
        }

        public Builder system_type(int val) {
            system_type = val;
            return this;
        }

        public Builder event(@EventCode int val) {
            event = val;
            return this;
        }

        public Builder entityId(String val) {
            entity_id = val;
            return this;
        }

        public Builder mobile(String val) {
            mobile = val;
            return this;
        }

        public ChannelInfoRequest build() {
            return new ChannelInfoRequest(this);
        }
    }
}
