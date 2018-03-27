package com.zmsoft.ccd.module.carryout;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.source.carryout.dagger.DaggerTakeoutSourceComponent;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.carryout.TakeoutMobile;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.carryout.dagger.CarryoutSettingPresenterModule;
import com.zmsoft.ccd.module.carryout.dagger.DaggerCarryoutSettingComponent;

import java.util.List;

import javax.inject.Inject;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/17.
 */

public class CarryoutSettingFragment extends BaseFragment implements CarryoutSettingContract.View {

    private SwitchCompat mSwitchCompat;
    private String mMobile;

    @Inject
    CarryoutSettingPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_carryout_setting;
    }

    @Override
    protected void initParameters() {
        super.initParameters();
        DaggerCarryoutSettingComponent.builder()
                .carryoutSettingPresenterModule(new CarryoutSettingPresenterModule(this))
                .takeoutSourceComponent(DaggerTakeoutSourceComponent.builder().build())
                .build()
                .inject(this);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mSwitchCompat = (SwitchCompat) view.findViewById(R.id.switch_receive_carryout_call);
        mSwitchCompat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getActionMasked() == MotionEvent.ACTION_MOVE;
            }
        });

        showLoading(false);
        mPresenter.loadCarryoutPhoneList(UserHelper.getEntityId());
    }

    @Override
    protected void initListener() {
        mSwitchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mMobile) && !UserHelper.getMobile().equals(mMobile)) {
                    getDialogUtil().showDialog(R.string.dialog_title,
                            getString(R.string.dialog_content_phone_carryout_setting, mMobile),
                            R.string.dialog_positive_phone_carryout_setting,
                            R.string.dialog_negative_phone_carryout_setting,
                            false, new SingleButtonCallback() {
                                @Override
                                public void onClick(DialogUtilAction which) {
                                    switch (which) {
                                        case POSITIVE:
                                            showLoading(false);
                                            mPresenter.updateCarryoutPhoneSetting(UserHelper.getEntityId(),
                                                    UserHelper.getMobile(), mSwitchCompat.isChecked());
                                            break;
                                        case NEGATIVE:
                                            mSwitchCompat.toggle();
                                            break;
                                    }
                                }
                            });
                } else if (!mSwitchCompat.isChecked()) {//之前是关闭状态
                    getDialogUtil().showDialog(R.string.dialog_title,
                            getString(R.string.dialog_positive_phone_carryout_setting_close_tip),
                            R.string.dialog_positive_phone_carryout_setting_close,
                            R.string.dialog_negative_phone_carryout_setting_cancel,
                            false, new SingleButtonCallback() {
                                @Override
                                public void onClick(DialogUtilAction which) {
                                    switch (which) {
                                        case POSITIVE:
                                            showLoading(false);
                                            mPresenter.updateCarryoutPhoneSetting(UserHelper.getEntityId(),
                                                    UserHelper.getMobile(), mSwitchCompat.isChecked());
                                            break;
                                        case NEGATIVE:
                                            mSwitchCompat.toggle();
                                            break;
                                    }
                                }
                            });
                } else {
                    showLoading(false);
                    mPresenter.updateCarryoutPhoneSetting(UserHelper.getEntityId(), UserHelper.getMobile(), mSwitchCompat.isChecked());
                }
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void setPresenter(CarryoutSettingContract.Presenter presenter) {

    }


    @Override
    public void loadCarryoutPhoneListSuccess(List<TakeoutMobile> list) {
        hideLoading();
        boolean hadCheck = false;
        if (list != null && !list.isEmpty()) {
            mMobile = list.get(0).getMobile();
            hadCheck = UserHelper.getMobile().equals(mMobile);
            ConfigHelper.saveReceiveCarryoutPhoneSetting(getContext(), hadCheck);
        } else {
            ConfigHelper.saveReceiveCarryoutPhoneSetting(getContext(), false);
        }
        mSwitchCompat.setChecked(hadCheck);
    }

    @Override
    public void loadCarryoutPhoneListFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
    }

    @Override
    public void updateCarryoutPhoneSettingSuccess(Boolean success) {
        hideLoading();
        if (success) {
            ConfigHelper.saveReceiveCarryoutPhoneSetting(getContext(), mSwitchCompat.isChecked());
            //更新开启开关的用户列表
            mPresenter.loadCarryoutPhoneList(UserHelper.getEntityId());
        } else {
            ToastUtils.showShortToast(getContext(), R.string.update_carryout_phone_setting_failed);
            mSwitchCompat.toggle();
        }
    }

    @Override
    public void updateCarryoutPhoneSettingFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
        mSwitchCompat.toggle();
    }
}
