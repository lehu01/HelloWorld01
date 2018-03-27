package com.zmsoft.ccd.data.source.filter;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.filter.Filter;
import com.zmsoft.ccd.lib.bean.filter.FilterItem;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.helper.FilterHelper;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 14:01
 */
@Singleton
public class FilterSource implements IFilterSource {

    @Override
    public void getFilterFindOrder(String entityId, String userId, final Callback<List<Filter>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Filter.METHOD_FILTER_FIND_ORDER);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<Filter>>() {
            @Override
            protected void onData(List<Filter> filterList) {
                callback.onSuccess(filterList);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public void getFilterMessage(Callback<List<FilterItem>> callback) {
        callback.onSuccess(FilterHelper.createMessageFilter());
    }
}
