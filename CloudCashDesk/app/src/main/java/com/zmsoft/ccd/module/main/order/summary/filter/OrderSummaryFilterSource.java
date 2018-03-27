package com.zmsoft.ccd.module.main.order.summary.filter;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterGroup;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterItem;

import java.util.ArrayList;
import java.util.List;


public class OrderSummaryFilterSource {

    public static List<OrderRightFilterGroup> getOrderSummaryFilterGroupList() {
        List<OrderRightFilterGroup> orderRightFilterGroupList = new ArrayList<>();
        // 日期
        OrderRightFilterGroup groupDate = new OrderRightFilterGroup();
        groupDate.setType(OrderRightFilterItem.Type.TYPE_DATE);
        groupDate.setGroupName(GlobalVars.context.getString(R.string.filter_title_date));
        List<OrderRightFilterItem> itemListDate = new ArrayList<>();
        itemListDate.add(new OrderRightFilterItem(true, GlobalVars.context.getString(R.string.filter_item_date_today), OrderRightFilterItem.Type.TYPE_DATE, OrderRightFilterItem.CodeDate.CODE_TODAY));
        itemListDate.add(new OrderRightFilterItem(false, GlobalVars.context.getString(R.string.filter_item_date_yesterday), OrderRightFilterItem.Type.TYPE_DATE, OrderRightFilterItem.CodeDate.CODE_YESTERDAY));
        itemListDate.add(new OrderRightFilterItem(false, GlobalVars.context.getString(R.string.filter_item_date_within_two_days), OrderRightFilterItem.Type.TYPE_DATE, OrderRightFilterItem.CodeDate.CODE_WITHIN_TWO_DAYS));
        groupDate.setItemList(itemListDate);
        orderRightFilterGroupList.add(groupDate);

        return orderRightFilterGroupList;
    }
}
