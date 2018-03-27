package com.zmsoft.ccd.data.source.setting;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.settings.MessageGroupSettingVO;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by huaixi on 2017/10/24.
 */

@Singleton
public class SettingSourceRepository implements ISettingSource {

    private final ISettingSource mSettingSource;

    @Inject
    public SettingSourceRepository(@Remote ISettingSource settingSource) {
        mSettingSource = settingSource;
    }

    @Override
    public void getSettings(String entity_id, String device_id, Callback<List<MessageGroupSettingVO>> callback) {
        mSettingSource.getSettings(entity_id, device_id, callback);
    }

    @Override
    public void uploadSettings(String entityId, String device_id, String message_group, Integer is_valid, Callback<Integer> callback) {
        mSettingSource.uploadSettings(entityId, device_id, message_group, is_valid, callback);
    }
}
