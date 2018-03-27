package com.zmsoft.ccd.helper;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.bean.filter.Filter;
import com.zmsoft.ccd.lib.bean.filter.FilterItem;
import com.zmsoft.ccd.lib.bean.filter.HttpFilterItem;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/16 11:43
 */
public class FilterHelper {

    public static List<FilterItem> getResultFilter(List<Filter> list) {
        List<FilterItem> resultList = new ArrayList<>();
        for (Filter filter : list) {
            /*1.标题*/
//            FilterItem titleItem = new FilterItem();
//            titleItem.setName(filter.getName());
//            titleItem.setType(FilterItem.FILTER_ITEM_TITLE);
//            resultList.add(titleItem);
            String code;
            if (filter.getCode().equals(FilterItem.MENU_0002)) { // 如果是座位的，就将父类的code给子类
                code = filter.getCode();
            } else {
                code = "";
            }
            /*2.内容*/
            List<HttpFilterItem> filterItemList = filter.getSystemMenuItemVOList();
            for (int j = 0; j < filterItemList.size(); j++) {
                HttpFilterItem httpFilterItem = filterItemList.get(j);
                FilterItem dataItem = new FilterItem();
                dataItem.setType(FilterItem.FILTER_ITEM_CONTENT);
                dataItem.setName(httpFilterItem.getName());
                dataItem.setBizId(httpFilterItem.getBizId());
                if (StringUtils.isEmpty(code)) {
                    dataItem.setCode(httpFilterItem.getCode());
                } else {
                    dataItem.setCode(code);
                }
                resultList.add(dataItem);
            }
        }
        return resultList;
    }

    /**
     * 是否有关注的零售单
     *
     * @param list 数据源
     * @return 结果
     */
    public static boolean isWatchedRetail(List<Filter> list) {
        if (list == null) return false;
        for (Filter filter : list) {
            if (filter.getCode().equals(FilterItem.MENU_0001)) {
                List<HttpFilterItem> filterItemList = filter.getSystemMenuItemVOList();
                for (HttpFilterItem item : filterItemList) {
                    if (item.getCode().equals(FilterItem.MENU_ITEM_0003)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isWatchedSeatOrOrder(List<Filter> list) {
        boolean result = false;
        for (Filter filter : list) {
            String code = filter.getCode();
            if (code.equals(FilterItem.MENU_0001)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 创建筛选订单分类的信息
     */
    public static List<FilterItem> createOrderFilter() {
        List<FilterItem> resultList = new ArrayList<>();
        FilterItem titleItem = new FilterItem();
        titleItem.setName(GlobalVars.context.getString(R.string.all_order));
        titleItem.setType(FilterItem.FILTER_ITEM_TITLE);
        resultList.add(titleItem);

        FilterItem data1 = new FilterItem();
        data1.setName(GlobalVars.context.getString(R.string.no_end_pay_order));
        data1.setType(FilterItem.FILTER_ITEM_CONTENT);
        data1.setCode(FilterItem.MENU_ITEM_NO_PAY);
        resultList.add(data1);

        FilterItem data2 = new FilterItem();
        data2.setName(GlobalVars.context.getString(R.string.end_pay_order));
        data2.setType(FilterItem.FILTER_ITEM_CONTENT);
        data2.setCode(FilterItem.MENU_ITEM_END_PAY);
        resultList.add(data2);
        return resultList;
    }

    /**
     * 创建已结账单
     */
    public static FilterItem createAfterEndPayFilter() {
        FilterItem item = new FilterItem();
        item.setName(GlobalVars.context.getString(R.string.end_pay_order));
        item.setType(FilterItem.FILTER_ITEM_CONTENT);
        item.setCode(FilterItem.MENU_ITEM_END_PAY);
        return item;
    }

    /**
     * 创建筛选消息分组的信息
     */
    public static List<FilterItem> createMessageFilter() {
        List<FilterItem> resultList = new ArrayList<>();
        FilterItem data2 = new FilterItem();
        data2.setName(GlobalVars.context.getString(R.string.msg_msgcenter_customer_new));
        data2.setType(FilterItem.FILTER_ITEM_CONTENT);
        data2.setCode(FilterItem.MENU_ITEM_MESSAGE_USER);
        resultList.add(data2);
        FilterItem data3 = new FilterItem();
        data3.setName(GlobalVars.context.getString(R.string.msg_msgcenter_handled));
        data3.setType(FilterItem.FILTER_ITEM_CONTENT);
        data3.setCode(FilterItem.MENU_ITEM_MESSAGE_DEAL_WITH);
        resultList.add(data3);
        FilterItem data1 = new FilterItem();
        data1.setName(GlobalVars.context.getString(R.string.msg_msgcenter_all));
        data1.setType(FilterItem.FILTER_ITEM_CONTENT);
        data1.setCode(FilterItem.MENU_ITEM_MESSAGE_ALL);
        resultList.add(data1);
        return resultList;
    }
}
