package com.zmsoft.ccd.data.source.electronic;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentListRequest;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentListResponse;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentRequest;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentResponse;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;


/**
 * 电子收款明细
 *
 * @author DangGui
 * @create 2017/3/4.
 */
@Singleton
public class ElectronicRemoteSource implements IElectronicSource {

    @Override
    public void getElePaymentList(int pageIndex, int pageSize, final Callback<GetElePaymentListResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        GetElePaymentListRequest getElePaymentListRequest = new GetElePaymentListRequest(pageIndex
                , pageSize, UserHelper.getEntityId(), UserHelper.getUserId());
        String requestJson = JsonMapper.toJsonString(getElePaymentListRequest);
        paramMap.put(HttpParasKeyConstant.PARA_PARAM, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.ElePayment.METHOD_GET_ELEPAYMENT_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<GetElePaymentListResponse>() {
            @Override
            protected void onData(GetElePaymentListResponse getElePaymentListResponse) {
                callback.onSuccess(getElePaymentListResponse);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getElePaymentDetail(String payId, final Callback<GetElePaymentResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        GetElePaymentRequest getElePaymentRequest = new GetElePaymentRequest(payId, UserHelper.getEntityId(), UserHelper.getUserId());
        String requestJson = JsonMapper.toJsonString(getElePaymentRequest);
        paramMap.put(HttpParasKeyConstant.PARA_PARAM, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.ElePayment.METHOD_GET_ELEPAYMENT_DETAIL);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<GetElePaymentResponse>() {
            @Override
            protected void onData(GetElePaymentResponse getElePaymentResponse) {
                callback.onSuccess(getElePaymentResponse);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
