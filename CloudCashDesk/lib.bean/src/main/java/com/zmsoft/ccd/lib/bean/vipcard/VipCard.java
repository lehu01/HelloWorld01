package com.zmsoft.ccd.lib.bean.vipcard;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/27 10:42
 */
public class VipCard extends Base {


    /**
     * balance : 0
     * giftBalance : 0
     * degree : 0
     * realBalance : 0
     * ratio : 100
     * kindCardName : 门店1会员价
     * code : 916829
     * mode : 1
     */

    private double balance; // 余额(单位:元)
    private int giftBalance; // 累计赠送金额(单位:元)
    private int degree; // 积分
    private int realBalance; // 累计充值实际金额(单位:元)
    private int ratio; // 折扣率(按100为满单位)
    private String kindCardName; // 会员卡类型名称
    private String code; // 卡号
    private int mode; // 优惠方式: 1/使用会员价; 2/使用打折方案; 3/设置折扣率
    private String shopEntityName; // 门店名
    private String cardId; // 会员卡id
    private String cardEntityId; // 会员卡系统id
    private String customerRegisterId; // 二维火用户id

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getGiftBalance() {
        return giftBalance;
    }

    public void setGiftBalance(int giftBalance) {
        this.giftBalance = giftBalance;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getRealBalance() {
        return realBalance;
    }

    public void setRealBalance(int realBalance) {
        this.realBalance = realBalance;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public String getKindCardName() {
        return kindCardName;
    }

    public void setKindCardName(String kindCardName) {
        this.kindCardName = kindCardName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getShopEntityName() {
        return shopEntityName;
    }

    public void setShopEntityName(String shopEntityName) {
        this.shopEntityName = shopEntityName;
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

    public String getCustomerRegisterId() {
        return customerRegisterId;
    }

    public void setCustomerRegisterId(String customerRegisterId) {
        this.customerRegisterId = customerRegisterId;
    }
}
