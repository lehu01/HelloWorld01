package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/31.
 */

public class DeliveryInfoResponse {
    private List<DeliveryInfo> takeoutDeliveryInfoVos;

    public List<DeliveryInfo> getTakeoutDeliveryInfoVos() {
        return takeoutDeliveryInfoVos;
    }

    public void setTakeoutDeliveryInfoVos(List<DeliveryInfo> takeoutDeliveryInfoVos) {
        this.takeoutDeliveryInfoVos = takeoutDeliveryInfoVos;
    }
}
