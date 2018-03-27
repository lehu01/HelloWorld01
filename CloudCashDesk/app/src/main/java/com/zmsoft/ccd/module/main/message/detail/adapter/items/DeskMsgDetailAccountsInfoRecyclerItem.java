package com.zmsoft.ccd.module.main.message.detail.adapter.items;

/**
 * 桌位消息详情——收款详情
 *
 * @author DangGui
 * @create 2017/4/8.
 */

public class DeskMsgDetailAccountsInfoRecyclerItem {

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
     * 多收款。如果已收金额大于应收金额，在应收金额下方显示：多收款：¥15.00，金额以实际的为准
     */
    private String exceedFee;
    /**
     * 是否是多收款，如果是应显示“多收款”,否则显示“少收款”
     */
    private boolean exceedMore;

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

    public String getExceedFee() {
        return exceedFee;
    }

    public void setExceedFee(String exceedFee) {
        this.exceedFee = exceedFee;
    }

    public boolean isExceedMore() {
        return exceedMore;
    }

    public void setExceedMore(boolean exceedMore) {
        this.exceedMore = exceedMore;
    }
}
