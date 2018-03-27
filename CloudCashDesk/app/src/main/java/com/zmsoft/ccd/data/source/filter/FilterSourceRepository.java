package com.zmsoft.ccd.data.source.filter;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.filter.Filter;
import com.zmsoft.ccd.lib.bean.filter.FilterItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 14:02
 */
@Singleton
public class FilterSourceRepository implements IFilterSource {

    private final IFilterSource mFilterSource;

    @Inject
    public FilterSourceRepository(@Remote IFilterSource checkShopSource) {
        mFilterSource = checkShopSource;
    }

    @Override
    public void getFilterFindOrder(String entityId, String userId, Callback<List<Filter>> callback) {
        mFilterSource.getFilterFindOrder(entityId, userId, callback);
    }

    @Override
    public void getFilterMessage(Callback<List<FilterItem>> callback) {
        mFilterSource.getFilterMessage(callback);
    }
}
