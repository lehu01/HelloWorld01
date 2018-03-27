package com.zmsoft.ccd.module.receipt.receiptway.coupon.model;

import com.zmsoft.ccd.receipt.bean.VoucherInfoVo;

/**
 * 代金券ITEM
 *
 * @author DangGui
 * @create 2017/6/16.
 */

public class CouponItem {
    private String name;
    private boolean checked;
    private VoucherInfoVo mVoucherInfoVo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public VoucherInfoVo getVoucherInfoVo() {
        return mVoucherInfoVo;
    }

    public void setVoucherInfoVo(VoucherInfoVo voucherInfoVo) {
        mVoucherInfoVo = voucherInfoVo;
    }
}
