package com.zmsoft.ccd.lib.bean.order.detail.servicebill;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/20 10:28
 */
public class ServiceBill extends Base {

    private double originAmount; // 原始消费金额，消费金额
    private double originServiceCharge; // 原始服务费金额
    private double originLeastAmount; // 原始最低消费金额
    private double finalAmount; // 最终消费金额,应收
    private double paidFee; // 已付金额，已收
    private double needPayFee; // 未付金额，多收/少收
    private double discountAmount; // 折扣金额，优惠金额
    private double outFee; // 配送费：外卖

    public double getOriginAmount() {
        return originAmount;
    }

    public void setOriginAmount(double originAmount) {
        this.originAmount = originAmount;
    }

    public double getOriginServiceCharge() {
        return originServiceCharge;
    }

    public void setOriginServiceCharge(double originServiceCharge) {
        this.originServiceCharge = originServiceCharge;
    }

    public double getOriginLeastAmount() {
        return originLeastAmount;
    }

    public void setOriginLeastAmount(double originLeastAmount) {
        this.originLeastAmount = originLeastAmount;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public double getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(double paidFee) {
        this.paidFee = paidFee;
    }

    public double getNeedPayFee() {
        return needPayFee;
    }

    public void setNeedPayFee(double needPayFee) {
        this.needPayFee = needPayFee;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getOutFee() {
        return outFee;
    }

    public void setOutFee(double outFee) {
        this.outFee = outFee;
    }
}
