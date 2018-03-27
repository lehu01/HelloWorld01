package com.zmsoft.ccd.takeout.bean;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class OperateOrderRequest extends BaseRequest {


    private String orderId;

    private long modifyTime;

    private short operateType;

    private String deliveryPlatformCode;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @see TakeoutConstants.OperationType#ORDER_TO_KITCHEN
     * @see TakeoutConstants.OperationType#ORDER_DISPATCH
     * @see TakeoutConstants.OperationType#ORDER_CANCEL_DISPATCH
     * @see TakeoutConstants.OperationType#ORDER_ARRIVED
     * @see TakeoutConstants.OperationType#ORDER_SELF_TAKE
     * @see TakeoutConstants.OperationType#ORDER_CHECK_OUT
     */
    public short getOperateType() {
        return operateType;
    }

    public void setOperateType(short operateType) {
        this.operateType = operateType;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDeliveryPlatformCode() {
        return deliveryPlatformCode;
    }

    public void setDeliveryPlatformCode(String deliveryPlatformCode) {
        this.deliveryPlatformCode = deliveryPlatformCode;
    }
}
