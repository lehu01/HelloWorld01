package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/11/17.
 */

public class DeliveryTypeVo {
    /**
     * 0-物流配送；1-第三方配送；3-店家配送
     */
    private short type;
    /**
     * 配送方式描述
     */
    private String desc;
    private List<TakeoutCourier> takeoutDelivererVos;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<TakeoutCourier> getTakeoutDelivererVos() {
        return takeoutDelivererVos;
    }

    public void setTakeoutDelivererVos(List<TakeoutCourier> takeoutDelivererVos) {
        this.takeoutDelivererVos = takeoutDelivererVos;
    }
}
