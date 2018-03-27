package com.zmsoft.ccd.module.main.message.takeout.adapter.items;

import com.zmsoft.ccd.lib.bean.message.TakeoutPayVo;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/8/16.
 */

public class TakeoutDetailPayDetailRecyclerItem {
    private List<TakeoutPayVo> payVoList;
    /**
     * 配送费
     */
    private String deliveryFee;

    public List<TakeoutPayVo> getPayVoList() {
        return payVoList;
    }

    public void setPayVoList(List<TakeoutPayVo> payVoList) {
        this.payVoList = payVoList;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
