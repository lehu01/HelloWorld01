package com.zmsoft.ccd.data.source.ordersummary;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.order.summary.BillSummaryVo;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/15 10:54.
 */

public class OrderSummarySource implements IOrderSummarySource {

    @Override
    public void getBillSummaryDays(String entityId, String startDate, String endDate, final Callback<List<BillSummaryVo>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.orderSummary.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.orderSummary.START_DATE, startDate);
        paramMap.put(HttpParasKeyConstant.orderSummary.END_DATE, endDate);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_ORDER_SUMMARY);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<BillSummaryVo>>() {
            @Override
            protected void onData(List<BillSummaryVo> data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
