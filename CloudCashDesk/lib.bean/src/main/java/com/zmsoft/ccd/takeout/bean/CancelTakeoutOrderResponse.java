package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/30.
 */

public class CancelTakeoutOrderResponse extends BaseRequest {

    private String orderId;
    /**
     * 订单最后修改时间
     */
    private long modifyTime;
    /**
     * 撤单结果提示
     */
    private String message;
    /**
     * 是否提示配送方式
     */
    private boolean showDeliveryType;
    /**
     * 配送方式提示
     */
    private String deliveryTypeTip;
    /**
     * 是否需要继续撤单,0表示否，1表示是
     */
    private int needCancelAgain;
    /**
     * 撤单菜肴ids
     */
    private List<String> instanceIds;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isShowDeliveryType() {
        return showDeliveryType;
    }

    public void setShowDeliveryType(boolean showDeliveryType) {
        this.showDeliveryType = showDeliveryType;
    }

    public String getDeliveryTypeTip() {
        return deliveryTypeTip;
    }

    public void setDeliveryTypeTip(String deliveryTypeTip) {
        this.deliveryTypeTip = deliveryTypeTip;
    }

    public int getNeedCancelAgain() {
        return needCancelAgain;
    }

    public void setNeedCancelAgain(int needCancelAgain) {
        this.needCancelAgain = needCancelAgain;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<String> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<String> instanceIds) {
        this.instanceIds = instanceIds;
    }
}
