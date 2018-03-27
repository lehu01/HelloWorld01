package com.zmsoft.ccd.data.source.filter;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.filter.Filter;
import com.zmsoft.ccd.lib.bean.filter.FilterItem;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 13:57
 */
public interface IFilterSource {

    /**
     * 获取右侧栏筛选：找单数据
     *
     * @param entityId 实体id
     * @param userId   用户id
     */
    void getFilterFindOrder(String entityId, String userId, Callback<List<Filter>> callback);

    /**
     * 获取右侧栏筛选：消息分类数据
     */
    void getFilterMessage(Callback<List<FilterItem>> callback);

}
