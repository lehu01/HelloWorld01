package com.zmsoft.ccd.module.receipt.receipt.model;

import android.os.Parcel;

import com.zmsoft.ccd.receipt.bean.Fund;

/**
 * 云收银收款-卡支付
 *
 * @author DangGui
 * @create 2017/6/13.
 */
public class CardFund extends Fund {
    //会员卡id
    private String cardId;
    //会员卡系统id
    private String cardEntityId;
    //二维火用户id
    private String customerRegisterId;
    //会员卡密码
    private String secret;

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

    public String getCustomerRegisterId() {
        return customerRegisterId;
    }

    public void setCustomerRegisterId(String customerRegisterId) {
        this.customerRegisterId = customerRegisterId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.cardId);
        dest.writeString(this.cardEntityId);
        dest.writeString(this.customerRegisterId);
        dest.writeString(this.secret);
    }

    public CardFund() {
    }

    protected CardFund(Parcel in) {
        super(in);
        this.cardId = in.readString();
        this.cardEntityId = in.readString();
        this.customerRegisterId = in.readString();
        this.secret = in.readString();
    }

    public static final Creator<CardFund> CREATOR = new Creator<CardFund>() {
        @Override
        public CardFund createFromParcel(Parcel source) {
            return new CardFund(source);
        }

        @Override
        public CardFund[] newArray(int size) {
            return new CardFund[size];
        }
    };
}
