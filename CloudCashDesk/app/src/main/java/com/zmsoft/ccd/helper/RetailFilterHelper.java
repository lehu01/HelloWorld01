package com.zmsoft.ccd.helper;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.bean.filter.FilterItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huaixi on 2017/11/6.
 */
public class RetailFilterHelper {

    /**
     * 创建筛选消息分组的信息
     */
    public static List<FilterItem> createMessageFilter() {
        List<FilterItem> resultList = new ArrayList<>();
        FilterItem data1 = new FilterItem();
        data1.setName(GlobalVars.context.getString(R.string.msg_msgcenter_all));
        data1.setType(FilterItem.FILTER_ITEM_CONTENT);
        data1.setCode(FilterItem.MENU_ITEM_MESSAGE_USER);
        resultList.add(data1);
        FilterItem data3 = new FilterItem();
        data3.setName(GlobalVars.context.getString(R.string.msg_msgcenter_handled));
        data3.setType(FilterItem.FILTER_ITEM_CONTENT);
        data3.setCode(FilterItem.MENU_ITEM_MESSAGE_DEAL_WITH);
        resultList.add(data3);
        return resultList;
    }
}
