package com.zmsoft.ccd.data.source.setting;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.settings.MessageGroupSettingVO;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Created by huaixi on 2017/10/24.
 */

@Singleton
public class SettingSource implements ISettingSource {

    @Override
    public void getSettings(String entity_id, String device_id, final Callback<List<MessageGroupSettingVO>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entity_id);
        paramMap.put(HttpParasKeyConstant.DEVICE_ID, device_id);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MSG_SETTINGS.GET_MSG_GROUP_SETTINGS);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<MessageGroupSettingVO>>() {
            @Override
            protected void onData(List<MessageGroupSettingVO> messageGroupSettingVOs) {
                callback.onSuccess(messageGroupSettingVOs);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public void uploadSettings(String entityId, String device_id, String message_group, Integer is_valid, final Callback<Integer> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.DEVICE_ID, device_id);
        paramMap.put(HttpParasKeyConstant.MESSAGE_GROUP, message_group);
        paramMap.put(HttpParasKeyConstant.IS_VALID, is_valid);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MSG_SETTINGS.UPLOAD_SETTINGS);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Integer>() {

            @Override
            protected void onData(Integer integer) {
                callback.onSuccess(integer);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }
}
