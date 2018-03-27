package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/27.
 */

public class CartItemModifyRequest implements Serializable {
    private String seatCode;
    private String orderId;
    private String entityId;
    private String customerRegisterId;
    private List<CartItem> cartItems;
    private String source;

    public CartItemModifyRequest(String seatCode, String orderId, String entityId, String customerRegisterId
            , List<CartItem> cartItems, String source) {
        this.seatCode = seatCode;
        this.orderId = orderId;
        this.entityId = entityId;
        this.customerRegisterId = customerRegisterId;
        this.cartItems = cartItems;
        this.source = source;
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

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
