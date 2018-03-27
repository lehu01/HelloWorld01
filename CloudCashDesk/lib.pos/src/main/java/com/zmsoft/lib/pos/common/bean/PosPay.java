package com.zmsoft.lib.pos.common.bean;

import java.io.Serializable;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/27 16:42
 *     desc  :
 * </pre>
 */
public class PosPay implements Serializable {

    private int payMoney; // 单位分
    private Short payType; // 支付类型

    public PosPay() {

    }

    public PosPay(int payMoney, Short payType) {
        this.payMoney = payMoney;
        this.payType = payType;
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
}
