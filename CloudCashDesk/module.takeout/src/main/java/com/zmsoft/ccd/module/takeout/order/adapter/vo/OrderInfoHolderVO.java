package com.zmsoft.ccd.module.takeout.order.adapter.vo;

import com.zmsoft.ccd.takeout.bean.Takeout;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/24.
 */

public class OrderInfoHolderVO extends BaseTakeoutHolderVO{

    private String orderInfo;

    public OrderInfoHolderVO(Takeout takeout) {
        super(takeout);
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }
}
