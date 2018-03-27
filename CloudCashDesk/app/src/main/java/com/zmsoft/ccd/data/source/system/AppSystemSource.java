package com.zmsoft.ccd.data.source.system;

import com.dfire.mobile.cashupdate.bean.CashUpdateInfoDO;
import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/20 14:18
 */
@Singleton
public class AppSystemSource implements IAppSystemSource {

    @Override
    public void checkAppUpdate(String entityId, String appCode, int version, final Callback<CashUpdateInfoDO> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        if (!StringUtils.isEmpty(entityId)) {
            paramMap.put("entity_id", entityId);
        }
        paramMap.put("app_code", appCode);
        paramMap.put("version", version);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.CheckUpdate.CHECK_UPDATE);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CashUpdateInfoDO>() {
            @Override
            protected void onData(CashUpdateInfoDO data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void checkNetworkLatency(String entityId, String appCode, int version, final CheckNetworkLatencyCallback callback) {
        Map<String, Object> paramMap = new HashMap<>();
        if (!StringUtils.isEmpty(entityId)) {
            paramMap.put("entity_id", entityId);
        }
        paramMap.put("app_code", appCode);
        paramMap.put("version", version);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.CheckUpdate.CHECK_UPDATE);

        NetworkService.getDefault().request(requestModel, new com.dfire.mobile.network.Callback<Object>() {

            @Override
            protected void onResponse(ResponseModel responseModel) {
                long duration = responseModel.receivedResponseAtMillis() - responseModel.sentRequestAtMillis();
                callback.onResponse(duration);
            }

            @Override
            protected void onSuccess(Object data) {

            }

            @Override
            protected void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
