package com.zmsoft.ccd.module.receipt.receipt.model;

import com.zmsoft.ccd.receipt.bean.VoucherFund;

import java.util.List;

/**
 * 云收银收款 参数
 *
 * @author DangGui
 * @create 2017/6/13.
 */

public class CouponCollectPayRequest extends BaseCloudCashCollectPayRequest {
    private List<VoucherFund> funds;

    public CouponCollectPayRequest(String customerRegisterId, String orderId, String entityId, String opUserId) {
        super(customerRegisterId, orderId, entityId, opUserId);
    }

    public List<VoucherFund> getFunds() {
        return funds;
    }

    public void setFunds(List<VoucherFund> funds) {
        this.funds = funds;
    }

}
