package com.zmsoft.ccd.module.main.message.detail.adapter.items;

import com.zmsoft.ccd.helper.MessageDetailHelper;

/**
 * 桌位消息详情——付款详情
 *
 * @author DangGui
 * @create 2017/4/8.
 */

public class DeskMsgDetailPayInfoRecyclerItem {
    /**
     * 支付方式(微信、支付宝等)
     *
     * @see MessageDetailHelper.PaymentMethod
     */
    private int paymentMethod;
    /**
     * 支付来源描述 eg：当归于12:25分微信支付了￥35.00
     */
    private String payInfoContent;
    /**
     * 交易单号 eg：微信交易单号：12345678
     */
    private String payOrderNo;

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPayInfoContent() {
        return payInfoContent;
    }

    public void setPayInfoContent(String payInfoContent) {
        this.payInfoContent = payInfoContent;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }
}
