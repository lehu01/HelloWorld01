package com.zmsoft.ccd.lib.bean.order.takeout;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/31 11:36
 *     desc  :
 * </pre>
 */
public class TakeoutOrderVo {


    /**
     * address : 教工路502
     * code : 10002
     * orderId : 999296455bc8a122015bc8a2a8cd0008
     * latitude : 222
     * deliveryType : 0
     * mobile : 15905517843
     * memo : 不要酸
     * source : 112
     * modifyTime : 1502936567
     * createTime : 1502886464607
     * peopleCount : 2
     * name : 小李
     * globalCode : 201708162027449326634836
     * openTime : 1502886464607
     * reserveDate : 1496306233124
     * deliveryStatus : 0
     * reserveStatus : 0
     * longitude : 111
     */

    private String address;
    private String code;
    private String orderId;
    private int latitude;
    private int deliveryType;
    private String mobile;
    private String memo;
    private int source;
    private int modifyTime;
    private String createTime;
    private int peopleCount;
    private String name;
    private String globalCode;
    private String openTime;
    private long reserveDate;
    private int deliveryStatus;
    private int reserveStatus;
    private int longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(int modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public long getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(long reserveDate) {
        this.reserveDate = reserveDate;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public int getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(int reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
}
