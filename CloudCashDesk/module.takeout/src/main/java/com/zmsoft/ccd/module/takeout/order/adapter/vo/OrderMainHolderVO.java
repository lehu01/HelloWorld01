package com.zmsoft.ccd.module.takeout.order.adapter.vo;

import android.support.annotation.DrawableRes;
import android.text.SpannableString;

import com.zmsoft.ccd.takeout.bean.Takeout;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/23.
 */

public class OrderMainHolderVO extends BaseTakeoutHolderVO {

    private String orderOriginal;
    private String orderDeliveryWay;
    private String orderStatus;
    private String orderTakeTime;
    private String orderAddress;
    private String orderDistance;
    private SpannableString orderPersonSpan;
    private String orderNo;
    private SpannableString orderNoSpan;
    private String distance;
    private boolean isOpen;
    private String nextMenuValue;
    @DrawableRes
    private int orderOriginalImage;
    private String appointmentFlagText;

    private List<Object> childNodes;



    public OrderMainHolderVO(Takeout takeout) {
        super(takeout);
    }

    public String getAppointmentFlagText() {
        return appointmentFlagText;
    }

    public void setAppointmentFlagText(String appointmentFlagText) {
        this.appointmentFlagText = appointmentFlagText;
    }

    public List<Object> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<Object> childNodes) {
        this.childNodes = childNodes;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getOrderOriginal() {
        return orderOriginal;
    }

    public void setOrderOriginal(String orderOriginal) {
        this.orderOriginal = orderOriginal;
    }

    public String getOrderDeliveryWay() {
        return orderDeliveryWay;
    }

    public void setOrderDeliveryWay(String orderDeliveryWay) {
        this.orderDeliveryWay = orderDeliveryWay;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTakeTime() {
        return orderTakeTime;
    }

    public void setOrderTakeTime(String orderTakeTime) {
        this.orderTakeTime = orderTakeTime;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderDistance() {
        return orderDistance;
    }

    public void setOrderDistance(String orderDistance) {
        this.orderDistance = orderDistance;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public SpannableString getOrderPersonSpan() {
        return orderPersonSpan;
    }

    public void setOrderPersonSpan(SpannableString orderPersonSpan) {
        this.orderPersonSpan = orderPersonSpan;
    }

    public SpannableString getOrderNoSpan() {
        return orderNoSpan;
    }

    public void setOrderNoSpan(SpannableString orderNoSpan) {
        this.orderNoSpan = orderNoSpan;
    }

    public String getNextMenuValue() {
        return nextMenuValue;
    }

    public void setNextMenuValue(String nextMenuValue) {
        this.nextMenuValue = nextMenuValue;
    }

    public int getOrderOriginalImage() {
        return orderOriginalImage;
    }

    public void setOrderOriginalImage(@DrawableRes int orderOriginalImage) {
        this.orderOriginalImage = orderOriginalImage;
    }
}
