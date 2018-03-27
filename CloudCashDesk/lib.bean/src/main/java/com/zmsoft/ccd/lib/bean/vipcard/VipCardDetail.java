package com.zmsoft.ccd.lib.bean.vipcard;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/13 11:48
 */
public class VipCardDetail extends Base {

    /**
     * 会员卡优惠方式
     * 1.会员价
     * 2.打折方案
     * 3.折扣
     */
    public static final int VIP_PRICE = 1;
    public static final int DISCOUNT_WAY = 2;
    public static final int RATIO = 3;

    /**
     * 会员卡类型：
     * 1.单店
     * 2.连锁
     */
    public static final int CARD_TYPE_SINGLE = 0;
    public static final int CARD_TYPE_CHAIN = 1;

    /**
     * mode : 3
     * realBalance : 1510
     * customerRegisterId : 999297035b5621e5015b5668a3a3000b
     * code : 054660
     * balance : 61.85
     * cardId : 999297035b5621e5015b5668a462000d
     * cardEntityId : 99929703
     * degree : 0
     * giftBalance : 0
     * kindCardName : 门店1普卡
     * ratio : 90
     */

    private int mode; // 优惠方式: 1/使用会员价; 2/使用打折方案; 3/设置折扣率
    private int realBalance; // 累计充值实际金额(单位:元)
    private String customerRegisterId; // 二维火用户id
    private String code; // 卡号
    private double balance; // 余额(单位:元)
    private String cardId; // 会员卡id
    private String cardEntityId; // 会员卡系统id
    private int degree; // 积分
    private int giftBalance; // 累计赠送金额(单位:元)
    private String kindCardName; // 会员卡类型名称
    private int ratio; // 折扣率(按100为满单位)
    private String shopEntityName; // 门店名
    private double actPayFee; // 实付金额
    private double needPayFee; // 应付金额
    private boolean isNeedPwd; // 是否需要密码
    private String mobile; // 手机号
    private String customerName; // 会员名称
    private String filePath; // 会员卡图片
    private int cardType; // 会员卡类型

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getRealBalance() {
        return realBalance;
    }

    public void setRealBalance(int realBalance) {
        this.realBalance = realBalance;
    }

    public String getCustomerRegisterId() {
        return customerRegisterId;
    }

    public void setCustomerRegisterId(String customerRegisterId) {
        this.customerRegisterId = customerRegisterId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getGiftBalance() {
        return giftBalance;
    }

    public void setGiftBalance(int giftBalance) {
        this.giftBalance = giftBalance;
    }

    public String getKindCardName() {
        return kindCardName;
    }

    public void setKindCardName(String kindCardName) {
        this.kindCardName = kindCardName;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public String getShopEntityName() {
        return shopEntityName;
    }

    public void setShopEntityName(String shopEntityName) {
        this.shopEntityName = shopEntityName;
    }

    public double getActPayFee() {
        return actPayFee;
    }

    public void setActPayFee(double actPayFee) {
        this.actPayFee = actPayFee;
    }

    public double getNeedPayFee() {
        return needPayFee;
    }

    public void setNeedPayFee(double needPayFee) {
        this.needPayFee = needPayFee;
    }

    public boolean isNeedPwd() {
        return isNeedPwd;
    }

    public void setNeedPwd(boolean needPwd) {
        isNeedPwd = needPwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }
}
