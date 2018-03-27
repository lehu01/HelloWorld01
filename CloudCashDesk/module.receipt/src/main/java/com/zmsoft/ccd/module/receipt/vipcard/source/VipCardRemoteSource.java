package com.zmsoft.ccd.module.receipt.vipcard.source;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardDetailResult;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardListResult;
import com.zmsoft.ccd.module.receipt.constant.HttpMethodConstants;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Description：具体实现类
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 19:45
 */
@Singleton
public class VipCardRemoteSource implements IVipCardSource {

    @Override
    public void getVipCardList(String entityId, String keyword, final Callback<VipCardListResult> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpMethodConstants.VipCard.Param.ENTITYID, entityId);
        paramMap.put(HttpMethodConstants.VipCard.Param.KEYWORD, keyword);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpMethodConstants.VipCard.Param.PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.VipCard.METHOD_GET_VIP_CARD_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<VipCardListResult>() {
            @Override
            protected void onData(VipCardListResult vipCardListResult) {
                callback.onSuccess(vipCardListResult);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getVipCardDetail(String entityId, String cardId, String orderId,final Callback<VipCardDetailResult> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpMethodConstants.VipCard.Param.ENTITYID, entityId);
        paramMap.put(HttpMethodConstants.VipCard.Param.CARDID, cardId);
        paramMap.put(HttpMethodConstants.VipCard.Param.ORDERID, orderId);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpMethodConstants.VipCard.Param.PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.VipCard.METHOD_GET_VIP_CARD_DETAIL);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<VipCardDetailResult>() {
            @Override
            protected void onData(VipCardDetailResult vipCardListResult) {
                callback.onSuccess(vipCardListResult);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
