package com.zmsoft.ccd.data.source.workmodel;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/31 10:14
 */
@Singleton
public class WorkModelSource implements IWorkModelSource {

    @Override
    public void getWorkModel(String entityId, List<String> codeList, final Callback<Map<String, String>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entity_id", entityId);
        paramMap.put("code_lst", JsonHelper.toJson(codeList));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.WorkMode.METHOD_GET_WORK_MODEL_CONFIG);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Map<String, String>>() {
            @Override
            protected void onData(Map<String, String> data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void saveWorkModelConfig(String entityId, boolean openCloudCash, boolean useLocalCash, String userId, final Callback<Boolean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entity_id", entityId);
        paramMap.put("open_cloud_cash", openCloudCash);
        paramMap.put("use_local_cash", useLocalCash);
        paramMap.put("user_id", userId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.WorkMode.METHOD_SAVE_WORK_MODEL_CONFIG);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Boolean>() {
            @Override
            protected void onData(Boolean data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
