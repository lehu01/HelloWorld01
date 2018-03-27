package com.zmsoft.ccd.data;

import com.zmsoft.ccd.helper.InstanceHelper;
import com.zmsoft.ccd.helper.OrderHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.lib.bean.instance.PersonInfo;
import com.zmsoft.ccd.lib.bean.instance.PersonInstance;
import com.zmsoft.ccd.lib.bean.instance.statistics.CategoryInfo;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailTakeoutInfoVo;
import com.zmsoft.ccd.lib.bean.order.detail.OrderItem;
import com.zmsoft.ccd.lib.bean.order.detail.OrderVo;
import com.zmsoft.ccd.lib.bean.order.detail.servicebill.ServiceBill;
import com.zmsoft.ccd.lib.bean.pay.Pay;

import java.util.ArrayList;
import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 11:43
 */
public class OrderDetailManager {

    /**
     * 处理订单详情数据
     */
    public static List<OrderDetailItem> getResultOrderDetail(OrderDetail orderDetail, boolean isUpdateServiceFee, boolean isDoubleSwitch) {
        List<OrderDetailItem> list = new ArrayList<>();
        // 3.处理order
        OrderDetailItem orderItem = new OrderDetailItem();
        orderItem.setType(OrderDetailItem.ITEM_TYPE_ORDER_INFO);
        orderItem.setPayOperator(orderDetail.getPayOperator());
        OrderItem orderItemVo = new OrderItem();
        orderItemVo.setEndPay(OrderHelper.isEndPay(orderDetail.getOrder().getStatus()));
        orderItemVo.setMinuteSinceOpen(orderDetail.getMinuteSinceOpen());
        orderItemVo.setMinuteToOverTime(orderDetail.getMinuteToOverTime());
        orderItemVo.setLimitTime(orderDetail.isLimitTime());
        orderItemVo.setOverTime(orderDetail.isOverTime());
        orderItemVo.setSeatName(orderDetail.getSeatName());
        orderItemVo.setOrderDetailStatus(orderDetail.getStatus());
        orderItemVo.setUpdateServiceFee(isUpdateServiceFee);
        orderItemVo.setPromotionVo(orderDetail.getPromotionVo());
        if (orderDetail.getCloudPayList() != null && orderDetail.getCloudPayList().size() > 0) {
            orderItemVo.setPayCount(true);
        } else {
            orderItemVo.setPayCount(false);
        }
        OrderVo order = orderDetail.getOrder();
        if (order != null) {
            orderItemVo.setEndPay(OrderHelper.isEndPay(order.getStatus()));
            orderItemVo.setOrderVo(order);
        }
        ServiceBill serviceBill = orderDetail.getServiceBillVO();
        if (serviceBill != null) {
            orderItemVo.setServiceBill(serviceBill);
        }
        orderItem.setOrderVo(orderItemVo);
        list.add(orderItem);

        // 4.处理PayInfo消息
        List<Pay> payList = orderDetail.getCloudPayList();
        if (payList != null) {
            for (Pay pay : payList) {
                OrderDetailItem payInfoItem = new OrderDetailItem();
                pay.setEndPay(OrderHelper.isEndPay(order.getStatus()));
                pay.setTakeOutOrder(OrderHelper.isTakeoutOrder(order.getOrderFrom()));
                payInfoItem.setPay(pay);
                payInfoItem.setType(OrderDetailItem.ITEM_TYPE_PAY_INFO);
                list.add(payInfoItem);
            }
        }

        // 外卖信息
        OrderDetailTakeoutInfoVo takeoutInfoVo = orderDetail.getDeliveryInfoVO();
        if (takeoutInfoVo != null) {
            OrderDetailItem takeoutItem = new OrderDetailItem();
            takeoutItem.setTakeoutInfo(takeoutInfoVo);
            takeoutItem.setType(OrderDetailItem.ITEM_TYPE_TAKEOUT_ADDRESS);
            list.add(takeoutItem);
        }

        // 5.处理user
        List<PersonInstance> personInstances = orderDetail.getPersonInstanceVoList();
        if (personInstances != null) {
            for (PersonInstance personInstance : personInstances) {
                // 点菜人信息
                OrderDetailItem userInfoItem = new OrderDetailItem();
                userInfoItem.setType(OrderDetailItem.ITEM_TYPE_USER_INFO);
                PersonInfo personInfo = new PersonInfo();
                personInfo.setCustomerRegisterId(personInstance.getCustomerRegisterId());
                personInfo.setCustomerName(personInstance.getCustomerName());
                personInfo.setFileUrl(personInstance.getFileUrl());
                personInfo.setInstanceNum(personInstance.getInstanceNum());
                personInfo.setCreateTime(personInstance.getCreateTime());
                personInfo.setFromCustomer(personInstance.getFromCustomer());
                personInfo.setMobile(personInstance.getMobile());
                userInfoItem.setPersonInfo(personInfo);
                list.add(userInfoItem);
                // 菜肴列表（普通菜，套菜，自定义菜，自定义套菜）
                List<Instance> instanceList = personInstance.getInstanceList();
                if (instanceList != null) {
                    for (Instance suitInstance : instanceList) {
                        // 1.处理父菜
                        OrderDetailItem suitItem = new OrderDetailItem();
                        suitInstance.setDoubleSwitch(isDoubleSwitch);
                        suitItem.setInstance(suitInstance);
                        Short kind = suitInstance.getKind();
                        if (InstanceHelper.isSuit(kind)) { // 套菜（套菜和自定义套菜）
                            suitItem.setType(OrderDetailItem.ITEM_TYPE_SUIT_INSTANCE);
                        } else { // 普通菜(包括自定义和普通菜)
                            suitItem.setType(OrderDetailItem.ITEM_TYPE_INSTANCE);
                        }
                        list.add(suitItem);
                        // 2.处理子菜（子菜或者加料菜）
                        List<Instance> childInstanceList = suitInstance.getChildInstanceList();
                        if (childInstanceList != null) {
                            for (Instance childInstance : childInstanceList) {
                                Short childKind = childInstance.getKind();
                                String parentId = childInstance.getParentId();
                                if (InstanceHelper.isSuitChild(childKind, parentId)) { // 套菜子菜
                                    OrderDetailItem childItem = new OrderDetailItem();
                                    childInstance.setDoubleSwitch(isDoubleSwitch);
                                    childItem.setInstance(childInstance);
                                    childItem.setType(OrderDetailItem.ITEM_TYPE_SUIT_CHILD_INSTANCE);
                                    list.add(childItem);
                                }
                            }
                        }
                    }
                }
            }
        }

        // 6.统计信息处理
        CategoryInfo categoryInfo = orderDetail.getCategoryInfoVo();
        if (categoryInfo != null) {
            OrderDetailItem categoryItem = new OrderDetailItem();
            categoryItem.setType(OrderDetailItem.ITEM_TYPE_INSTANCE_ALL);
            categoryItem.setCategoryInfoVo(categoryInfo);
            list.add(categoryItem);
        }
        return list;
    }

    /**
     * 菜肴列表是否有双单位菜肴，并且没有修改重量
     */
    public static boolean isDoubleUnitInstanceAndNoUpdate(List<OrderDetailItem> list) {
        boolean result = false;
        for (OrderDetailItem item : list) {
            if (item.getType() == OrderDetailItem.ITEM_TYPE_INSTANCE
                    || item.getType() == OrderDetailItem.ITEM_TYPE_SUIT_CHILD_INSTANCE) {
                Instance instance = item.getInstance();
                if (InstanceHelper.isRejectInstance(instance.getStatus())) {
                    continue;
                }
                if (Base.INT_TRUE == instance.getIsTwoAccount()) {
                    if (instance.getIsBuyNumberChanged() == Base.SHORT_FALSE) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

}
