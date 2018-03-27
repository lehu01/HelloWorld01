package com.zmsoft.ccd.lib.bean.order;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/23 17:33
 */
public class OrderListResult extends Base {

    private List<Order> basicOrderVos;

    public List<Order> getBasicOrderVos() {
        return basicOrderVos;
    }

    public void setBasicOrderVos(List<Order> basicOrderVos) {
        this.basicOrderVos = basicOrderVos;
    }
}
