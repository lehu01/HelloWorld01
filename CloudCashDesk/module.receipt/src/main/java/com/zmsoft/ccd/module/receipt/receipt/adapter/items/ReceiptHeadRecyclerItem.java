package com.zmsoft.ccd.module.receipt.receipt.adapter.items;

import com.zmsoft.ccd.receipt.bean.CloudCashBill;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptHeadRecyclerItem {
    /**
     * 消费金额
     */
    private String consumeFee;
    /**
     * 服务费金额
     */
    private String serviceFee;
    /**
     * 最低消费
     */
    private String minimumFee;
    /**
     * 优惠金额
     */
    private String discountFee;
    /**
     * 应收金额
     */
    private String receivableFee;
    /**
     * 已收金额
     */
    private String receivedFee;
    /**
     * 还需收款
     */
    private String needReceiptFee;
    /**
     * 配送费金额
     */
    private String deliveryFee;
    /**
     * 优惠金额ITEMS
     */
    private List<ReceiptDiscountItem> mReceiptDiscountItemList;
    /**
     * 已收金额ITEMS
     */
    private List<ReceiptHeadReceivedItem> mReceivedItemList;

    /**
     * 云收银账单金额详情(服务端返回实例)
     */
    private CloudCashBill cloudCashBill;

    /**
     * 来源，普通付款、外卖付款等
     *
     * @see com.zmsoft.ccd.lib.base.constant.RouterPathConstant.Receipt
     */
    private boolean thirdTakeout;

    public String getConsumeFee() {
        return consumeFee;
    }

    public void setConsumeFee(String consumeFee) {
        this.consumeFee = consumeFee;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getMinimumFee() {
        return minimumFee;
    }

    public void setMinimumFee(String minimumFee) {
        this.minimumFee = minimumFee;
    }

    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }

    public String getReceivableFee() {
        return receivableFee;
    }

    public void setReceivableFee(String receivableFee) {
        this.receivableFee = receivableFee;
    }

    public String getReceivedFee() {
        return receivedFee;
    }

    public void setReceivedFee(String receivedFee) {
        this.receivedFee = receivedFee;
    }

    public String getNeedReceiptFee() {
        return needReceiptFee;
    }

    public void setNeedReceiptFee(String needReceiptFee) {
        this.needReceiptFee = needReceiptFee;
    }

    public List<ReceiptDiscountItem> getReceiptDiscountItemList() {
        return mReceiptDiscountItemList;
    }

    public void setReceiptDiscountItemList(List<ReceiptDiscountItem> receiptDiscountItemList) {
        mReceiptDiscountItemList = receiptDiscountItemList;
    }

    public List<ReceiptHeadReceivedItem> getReceivedItemList() {
        return mReceivedItemList;
    }

    public void setReceivedItemList(List<ReceiptHeadReceivedItem> receivedItemList) {
        mReceivedItemList = receivedItemList;
    }

    public CloudCashBill getCloudCashBill() {
        return cloudCashBill;
    }

    public void setCloudCashBill(CloudCashBill cloudCashBill) {
        this.cloudCashBill = cloudCashBill;
    }

    public boolean isThirdTakeout() {
        return thirdTakeout;
    }

    public void setThirdTakeout(boolean thirdTakeout) {
        this.thirdTakeout = thirdTakeout;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
