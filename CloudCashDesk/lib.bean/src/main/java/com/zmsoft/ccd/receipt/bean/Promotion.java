package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 云收银账单优惠详情
 *
 * @author DangGui
 * @create 2017/6/13.
 */
public class Promotion implements Parcelable {
    //会员卡存入卡号;优惠券存入优惠券id；
    private String id;
    //优惠类型，目前版本只有“会员卡”这种类型
    private short type;
    //优惠名称（展示内容）
    private String name;
    //优惠金额（分）
    private int fee;
    //折扣率，比如8.5折，显示85
    private int ratio;
    //优惠类型 @see
    /**
     * @see com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper.DiscountMode
     */
    private int mode;
    /**
     * 折扣原因
     */
    private String memo;
    /**
     * 不允许打折的商品也允许打折（选填），true表示不允许打折的商品也允许打折，false反之
     */
    private boolean forceRatio;
    private String className;
    /**
     * 状态，1表示成功，-1表示失败，0表示正在核销
     */
    private int status;
    /**
     * 失败原因
     */
    private String verifyMessage;

    // 本地传参数：会员卡优惠的时候使用
    private String cardId; // 会员卡id
    private String cardEntityId; // 会员卡系统id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isForceRatio() {
        return forceRatio;
    }

    public void setForceRatio(boolean forceRatio) {
        this.forceRatio = forceRatio;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVerifyMessage() {
        return verifyMessage;
    }

    public void setVerifyMessage(String verifyMessage) {
        this.verifyMessage = verifyMessage;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardEntityId() {
        return cardEntityId;
    }

    public void setCardEntityId(String cardEntityId) {
        this.cardEntityId = cardEntityId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeInt(this.fee);
        dest.writeInt(this.ratio);
        dest.writeInt(this.mode);
        dest.writeString(this.memo);
        dest.writeByte(this.forceRatio ? (byte) 1 : (byte) 0);
        dest.writeString(this.className);
        dest.writeInt(this.status);
        dest.writeString(this.verifyMessage);
        dest.writeString(this.cardId);
        dest.writeString(this.cardEntityId);
    }

    public Promotion() {
    }

    protected Promotion(Parcel in) {
        this.id = in.readString();
        this.type = (short) in.readInt();
        this.name = in.readString();
        this.fee = in.readInt();
        this.ratio = in.readInt();
        this.mode = in.readInt();
        this.memo = in.readString();
        this.forceRatio = in.readByte() != 0;
        this.className = in.readString();
        this.status = in.readInt();
        this.verifyMessage = in.readString();
        this.cardId = in.readString();
        this.cardEntityId = in.readString();
    }

    public static final Creator<Promotion> CREATOR = new Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel source) {
            return new Promotion(source);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };
}
