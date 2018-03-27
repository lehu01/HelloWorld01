package com.zmsoft.ccd.takeout.bean;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class OperateOrderResponse {

    private long modifyTime;

    private String orderId;

    private String msg;

    private String resultCode;

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
