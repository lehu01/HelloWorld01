package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;

/**
 * 清理购物车
 *
 * @author DangGui
 * @create 2017/4/17.
 */

public class CartClearVo implements Serializable {
    private long cartTime;

    public long getCartTime() {
        return cartTime;
    }

    public void setCartTime(long cartTime) {
        this.cartTime = cartTime;
    }
}
