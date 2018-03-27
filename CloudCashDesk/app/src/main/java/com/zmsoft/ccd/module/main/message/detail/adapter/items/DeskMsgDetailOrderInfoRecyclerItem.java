package com.zmsoft.ccd.module.main.message.detail.adapter.items;

import java.io.Serializable;

/**
 * 桌位消息详情——订单信息
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class DeskMsgDetailOrderInfoRecyclerItem implements Serializable {

    /**
     * 订单标题 eg: “加菜”
     */
    private String title;
    /**
     * 订单来源 eg: 微信
     */
    private String source;
    /**
     * 订单状态 eg: 待审核、已同意
     */
    private int status;
    private String statusStr;
    /**
     * 人数
     */
    private int peopleCount;
    /**
     * 桌号
     */
    private String seatCode;
    /**
     * 桌位名称
     */
    private String seatName;
    /**
     * 单号
     */
    private String orderCode;
    /**
     * 客单备注
     */
    private String orderMemo;
    private DeskMsgDetailAccountsInfoRecyclerItem mAccountsInfoRecyclerItem;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderMemo() {
        return orderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public DeskMsgDetailAccountsInfoRecyclerItem getAccountsInfoRecyclerItem() {
        return mAccountsInfoRecyclerItem;
    }

    public void setAccountsInfoRecyclerItem(DeskMsgDetailAccountsInfoRecyclerItem accountsInfoRecyclerItem) {
        mAccountsInfoRecyclerItem = accountsInfoRecyclerItem;
    }
}
