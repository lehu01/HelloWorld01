package com.zmsoft.ccd.takeout.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zmsoft.ccd.menu.business.MenuConstant;


import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/23.
 */

public class Takeout implements Parcelable {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单最后修改时间
     */
    private long modifyTime;

    /**
     * 订单来源
     *
     * @see TakeoutConstants.OrderFrom
     */
    private short orderFrom;
    /**
     * 配送类型
     *
     * @see TakeoutConstants.SendType
     */
    private short sendType;

    /**
     * @see TakeoutConstants.ReserveStatus
     */
    private short reserveStatus;


    /**
     * 订单状态
     *
     * @see TakeoutConstants.OrderStatus
     */
    private short status;

    /**
     * 收货人
     */
    private String name;

    /**
     * 收货人手机号码
     */
    private String mobile;

    /**
     * 收货人地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    private String sendTime;

    /**
     * 单号
     */
    private int code;

    /**
     * 距离（KM）
     */
    private double distance;

    /**
     * 订单详情信息
     */
    private TakeoutOrderDetail takeoutOrderDetailVo;

    /**
     * 订单所在组（按日分组），总计下单数量
     */
    private int groupTotalNum;

    /**
     * 是否需要继续撤单,0表示否，1表示是
     */
    private int needCancelAgain;

    public int getGroupTotalNum() {
        return groupTotalNum;
    }

    public void setGroupTotalNum(int groupTotalNum) {
        this.groupTotalNum = groupTotalNum;
    }

    public void setNeedCancelAgain(int needCancelAgain) {
        this.needCancelAgain = needCancelAgain;
    }

    public int getNeedCancelAgain() {
        return needCancelAgain;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public TakeoutOrderDetail getTakeoutOrderDetailVo() {
        return takeoutOrderDetailVo;
    }

    public void setTakeoutOrderDetailVo(TakeoutOrderDetail takeoutOrderDetailVo) {
        this.takeoutOrderDetailVo = takeoutOrderDetailVo;
    }


    public static class TakeoutOrderDetail implements Parcelable {

        /**
         * 配送平台编码(自配送=P000)
         */
        private String deliveryPlatformCode;

        /**
         * 用餐人数
         */
        private int peopleCount;

        /**
         * 备注
         */
        private String memo;

        /**
         * 下单时间
         */
        private String createTime;

        /**
         * 接单/开单时间
         */
        private String openTime;

        /**
         * 流水号
         */
        private String globalCode;

        /**
         * 流水号
         */
        private String innerCode;

        /**
         * 外送费
         */
        private double outFee;

        /**
         * 送货人
         */
        private String courierName;

        /**
         * 配送平台
         */
        private String deliveryPlatform;

        /**
         * 发起配送时间
         */
        private String deliveryTime;

        /**
         * 送货人联系方式
         */
        private String courierPhone;

        private List<TakeoutInstance> takeoutInstanceVoList;


        private List<TakeoutPay> takeoutPayVoList;

        /**
         * 商品数量
         */
        private int instanceNum;

        /**
         * 第三方外卖ID
         */
        private String daySeq;

        private List<DeliveryInfo> takeoutDeliveryInfoVos;

        /**
         * 运送单
         */
        private String expressCode;

        public String getExpressCode() {
            return expressCode;
        }

        public void setExpressCode(String expressCode) {
            this.expressCode = expressCode;
        }

        public List<DeliveryInfo> getTakeoutDeliveryInfoVos() {
            return takeoutDeliveryInfoVos;
        }

        public void setTakeoutDeliveryInfoVos(List<DeliveryInfo> takeoutDeliveryInfoVos) {
            this.takeoutDeliveryInfoVos = takeoutDeliveryInfoVos;
        }

        public String getDeliveryPlatformCode() {
            return deliveryPlatformCode;
        }

        public void setDeliveryPlatformCode(String deliveryPlatformCode) {
            this.deliveryPlatformCode = deliveryPlatformCode;
        }

        public int getInstanceNum() {
            return instanceNum;
        }

        public void setInstanceNum(int instanceNum) {
            this.instanceNum = instanceNum;
        }

        public String getDaySeq() {
            return daySeq;
        }

        public void setDaySeq(String daySeq) {
            this.daySeq = daySeq;
        }

        public String getDeliveryPlatform() {
            return deliveryPlatform;
        }

        public void setDeliveryPlatform(String deliveryPlatform) {
            this.deliveryPlatform = deliveryPlatform;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public int getPeopleCount() {
            return peopleCount;
        }

        public void setPeopleCount(int peopleCount) {
            this.peopleCount = peopleCount;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getGlobalCode() {
            return globalCode;
        }

        public void setGlobalCode(String globalCode) {
            this.globalCode = globalCode;
        }

        public String getInnerCode() {
            return innerCode;
        }

        public void setInnerCode(String innerCode) {
            this.innerCode = innerCode;
        }

        public double getOutFee() {
            return outFee;
        }

        public void setOutFee(double outFee) {
            this.outFee = outFee;
        }

        public String getCourierName() {
            return courierName;
        }

        public void setCourierName(String courierName) {
            this.courierName = courierName;
        }

        public String getCourierPhone() {
            return courierPhone;
        }

        public void setCourierPhone(String courierPhone) {
            this.courierPhone = courierPhone;
        }

        public List<TakeoutInstance> getTakeoutInstanceVoList() {
            return takeoutInstanceVoList;
        }

        public void setTakeoutInstanceVoList(List<TakeoutInstance> takeoutInstanceVoList) {
            this.takeoutInstanceVoList = takeoutInstanceVoList;
        }

        public List<TakeoutPay> getTakeoutPayVoList() {
            return takeoutPayVoList;
        }

        public void setTakeoutPayVoList(List<TakeoutPay> takeoutPayVoList) {
            this.takeoutPayVoList = takeoutPayVoList;
        }

        public TakeoutOrderDetail() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.deliveryPlatformCode);
            dest.writeInt(this.peopleCount);
            dest.writeString(this.memo);
            dest.writeString(this.createTime);
            dest.writeString(this.openTime);
            dest.writeString(this.globalCode);
            dest.writeString(this.innerCode);
            dest.writeDouble(this.outFee);
            dest.writeString(this.courierName);
            dest.writeString(this.deliveryPlatform);
            dest.writeString(this.deliveryTime);
            dest.writeString(this.courierPhone);
            dest.writeTypedList(this.takeoutInstanceVoList);
            dest.writeTypedList(this.takeoutPayVoList);
            dest.writeInt(this.instanceNum);
            dest.writeString(this.daySeq);
            dest.writeTypedList(this.takeoutDeliveryInfoVos);
            dest.writeString(this.expressCode);
        }

        protected TakeoutOrderDetail(Parcel in) {
            this.deliveryPlatformCode = in.readString();
            this.peopleCount = in.readInt();
            this.memo = in.readString();
            this.createTime = in.readString();
            this.openTime = in.readString();
            this.globalCode = in.readString();
            this.innerCode = in.readString();
            this.outFee = in.readDouble();
            this.courierName = in.readString();
            this.deliveryPlatform = in.readString();
            this.deliveryTime = in.readString();
            this.courierPhone = in.readString();
            this.takeoutInstanceVoList = in.createTypedArrayList(TakeoutInstance.CREATOR);
            this.takeoutPayVoList = in.createTypedArrayList(TakeoutPay.CREATOR);
            this.instanceNum = in.readInt();
            this.daySeq = in.readString();
            this.takeoutDeliveryInfoVos = in.createTypedArrayList(DeliveryInfo.CREATOR);
            this.expressCode = in.readString();
        }

        public static final Creator<TakeoutOrderDetail> CREATOR = new Creator<TakeoutOrderDetail>() {
            @Override
            public TakeoutOrderDetail createFromParcel(Parcel source) {
                return new TakeoutOrderDetail(source);
            }

            @Override
            public TakeoutOrderDetail[] newArray(int size) {
                return new TakeoutOrderDetail[size];
            }
        };
    }


    public static class TakeoutPay implements Parcelable {
        /**
         * 支付类型
         */
        private int type;
        /**
         * 支付金额
         */
        private double fee;
        /**
         * 支付类型名称
         */
        private String name;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.type);
            dest.writeDouble(this.fee);
            dest.writeString(this.name);
        }

        public TakeoutPay() {
        }

        protected TakeoutPay(Parcel in) {
            this.type = in.readInt();
            this.fee = in.readDouble();
            this.name = in.readString();
        }

        public static final Creator<TakeoutPay> CREATOR = new Creator<TakeoutPay>() {
            @Override
            public TakeoutPay createFromParcel(Parcel source) {
                return new TakeoutPay(source);
            }

            @Override
            public TakeoutPay[] newArray(int size) {
                return new TakeoutPay[size];
            }
        };
    }

    public static class TakeoutInstance implements Parcelable {
        /**
         * 菜名
         */
        private String name;
        /**
         * 状态
         */
        private int status;

        /**
         * 做法
         */
        private String makeName;
        /**
         * 规格
         */
        private String specDetailName;
        /**
         * 备注
         */
        private String memo;
        /**
         * 数量
         */
        private double num;

        private double accountNum;
        /**
         * 折后金额
         */
        private double ratioFee;
        /**
         * 菜的类型
         * 普通菜   1;
         * 套菜   2;
         * 自定义菜   3;
         * 自定义套菜  4;
         * 加料菜   5;
         *
         * @see MenuConstant.CartFoodKind
         */
        private int kind;
        /**
         * 点菜单位
         */
        private String unit;

        /**
         * 结账单位
         */
        private String accountUnit;

        private List<TakeoutInstance> childTakeoutInstances;

        /**
         * 商品类型
         * 0, "食物"
         * 1, "打包盒"
         */
        private int type;

        /**
         * 条形码
         */
        private String code;

        /**
         * 双单位标识，用以判断零售称重商品
         */
        private int doubleUnits;

        public void setType(int type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String barCode) {
            this.code = code;
        }

        public List<TakeoutInstance> getChildTakeoutInstances() {
            return childTakeoutInstances;
        }

        public void setChildTakeoutInstances(List<TakeoutInstance> childTakeoutInstances) {
            this.childTakeoutInstances = childTakeoutInstances;
        }

        public double getAccountNum() {
            return accountNum;
        }

        public void setAccountNum(double accountNum) {
            this.accountNum = accountNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMakeName() {
            return makeName;
        }

        public void setMakeName(String makeName) {
            this.makeName = makeName;
        }

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }

        public double getRatioFee() {
            return ratioFee;
        }

        public void setRatioFee(double ratioFee) {
            this.ratioFee = ratioFee;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSpecDetailName() {
            return specDetailName;
        }

        public void setSpecDetailName(String specDetailName) {
            this.specDetailName = specDetailName;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public int getKind() {
            return kind;
        }

        public void setKind(int kind) {
            this.kind = kind;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getAccountUnit() {
            return accountUnit;
        }

        public void setAccountUnit(String accountUnit) {
            this.accountUnit = accountUnit;
        }

        public int getType() {
            return type;
        }

        public int getDoubleUnits() {
            return doubleUnits;
        }

        public void setDoubleUnits(int doubleUnits) {
            this.doubleUnits = doubleUnits;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeInt(this.status);
            dest.writeString(this.makeName);
            dest.writeString(this.specDetailName);
            dest.writeString(this.memo);
            dest.writeDouble(this.num);
            dest.writeDouble(this.accountNum);
            dest.writeDouble(this.ratioFee);
            dest.writeInt(this.kind);
            dest.writeString(this.unit);
            dest.writeString(this.accountUnit);
            dest.writeTypedList(this.childTakeoutInstances);
            dest.writeInt(this.type);
            dest.writeString(this.code);
            dest.writeInt(this.doubleUnits);
        }

        protected TakeoutInstance(Parcel in) {
            this.name = in.readString();
            this.status = in.readInt();
            this.makeName = in.readString();
            this.specDetailName = in.readString();
            this.memo = in.readString();
            this.num = in.readDouble();
            this.accountNum = in.readDouble();
            this.ratioFee = in.readDouble();
            this.kind = in.readInt();
            this.unit = in.readString();
            this.accountUnit = in.readString();
            this.childTakeoutInstances = in.createTypedArrayList(TakeoutInstance.CREATOR);
            this.type = in.readInt();
            this.code = in.readString();
            this.doubleUnits = in.readInt();
        }

        public static final Creator<TakeoutInstance> CREATOR = new Creator<TakeoutInstance>() {
            @Override
            public TakeoutInstance createFromParcel(Parcel source) {
                return new TakeoutInstance(source);
            }

            @Override
            public TakeoutInstance[] newArray(int size) {
                return new TakeoutInstance[size];
            }
        };
    }

    public Takeout() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeLong(this.modifyTime);
        dest.writeInt(this.orderFrom);
        dest.writeInt(this.sendType);
        dest.writeInt(this.reserveStatus);
        dest.writeInt(this.status);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.address);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.sendTime);
        dest.writeInt(this.code);
        dest.writeDouble(this.distance);
        dest.writeParcelable(this.takeoutOrderDetailVo, flags);
        dest.writeInt(this.groupTotalNum);
        dest.writeInt(this.needCancelAgain);
    }

    protected Takeout(Parcel in) {
        this.orderId = in.readString();
        this.modifyTime = in.readLong();
        this.orderFrom = (short) in.readInt();
        this.sendType = (short) in.readInt();
        this.reserveStatus = (short) in.readInt();
        this.status = (short) in.readInt();
        this.name = in.readString();
        this.mobile = in.readString();
        this.address = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.sendTime = in.readString();
        this.code = in.readInt();
        this.distance = in.readDouble();
        this.takeoutOrderDetailVo = in.readParcelable(TakeoutOrderDetail.class.getClassLoader());
        this.groupTotalNum = in.readInt();
        this.needCancelAgain = in.readInt();
    }

    public static final Creator<Takeout> CREATOR = new Creator<Takeout>() {
        @Override
        public Takeout createFromParcel(Parcel source) {
            return new Takeout(source);
        }

        @Override
        public Takeout[] newArray(int size) {
            return new Takeout[size];
        }
    };
}
