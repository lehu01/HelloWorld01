package com.zmsoft.ccd.module.takeout.delivery.helper;

import com.zmsoft.ccd.lib.bean.message.TakeoutInstanceVo;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.delivery.adapter.DeliveryAdapter;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryJagItem;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryOrderItem;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryOrderListItem;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryOrderPendingItem;
import com.zmsoft.ccd.takeout.bean.DeliveryOrderVo;
import com.zmsoft.ccd.takeout.bean.GetDeliveryOrderListResponse;
import com.zmsoft.ccd.takeout.bean.Takeout;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import java.util.ArrayList;
import java.util.List;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/9/1.
 */

public class DataMapLayer {
    /**
     * 生成配送页头部订单ITEMS
     *
     * @param takeout
     * @return
     */
    public static List<DeliveryOrderListItem> getDeliveryOrderItemList(Takeout takeout) {
        List<DeliveryOrderListItem> deliveryOrderListItems = null;
        if (null != takeout) {
            deliveryOrderListItems = new ArrayList<>();
            //头部锯齿
            DeliveryOrderListItem jagUpItem = new DeliveryOrderListItem();
            DeliveryJagItem jagItem = new DeliveryJagItem();
            jagItem.setUp(true);
            jagUpItem.setItemType(DeliveryAdapter.ItemType.ITEM_TYPE_JAG_SLIDE);
            jagUpItem.setDeliveryJagItem(jagItem);
            deliveryOrderListItems.add(jagUpItem);
            //头部订单信息
            DeliveryOrderListItem orderItem = new DeliveryOrderListItem();
            DeliveryOrderItem deliveryOrderItem = new DeliveryOrderItem();
            deliveryOrderItem.setUserName(takeout.getName());
            deliveryOrderItem.setUserPhone(takeout.getMobile());
            deliveryOrderItem.setUserAddress(takeout.getAddress());
            deliveryOrderItem.setOrderFromType(takeout.getOrderFrom());
            switch (takeout.getOrderFrom()) {
                case TakeoutConstants.OrderFrom.XIAOER:
                    deliveryOrderItem.setOrderFrom(context.getString(R.string.takeout_order_from_name_xiaoer));
                    break;
                case TakeoutConstants.OrderFrom.MEITUAN:
                    deliveryOrderItem.setOrderFrom(context.getString(R.string.takeout_order_from_name_meituan));
                    break;
                case TakeoutConstants.OrderFrom.BAIDU:
                    deliveryOrderItem.setOrderFrom(context.getString(R.string.takeout_order_from_name_baidu));
                    break;
                case TakeoutConstants.OrderFrom.ERLEME:
                    deliveryOrderItem.setOrderFrom(context.getString(R.string.takeout_order_from_name_eleme));
                    break;
                case TakeoutConstants.OrderFrom.WEIDIAN:
                    deliveryOrderItem.setOrderFrom(context.getString(R.string.module_takeout_order_from_name_weidian));
                    break;
            }
            deliveryOrderItem.setOrderCode(String.valueOf(takeout.getCode()));
            if (null != takeout.getTakeoutOrderDetailVo() && null != takeout.getTakeoutOrderDetailVo().getTakeoutInstanceVoList()
                    && !takeout.getTakeoutOrderDetailVo().getTakeoutInstanceVoList().isEmpty()) {
                List<Takeout.TakeoutInstance> takeoutInstanceList = takeout.getTakeoutOrderDetailVo().getTakeoutInstanceVoList();
                StringBuilder stringBuilder = new StringBuilder(context.getString(R.string.module_takeout_good_name_label));
                stringBuilder.append(": ");
                for (int i = 0; i < takeoutInstanceList.size(); i++) {
                    Takeout.TakeoutInstance takeoutInstance = takeoutInstanceList.get(i);
                    if (null != takeoutInstance) {
                        stringBuilder.append(takeoutInstance.getName());
                        if (i != takeoutInstanceList.size() - 1) {
                            stringBuilder.append(context.getString(R.string.pause_separator));
                        }
                    }
                }
                deliveryOrderItem.setFoodsTotalNum(String.format(context.getResources().getString(R.string.module_takeout_delivery_foods_total_num)
                        , takeout.getTakeoutOrderDetailVo().getInstanceNum()));
                deliveryOrderItem.setFoodsName(stringBuilder.toString());
                deliveryOrderItem.setOutId(takeout.getTakeoutOrderDetailVo().getDaySeq());
            }
            orderItem.setItemType(DeliveryAdapter.ItemType.ITEM_TYPE_ORDER);
            orderItem.setDeliveryOrderItem(deliveryOrderItem);
            deliveryOrderListItems.add(orderItem);
            //下锯齿
            DeliveryOrderListItem jagDownItem = new DeliveryOrderListItem();
            DeliveryJagItem downItem = new DeliveryJagItem();
            downItem.setUp(false);
            jagDownItem.setItemType(DeliveryAdapter.ItemType.ITEM_TYPE_JAG_SLIDE);
            jagDownItem.setDeliveryJagItem(downItem);
            deliveryOrderListItems.add(jagDownItem);
        }
        return deliveryOrderListItems;
    }

    /**
     * 生成待配送订单ITEMS
     *
     * @param getDeliveryOrderListResponse
     * @return
     */
    public static List<DeliveryOrderListItem> getDeliveryPendingOrderList(GetDeliveryOrderListResponse getDeliveryOrderListResponse
            , int currentPage) {
        List<DeliveryOrderListItem> deliveryOrderListItems = null;
        if (null != getDeliveryOrderListResponse) {
            deliveryOrderListItems = new ArrayList<>();
            if (null != getDeliveryOrderListResponse.getDeliveryOrderVos()
                    && !getDeliveryOrderListResponse.getDeliveryOrderVos().isEmpty()) {
                List<DeliveryOrderVo> deliveryOrderVos = getDeliveryOrderListResponse.getDeliveryOrderVos();
                if (currentPage == 1) {
                    //上锯齿
                    DeliveryOrderListItem jagUpItem = new DeliveryOrderListItem();
                    DeliveryJagItem jagItem = new DeliveryJagItem();
                    jagItem.setUp(true);
                    jagUpItem.setItemType(DeliveryAdapter.ItemType.ITEM_TYPE_JAG_SLIDE);
                    jagUpItem.setDeliveryJagItem(jagItem);
                    deliveryOrderListItems.add(jagUpItem);
                }
                //待配送订单
                for (int i = 0; i < deliveryOrderVos.size(); i++) {
                    DeliveryOrderVo deliveryOrderVo = deliveryOrderVos.get(i);
                    if (null != deliveryOrderVo) {
                        DeliveryOrderListItem pendingListItem = new DeliveryOrderListItem();
                        DeliveryOrderPendingItem pendingItem = new DeliveryOrderPendingItem();
                        pendingItem.setUserName(deliveryOrderVo.getName());
                        pendingItem.setUserPhone(deliveryOrderVo.getMobile());
                        pendingItem.setOrderCode(String.valueOf(deliveryOrderVo.getCode()));
                        pendingItem.setOrderId(deliveryOrderVo.getOrderId());
                        pendingItem.setAddress(deliveryOrderVo.getAddress());
                        List<TakeoutInstanceVo> childTakeoutInstances = deliveryOrderVo.getChildTakeoutInstances();
                        if (null != childTakeoutInstances && !childTakeoutInstances.isEmpty()) {
                            StringBuilder stringBuilder = new StringBuilder(context.getString(R.string.module_takeout_good_name_label));
                            stringBuilder.append(": ");
                            for (int j = 0; j < childTakeoutInstances.size(); j++) {
                                TakeoutInstanceVo takeoutInstanceVo = childTakeoutInstances.get(j);
                                if (null != takeoutInstanceVo) {
                                    stringBuilder.append(takeoutInstanceVo.getName());
                                    if (j != childTakeoutInstances.size() - 1) {
                                        stringBuilder.append(context.getString(R.string.pause_separator));
                                    }
                                }
                            }
                            pendingItem.setFoodsTotalNum(String.format(context.getResources().getString(R.string.module_takeout_delivery_foods_total_num)
                                    , deliveryOrderVo.getInstanceNum()));
                            pendingItem.setFoodsName(stringBuilder.toString());
                        }
                        pendingListItem.setItemType(DeliveryAdapter.ItemType.ITEM_TYPE_PENDING_DELEVIRY);
                        pendingListItem.setPendingItem(pendingItem);
                        deliveryOrderListItems.add(pendingListItem);
                    }
                }
            }
        }
        return deliveryOrderListItems;
    }

    /**
     * 生成最底部锯齿
     *
     * @return
     */
    public static List<DeliveryOrderListItem> getBottomJagItem() {
        List<DeliveryOrderListItem> deliveryOrderListItems = new ArrayList<>(1);
        //下锯齿
        DeliveryOrderListItem jagDownItem = new DeliveryOrderListItem();
        DeliveryJagItem downItem = new DeliveryJagItem();
        downItem.setUp(false);
        jagDownItem.setItemType(DeliveryAdapter.ItemType.ITEM_TYPE_JAG_SLIDE);
        jagDownItem.setDeliveryJagItem(downItem);
        deliveryOrderListItems.add(jagDownItem);
        return deliveryOrderListItems;
    }
}
