package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items;

/**
 * @author DangGui
 * @create 2017/4/10.
 */

public class BaseCartRecyclerItem {
    /**
     * 购物车菜的类型,必选商品是1，其他商品默认是0
     *
     * @see com.zmsoft.ccd.module.menu.helper.CartHelper.CartFoodType
     */
    private int foodType;
    /**
     * 如果点菜时选择“赞不上菜”，则在商品名称前面标记：（待）
     */
    private boolean standby;

    /**
     * 桌位
     */
    private String seatCode;
    /**
     * 下单Id
     */
    private String orderId;

    public int getFoodType() {
        return foodType;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public boolean isStandby() {
        return standby;
    }

    public void setStandby(boolean standby) {
        this.standby = standby;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
