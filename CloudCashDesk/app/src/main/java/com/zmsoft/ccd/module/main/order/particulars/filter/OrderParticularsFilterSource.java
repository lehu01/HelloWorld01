package com.zmsoft.ccd.module.main.order.particulars.filter;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterGroup;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterItem;

import java.util.ArrayList;
import java.util.List;


public class OrderParticularsFilterSource {

    public static List<OrderRightFilterGroup> getOrderParticularsFilterGroupList() {
        List<OrderRightFilterGroup> orderRightFilterGroupList = new ArrayList<>();

        // 订单来源
        OrderRightFilterGroup groupSource = new OrderRightFilterGroup();
        groupSource.setType(OrderRightFilterItem.Type.TYPE_SOURCE);
        groupSource.setGroupName(GlobalVars.context.getString(R.string.filter_title_source));
        List<OrderRightFilterItem> itemListSource = new ArrayList<>();
        itemListSource.add(new OrderRightFilterItem(true, GlobalVars.context.getString(R.string.filter_item_source_all), OrderRightFilterItem.Type.TYPE_SOURCE, OrderRightFilterItem.CodeSource.CODE_ALL));
        itemListSource.add(new OrderRightFilterItem(false, GlobalVars.context.getString(R.string.filter_item_source_desk), OrderRightFilterItem.Type.TYPE_SOURCE, OrderRightFilterItem.CodeSource.CODE_DESK));
        itemListSource.add(new OrderRightFilterItem(false, GlobalVars.context.getString(R.string.filter_item_source_retail), OrderRightFilterItem.Type.TYPE_SOURCE, OrderRightFilterItem.CodeSource.CODE_RETAIL));
        itemListSource.add(new OrderRightFilterItem(false, GlobalVars.context.getString(R.string.filter_item_source_waiter_takeout), OrderRightFilterItem.Type.TYPE_SOURCE, OrderRightFilterItem.CodeSource.CODE_WAITER_TAKEOUT));
        itemListSource.add(new OrderRightFilterItem(false, GlobalVars.context.getString(R.string.filter_item_source_meituan), OrderRightFilterItem.Type.TYPE_SOURCE, OrderRightFilterItem.CodeSource.CODE_MEITUAN));
        itemListSource.add(new OrderRightFilterItem(false, GlobalVars.context.getString(R.string.filter_item_source_elme), OrderRightFilterItem.Type.TYPE_SOURCE, OrderRightFilterItem.CodeSource.CODE_ELME));
        itemListSource.add(new OrderRightFilterItem(false, GlobalVars.context.getString(R.string.filter_item_source_baidu), OrderRightFilterItem.Type.TYPE_SOURCE, OrderRightFilterItem.CodeSource.CODE_BAIDU));
        groupSource.setItemList(itemListSource);
        orderRightFilterGroupList.add(groupSource);
        // 收银员
        OrderRightFilterGroup groupCashier = new OrderRightFilterGroup();
        groupCashier.setType(OrderRightFilterItem.Type.TYPE_CASHIER);
        groupCashier.setGroupName(GlobalVars.context.getString(R.string.filter_title_cashier));
        List<OrderRightFilterItem> itemListCashier = new ArrayList<>();
        itemListCashier.add(new OrderRightFilterItem(true, GlobalVars.context.getString(R.string.filter_item_cashier_all), OrderRightFilterItem.Type.TYPE_CASHIER, OrderRightFilterItem.CodeCashier.CODE_ALL));
        itemListCashier.add(new OrderRightFilterItem(false, GlobalVars.context.getString(R.string.filter_item_cashier_current), OrderRightFilterItem.Type.TYPE_CASHIER, OrderRightFilterItem.CodeCashier.CODE_CURRENT));
        groupCashier.setItemList(itemListCashier);
        orderRightFilterGroupList.add(groupCashier);
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
