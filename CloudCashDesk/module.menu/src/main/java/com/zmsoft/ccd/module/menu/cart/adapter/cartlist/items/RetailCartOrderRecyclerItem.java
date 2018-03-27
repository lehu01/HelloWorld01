package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items;

/**
 * 订单信息
 *
 * @author DangGui
 * @create 2017/4/10.
 */

public class RetailCartOrderRecyclerItem extends BaseCartRecyclerItem {
    /**
     * 桌号
     */
    private String seatCode;
    /**
     * 桌名
     */
    private String seatName;
    /**
     * 人数
     */
    private int peopleCount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 购物车里有的菜类和数量
     */
    private String summaryFoodInfo;
    /**
     * 合计金额
     */
    private String summaryAmount;

    /**
     * 标识购物车是否是空的
     */
    private boolean isCartEmpty;
    /**
     * 标识购物车是否过期
     */
    private boolean isCartExpired;

    /**
     * 标识能否改单（已下单再加菜的情况下，是不能改单的，通过判断orderId是否是空来区分）
     */
    private boolean canModifyOrder;

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSummaryFoodInfo() {
        return summaryFoodInfo;
    }

    public void setSummaryFoodInfo(String summaryFoodInfo) {
        this.summaryFoodInfo = summaryFoodInfo;
    }

    public String getSummaryAmount() {
        return summaryAmount;
    }

    public void setSummaryAmount(String summaryAmount) {
        this.summaryAmount = summaryAmount;
    }

    public boolean isCartEmpty() {
        return isCartEmpty;
    }

    public void setCartEmpty(boolean cartEmpty) {
        isCartEmpty = cartEmpty;
    }

    public boolean isCartExpired() {
        return isCartExpired;
    }

    public void setCartExpired(boolean cartExpired) {
        isCartExpired = cartExpired;
    }

    public boolean isCanModifyOrder() {
        return canModifyOrder;
    }

    public void setCanModifyOrder(boolean canModifyOrder) {
        this.canModifyOrder = canModifyOrder;
    }
}
