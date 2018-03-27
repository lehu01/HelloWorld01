package com.zmsoft.ccd.module.receipt.receipt.adapter.items;

import com.zmsoft.ccd.receipt.bean.Pay;

/**
 * 已收金额ITEM
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptHeadReceivedItem {
    /**
     * 支付名称（比如 会员卡、挂账）
     */
    private String payName;

    /**
     * 支付金额（比如 ¥150.00）
     */
    private String payFee;

    /**
     * 云收银账单支付详情（服务端返回实例）
     */
    private Pay mPay;

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayFee() {
        return payFee;
    }

    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }

    public Pay getPay() {
        return mPay;
    }

    public void setPay(Pay pay) {
        mPay = pay;
    }
}
