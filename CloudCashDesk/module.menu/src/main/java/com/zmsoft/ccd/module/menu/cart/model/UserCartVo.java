package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class UserCartVo implements Serializable {
    /**
     * 小二用户信息
     */
    private CustomerVo customerVo;
    /**
     * 购物车商品对象
     */
    private List<ItemVo> itemVos;


    public CustomerVo getCustomerVo() {
        return customerVo;
    }

    public void setCustomerVo(CustomerVo customerVo) {
        this.customerVo = customerVo;
    }

    public List<ItemVo> getItemVos() {
        return itemVos;
    }

    public void setItemVos(List<ItemVo> itemVos) {
        this.itemVos = itemVos;
    }
}
