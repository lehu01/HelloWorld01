package com.zmsoft.ccd.data.source.orderparticulars;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.order.particulars.DateOrderParticularsListResult;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 14:40.
 */

public class OrderParticularsSource implements IOrderParticulars {

    @Override
    public void getOrderParticularsList(String entityId, String opUserId, String opUserName, String orderCode, Integer orderFrom, String cashier, Integer date, int pageIndex, int pageSize, final Callback<DateOrderParticularsListResult> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_ID, opUserId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_NAME, opUserName);
        paramMap.put(HttpParasKeyConstant.Hump.PAGE_INDEX, pageIndex);
        paramMap.put(HttpParasKeyConstant.Hump.PAGE_SIZE, pageSize);
        // 单号搜索
        if (null != orderCode) {
            paramMap.put(HttpParasKeyConstant.orderParticulars.ORDER_CODE, orderCode);
        }
        // 筛选
        if (null != orderFrom) {
            paramMap.put(HttpParasKeyConstant.orderParticulars.ORDER_FROM, orderFrom);
        }
        if (null != cashier) {
            paramMap.put(HttpParasKeyConstant.orderParticulars.CASHIER, cashier);
        }
        if (null != date) {
            paramMap.put(HttpParasKeyConstant.orderParticulars.DATE, date);
        }

        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Order.METHOD_ORDER_PARTICULARS);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<DateOrderParticularsListResult>() {
            @Override
            protected void onData(DateOrderParticularsListResult data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
