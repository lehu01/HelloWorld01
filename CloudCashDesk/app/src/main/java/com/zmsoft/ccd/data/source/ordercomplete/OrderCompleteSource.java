package com.zmsoft.ccd.data.source.ordercomplete;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.order.complete.CompleteBillVo;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/27 10:34.
 */

public class OrderCompleteSource implements IOrderComplete {
    @Override
    public void getCompleteBillByDate(String entityId, String startDate, String endDate, final Callback<List<CompleteBillVo>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.orderComplete.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.orderComplete.START_DATE, startDate);
        paramMap.put(HttpParasKeyConstant.orderComplete.END_DATE, endDate);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_ORDER_COMPLETE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<CompleteBillVo>>() {
            @Override
            protected void onData(List<CompleteBillVo> data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
