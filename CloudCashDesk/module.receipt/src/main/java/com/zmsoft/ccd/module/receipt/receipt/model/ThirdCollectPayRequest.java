package com.zmsoft.ccd.module.receipt.receipt.model;

import java.util.List;

/**
 * 云收银收款 参数
 *
 * @author DangGui
 * @create 2017/6/13.
 */

public class ThirdCollectPayRequest extends BaseCloudCashCollectPayRequest {
    private List<ThirdFund> funds;

    public ThirdCollectPayRequest(String customerRegisterId, String orderId, String entityId, String opUserId) {
        super(customerRegisterId, orderId, entityId, opUserId);
    }

    public List<ThirdFund> getFunds() {
        return funds;
    }

    public void setFunds(List<ThirdFund> funds) {
        this.funds = funds;
    }
}
