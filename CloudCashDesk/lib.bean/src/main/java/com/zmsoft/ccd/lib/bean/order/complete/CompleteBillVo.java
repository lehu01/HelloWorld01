package com.zmsoft.ccd.lib.bean.order.complete;

import com.zmsoft.ccd.lib.bean.Base;

import java.math.BigDecimal;

/**
 * 已经完成账单信息
 * @author : heniu@2dfire.com
 * @time : 2017/10/26 11:14.
 */

public class CompleteBillVo extends Base {
    private Integer orderTotalNum;                  // 账单总数
    private BigDecimal orderTotalAmount;             // 账单总金额
    private Integer completeOrderNum;               // 已结账单数
    private Integer notCompleteOrderNum;            // 未结账单数
    private BigDecimal completeOrderTotalAmount;     // 已结账单总金额
    private BigDecimal notCompleteOrderTotalAmount;  // 未结账单总金额
    private BigDecimal earningAmount;                // 今日收益

    public CompleteBillVo() {
        orderTotalNum = 0;
        orderTotalAmount = BigDecimal.ZERO;
        completeOrderNum = 0;
        notCompleteOrderNum = 0;
        completeOrderTotalAmount = BigDecimal.ZERO;
        notCompleteOrderTotalAmount = BigDecimal.ZERO;
        earningAmount = BigDecimal.ZERO;
    }

    public Integer getOrderTotalNum() {
        return orderTotalNum;
    }

    public BigDecimal getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public Integer getCompleteOrderNum() {
        return completeOrderNum;
    }

    public Integer getNotCompleteOrderNum() {
        return notCompleteOrderNum;
    }

    public BigDecimal getCompleteOrderTotalAmount() {
        return completeOrderTotalAmount;
    }

    public BigDecimal getNotCompleteOrderTotalAmount() {
        return notCompleteOrderTotalAmount;
    }

    public BigDecimal getEarningAmount() {
        return earningAmount;
    }
}
