package com.zmsoft.ccd.module.menu.cart.model;

import com.zmsoft.ccd.shop.IndustryTypeUtils;
import com.zmsoft.ccd.shop.bean.IndustryType;

import java.io.Serializable;

/**
 * @author DangGui
 * @create 2017/4/27.
 */

public class SubmitOrderRequest implements Serializable {
    private String seatCode;
    private String orderId;
    private String entityId;
    private String opUserId;
    private long cartTime;
    private String memberId;

    /**
     * 0表示餐饮，1表示零售
     */
    private short industryCode;

    private SubmitOrderRequest(String seatCode, String orderId, String entityId, String opUserId, long cartTime
            , String memberId, short orderKind) {
        this.seatCode = seatCode;
        this.orderId = orderId;
        this.entityId = entityId;
        this.opUserId = opUserId;
        this.cartTime = cartTime;
        this.memberId = memberId;
        this.industryCode = orderKind;
    }

    /**
     * 提交餐饮单需要的参数
     * @return
     */
    public static SubmitOrderRequest createRequestForCatering(String seatCode, String orderId, String entityId, String opUserId, long cartTime
            , String memberId) {
        return new SubmitOrderRequest(seatCode, orderId, entityId, opUserId, cartTime, memberId, IndustryTypeUtils.getIndustryType(IndustryType.CATERING));
    }
    /**
     * 提交单需参零售的参数
     * @return
     */
    public static SubmitOrderRequest createRequestForRetail(String seatCode, String orderId, String entityId, String opUserId, long cartTime
            , String memberId) {
        return new SubmitOrderRequest(seatCode, orderId, entityId, opUserId, cartTime, memberId, IndustryTypeUtils.getIndustryType(IndustryType.RETAIL));
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

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public long getCartTime() {
        return cartTime;
    }

    public void setCartTime(long cartTime) {
        this.cartTime = cartTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public short getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(short industryCode) {
        this.industryCode = industryCode;
    }
}
