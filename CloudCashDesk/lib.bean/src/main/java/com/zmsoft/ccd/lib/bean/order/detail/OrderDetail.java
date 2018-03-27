package com.zmsoft.ccd.lib.bean.order.detail;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.PersonInstance;
import com.zmsoft.ccd.lib.bean.instance.statistics.CategoryInfo;
import com.zmsoft.ccd.lib.bean.message.Message;
import com.zmsoft.ccd.lib.bean.order.detail.servicebill.ServiceBill;
import com.zmsoft.ccd.lib.bean.pay.Pay;

import java.util.List;

/**
 * Description：订单详情数据源
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2016/12/21 15:41
 */
public class OrderDetail extends Base {

    private int minuteSinceOpen; // 已开单时间（单位为分钟）
    private boolean limitTime; // 是否限时用餐
    private boolean overTime; // 限时用餐时，是否已经超时
    private int minuteToOverTime; //  限时用餐时，距离超时的时间(单位为分钟)
    private int status; // 订单支付状态。0表示未付款，1表示未付清，2表示已付清
    private String seatName; // 桌位名称
    private List<Message> messageList; // 未处理消息列表
    private OrderVo order; // 订单
    private List<Pay> cloudPayList; // 支付信息
    private ServiceBill serviceBillVO; // 消费金额
    private CategoryInfo categoryInfoVo; // 统计信息
    private List<PersonInstance> personInstanceVoList; // 菜肴列表
    private PromotionVo promotionVo; // 优惠信息
    private OrderDetailTakeoutInfoVo deliveryInfoVO;
    private String payOperator; //收银员姓名

    public int getMinuteSinceOpen() {
        return minuteSinceOpen;
    }

    public void setMinuteSinceOpen(int minuteSinceOpen) {
        this.minuteSinceOpen = minuteSinceOpen;
    }

    public boolean isLimitTime() {
        return limitTime;
    }

    public void setLimitTime(boolean limitTime) {
        this.limitTime = limitTime;
    }

    public boolean isOverTime() {
        return overTime;
    }

    public void setOverTime(boolean overTime) {
        this.overTime = overTime;
    }

    public int getMinuteToOverTime() {
        return minuteToOverTime;
    }

    public void setMinuteToOverTime(int minuteToOverTime) {
        this.minuteToOverTime = minuteToOverTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public OrderVo getOrder() {
        return order;
    }

    public void setOrder(OrderVo order) {
        this.order = order;
    }

    public List<Pay> getCloudPayList() {
        return cloudPayList;
    }

    public void setCloudPayList(List<Pay> cloudPayList) {
        this.cloudPayList = cloudPayList;
    }

    public ServiceBill getServiceBillVO() {
        return serviceBillVO;
    }

    public void setServiceBillVO(ServiceBill serviceBillVO) {
        this.serviceBillVO = serviceBillVO;
    }

    public CategoryInfo getCategoryInfoVo() {
        return categoryInfoVo;
    }

    public void setCategoryInfoVo(CategoryInfo categoryInfoVo) {
        this.categoryInfoVo = categoryInfoVo;
    }

    public List<PersonInstance> getPersonInstanceVoList() {
        return personInstanceVoList;
    }

    public void setPersonInstanceVoList(List<PersonInstance> personInstanceVoList) {
        this.personInstanceVoList = personInstanceVoList;
    }

    public PromotionVo getPromotionVo() {
        return promotionVo;
    }

    public void setPromotionVo(PromotionVo promotionVo) {
        this.promotionVo = promotionVo;
    }

    public OrderDetailTakeoutInfoVo getDeliveryInfoVO() {
        return deliveryInfoVO;
    }

    public void setDeliveryInfoVO(OrderDetailTakeoutInfoVo deliveryInfoVO) {
        this.deliveryInfoVO = deliveryInfoVO;
    }

    public String getPayOperator() {
        return payOperator;
    }

    public void setPayOperator(String payOperator) {
        this.payOperator = payOperator;
    }
}
