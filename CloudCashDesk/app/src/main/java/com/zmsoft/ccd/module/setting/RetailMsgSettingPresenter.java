package com.zmsoft.ccd.module.setting;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.setting.SettingSourceRepository;
import com.zmsoft.ccd.lib.bean.settings.MessageGroupSettingVO;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by huaixi on 2017/10/24.
 */

public class RetailMsgSettingPresenter implements RetailMsgSettingContract.Presenter {

    private RetailMsgSettingContract.View view;
    private final SettingSourceRepository mSettingSourceRepository;

    @Inject
    public RetailMsgSettingPresenter(RetailMsgSettingContract.View view, SettingSourceRepository mSettingSourceRepository) {
        this.view = view;
        this.mSettingSourceRepository = mSettingSourceRepository;
    }

    @Inject
    void setupPresenterForView() {
        view.setPresenter(this);
    }

    @Override
    public void getSettings(String entity_id, String device_id) {
        mSettingSourceRepository.getSettings(entity_id, device_id, new Callback<List<MessageGroupSettingVO>>() {
            @Override
            public void onSuccess(List<MessageGroupSettingVO> data) {
                if (data != null) {
                    view.getSettingsSuccess(data);
                } else {
                    view.getSettingsFailure();
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                view.getSettingsFailure();
            }
        });
    }

    @Override
    public void uploadSettings(String entityId, String device_id, final String message_group, final Integer is_valid) {
        mSettingSourceRepository.uploadSettings(entityId, device_id, message_group, is_valid, new Callback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                if (data != null) {
                    view.uploadSettingsSuccess();
                } else {
                    //若失败，按钮状态需要还原为之前的状态
                    view.uploadSettingsFailure(message_group, is_valid != 1);
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
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
