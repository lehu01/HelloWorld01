package com.zmsoft.ccd.lib.bean.message;


import android.os.Parcel;
import android.os.Parcelable;

public class TakeoutOrderVo implements Parcelable {

    /**
     * 来源
     */
    private short orderFrom;

    /**
     * 配送方式
     */
    private short sendType;

    /**
     * 类型
     */
    private short reserveStatus;

    /**
     * 待配送（需标注所有待配送订单数）、等候配送、配送中、已送达、未下厨、配送异常
     */
    private short status;
    /**
     * 审核状态 0/无需审核;1/下单待审核;2/撤单待审核
     */
    private short auditstatus;
    //配送时间
    private String sendTime;

    //送达时间
    private String reachTime;

    //收货人
    private String name;

    //收货号码
    private String mobile;

    //收获地址
    private String address;

    //收货地址经度
    private double longitude;

    //收货地址纬度
    private double latitude;

    //收货人与商家的直线距离
    private double distance;

    //单号
    private int code;

    //所在按天分组的订单总数
    private int groupTotalNum;

    //外卖单详情
    private TakeoutOrderDetailVo takeoutOrderDetailVo;

    //版本号
    private int lastVer;

    public short getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(short orderFrom) {
        this.orderFrom = orderFrom;
    }

    public short getSendType() {
        return sendType;
    }

    public void setSendType(short sendType) {
        this.sendType = sendType;
    }

    public short getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(short reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getAuditstatus() {
        return auditstatus;
    }

    public void setAuditstatus(short auditstatus) {
        this.auditstatus = auditstatus;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReachTime() {
        return reachTime;
    }

    public void setReachTime(String reachTime) {
        this.reachTime = reachTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getGroupTotalNum() {
        return groupTotalNum;
    }

    public void setGroupTotalNum(int groupTotalNum) {
        this.groupTotalNum = groupTotalNum;
    }

    public TakeoutOrderDetailVo getTakeoutOrderDetailVo() {
        return takeoutOrderDetailVo;
    }

    public void setTakeoutOrderDetailVo(TakeoutOrderDetailVo takeoutOrderDetailVo) {
        this.takeoutOrderDetailVo = takeoutOrderDetailVo;
    }

    public int getLastVer() {
        return lastVer;
    }

    public void setLastVer(int lastVer) {
        this.lastVer = lastVer;
    }

    public TakeoutOrderVo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.orderFrom);
        dest.writeInt(this.sendType);
        dest.writeInt(this.reserveStatus);
        dest.writeInt(this.status);
        dest.writeInt(this.auditstatus);
        dest.writeString(this.sendTime);
        dest.writeString(this.reachTime);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.address);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.distance);
        dest.writeInt(this.code);
        dest.writeInt(this.groupTotalNum);
        dest.writeParcelable(this.takeoutOrderDetailVo, flags);
        dest.writeInt(this.lastVer);
    }

    protected TakeoutOrderVo(Parcel in) {
        this.orderFrom = (short) in.readInt();
        this.sendType = (short) in.readInt();
        this.reserveStatus = (short) in.readInt();
        this.status = (short) in.readInt();
        this.auditstatus = (short) in.readInt();
        this.sendTime = in.readString();
        this.reachTime = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.address = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.distance = in.readDouble();
        this.code = in.readInt();
        this.groupTotalNum = in.readInt();
        this.takeoutOrderDetailVo = in.readParcelable(TakeoutOrderDetailVo.class.getClassLoader());
        this.lastVer = in.readInt();
    }

    public static final Creator<TakeoutOrderVo> CREATOR = new Creator<TakeoutOrderVo>() {
        @Override
        public TakeoutOrderVo createFromParcel(Parcel source) {
            return new TakeoutOrderVo(source);
        }

        @Override
        public TakeoutOrderVo[] newArray(int size) {
            return new TakeoutOrderVo[size];
        }
    };
}
