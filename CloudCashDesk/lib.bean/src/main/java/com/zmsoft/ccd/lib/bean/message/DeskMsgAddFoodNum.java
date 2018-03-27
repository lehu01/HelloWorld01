package com.zmsoft.ccd.lib.bean.message;

/**
 * 解析加菜消息文本内容中的“扫码加菜，2个菜”，菜的个数服务端通过json传递，需要解析
 *
 * @author DangGui
 * @create 2017/2/28.
 */

public class DeskMsgAddFoodNum {
    /**
     * 已支付金额
     */
    private double fee;
    /**
     * 已付清、未付清
     */
    private String payStatus;
    /**
     * 菜的数量
     */
    private int instanceCount;

    private String orderCode;
    private String waitingOrderId;

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public int getWaitingInstanceCount() {
        return instanceCount;
    }

    public void setWaitingInstanceCount(int waitingInstanceCount) {
        this.instanceCount = waitingInstanceCount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getWaitingOrderId() {
        return waitingOrderId;
    }

    public void setWaitingOrderId(String waitingOrderId) {
        this.waitingOrderId = waitingOrderId;
    }
}
