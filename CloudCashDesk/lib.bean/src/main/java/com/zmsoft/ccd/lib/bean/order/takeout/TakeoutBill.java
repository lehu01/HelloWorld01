package com.zmsoft.ccd.lib.bean.order.takeout;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/31 11:29
 *     desc  : 外卖单账单bill
 * </pre>
 */
public class TakeoutBill {


    /**
     * serviceFee : 1000
     * discountFee : 1000
     * needFee : 0
     * fee : 3000
     * leastFee : 0
     * taxFee : 0
     * paidFee : 3000
     * originFee : 3000
     * promotion : {"mode":3,"fee":1000,"name":"会员卡优惠","id":"e6e8f7a0aaec485ebc728994585d718e","type":0,"ratio":85}
     */

    private int serviceFee;
    private int discountFee;
    private int needFee;
    private int fee;
    private int leastFee;
    private int taxFee;
    private int paidFee;
    private int originFee;
    private TakeoutPromotion promotion;

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(int discountFee) {
        this.discountFee = discountFee;
    }

    public int getNeedFee() {
        return needFee;
    }

    public void setNeedFee(int needFee) {
        this.needFee = needFee;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getLeastFee() {
        return leastFee;
    }

    public void setLeastFee(int leastFee) {
        this.leastFee = leastFee;
    }

    public int getTaxFee() {
        return taxFee;
    }

    public void setTaxFee(int taxFee) {
        this.taxFee = taxFee;
    }

    public int getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(int paidFee) {
        this.paidFee = paidFee;
    }

    public int getOriginFee() {
        return originFee;
    }

    public void setOriginFee(int originFee) {
        this.originFee = originFee;
    }

    public TakeoutPromotion getPromotion() {
        return promotion;
    }

    public void setPromotion(TakeoutPromotion promotion) {
        this.promotion = promotion;
    }
}
