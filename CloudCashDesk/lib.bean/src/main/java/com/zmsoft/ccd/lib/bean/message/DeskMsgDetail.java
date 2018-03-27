package com.zmsoft.ccd.lib.bean.message;

import com.zmsoft.ccd.lib.bean.order.detail.servicebill.ServiceBill;

import java.io.Serializable;
import java.util.List;

/**
 * 桌位消息详情
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class DeskMsgDetail implements Serializable {
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
     *
     * @see
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
     * 单号
     */
    private String orderCode;
    /**
     * 客单备注
     */
    private String orderMemo;
    /**
     * 点菜人的头像
     */
    private String customerAvatarUrl;
    /**
     * 点菜人的姓名
     */
    private String customerName;

    /**
     * 下单时间，精确到小时和分钟
     */
    private long openTime;
    /**
     * 点的菜的数量，此处数量是本栏里面所有数量的和，如果有小数，需显示小数，单位统一都是“份”。
     */
    private int menuNum;
    /**
     * 桌位名称
     */
    private String seatName;
    /**
     * 消息创建时间的时间戳
     */
    private long createTime;
    /**
     * 修改时间(消息处理时间)
     */
    private long modifiedTime;

    private List<DeskMsgDetailFood> waitingInstanceList;
    /**
     * 支付信息
     */
    private List<DeskMsgPay> cloudPayList;
    /**
     * 消费金额
     */
    private ServiceBill serviceBillVO;
    /**
     * 消息类型，点菜消息、加菜消息等
     */
    private int messageType;

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

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
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

    public String getCustomerAvatarUrl() {
        return customerAvatarUrl;
    }

    public void setCustomerAvatarUrl(String customerAvatarUrl) {
        this.customerAvatarUrl = customerAvatarUrl;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public int getMenuNum() {
        return menuNum;
    }

    public void setMenuNum(int menuNum) {
        this.menuNum = menuNum;
    }

    public List<DeskMsgDetailFood> getWaitingInstanceList() {
        return waitingInstanceList;
    }

    public void setWaitingInstanceList(List<DeskMsgDetailFood> waitingInstanceList) {
        this.waitingInstanceList = waitingInstanceList;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<DeskMsgPay> getCloudPayList() {
        return cloudPayList;
    }

    public void setCloudPayList(List<DeskMsgPay> cloudPayList) {
        this.cloudPayList = cloudPayList;
    }

    public ServiceBill getServiceBillVO() {
        return serviceBillVO;
    }

    public void setServiceBillVO(ServiceBill serviceBillVO) {
        this.serviceBillVO = serviceBillVO;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
