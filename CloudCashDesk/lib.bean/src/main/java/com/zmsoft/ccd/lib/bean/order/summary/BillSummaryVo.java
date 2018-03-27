package com.zmsoft.ccd.lib.bean.order.summary;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.order.complete.CompleteBillVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/26 11:11.
 */

public class BillSummaryVo extends Base {
    private String entityId;
    private String orderDate;               // 订单日期
    private Integer orderCount;             // 订单数量
    private BigDecimal actualAmount;        // 实收金额
    private BigDecimal discountAmount;      // 折扣金额
    private BigDecimal profitLossAmount;    // 损益金额
    private BigDecimal finalAmount;         // 应收金额
    private BigDecimal wipeDiscount;        // 抹零金额
    private BigDecimal couponDiscount;      // 劵优惠金额
    private BigDecimal sourceAmount;        // 消费合计
    private CompleteBillVo completeBill;  // 已经完成账单信息
    private List<ShopPaymentStatisticsDay> paymentStatisticsDays;

    public BillSummaryVo() {
        orderCount = 0;
        actualAmount = BigDecimal.ZERO;
        discountAmount = BigDecimal.ZERO;
        profitLossAmount = BigDecimal.ZERO;
        finalAmount = BigDecimal.ZERO;
        wipeDiscount = BigDecimal.ZERO;
        couponDiscount = BigDecimal.ZERO;
        sourceAmount = BigDecimal.ZERO;
        completeBill = null;
        paymentStatisticsDays = null;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getProfitLossAmount() {
        return profitLossAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public BigDecimal getWipeDiscount() {
        return wipeDiscount;
    }

    public BigDecimal getCouponDiscount() {
        return couponDiscount;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public CompleteBillVo getCompleteBill() {
        return completeBill;
    }

    public List<ShopPaymentStatisticsDay> getPaymentStatisticsDays() {
        return paymentStatisticsDays;
    }
}
