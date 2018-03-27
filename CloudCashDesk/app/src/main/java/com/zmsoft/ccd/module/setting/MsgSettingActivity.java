package com.zmsoft.ccd.module.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.constant.SPConstants;
import com.zmsoft.ccd.helper.MsgSettingType;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.settings.MessageGroupSettingVO;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.ccd.widget.settingview.CcdSettingItemView;

import java.util.List;

import butterknife.BindView;

import static com.zmsoft.ccd.module.setting.MsgSettingActivity.PATH_MSG_SETTING;


@Route(path = PATH_MSG_SETTING)
public class MsgSettingActivity extends ToolBarActivity implements MsgSettingContract.View, CcdSettingItemView.OnCheckChangeListener {
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
    @BindView(R.id.settingview_custom_tts)
    CcdSettingItemView customTtsItemView;
    @BindView(R.id.settingview_scan_order)
    CcdSettingItemView scanOrderItemView;
    @BindView(R.id.settingview_takeout)
    CcdSettingItemView takeoutItemView;
    @BindView(R.id.settingview_pay)
    CcdSettingItemView payItemView;
    @BindView(R.id.itemview_takeout)
    View mTakeoutDividerView;
    @BindView(R.id.layout_custom_tts_sub)
    LinearLayout mCustomTtsSubLayout;
    @BindView(R.id.itemview_auto_check_third_takeout)
    View mAutoCheckThirdDividerView;

    public static final String PATH_MSG_SETTING = "/main/msg_setting";
    private MsgSettingContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_setting);
        new MsgSettingPresent(this);
        getSettings();
        initView();
    }

    private void initView() {
        showNewMsg.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.SHOW_NEW_MSG, true));
        receiveMsgFromWaiterWechat.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.RECEIVE_MSG_FROM_WAITER_WECHAT, true));
        receiveMsgFromTakeout.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.RECEIVE_MSG_FROM_TAKEOUT, true));
        autoCheckTakeout.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.AUTO_CHECK_TAKEOUT, false));
        customTtsItemView.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.CUSTOM_TTS, true));
        scanOrderItemView.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.CUSTOM_TTS_SCAN_ORDER, true));
        takeoutItemView.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.CUSTOM_TTS_TAKE_OUT, true));
        payItemView.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.CUSTOM_TTS_PAY, true));
        autoCheckThirdTakeout.setChecked(SPUtils.getInstance(this).getBoolean(SPConstants.MsgSetting.AUTO_CHECK_THIRD_TAKEOUT, true));
        showNewMsg.setOnCheckChangeListener(this);
        receiveMsgFromWaiterWechat.setOnCheckChangeListener(this);
        receiveMsgFromTakeout.setOnCheckChangeListener(this);
        autoCheckTakeout.setOnCheckChangeListener(this);
        customTtsItemView.setOnCheckChangeListener(this);
        scanOrderItemView.setOnCheckChangeListener(this);
        takeoutItemView.setOnCheckChangeListener(this);
        payItemView.setOnCheckChangeListener(this);
        autoCheckThirdTakeout.setOnCheckChangeListener(this);
        if (UserHelper.getWorkModeIsMixture()) {
            receiveMsgFromTakeout.setVisibility(View.GONE);
            autoCheckTakeout.setVisibility(View.GONE);
            autoCheckThirdTakeout.setVisibility(View.GONE);
            mReceiveMsgDividerView.setVisibility(View.GONE);
            mAutoCheckividerView.setVisibility(View.GONE);
            takeoutItemView.setVisibility(View.GONE);
            mTakeoutDividerView.setVisibility(View.GONE);
            mAutoCheckThirdDividerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void getSettingsSuccess(List<MessageGroupSettingVO> list) {
        hideLoading();
        if (list == null || list.size() == 0) {
            return;
        }
        changeSetting(showNewMsg, getSettingOpen(list, MsgSettingType.IS_SHOW_NEW_MESSAGE));
        changeSetting(receiveMsgFromWaiterWechat, getSettingOpen(list, MsgSettingType.SEAT_SERVICE_BELL));
        changeSetting(receiveMsgFromTakeout, getSettingOpen(list, MsgSettingType.TAKE_OUT_SEAT_CODE));
        changeSetting(autoCheckTakeout, getSettingOpen(list, MsgSettingType.OUR_TAKEOUT_SET));
        changeSetting(customTtsItemView, getSettingOpen(list, MsgSettingType.MESSAGE_SOUND));
        changeSetting(scanOrderItemView, getSettingOpen(list, MsgSettingType.TANGSHI_SOUND));
        changeSetting(takeoutItemView, getSettingOpen(list, MsgSettingType.WAIMAI_SOUND));
        changeSetting(payItemView, getSettingOpen(list, MsgSettingType.PAY_SOUND));
        changeSetting(autoCheckThirdTakeout, getSettingOpen(list, MsgSettingType.THIRD_TAKEOUT_SET));
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
            case MsgSettingType.SEAT_SERVICE_BELL:
                changeSetting(receiveMsgFromWaiterWechat, isCheckedOrigin);
                break;
            case MsgSettingType.TAKE_OUT_SEAT_CODE:
                changeSetting(receiveMsgFromTakeout, isCheckedOrigin);
                break;
            case MsgSettingType.OUR_TAKEOUT_SET:
                changeSetting(autoCheckTakeout, isCheckedOrigin);
                break;
            case MsgSettingType.MESSAGE_SOUND:
                changeSetting(customTtsItemView, isCheckedOrigin);
                break;
            case MsgSettingType.TANGSHI_SOUND:
                changeSetting(scanOrderItemView, isCheckedOrigin);
                break;
            case MsgSettingType.WAIMAI_SOUND:
                changeSetting(takeoutItemView, isCheckedOrigin);
                break;
            case MsgSettingType.PAY_SOUND:
                changeSetting(payItemView, isCheckedOrigin);
                break;
            case MsgSettingType.THIRD_TAKEOUT_SET:
                changeSetting(autoCheckThirdTakeout, isCheckedOrigin);
                break;
        }
    }

    @Override
    public void setPresenter(MsgSettingContract.Presenter presenter) {
        this.presenter = presenter;
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
            case R.id.settingview_receive_msg_from_waiter_wechat:
                msgGroupName = MsgSettingType.SEAT_SERVICE_BELL;
                changeSetting(receiveMsgFromWaiterWechat, isChecked);
                break;
            case R.id.settingview_receive_msg_from_takeout:
                msgGroupName = MsgSettingType.TAKE_OUT_SEAT_CODE;
                changeSetting(receiveMsgFromTakeout, isChecked);
                break;
            case R.id.settingview_auto_check_takeout:
                msgGroupName = MsgSettingType.OUR_TAKEOUT_SET;
                changeSetting(autoCheckTakeout, isChecked);
                break;
            case R.id.settingview_custom_tts:
                msgGroupName = MsgSettingType.MESSAGE_SOUND;
                changeSetting(customTtsItemView, isChecked);
                break;
            case R.id.settingview_scan_order:
                msgGroupName = MsgSettingType.TANGSHI_SOUND;
                changeSetting(scanOrderItemView, isChecked);
                break;
            case R.id.settingview_takeout:
                msgGroupName = MsgSettingType.WAIMAI_SOUND;
                changeSetting(takeoutItemView, isChecked);
                break;
            case R.id.settingview_pay:
                msgGroupName = MsgSettingType.PAY_SOUND;
                changeSetting(payItemView, isChecked);
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
                receiveMsgFromWaiterWechat.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.RECEIVE_MSG_FROM_WAITER_WECHAT, isChecked);
                break;
            case R.id.settingview_receive_msg_from_takeout:
                receiveMsgFromTakeout.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.RECEIVE_MSG_FROM_TAKEOUT, isChecked);
                break;
            case R.id.settingview_auto_check_takeout:
                autoCheckTakeout.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.AUTO_CHECK_TAKEOUT, isChecked);
                break;
            case R.id.settingview_custom_tts:
                customTtsItemView.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.CUSTOM_TTS, isChecked);
                mCustomTtsSubLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            case R.id.settingview_scan_order:
                scanOrderItemView.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.CUSTOM_TTS_SCAN_ORDER, isChecked);
                break;
            case R.id.settingview_takeout:
                takeoutItemView.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.CUSTOM_TTS_TAKE_OUT, isChecked);
                break;
            case R.id.settingview_pay:
                payItemView.setChecked(isChecked);
                SPUtils.getInstance(getBaseContext()).putBoolean(SPConstants.MsgSetting.CUSTOM_TTS_PAY, isChecked);
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
}
