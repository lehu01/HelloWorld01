package com.zmsoft.lib.pos.common.bean;

import java.io.Serializable;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/27 16:16
 *     desc  :
 * </pre>
 */
public class PosCancelPay implements Serializable {

    private int payMoney; // 单位分
    private Short payType; // 支付类型
    private String payTransNo;// 凭证号

    public PosCancelPay() {
    }

    public PosCancelPay(int payMoney, Short payType, String payTransNo) {
        this.payMoney = payMoney;
        this.payType = payType;
        this.payTransNo = payTransNo;
    }

    public int getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(int payMoney) {
        this.payMoney = payMoney;
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public String getPayTransNo() {
        return payTransNo;
    }

    public void setPayTransNo(String payTransNo) {
        this.payTransNo = payTransNo;
    }
}
