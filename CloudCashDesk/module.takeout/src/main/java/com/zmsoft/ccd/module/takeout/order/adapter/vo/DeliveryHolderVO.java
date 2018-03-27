package com.zmsoft.ccd.module.takeout.order.adapter.vo;

import com.zmsoft.ccd.takeout.bean.Takeout;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/23.
 */

public class DeliveryHolderVO extends BaseTakeoutHolderVO{


    private String deliveryPlatform;
    private String deliveryTime;
    private String horseManStatus;
    private String horseManName;

    private String expressCode;

    public DeliveryHolderVO(Takeout takeout) {
        super(takeout);
    }


    public String getDeliveryPlatform() {
        return deliveryPlatform;
    }

    public void setDeliveryPlatform(String deliveryPlatform) {
        this.deliveryPlatform = deliveryPlatform;
    }

    public String getHorseManStatus() {
        return horseManStatus;
    }

    public void setHorseManStatus(String horseManTakeTime) {
        this.horseManStatus = horseManTakeTime;
    }

    public String getHorseManName() {
        return horseManName;
    }

    public void setHorseManName(String horseManName) {
        this.horseManName = horseManName;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }
}
