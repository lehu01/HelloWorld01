package com.zmsoft.ccd.module.setting;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.settings.MessageGroupSettingVO;

import java.util.List;

/**
 * Created by jihuo on 2017/2/28.
 */

public interface MsgSettingContract {

    interface View extends BaseView<MsgSettingContract.Presenter> {
        void getSettingsSuccess(List<MessageGroupSettingVO> list);

        void getSettingsFailure();

        void uploadSettingsSuccess();

        void uploadSettingsFailure(String groupName, boolean isCheckedOrigin);
    }

    interface Presenter extends BasePresenter {
        void getSettings(String entity_id, String device_id);

        void uploadSettings(String entityId, String device_id, String message_group, Integer is_valid);
    }
}
