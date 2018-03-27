package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/9/5.
 */

public class DeliverTakeoutResponse {
    private List<TakeoutDeliveryResult> takeoutDeliveryResultList;
    private Double fee;

    public List<TakeoutDeliveryResult> getTakeoutDeliveryResultList() {
        return takeoutDeliveryResultList;
    }

    public void setTakeoutDeliveryResultList(List<TakeoutDeliveryResult> takeoutDeliveryResultList) {
        this.takeoutDeliveryResultList = takeoutDeliveryResultList;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
