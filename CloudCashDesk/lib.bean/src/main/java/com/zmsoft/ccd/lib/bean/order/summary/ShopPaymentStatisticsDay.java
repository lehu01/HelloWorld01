package com.zmsoft.ccd.lib.bean.order.summary;

import com.zmsoft.ccd.lib.bean.Base;

import java.math.BigDecimal;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/26 11:18.
 */

public class ShopPaymentStatisticsDay extends Base {

    public interface Constant {
        int NOT_INCLUDE = 0;
    }

    private String entityId;
    private String orderDate;
    private String payKindName;             // 支付方式
    private BigDecimal payAmount;          // 支付金额
    private Integer isInclude;             // 是否计入营业额
    private Integer payCount;              // 支付笔数

    public String getEntityId() {
        return entityId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getPayKindName() {
        return payKindName;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public Integer getIsInclude() {
        return isInclude;
    }

    public Integer getPayCount() {
        return payCount;
    }
}
