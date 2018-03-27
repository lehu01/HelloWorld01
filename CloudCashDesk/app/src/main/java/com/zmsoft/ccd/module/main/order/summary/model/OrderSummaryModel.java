package com.zmsoft.ccd.module.main.order.summary.model;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.order.complete.CompleteBillVo;
import com.zmsoft.ccd.lib.bean.order.summary.BillSummaryVo;
import com.zmsoft.ccd.lib.bean.order.summary.ShopPaymentStatisticsDay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/31 11:32.
 */

public class OrderSummaryModel extends Base {

    private Integer completedOrderNum;
    private BigDecimal completedOrderTotalAmount;
    private Integer uncompletedOrderNum;
    private BigDecimal uncompletedOrderTotalAmount;
    private Integer orderTotalNum;
    private BigDecimal orderTotalAmount;

    private List<PayParticulars> payParticularsList;    // 收入明细

    private BigDecimal sourceAmount;
    private BigDecimal discountAmount;
    private BigDecimal profitLossAmount;
    private BigDecimal wipeDiscountAmount;
    private BigDecimal couponDiscountAmount;

    private BigDecimal actualAmount;
    private BigDecimal excludeAmount;

    public OrderSummaryModel() {
        initModel();
    }

    public void update(List<BillSummaryVo> billSummaryVos) {
        initModel();
        if (null == billSummaryVos || 0 == billSummaryVos.size()) {
            return;
        }
        // 支付明细
        Map<String, PayParticulars> payParticularsMap = new LinkedHashMap<>();

        for (BillSummaryVo billSummaryVo : billSummaryVos) {
            if (null == billSummaryVo) {
                continue;
            }
            CompleteBillVo completeBillVo = billSummaryVo.getCompleteBill();
            if (null != completeBillVo) {
                completedOrderNum += completeBillVo.getCompleteOrderNum();
                completedOrderTotalAmount = completedOrderTotalAmount.add(completeBillVo.getCompleteOrderTotalAmount());
                uncompletedOrderNum += completeBillVo.getNotCompleteOrderNum();
                uncompletedOrderTotalAmount = uncompletedOrderTotalAmount.add(completeBillVo.getNotCompleteOrderTotalAmount());
                orderTotalNum += completeBillVo.getOrderTotalNum();
                orderTotalAmount = orderTotalAmount.add(completeBillVo.getOrderTotalAmount());
            }

            sourceAmount = sourceAmount.add(billSummaryVo.getSourceAmount());
            discountAmount = discountAmount.add(billSummaryVo.getDiscountAmount());
            profitLossAmount = profitLossAmount.add(billSummaryVo.getProfitLossAmount());
            wipeDiscountAmount = wipeDiscountAmount.add(billSummaryVo.getWipeDiscount());
            couponDiscountAmount = couponDiscountAmount.add(billSummaryVo.getCouponDiscount());

            actualAmount = actualAmount.add(billSummaryVo.getActualAmount());

            List<ShopPaymentStatisticsDay> paymentStatisticsDays = billSummaryVo.getPaymentStatisticsDays();
            if (null != paymentStatisticsDays && 0 != paymentStatisticsDays.size()) {
                for (ShopPaymentStatisticsDay shopPaymentStatisticsDay : paymentStatisticsDays) {
                    // 不计营业额
                    if (ShopPaymentStatisticsDay.Constant.NOT_INCLUDE == shopPaymentStatisticsDay.getIsInclude()) {
                        excludeAmount = excludeAmount.add(shopPaymentStatisticsDay.getPayAmount());
                    }
                    // 收入明细
                    String payName = shopPaymentStatisticsDay.getPayKindName();
                    if (!payParticularsMap.containsKey(payName)) {
                        PayParticulars payParticulars = new PayParticulars(shopPaymentStatisticsDay);
                        payParticularsMap.put(payName, payParticulars);
                    } else {
                        PayParticulars payParticulars = payParticularsMap.get(payName);
                        payParticulars.add(shopPaymentStatisticsDay);
                    }
                }
            }
        }

        for(Map.Entry<String, PayParticulars> entry : payParticularsMap.entrySet()) {
            payParticularsList.add(entry.getValue());
        }
    }

    private void initModel() {
        completedOrderNum = 0;
        completedOrderTotalAmount = BigDecimal.ZERO;
        uncompletedOrderNum = 0;
        uncompletedOrderTotalAmount = BigDecimal.ZERO;
        orderTotalNum = 0;
        orderTotalAmount = BigDecimal.ZERO;

        if (payParticularsList == null) {
            payParticularsList = new ArrayList<>();
        } else {
            payParticularsList.clear();
        }

        sourceAmount = BigDecimal.ZERO;
        discountAmount = BigDecimal.ZERO;
        profitLossAmount = BigDecimal.ZERO;
        wipeDiscountAmount = BigDecimal.ZERO;
        couponDiscountAmount = BigDecimal.ZERO;

        actualAmount = BigDecimal.ZERO;
        excludeAmount = BigDecimal.ZERO;
    }


    public Integer getCompletedOrderNum() {
        return completedOrderNum;
    }

    public BigDecimal getCompletedOrderTotalAmount() {
        return completedOrderTotalAmount;
    }

    public Integer getUncompletedOrderNum() {
        return uncompletedOrderNum;
    }

    public BigDecimal getUncompletedOrderTotalAmount() {
        return uncompletedOrderTotalAmount;
    }

    public Integer getOrderTotalNum() {
        return orderTotalNum;
    }

    public BigDecimal getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getProfitLossAmount() {
        return profitLossAmount;
    }

    public BigDecimal getWipeDiscountAmount() {
        return wipeDiscountAmount;
    }

    public BigDecimal getCouponDiscountAmount() {
        return couponDiscountAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public BigDecimal getExcludeAmount() {
        return excludeAmount;
    }

    public List<PayParticulars> getPayParticularsList() {
        return payParticularsList;
    }
}
