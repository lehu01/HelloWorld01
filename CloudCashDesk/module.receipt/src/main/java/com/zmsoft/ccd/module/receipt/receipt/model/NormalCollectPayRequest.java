package com.zmsoft.ccd.module.receipt.receipt.model;

import com.zmsoft.ccd.receipt.bean.Fund;

import java.util.List;

/**
 * 云收银收款 参数
 *
 * @author DangGui
 * @create 2017/6/13.
 */

public class NormalCollectPayRequest extends BaseCloudCashCollectPayRequest {
    private List<Fund> funds;
    private boolean checkLimit;
    /**
     * 收款用户名
     */
    private String userName;
    /**
     * 付款方式名称
     */
    private String payName;
    /**
     * 订单编号
     */
    private int code;
    /**
     * 桌位编码
     */
    private String seatCode;
    /**
     * 桌位名称
     */
    private String seatName;

    public NormalCollectPayRequest(String customerRegisterId, String orderId, String entityId, String opUserId) {
        super(customerRegisterId, orderId, entityId, opUserId);
    }

    public NormalCollectPayRequest(String customerRegisterId, String orderId, String entityId, String opUserId, boolean notCheckPromotion) {
        super(customerRegisterId, orderId, entityId, opUserId, notCheckPromotion);
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public void setFunds(List<Fund> funds) {
        this.funds = funds;
    }

    public boolean isCheckLimit() {
        return checkLimit;
    }

    public void setCheckLimit(boolean checkLimit) {
        this.checkLimit = checkLimit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
}
