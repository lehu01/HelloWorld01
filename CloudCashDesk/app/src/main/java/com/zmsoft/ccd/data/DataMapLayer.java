package com.zmsoft.ccd.data;

import android.text.TextUtils;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.helper.MessageDetailHelper;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.helper.MsgSettingType;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.desk.Seat;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;
import com.zmsoft.ccd.lib.bean.desk.SelectSeatVo;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;
import com.zmsoft.ccd.lib.bean.message.DeskMessage;
import com.zmsoft.ccd.lib.bean.message.DeskMsgDetail;
import com.zmsoft.ccd.lib.bean.message.DeskMsgDetailFood;
import com.zmsoft.ccd.lib.bean.message.DeskMsgDetailFoodItem;
import com.zmsoft.ccd.lib.bean.message.DeskMsgPay;
import com.zmsoft.ccd.lib.bean.message.TakeoutInstanceVo;
import com.zmsoft.ccd.lib.bean.message.TakeoutMsgDetailResponse;
import com.zmsoft.ccd.lib.bean.message.TakeoutOrderDetailVo;
import com.zmsoft.ccd.lib.bean.message.TakeoutOrderVo;
import com.zmsoft.ccd.lib.bean.order.detail.servicebill.ServiceBill;
import com.zmsoft.ccd.lib.bean.order.remark.Memo;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailAccountsInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailComboFoodsItemInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailComboFoodsTitleRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailFoodsItemInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailFoodsTitleRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailOrderInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailPayInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailComboFoodsItemInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailComboFoodsTitleRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailFoodsItemInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailOrderInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailPayDetailRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailRecyclerItem;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * 数据处理层
 * 处理服务端返回的数据，转化为自己想要的数据格式
 *
 * @author DangGui
 * @create 2017/2/14.
 */

public final class DataMapLayer {
    private DataMapLayer() {

    }

    /**
     * 获取订单备注列表
     *
     * @param list list
     * @return list<Remark>
     */
    public static List<Memo> getRemarkList(List<Reason> list) {
        List<Memo> remarkList = new ArrayList<>();
        for (Reason item : list) {
            Memo remark = new Memo();
            remark.setName(item.getName());
            remarkList.add(remark);
        }
        return remarkList;
    }

    /**
     * 解析成SelectSeatFragment显示数据
     *
     * @param list
     * @param selectSeatCode null或“”时表示，当前未选中桌位
     * @param isWatch        是否选择，我关注的桌位
     * @return
     */
    public static List<SelectSeatVo> getSelectAllSeatList(List<SeatArea> list, String selectSeatCode, boolean isWatch) {
        List<SelectSeatVo> selectSeatVos = new ArrayList<>();
        if (list == null) {
            return selectSeatVos;
        }
        for (SeatArea seatArea : list) {
            // 去除特殊座位/关注的外卖单
            if (seatArea.getAreaId() == null || StringUtils.isEmpty(seatArea.getAreaId())) {
                continue;
            }
            // 区域处理
            SelectSeatVo areaVo = new SelectSeatVo();
            areaVo.setType(SelectSeatVo.ITEM_TYPE_AREA);
            areaVo.setAreaId(seatArea.getAreaId());
            areaVo.setAreaName(seatArea.getAreaName());
            // 桌位处理
            List<SelectSeatVo> seatVoList = new ArrayList<>();
            List<Seat> seatList = seatArea.getSeatList();
            for (Seat st : seatList) {
                // 我关注的桌位
                if (isWatch) {
                    if (!st.isBind()) {
                        continue;
                    }
                }
                SelectSeatVo seatVo = new SelectSeatVo();
                seatVo.setType(SelectSeatVo.ITEM_TYPE_SEAT);
                seatVo.setSeat(st);
                if (!StringUtils.isEmpty(selectSeatCode)) {
                    if (st.getCode().equals(selectSeatCode)) {
                        seatVo.setCheck(true);
                    }
                }
                seatVoList.add(seatVo);
            }
            // 当前区域有桌位，才加入areaVO
            if (0 != seatVoList.size()) {
                selectSeatVos.add(areaVo);
                selectSeatVos.addAll(seatVoList);
            }
        }
        return selectSeatVos;
    }

    /**
     * 将服务的返回的所有桌位数据转换为recyclerView所需的List<ViewHolderSeat>
     *
     * @param allSeatsResponse 服务的返回的数据集合
     * @return recyclerView所需的List<ViewHolderSeat>
     */
    public static List<ViewHolderSeat> getAllSeatsList(List<SeatArea> allSeatsResponse, boolean isWatchSeat) {
        List<ViewHolderSeat> viewHolderSeatList = new ArrayList<>();
        for (int i = 0; i < allSeatsResponse.size(); i++) {
            SeatArea seatArea = allSeatsResponse.get(i);
            List<Seat> seatList = seatArea.getSeatList();
            if (null != seatList && !seatList.isEmpty()) {
                if (TextUtils.isEmpty(seatArea.getAreaId())) {
                    seatArea.setAreaId(ViewHolderSeat.DEFAULT_AREA_ID);
                    // 零售单时，APP本地定义"桌位区域名"
                    seatArea.setAreaName(GlobalVars.context.getString(R.string.retail_order));
                }
                ViewHolderSeat areaViewHolderSeat = new ViewHolderSeat(true, seatArea.getAreaId(), seatArea.getAreaName());
                viewHolderSeatList.add(areaViewHolderSeat);
                //A区域下的item是否都已是选中状态
                boolean isAllSeatInAreaBinded = true;
                for (int j = 0; j < seatList.size(); j++) {
                    //过滤外卖单桌位
                    if (seatList.get(j).getCode().equals(MsgSettingType.TAKE_OUT_SEAT_CODE)) {
                        continue;
                    }
                    if (isWatchSeat && !seatList.get(j).isBind()) {
                        continue;
                    } else if (!isWatchSeat && !seatList.get(j).isBind()) {
                        isAllSeatInAreaBinded = false;
                    }
                    if (TextUtils.isEmpty(seatList.get(j).getAreaId())) {
                        seatList.get(j).setAreaId(ViewHolderSeat.DEFAULT_AREA_ID);
                        // 零售单时，APP本地定义"桌名"
                        seatList.get(j).setName(GlobalVars.context.getString(R.string.retail_order));
                    }
                    viewHolderSeatList.add(new ViewHolderSeat(seatList.get(j)));
                }
                areaViewHolderSeat.setHasChecked(isAllSeatInAreaBinded);
                //如果当期area下没有seat，删除这个area
                if (viewHolderSeatList.get(viewHolderSeatList.size() - 1).isHeader()) {
                    viewHolderSeatList.remove(viewHolderSeatList.size() - 1);
                }
            }
        }
        return viewHolderSeatList;
    }

    /**
     * 将服务的返回的所有桌位数据转换为recyclerView所需的List<ViewHolderSeat>
     *
     * @param allSeatsResponse 服务的返回的数据集合
     * @return recyclerView所需的List<ViewHolderSeat>
     */
    public static List<ViewHolderSeat> getWatchedSeatsList(List<SeatArea> allSeatsResponse) {
        List<ViewHolderSeat> viewHolderSeatList = new ArrayList<>();
        for (int i = 0; i < allSeatsResponse.size(); i++) {
            SeatArea seatArea = allSeatsResponse.get(i);
            List<Seat> seatList = seatArea.getSeatList();
            if (null != seatList && !seatList.isEmpty()) {
                if (TextUtils.isEmpty(seatArea.getAreaId())) {
                    seatArea.setAreaId(ViewHolderSeat.DEFAULT_AREA_ID);
                }
                viewHolderSeatList.add(new ViewHolderSeat(true, seatArea.getAreaId(), seatArea.getAreaName()));
                for (int j = 0; j < seatList.size(); j++) {
                    if (TextUtils.isEmpty(seatList.get(j).getAreaId())) {
                        seatList.get(j).setAreaId(ViewHolderSeat.DEFAULT_AREA_ID);
                    }
                    viewHolderSeatList.add(new ViewHolderSeat(seatList.get(j)));
                }
            }
        }
        return viewHolderSeatList;
    }

    /**
     * 根据服务端返回的数据，初始化便于UI展示处理的deskMessage
     *
     * @param deskList
     */
    public static List<DeskMessage> initDeskMsg(List<DeskMessage> deskList) {
        if (null == deskList || deskList.isEmpty()) {
            return deskList;
        }
        filterTakeoutMsg(deskList);
        return deskList;
    }

    private static void filterTakeoutMsg(List<DeskMessage> deskList) {
        if (!UserHelper.getWorkModeIsMixture()) {
            return;
        }
        Iterator<DeskMessage> it = deskList.iterator();
        while (it.hasNext()) {
            DeskMessage deskMessage = it.next();
            if (deskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                    || deskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE
                    || deskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT
                    || deskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_ORDER_CANCLE_INDEPENDENT
                    || deskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_ORDER_REMINDER
                    || deskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_DELIVERY_REFRESH) {
                it.remove();
            }
        }
    }

    /**
     * 根据服务器返回的消息详情转换为recyclerview需要的List<MessageDetailRecyclerItem>
     *
     * @param deskMsgDetail 服务端返回的消息详情
     * @return recyclerview需要的List<TakeoutDetailRecyclerItem>
     */
    public static List<DeskMsgDetailRecyclerItem> getDeskMsgDetailRecyclerItemList(DeskMsgDetail deskMsgDetail) {
        List<DeskMsgDetailRecyclerItem> deskMsgDetailRecyclerItemList = new ArrayList<>();
        if (null != deskMsgDetail) {
            //订单信息
            DeskMsgDetailRecyclerItem orderRecyclerItem = new DeskMsgDetailRecyclerItem();
            orderRecyclerItem.setItemType(DeskMsgDetailRecyclerItem.ItemType.TYPE_ORDER_INFO);
            DeskMsgDetailOrderInfoRecyclerItem orderInfoRecyclerItem = new DeskMsgDetailOrderInfoRecyclerItem();
            orderInfoRecyclerItem.setTitle(deskMsgDetail.getTitle());
            orderInfoRecyclerItem.setSource(deskMsgDetail.getSource());
            orderInfoRecyclerItem.setStatusStr(deskMsgDetail.getStatusStr());
            orderInfoRecyclerItem.setStatus(deskMsgDetail.getStatus());
            orderInfoRecyclerItem.setPeopleCount(deskMsgDetail.getPeopleCount());
            orderInfoRecyclerItem.setSeatCode(deskMsgDetail.getSeatCode());
            orderInfoRecyclerItem.setSeatName(deskMsgDetail.getSeatName());
            orderInfoRecyclerItem.setOrderCode(deskMsgDetail.getOrderCode());
            orderInfoRecyclerItem.setOrderMemo(deskMsgDetail.getOrderMemo());
            //收款信息
            DeskMsgDetailAccountsInfoRecyclerItem accountsInfoRecyclerItem = new DeskMsgDetailAccountsInfoRecyclerItem();
            ServiceBill serviceBillVO = deskMsgDetail.getServiceBillVO();
            //如果支付列表为空，则不显示收款信息
            if (null != deskMsgDetail.getCloudPayList() && !deskMsgDetail.getCloudPayList().isEmpty()) {
                if (null != serviceBillVO) {
                    //应收
                    String receivableFee = null;
                    if (serviceBillVO.getFinalAmount() > 0) {
                        receivableFee = FeeHelper.getDecimalFee(serviceBillVO.getFinalAmount());
                        receivableFee = String.format(context.getResources()
                                .getString(R.string.msg_detail_price_format), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , receivableFee));
                    }
                    accountsInfoRecyclerItem.setReceivableFee(receivableFee);
                    //已收
                    String receivedFee = null;
                    if (serviceBillVO.getPaidFee() > 0) {
                        receivedFee = FeeHelper.getDecimalFee(serviceBillVO.getPaidFee());
                        receivedFee = String.format(context.getResources()
                                .getString(R.string.msg_detail_price_format), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , receivedFee));
                    }
                    accountsInfoRecyclerItem.setReceivedFee(receivedFee);
                    //多收/少收 ，正数代表多收，负数代表少收
                    String exceedFee = null;
                    if (serviceBillVO.getNeedPayFee() != 0) {
                        exceedFee = FeeHelper.getDecimalFee(Math.abs(serviceBillVO.getNeedPayFee()));
                        exceedFee = String.format(context.getResources()
                                .getString(R.string.msg_detail_price_format), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , exceedFee));
                        if (serviceBillVO.getNeedPayFee() > 0) {
                            accountsInfoRecyclerItem.setExceedMore(false);
                        } else {
                            accountsInfoRecyclerItem.setExceedMore(true);
                        }
                    }
                    accountsInfoRecyclerItem.setExceedFee(exceedFee);
                    //消费金额
                    String consumeFee = null;
                    if (serviceBillVO.getOriginAmount() > 0) {
                        consumeFee = FeeHelper.getDecimalFee(serviceBillVO.getOriginAmount());
                        consumeFee = String.format(context.getResources()
                                .getString(R.string.msg_detail_price_format), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , consumeFee));
                    }
                    accountsInfoRecyclerItem.setConsumeFee(consumeFee);
                    //服务费
                    String serviceFee = null;
                    if (serviceBillVO.getOriginServiceCharge() > 0) {
                        serviceFee = FeeHelper.getDecimalFee(serviceBillVO.getOriginServiceCharge());
                        serviceFee = String.format(context.getResources()
                                .getString(R.string.msg_detail_price_format), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , serviceFee));
                    }
                    accountsInfoRecyclerItem.setServiceFee(serviceFee);
                    //最低消费
                    String minimumFee = null;
                    if (serviceBillVO.getOriginLeastAmount() > 0) {
                        minimumFee = FeeHelper.getDecimalFee(serviceBillVO.getOriginLeastAmount());
                        minimumFee = String.format(context.getResources()
                                .getString(R.string.msg_detail_price_format), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , minimumFee));
                    }
                    accountsInfoRecyclerItem.setMinimumFee(minimumFee);
                    //最低消费
                    String discountFee = null;
                    if (serviceBillVO.getDiscountAmount() != 0) {
                        discountFee = FeeHelper.getDecimalFee(Math.abs(serviceBillVO.getDiscountAmount()));
                        if (serviceBillVO.getDiscountAmount() < 0) {
                            discountFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                    , discountFee, true);
                        } else {
                            discountFee = String.format(context.getResources()
                                    .getString(R.string.msg_detail_price_format), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                    , discountFee));
                        }
                    }
                    accountsInfoRecyclerItem.setDiscountFee(discountFee);
                } else {
                    accountsInfoRecyclerItem = null;
                }
            } else {
                accountsInfoRecyclerItem = null;
            }
            orderInfoRecyclerItem.setAccountsInfoRecyclerItem(accountsInfoRecyclerItem);
            orderRecyclerItem.setOrderInfo(orderInfoRecyclerItem);
            deskMsgDetailRecyclerItemList.add(orderRecyclerItem);
            //付款信息
            List<DeskMsgPay> cloudPayList = deskMsgDetail.getCloudPayList();
            if (null != cloudPayList) {
                for (int i = 0; i < cloudPayList.size(); i++) {
                    DeskMsgPay pay = cloudPayList.get(i);
                    if (null != pay) {
                        DeskMsgDetailRecyclerItem payInfoRecyclerItem = new DeskMsgDetailRecyclerItem();
                        payInfoRecyclerItem.setItemType(DeskMsgDetailRecyclerItem.ItemType.TYPE_PAY_ITEM);
                        DeskMsgDetailPayInfoRecyclerItem detailPayInfoRecyclerItem = new DeskMsgDetailPayInfoRecyclerItem();
                        detailPayInfoRecyclerItem.setPaymentMethod(pay.getType());
                        String payMethod;
                        switch (pay.getType()) {
                            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_WEIXIN:
                                payMethod = GlobalVars.context.getString(R.string.order_from_weixin);
                                break;
                            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_ALIPAY:
                                payMethod = GlobalVars.context.getString(R.string.order_from_alipay);
                                break;
                            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_QQ:
                                payMethod = GlobalVars.context.getString(R.string.qq);
                                break;
                            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_CASH_MONEY:
                                payMethod = GlobalVars.context.getString(R.string.cash_discount);
                                break;
                            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_CASH_MONEY0:
                                payMethod = GlobalVars.context.getString(R.string.cash_money);
                                break;
                            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_VIP_CARD:
                                payMethod = GlobalVars.context.getString(R.string.vip_car);
                                break;
                            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_YIN_LIAN:
                            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_XUNLIAN:
                            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_YIN_LIAN_NET:
                                payMethod = GlobalVars.context.getString(R.string.bank);
                                break;
                            default:
                                payMethod = GlobalVars.context.getString(R.string.order_from_other);
                                break;
                        }
                        String payFee = FeeHelper.getDecimalFee(pay.getFee());
//                        payFee = String.format(context.getResources().getString(R.string.msg_detail_price_format)
//                                , payFee);
                        String payInfoContent = String.format(context.getResources()
                                        .getString(R.string.msg_detail_pay_info), pay.getCustomerName()
                                , TimeUtils.getTimeStr(pay.getPayTime(), TimeUtils.FORMAT_TIME)
                                , payMethod, String.format(context.getResources().getString(R.string.msg_detail_price_format)
                                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                                , payFee)));
                        detailPayInfoRecyclerItem.setPayInfoContent(payInfoContent);
                        String payOrderNo = String.format(context.getResources()
                                .getString(R.string.msg_detail_pay_orderno), payMethod, pay.getCode());
                        detailPayInfoRecyclerItem.setPayOrderNo(payOrderNo);
                        payInfoRecyclerItem.setPayInfoRecyclerItem(detailPayInfoRecyclerItem);
                        deskMsgDetailRecyclerItemList.add(payInfoRecyclerItem);
                    }
                }
            }
            //点菜人信息
            DeskMsgDetailRecyclerItem detailFoodsTitleRecyclerItem = new DeskMsgDetailRecyclerItem();
            detailFoodsTitleRecyclerItem.setItemType(DeskMsgDetailRecyclerItem.ItemType.TYPE_FOOD_TITLE);
            DeskMsgDetailFoodsTitleRecyclerItem foodsTitleRecyclerItem = new DeskMsgDetailFoodsTitleRecyclerItem();
            foodsTitleRecyclerItem.setCustomerAvatarUrl(deskMsgDetail.getCustomerAvatarUrl());
            foodsTitleRecyclerItem.setCustomerName(deskMsgDetail.getCustomerName());

            foodsTitleRecyclerItem.setOpenTime(TimeUtils.getTimeStr(deskMsgDetail.getOpenTime()
                    , TimeUtils.FORMAT_TIME));
            foodsTitleRecyclerItem.setCreateTime(TimeUtils.getTimeStr(deskMsgDetail.getCreateTime()
                    , TimeUtils.FORMAT_TIME));
            foodsTitleRecyclerItem.setModifiedTime(TimeUtils.getTimeStr(deskMsgDetail.getModifiedTime()
                    , TimeUtils.FORMAT_TIME));
            //消息详情展示用的时间，未审核的消息用createTime，审核过的（处理过的）消息用openTime
            switch (deskMsgDetail.getStatus()) {
                case MessageDetailHelper.OrderState.STATE_AGREED:
                case MessageDetailHelper.OrderState.STATE_TIMEOUT:
                case MessageDetailHelper.OrderState.STATE_REJECTED:
                    foodsTitleRecyclerItem.setShowTime(foodsTitleRecyclerItem.getModifiedTime());
                    break;
                default:
                    foodsTitleRecyclerItem.setShowTime(foodsTitleRecyclerItem.getCreateTime());
                    break;
            }
            foodsTitleRecyclerItem.setMenuNum(String.format(context.getResources().getString(R.string.msg_detail_foods_count), deskMsgDetail.getMenuNum()));
            detailFoodsTitleRecyclerItem.setFoodsTitle(foodsTitleRecyclerItem);
            deskMsgDetailRecyclerItemList.add(detailFoodsTitleRecyclerItem);

            //菜
            List<DeskMsgDetailFood> detailFoodList = deskMsgDetail.getWaitingInstanceList();
            if (null != detailFoodList) {
                for (int i = 0; i < detailFoodList.size(); i++) {
                    DeskMsgDetailFood deskMsgDetailFood = detailFoodList.get(i);
                    if (null != deskMsgDetailFood) {
                        switch (deskMsgDetailFood.getKind()) {
                            //普通菜
                            case MessageDetailHelper.FoodType.TYPE_NORMAL_FOOD:
                                DeskMsgDetailRecyclerItem detailFoodsRecyclerItem = new DeskMsgDetailRecyclerItem();
                                detailFoodsRecyclerItem.setItemType(DeskMsgDetailRecyclerItem.ItemType.TYPE_FOOD_ITEM);
                                DeskMsgDetailFoodsItemInfoRecyclerItem foodsItemInfo = new DeskMsgDetailFoodsItemInfoRecyclerItem();
                                foodsItemInfo.setName(deskMsgDetailFood.getName());
                                foodsItemInfo.setMakeName(getFoodMakeName(deskMsgDetailFood));
                                //计算总价格，商品的总金额，例如商品点了两份，这里的金额为两份的金额，如果有做法、加料加价，此金额包含加价
                                String fee = FeeHelper.getDecimalFee(deskMsgDetailFood.getFee(), 2);
                                foodsItemInfo.setPrice(String.format(context.getResources().getString(R.string.msg_detail_price_format)
                                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                                , fee)));
                                foodsItemInfo.setNum(getFoodNum(deskMsgDetailFood));
                                foodsItemInfo.setMemo(deskMsgDetailFood.getMemo());
                                foodsItemInfo.setStatus(deskMsgDetailFood.getStatus());
                                foodsItemInfo.setOrderStatus(deskMsgDetail.getStatus());
                                detailFoodsRecyclerItem.setFoodsItemInfo(foodsItemInfo);
                                deskMsgDetailRecyclerItemList.add(detailFoodsRecyclerItem);
                                break;
                            //套餐
                            case MessageDetailHelper.FoodType.TYPE_COMBO_FOOD:
                                DeskMsgDetailRecyclerItem comboFoodsTitleRecyclerItem = new DeskMsgDetailRecyclerItem();
                                comboFoodsTitleRecyclerItem.setItemType(DeskMsgDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_TITLE);
                                DeskMsgDetailComboFoodsTitleRecyclerItem deskMsgDetailComboFoodsTitleRecyclerItem
                                        = new DeskMsgDetailComboFoodsTitleRecyclerItem();
                                deskMsgDetailComboFoodsTitleRecyclerItem.setFoodName(deskMsgDetailFood.getName());
                                if (doubleIsInteger(deskMsgDetailFood.getNum())) {
                                    deskMsgDetailComboFoodsTitleRecyclerItem.setFoodNum((int) deskMsgDetailFood.getNum()
                                            + deskMsgDetailFood.getUnit());
                                } else {
                                    deskMsgDetailComboFoodsTitleRecyclerItem.setFoodNum(deskMsgDetailFood.getNum()
                                            + deskMsgDetailFood.getUnit());
                                }
                                //计算总价格，商品的总金额，例如商品点了两份，这里的金额为两份的金额，如果有做法、加料加价，此金额包含加价
                                String comboFee = FeeHelper.getDecimalFee(deskMsgDetailFood.getFee(), 2);
                                deskMsgDetailComboFoodsTitleRecyclerItem.setFoodPrice(String.format(context.getResources().getString(R.string.msg_detail_price_format)
                                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                                , comboFee)));
                                comboFoodsTitleRecyclerItem.setComboFoodsTitle(deskMsgDetailComboFoodsTitleRecyclerItem);
                                deskMsgDetailRecyclerItemList.add(comboFoodsTitleRecyclerItem);
                                //套餐—子菜
                                deskMsgDetailRecyclerItemList.addAll(getComboMenuList(deskMsgDetailFood));
                                break;
                        }
                    }
                }
            }
        }
        return deskMsgDetailRecyclerItemList;
    }

    /**
     * 根据服务器返回的消息详情转换为recyclerview需要的List<TakeoutDetailRecyclerItem>
     *
     * @param takeoutMsgDetailResponse 服务端返回的消息详情
     * @return recyclerview需要的List<TakeoutDetailRecyclerItem>
     */
    public static List<TakeoutDetailRecyclerItem> getTakeoutDetailRecyclerItemList(TakeoutMsgDetailResponse takeoutMsgDetailResponse) {
        TakeoutOrderVo takeoutOrderVo = takeoutMsgDetailResponse.getTakeoutOrderVo();
        List<TakeoutDetailRecyclerItem> deskMsgDetailRecyclerItemList = new ArrayList<>();
        if (null != takeoutOrderVo) {
            //订单信息
            TakeoutDetailRecyclerItem orderRecyclerItem = new TakeoutDetailRecyclerItem();
            orderRecyclerItem.setItemType(TakeoutDetailRecyclerItem.ItemType.TYPE_ORDER_INFO);
            TakeoutDetailOrderInfoRecyclerItem orderInfoRecyclerItem = new TakeoutDetailOrderInfoRecyclerItem();
            orderInfoRecyclerItem.setOrderFrom(takeoutOrderVo.getOrderFrom());
            switch (takeoutOrderVo.getOrderFrom()) {
                case TakeoutConstants.OrderFrom.XIAOER:
                    orderInfoRecyclerItem.setSource(context.getString(R.string.takeout_order_from_name_xiaoer));
                    break;
                case TakeoutConstants.OrderFrom.MEITUAN:
                    orderInfoRecyclerItem.setSource(context.getString(R.string.takeout_order_from_name_meituan));
                    break;
                case TakeoutConstants.OrderFrom.BAIDU:
                    orderInfoRecyclerItem.setSource(context.getString(R.string.takeout_order_from_name_baidu));
                    break;
                case TakeoutConstants.OrderFrom.ERLEME:
                    orderInfoRecyclerItem.setSource(context.getString(R.string.takeout_order_from_name_eleme));
                    break;
            }
            switch (takeoutOrderVo.getSendType()) {
                case TakeoutConstants.SendType.MERCHANT:
                    orderInfoRecyclerItem.setDeliveryMethod(context.getString(R.string.takeout_take_by_other));
                    break;
                case TakeoutConstants.SendType.THIRD_PARTY:
                    orderInfoRecyclerItem.setDeliveryMethod(context.getString(R.string.takeout_take_by_other));
                    break;
                case TakeoutConstants.SendType.SELF_TAKE:
                    orderInfoRecyclerItem.setDeliveryMethod(context.getString(R.string.takeout_take_self));
                    break;
            }
            orderInfoRecyclerItem.setSendType(takeoutOrderVo.getSendType());
            orderInfoRecyclerItem.setReserveStatus(takeoutOrderVo.getReserveStatus());
            if (takeoutOrderVo.getSendType() != TakeoutConstants.SendType.SELF_TAKE) {
                switch (takeoutOrderVo.getReserveStatus()) {
                    case TakeoutConstants.ReserveStatus.DELIVERY_IMMEDIATELY:
                        orderInfoRecyclerItem.setSendTime(String.format(context.getResources().getString(R.string.takeout_send_set_time)
                                , context.getString(R.string.takeout_send_rightnow)));
                        break;
                    case TakeoutConstants.ReserveStatus.APPOINTMENT:
                        if (takeoutOrderVo.getSendType() == TakeoutConstants.SendType.SELF_TAKE) {
                            orderInfoRecyclerItem.setSendTime(String.format(context.getResources().getString(R.string.takeout_takeself_set_time)
                                    , takeoutOrderVo.getSendTime()));
                        } else {
                            orderInfoRecyclerItem.setSendTime(String.format(context.getResources().getString(R.string.takeout_send_set_time)
                                    , takeoutOrderVo.getSendTime()));
                        }
                        break;
                }
            } else {
                orderInfoRecyclerItem.setSendTime(String.format(context.getResources().getString(R.string.takeout_takeself_set_time)
                        , takeoutOrderVo.getSendTime()));
            }
            orderInfoRecyclerItem.setStatus(takeoutMsgDetailResponse.getMsgState());
            orderInfoRecyclerItem.setReceiverName(takeoutOrderVo.getName());
            orderInfoRecyclerItem.setReceiverPhone(takeoutOrderVo.getMobile());
            orderInfoRecyclerItem.setAddress(takeoutOrderVo.getAddress());
            orderInfoRecyclerItem.setDistance(MessageDetailHelper.formatDistance(takeoutOrderVo.getDistance()));
            orderInfoRecyclerItem.setLatitude(takeoutOrderVo.getLatitude());
            orderInfoRecyclerItem.setLongitude(takeoutOrderVo.getLongitude());

            TakeoutOrderDetailVo takeoutOrderDetailVo = takeoutOrderVo.getTakeoutOrderDetailVo();
            if (null != takeoutOrderDetailVo) {
                orderInfoRecyclerItem.setPeopleCount(takeoutOrderDetailVo.getPeopleCount());
                orderInfoRecyclerItem.setRemark(takeoutOrderDetailVo.getMemo());
            }
            orderRecyclerItem.setOrderInfo(orderInfoRecyclerItem);
            deskMsgDetailRecyclerItemList.add(orderRecyclerItem);

            if (null != takeoutOrderDetailVo) {
                //菜
                List<TakeoutInstanceVo> detailFoodList = takeoutOrderDetailVo.getTakeoutInstanceVoList();
                if (null != detailFoodList) {
                    for (int i = 0; i < detailFoodList.size(); i++) {
                        TakeoutInstanceVo detailFood = detailFoodList.get(i);
                        if (null != detailFood) {
                            switch (detailFood.getKind()) {
                                //普通菜
                                case MessageDetailHelper.FoodType.TYPE_NORMAL_FOOD:
                                    TakeoutDetailRecyclerItem detailFoodsRecyclerItem = new TakeoutDetailRecyclerItem();
                                    detailFoodsRecyclerItem.setItemType(TakeoutDetailRecyclerItem.ItemType.TYPE_FOOD_ITEM);
                                    TakeoutDetailFoodsItemInfoRecyclerItem foodsItemInfo = new TakeoutDetailFoodsItemInfoRecyclerItem();
                                    foodsItemInfo.setName(detailFood.getName());
                                    foodsItemInfo.setMakeName(getTakeoutFoodMakeName(detailFood));
                                    foodsItemInfo.setFoodNum(detailFood.getNum());
                                    if (doubleIsInteger(detailFood.getNum())) {
                                        foodsItemInfo.setNum(String.format(context.getResources().getString(R.string.msg_detail_combo_foods_count)
                                                , (int) detailFood.getNum() + ""));
                                    } else {
                                        foodsItemInfo.setNum(String.format(context.getResources().getString(R.string.msg_detail_combo_foods_count)
                                                , detailFood.getNum() + ""));
                                    }
                                    detailFoodsRecyclerItem.setFoodsItemInfo(foodsItemInfo);
                                    deskMsgDetailRecyclerItemList.add(detailFoodsRecyclerItem);
                                    break;
                                //套餐
                                case MessageDetailHelper.FoodType.TYPE_COMBO_FOOD:
                                    TakeoutDetailRecyclerItem comboFoodsTitleRecyclerItem = new TakeoutDetailRecyclerItem();
                                    comboFoodsTitleRecyclerItem.setItemType(TakeoutDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_TITLE);
                                    TakeoutDetailComboFoodsTitleRecyclerItem deskMsgDetailComboFoodsTitleRecyclerItem
                                            = new TakeoutDetailComboFoodsTitleRecyclerItem();
                                    deskMsgDetailComboFoodsTitleRecyclerItem.setFoodName(detailFood.getName());
                                    deskMsgDetailComboFoodsTitleRecyclerItem.setNum(detailFood.getNum());
                                    if (doubleIsInteger(detailFood.getNum())) {
                                        deskMsgDetailComboFoodsTitleRecyclerItem.setFoodNum(String.format(context.getResources().getString(R.string.msg_detail_combo_foods_count)
                                                , (int) detailFood.getNum() + ""));
                                    } else {
                                        deskMsgDetailComboFoodsTitleRecyclerItem.setFoodNum(String.format(context.getResources().getString(R.string.msg_detail_combo_foods_count)
                                                , detailFood.getNum() + ""));
                                    }
                                    comboFoodsTitleRecyclerItem.setComboFoodsTitle(deskMsgDetailComboFoodsTitleRecyclerItem);
                                    deskMsgDetailRecyclerItemList.add(comboFoodsTitleRecyclerItem);
                                    //套餐—子菜
                                    deskMsgDetailRecyclerItemList.addAll(getTakeoutComboMenuList(detailFood));
                                    break;
                            }
                        }
                    }
                    orderInfoRecyclerItem.setFoodsTotalCount(takeoutOrderDetailVo.getInstanceNum());
                    orderInfoRecyclerItem.setOutId(takeoutOrderDetailVo.getOutId());
                }
                //支付明细
                TakeoutDetailRecyclerItem payRecyclerItem = new TakeoutDetailRecyclerItem();
                payRecyclerItem.setItemType(TakeoutDetailRecyclerItem.ItemType.TYPE_PAY_DETAIL);
                TakeoutDetailPayDetailRecyclerItem payDetailRecyclerItem = new TakeoutDetailPayDetailRecyclerItem();
                payDetailRecyclerItem.setPayVoList(takeoutOrderDetailVo.getTakeoutPayVoList());
                if (takeoutOrderDetailVo.getOutFee() > 0) {
                    payDetailRecyclerItem.setDeliveryFee(String.format(context.getResources().getString(R.string.takeout_msg_pay_detail_outfee)
                            , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                    , FeeHelper.getDecimalFee(takeoutOrderDetailVo.getOutFee()))));
                }
                payRecyclerItem.setTakeoutDetailPayDetailRecyclerItem(payDetailRecyclerItem);
                deskMsgDetailRecyclerItemList.add(payRecyclerItem);

            }
        }
        return deskMsgDetailRecyclerItemList;
    }

    /**
     * 普通菜，商品如果有做法、规格、备注、加料在商品的下一行显示，超过一行时换行显示，例如大杯，加冰，椰果1份
     * 加料需展示每份加料的数量
     * 依次显示规格、做法、加料、备注，用逗号隔开
     *
     * @param deskMsgDetailFood
     * @return recyclerview item中展示需要的makeName
     */
    private static String getFoodMakeName(DeskMsgDetailFood deskMsgDetailFood) {
        StringBuilder makeNameBuilder = new StringBuilder();
        //规格
        if (!TextUtils.isEmpty(deskMsgDetailFood.getSpecDetailName())) {
            makeNameBuilder.append(deskMsgDetailFood.getSpecDetailName());
            makeNameBuilder.append(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
        }
        //做法
        if (!TextUtils.isEmpty(deskMsgDetailFood.getMakeName())) {
            makeNameBuilder.append(deskMsgDetailFood.getMakeName());
            makeNameBuilder.append(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
        }
        //加料
        List<DeskMsgDetailFoodItem> feedList = deskMsgDetailFood.getFeedList();
        if (null != feedList && !feedList.isEmpty()) {
            for (int i = 0; i < feedList.size(); i++) {
                DeskMsgDetailFoodItem foodItem = feedList.get(i);
                if (null != foodItem) {
                    makeNameBuilder.append(foodItem.getName());
                    makeNameBuilder.append(foodItem.getNum());
                    makeNameBuilder.append(feedList.get(i).getUnit());
                    makeNameBuilder.append(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
                }
            }
        }
        //备注
        if (!TextUtils.isEmpty(deskMsgDetailFood.getMemo())) {
            makeNameBuilder.append(deskMsgDetailFood.getMemo());
        }
        //如果最后是逗号结尾，则切割掉最后一个字符
        boolean isValid = makeNameBuilder.length() > 0 && String.valueOf(makeNameBuilder.charAt(makeNameBuilder.length() - 1))
                .equals(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
        if (isValid) {
            return makeNameBuilder.substring(0, makeNameBuilder.length() - 1);
        }
        return makeNameBuilder.toString();
    }

    /**
     * 套餐子菜，商品如果有做法、规格、备注、加料在商品的下一行显示，超过一行时换行显示，例如大杯，加冰，椰果1份
     * 加料需展示每份加料的数量
     * 依次显示规格、做法、加料、备注，用逗号隔开
     *
     * @param deskMsgDetailFoodItem
     * @return recyclerview item中展示需要的makeName
     */
    private static String getComboFoodItemMakeName(DeskMsgDetailFoodItem deskMsgDetailFoodItem) {
        StringBuilder makeNameBuilder = new StringBuilder();
        //规格
        if (!TextUtils.isEmpty(deskMsgDetailFoodItem.getSpecDetailName())) {
            makeNameBuilder.append(deskMsgDetailFoodItem.getSpecDetailName());
            makeNameBuilder.append(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
        }
        //做法
        if (!TextUtils.isEmpty(deskMsgDetailFoodItem.getMakeName())) {
            makeNameBuilder.append(deskMsgDetailFoodItem.getMakeName());
            makeNameBuilder.append(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
        }
        //加料
        List<DeskMsgDetailFoodItem> feedList = deskMsgDetailFoodItem.getFeedList();
        if (null != feedList && !feedList.isEmpty()) {
            for (int i = 0; i < feedList.size(); i++) {
                DeskMsgDetailFoodItem foodItem = feedList.get(i);
                if (null != foodItem) {
                    makeNameBuilder.append(foodItem.getName());
                    if (doubleIsInteger(foodItem.getNum())) {
                        makeNameBuilder.append((int) foodItem.getNum());
                    } else {
                        makeNameBuilder.append(foodItem.getNum());
                    }
                    makeNameBuilder.append(feedList.get(i).getUnit());
                    makeNameBuilder.append(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
                }
            }
        }
        //备注
        if (!TextUtils.isEmpty(deskMsgDetailFoodItem.getMemo())) {
            makeNameBuilder.append(deskMsgDetailFoodItem.getMemo());
        }
        //如果最后是逗号结尾，则切割掉最后一个字符
        boolean isValid = makeNameBuilder.length() > 0 && String.valueOf(makeNameBuilder.charAt(makeNameBuilder.length() - 1))
                .equals(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
        if (isValid) {
            return makeNameBuilder.substring(0, makeNameBuilder.length() - 1);
        }
        return makeNameBuilder.toString();
    }

    /**
     * 获取套餐的子菜列表
     *
     * @param deskMsgDetailFood
     * @return
     */
    private static List<DeskMsgDetailRecyclerItem> getComboMenuList(DeskMsgDetailFood deskMsgDetailFood) {
        List<DeskMsgDetailRecyclerItem> deskMsgDetailRecyclerItems = new ArrayList<>();
        List<DeskMsgDetailFoodItem> menuList = deskMsgDetailFood.getMenuList();
        if (null != menuList) {
            for (int j = 0; j < menuList.size(); j++) {
                DeskMsgDetailFoodItem deskMsgDetailFoodItem = menuList.get(j);
                if (null != deskMsgDetailFoodItem) {
                    DeskMsgDetailRecyclerItem comboDetailFoodsRecyclerItem = new DeskMsgDetailRecyclerItem();
                    comboDetailFoodsRecyclerItem.setItemType(DeskMsgDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_ITEM);
                    DeskMsgDetailComboFoodsItemInfoRecyclerItem deskMsgDetailComboFoodsItemInfoRecyclerItem
                            = new DeskMsgDetailComboFoodsItemInfoRecyclerItem();
                    deskMsgDetailComboFoodsItemInfoRecyclerItem.setFoodName(deskMsgDetailFoodItem.getName());
                    deskMsgDetailComboFoodsItemInfoRecyclerItem.setFoodRemark(getComboFoodItemMakeName(deskMsgDetailFoodItem));
                    if (doubleIsInteger(deskMsgDetailFoodItem.getNum())) {
                        deskMsgDetailComboFoodsItemInfoRecyclerItem.setFoodNum(String.format(context.getResources().getString(R.string.msg_detail_combo_foods_count)
                                , (int) deskMsgDetailFoodItem.getNum() + ""));
                    } else {
                        deskMsgDetailComboFoodsItemInfoRecyclerItem.setFoodNum(String.format(context.getResources().getString(R.string.msg_detail_combo_foods_count)
                                , deskMsgDetailFoodItem.getNum() + ""));
                    }
                    deskMsgDetailComboFoodsItemInfoRecyclerItem.setStatus(deskMsgDetailFoodItem.getStatus());
                    comboDetailFoodsRecyclerItem.setComboFoodsItemInfo(deskMsgDetailComboFoodsItemInfoRecyclerItem);
                    deskMsgDetailRecyclerItems.add(comboDetailFoodsRecyclerItem);
                }
            }
        }
        return deskMsgDetailRecyclerItems;
    }

    /**
     * 获取外卖套餐的子菜列表
     *
     * @return
     */
    private static List<TakeoutDetailRecyclerItem> getTakeoutComboMenuList(TakeoutInstanceVo takeoutInstanceVo) {
        List<TakeoutDetailRecyclerItem> deskMsgDetailRecyclerItems = new ArrayList<>();
        List<TakeoutInstanceVo> menuList = takeoutInstanceVo.getChildTakeoutInstances();
        if (null != menuList) {
            for (int j = 0; j < menuList.size(); j++) {
                TakeoutInstanceVo childInstance = menuList.get(j);
                if (null != childInstance) {
                    TakeoutDetailRecyclerItem comboDetailFoodsRecyclerItem = new TakeoutDetailRecyclerItem();
                    comboDetailFoodsRecyclerItem.setItemType(TakeoutDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_ITEM);
                    TakeoutDetailComboFoodsItemInfoRecyclerItem deskMsgDetailComboFoodsItemInfoRecyclerItem
                            = new TakeoutDetailComboFoodsItemInfoRecyclerItem();
                    deskMsgDetailComboFoodsItemInfoRecyclerItem.setFoodName(childInstance.getName());
                    deskMsgDetailComboFoodsItemInfoRecyclerItem.setFoodRemark(getTakeoutFoodMakeName(childInstance));
                    deskMsgDetailComboFoodsItemInfoRecyclerItem.setNum(childInstance.getNum());
                    if (doubleIsInteger(childInstance.getNum())) {
                        deskMsgDetailComboFoodsItemInfoRecyclerItem.setFoodNum(String.format(context.getResources().getString(R.string.msg_detail_combo_foods_count)
                                , (int) childInstance.getNum() + ""));
                    } else {
                        deskMsgDetailComboFoodsItemInfoRecyclerItem.setFoodNum(String.format(context.getResources().getString(R.string.msg_detail_combo_foods_count)
                                , childInstance.getNum() + ""));
                    }
                    comboDetailFoodsRecyclerItem.setComboFoodsItemInfo(deskMsgDetailComboFoodsItemInfoRecyclerItem);
                    deskMsgDetailRecyclerItems.add(comboDetailFoodsRecyclerItem);
                }
            }
        }
        return deskMsgDetailRecyclerItems;
    }

    /**
     * 数量，数量+单位，如果是双单位商品，数量+点菜单位/重量单位
     * eg. 2只/1.3斤
     *
     * @param deskMsgDetailFood
     * @return 点菜的数量
     */
    private static String getFoodNum(DeskMsgDetailFood deskMsgDetailFood) {
        StringBuilder foodNumBuilder = new StringBuilder();
        double foodNum = deskMsgDetailFood.getNum();
        if (foodNum != 0) {
            if (doubleIsInteger(foodNum)) {
                foodNumBuilder.append((int) foodNum);
            } else {
                foodNumBuilder.append(deskMsgDetailFood.getNum());
            }
            foodNumBuilder.append(deskMsgDetailFood.getUnit());
        }
        boolean isNotValid = !TextUtils.isEmpty(deskMsgDetailFood.getUnit()) && !TextUtils.isEmpty(deskMsgDetailFood.getAccountUnit())
                && deskMsgDetailFood.getUnit().equals(deskMsgDetailFood.getAccountUnit());
        double accountNum = deskMsgDetailFood.getAccountNum();
        if (!isNotValid && accountNum != 0) {
            if (foodNumBuilder.length() != 0) {
                foodNumBuilder.append("/");
            }
            if (doubleIsInteger(accountNum)) {
                foodNumBuilder.append((int) accountNum);
            } else {
                foodNumBuilder.append(accountNum);
            }
            foodNumBuilder.append(deskMsgDetailFood.getAccountUnit());
        }
        return foodNumBuilder.toString();

    }

    /**
     * 外卖 普通菜，商品如果有做法、规格、备注、加料在商品的下一行显示，超过一行时换行显示，例如大杯，加冰，椰果1份
     * 加料需展示每份加料的数量
     * 依次显示规格、做法、加料、备注，用逗号隔开
     *
     * @param detailFood
     * @return recyclerview item中展示需要的makeName
     */
    private static String getTakeoutFoodMakeName(TakeoutInstanceVo detailFood) {
        StringBuilder makeNameBuilder = new StringBuilder();
        //规格
        if (!TextUtils.isEmpty(detailFood.getSpecDetailName())) {
            makeNameBuilder.append(detailFood.getSpecDetailName());
            makeNameBuilder.append(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
        }
        //做法
        if (!TextUtils.isEmpty(detailFood.getMakeName())) {
            makeNameBuilder.append(detailFood.getMakeName());
            makeNameBuilder.append(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
        }
        //加料
        List<TakeoutInstanceVo> feedList = detailFood.getChildTakeoutInstances();
        if (null != feedList && !feedList.isEmpty()) {
            for (int i = 0; i < feedList.size(); i++) {
                TakeoutInstanceVo foodItem = feedList.get(i);
                if (null != foodItem) {
                    makeNameBuilder.append(foodItem.getName());
                    if (doubleIsInteger(foodItem.getNum())) {
                        makeNameBuilder.append((int) foodItem.getNum());
                    } else {
                        makeNameBuilder.append(foodItem.getNum());
                    }
                    makeNameBuilder.append(feedList.get(i).getUnit());
                    makeNameBuilder.append(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
                }
            }
        }
        //备注
        if (!TextUtils.isEmpty(detailFood.getMemo())) {
            makeNameBuilder.append(detailFood.getMemo());
        }
        //如果最后是逗号结尾，则切割掉最后一个字符
        boolean isValid = makeNameBuilder.length() > 0 && String.valueOf(makeNameBuilder.charAt(makeNameBuilder.length() - 1))
                .equals(MessageDetailHelper.FOOD_MAKENAME_SEPARATOR);
        if (isValid) {
            return makeNameBuilder.substring(0, makeNameBuilder.length() - 1);
        }
        return makeNameBuilder.toString();
    }

    /**
     * 判断double是否是整型
     *
     * @param number double类型
     * @return
     */
    private static boolean doubleIsInteger(double number) {
        return !Double.isNaN(number) && !Double.isInfinite(number) && number == Math.rint(number);
    }
}
