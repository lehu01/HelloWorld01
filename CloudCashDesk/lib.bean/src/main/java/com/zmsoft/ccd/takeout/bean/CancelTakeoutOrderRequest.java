package com.zmsoft.ccd.takeout.bean;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/30.
 */

public class CancelTakeoutOrderRequest extends BaseRequest {

    private String orderId;
    private long modifyTime;
    /**
     *  撤单原因
     */
    private String reason;
    /**
     * 取消外卖单前是否检查配送方式
     */
    private boolean checkDeliveryType;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isCheckDeliveryType() {
        return checkDeliveryType;
    }

    public void setCheckDeliveryType(boolean checkDeliveryType) {
        this.checkDeliveryType = checkDeliveryType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

}
