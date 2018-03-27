package com.zmsoft.ccd.module.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.constant.SPConstants;
import com.zmsoft.ccd.data.source.setting.dagger.DaggerSettingSourceComponent;
import com.zmsoft.ccd.helper.MsgSettingType;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.settings.MessageGroupSettingVO;
import com.zmsoft.ccd.lib.utils.DeviceUtil;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.ccd.module.setting.dagger.DaggerRetailMsgSettingComponent;
import com.zmsoft.ccd.module.setting.dagger.RetailMsgSettingModule;
import com.zmsoft.ccd.widget.settingview.CcdSettingItemView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.zmsoft.ccd.module.setting.RetailMsgSettingActivity.PATH_MSG_SETTING;

@Route(path = PATH_MSG_SETTING)
public class RetailMsgSettingActivity extends ToolBarActivity implements RetailMsgSettingContract.View, CcdSettingItemView.OnCheckChangeListener {

    public static final String PATH_MSG_SETTING = "/main/retail/msg_setting";

    @BindView(R.id.settingview_show_new_msg)
    CcdSettingItemView showNewMsg;
    @BindView(R.id.settingview_receive_msg_from_waiter_wechat)
    CcdSettingItemView receiveMsgFromWaiterWechat;
    @BindView(R.id.settingview_receive_msg_from_takeout)
    CcdSettingItemView receiveMsgFromTakeout;
    @BindView(R.id.settingview_auto_check_takeout)
    CcdSettingItemView autoCheckTakeout;
    @BindView(R.id.settingview_auto_check_third_takeout)
    CcdSettingItemView autoCheckThirdTakeout;
    @BindView(R.id.itemview_receive_msg_from_takeout)
    View mReceiveMsgDividerView;
    @BindView(R.id.itemview_auto_check_takeout)
    View mAutoCheckividerView;
    @BindView(R.id.itemview_auto_check_third_takeout)
    View mAutoCheckThirdDividerView;

    @Inject
    RetailMsgSettingPresenter presenter;

    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_msg_setting);

        initDagger();

        deviceId = DeviceUtil.getDeviceId(this);
        getSettings();
        initView();
    }

    private void initDagger() {
        DaggerRetailMsgSettingComponent.builder()
                .settingSourceComponent(DaggerSettingSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .retailMsgSettingModule(new RetailMsgSettingModule(this))
                .build()
                .inject(this);
    }

    private void initView() {
        showNewMsg.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.SHOW_NEW_MSG, true));
        receiveMsgFromTakeout.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.RECEIVE_MSG_FROM_TAKEOUT, true));
        autoCheckThirdTakeout.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.AUTO_CHECK_THIRD_TAKEOUT, true));
        showNewMsg.setOnCheckChangeListener(this);
        receiveMsgFromTakeout.setOnCheckChangeListener(this);
        autoCheckThirdTakeout.setOnCheckChangeListener(this);
        if (UserHelper.getWorkModeIsMixture()) {
            receiveMsgFromTakeout.setVisibility(View.GONE);
            mReceiveMsgDividerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void getSettingsSuccess(List<MessageGroupSettingVO> list) {
        hideLoading();
        if (list == null || list.size() == 0) {
            return;
        }
        changeSetting(showNewMsg, getSettingOpen(list, MsgSettingType.IS_SHOW_NEW_MESSAGE));
        changeSetting(receiveMsgFromTakeout, getSettingOpen(list, MsgSettingType.TAKE_OUT_SEAT_CODE));
        changeSetting(receiveMsgFromWaiterWechat, getSettingOpen(list, MsgSettingType.SEAT_SERVICE_BELL));
        changeSetting(autoCheckTakeout, getSettingOpen(list, MsgSettingType.OUR_TAKEOUT_SET));
        changeSetting(autoCheckThirdTakeout, getSettingOpen(list, MsgSettingType.THIRD_TAKEOUT_SET));
        initDefaultMessageSetting();
    }

    @Override
    public void getSettingsFailure() {
        hideLoading();
        Toast.makeText(this, getString(R.string.get_msg_setting_failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadSettingsSuccess() {
        hideLoading();
    }

    @Override
    public void uploadSettingsFailure(String groupName, boolean isCheckedOrigin) {
        hideLoading();
        Toast.makeText(this, getString(R.string.upload_msg_setting_failure), Toast.LENGTH_SHORT).show();
        switch (groupName) {
            case MsgSettingType.IS_SHOW_NEW_MESSAGE:
                changeSetting(showNewMsg, isCheckedOrigin);
                break;
            case MsgSettingType.TAKE_OUT_SEAT_CODE:
                changeSetting(receiveMsgFromTakeout, isCheckedOrigin);
                break;
            case MsgSettingType.THIRD_TAKEOUT_SET:
                changeSetting(autoCheckThirdTakeout, isCheckedOrigin);
                break;
        }
    }

    @Override
    public void setPresenter(RetailMsgSettingContract.Presenter presenter) {
        this.presenter = (RetailMsgSettingPresenter) presenter;
    }

    @Override
    public void checkChange(CompoundButton compoundButton, boolean isChecked, int id) {
        if (!compoundButton.isPressed()) {
            return;
        }
        String msgGroupName = "";
        switch (id) {
            case R.id.settingview_show_new_msg:
                msgGroupName = MsgSettingType.IS_SHOW_NEW_MESSAGE;
                changeSetting(showNewMsg, isChecked);
                break;
            case R.id.settingview_receive_msg_from_takeout:
                msgGroupName = MsgSettingType.TAKE_OUT_SEAT_CODE;
                changeSetting(receiveMsgFromTakeout, isChecked);
                break;
            case R.id.settingview_auto_check_third_takeout:
                msgGroupName = MsgSettingType.THIRD_TAKEOUT_SET;
                changeSetting(autoCheckThirdTakeout, isChecked);
                break;
        }
        showLoading(false);
        presenter.uploadSettings(UserHelper.getEntityId(), UserHelper.getUserId(), msgGroupName, isChecked ? 1 : 0);
    }

    private void getSettings() {
        showLoading(false);
        presenter.getSettings(UserHelper.getEntityId(), UserHelper.getUserId());
    }

    private void changeSetting(CcdSettingItemView view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.settingview_show_new_msg:
                showNewMsg.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.SHOW_NEW_MSG, isChecked);
                break;
            case R.id.settingview_receive_msg_from_waiter_wechat:
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.RECEIVE_MSG_FROM_WAITER_WECHAT, isChecked);
                break;
            case R.id.settingview_receive_msg_from_takeout:
                receiveMsgFromTakeout.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.RECEIVE_MSG_FROM_TAKEOUT, isChecked);
                break;
            case R.id.settingview_auto_check_takeout:
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.AUTO_CHECK_TAKEOUT, isChecked);
                break;
            case R.id.settingview_auto_check_third_takeout:
                autoCheckThirdTakeout.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.AUTO_CHECK_THIRD_TAKEOUT, isChecked);
                break;
        }
    }

    private boolean getSettingOpen(List<MessageGroupSettingVO> list, String msgType) {
        boolean open = false;
        for (int i = 0; i < list.size(); i++) {
            MessageGroupSettingVO vo = list.get(i);
            if (vo.getGroupName().equals(msgType)) {
                try {
                    open = (Integer.parseInt(vo.getValue()) == 1);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return open;
    }

    private void initDefaultMessageSetting() {
        if (true == SPUtils.getInstance(getBaseContext()).getBoolean(SPConstants.MsgSetting.RECEIVE_MSG_FROM_WAITER_WECHAT))
            presenter.uploadSettings(UserHelper.getEntityId(), UserHelper.getUserId(), MsgSettingType.SEAT_SERVICE_BELL, 0);
        if (false == SPUtils.getInstance(getBaseContext()).getBoolean(SPConstants.MsgSetting.AUTO_CHECK_TAKEOUT))
            presenter.uploadSettings(UserHelper.getEntityId(), UserHelper.getUserId(), MsgSettingType.OUR_TAKEOUT_SET, 1);
    }
}
