package com.zmsoft.ccd.module.setting;

import com.dfire.mobile.network.Callback;
import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.lib.bean.settings.MessageGroupSettingVO;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.lib.base.bean.HttpResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jihuo on 2017/2/28.
 */

public class MsgSettingPresent implements MsgSettingContract.Presenter {

    private MsgSettingContract.View view;

    public MsgSettingPresent(MsgSettingContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void getSettings(String entity_id, String device_id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entity_id);
        paramMap.put(HttpParasKeyConstant.DEVICE_ID, device_id);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MSG_SETTINGS.GET_MSG_GROUP_SETTINGS);
        NetworkService.getDefault().request(requestModel, new Callback<HttpResult<HttpBean<List<MessageGroupSettingVO>>>>() {
            @Override
            protected void onSuccess(HttpResult<HttpBean<List<MessageGroupSettingVO>>> data) {
                if (data != null && data.getData() != null) {
                    view.getSettingsSuccess(data.getData().getData());
                } else {
                    view.getSettingsFailure();
                }
            }

            @Override
            protected void onFailure(Throwable t) {
                view.getSettingsFailure();
            }
        });
    }

    @Override
    public void uploadSettings(String entityId, String device_id, final String message_group, final Integer is_valid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.DEVICE_ID, device_id);
        paramMap.put(HttpParasKeyConstant.MESSAGE_GROUP, message_group);
        paramMap.put(HttpParasKeyConstant.IS_VALID, is_valid);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MSG_SETTINGS.UPLOAD_SETTINGS);
        NetworkService.getDefault().request(requestModel, new Callback<HttpResult<HttpBean<Integer>>>() {
            @Override
            protected void onSuccess(HttpResult<HttpBean<Integer>> data) {
                if (data != null && data.getData() != null) {
                    view.uploadSettingsSuccess();
                } else {
                    //若失败，按钮状态需要还原为之前的状态
                    view.uploadSettingsFailure(message_group, is_valid != 1);
                }
            }

            @Override
            protected void onFailure(Throwable t) {
                view.uploadSettingsFailure(message_group, is_valid != 1);
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
