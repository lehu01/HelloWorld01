package com.zmsoft.ccd.module.main.order.summary.model;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.order.summary.ShopPaymentStatisticsDay;

import java.math.BigDecimal;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/31 14:49.
 */

public class PayParticulars extends Base {

    private String payKindName;             // 支付方式
    private Integer payCount;               // 支付笔数
    private BigDecimal payAmount;           // 支付金额

    public PayParticulars(ShopPaymentStatisticsDay shopPaymentStatisticsDay) {
        payKindName = shopPaymentStatisticsDay.getPayKindName();
        payCount = shopPaymentStatisticsDay.getPayCount();
        payAmount = shopPaymentStatisticsDay.getPayAmount();
    }

    // 相同payKindName数据，笔数和金额相加
    public void add(ShopPaymentStatisticsDay shopPaymentStatisticsDay) {
        payCount += shopPaymentStatisticsDay.getPayCount();
        payAmount = payAmount.add(shopPaymentStatisticsDay.getPayAmount());
    }

    public String getPayKindName() {
        return payKindName;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }
}
