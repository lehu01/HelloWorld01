package com.zmsoft.ccd.module.receipt.receipt.adapter.items;

/**
 * 优惠金额 ITEM
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptDiscountItem {
    /**
     * 打折类型（比如 会员卡）
     */
    private short discountType;
    /**
     * 打折名称（比如 V咖初级会员卡）
     */
    private String discountName;

    /**
     * 折扣率（比如 9折）
     */
    private String discountRate;

    public short getDiscountType() {
        return discountType;
    }

    public void setDiscountType(short discountType) {
        this.discountType = discountType;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }
}
