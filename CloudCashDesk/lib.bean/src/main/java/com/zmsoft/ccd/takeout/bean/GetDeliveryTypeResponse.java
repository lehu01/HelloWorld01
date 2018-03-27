package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/11/17.
 */

public class GetDeliveryTypeResponse {
    private List<DeliveryTypeVo> deliveryTypeVos;

    public List<DeliveryTypeVo> getDeliveryTypeVos() {
        return deliveryTypeVos;
    }

    public void setDeliveryTypeVos(List<DeliveryTypeVo> deliveryTypeVos) {
        this.deliveryTypeVos = deliveryTypeVos;
    }
}
