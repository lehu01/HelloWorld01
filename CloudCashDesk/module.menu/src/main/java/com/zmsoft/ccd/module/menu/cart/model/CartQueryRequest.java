package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;

/**
 * 查询购物车 参数
 *
 * @author DangGui
 * @create 2017/4/27.
 */

public class CartQueryRequest implements Serializable {
    private String seatCode;
    private String orderId;
    private String entityId;
    private String customerRegisterId;

    public CartQueryRequest(String seatCode, String orderId, String entityId, String customerRegisterId) {
        this.seatCode = seatCode;
        this.orderId = orderId;
        this.entityId = entityId;
        this.customerRegisterId = customerRegisterId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCustomerRegisterId() {
        return customerRegisterId;
    }

    public void setCustomerRegisterId(String customerRegisterId) {
        this.customerRegisterId = customerRegisterId;
    }
}
