package com.zmsoft.ccd.module.workmodel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.chiclaim.modularization.utils.RouterActivityManager;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.main.MainActivity;
import com.zmsoft.ccd.module.menubalance.MenuItemCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zmsoft.ccd.lib.bean.Base.STRING_TRUE;


/**
 * Description：工作模式
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/24 11:37
 */
public class WorkModelFragment extends BaseFragment implements WorkModelContract.View {

    @BindView(R.id.switch_work_model)
    SwitchCompat mSwitchWorkModel;
    @BindView(R.id.button_ok)
    Button mButtonOk;
    @BindView(R.id.content)
    LinearLayout mContent;

    private int mFrom;
    private WorkModelPresenter mPresenter;
    /**
     * 保存初始值
     */
    private boolean mIsUseLocal;
    private boolean mIsUseCloudCash;
    private MenuItemCallback menuItemCallback;

    public static WorkModelFragment newInstance(int from) {
        WorkModelFragment fragment = new WorkModelFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RouterPathConstant.WorkModel.FROM, from);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setMenuItemCallback(MenuItemCallback menuItemCallback) {
        this.menuItemCallback = menuItemCallback;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_model;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mContent.setVisibility(View.GONE);
        initIntentData();
        initView();
    }

    private void initIntentData() {
        Bundle bundle = getArguments();
        mFrom = bundle.getInt(RouterPathConstant.WorkModel.FROM);
    }

    private void initView() {
        if (mFrom == RouterPathConstant.WorkModel.FROM_MAIN) {
            mButtonOk.setVisibility(View.GONE);
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
        getWorkModelConfig();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        getWorkModelConfig();
    }

    private void getWorkModelConfig() {
        List<String> list = new ArrayList<>();
        list.add(SystemDirCodeConstant.IS_USE_LOCAL_CASH);
        list.add(SystemDirCodeConstant.TURN_ON_CLOUD_CASH);
        mPresenter.getWorkModelConfig(UserHelper.getEntityId(), list);
    }

    @Override
    protected void initListener() {
        mSwitchWorkModel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (menuItemCallback != null) {
                    menuItemCallback.setMenuItemVisible(b != mIsUseLocal);
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

    @OnClick({R.id.button_ok})
    void processClick(View view) {
        switch (view.getId()) {
            case R.id.button_ok:
                startWork();
                break;
        }
    }

    /**
     * <p/>
     * 开始工作
     */
    private void startWork() {
        if (mSwitchWorkModel.isChecked()) {
            checkLocalSupportVersion();
        } else {
            saveWorkModelConfig();
        }
    }

    /**
     * 检测版本
     */
    private void checkLocalSupportVersion() {
        mPresenter.checkCashSupportVersion(UserHelper.getEntityId());
    }

    /**
     * 跳转主界面
     */
    private void gotoMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        RouterActivityManager.get().finishAllActivityExcept(MainActivity.class);
    }

    /**
     * 获取工作模式
     */
    private void saveWorkModelConfig() {
        mPresenter.saveWorkModelConfig(UserHelper.getEntityId(), true, mSwitchWorkModel.isChecked(), UserHelper.getUserId());
    }

    @Override
    public void setPresenter(WorkModelContract.Presenter presenter) {
        mPresenter = (WorkModelPresenter) presenter;
    }

    @Override
    public void showDialogPrompt(String errorMessage) {
        DialogUtil dialogUtil = new DialogUtil(getActivity());
        dialogUtil.showNoticeDialog(R.string.material_dialog_title
                , errorMessage, R.string.dialog_hint_know, false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            onStart();
                        }
                    }
                });
    }

    @Override
    public void getWorkModelConfigSuccess(Map<String, String> map) {
        mContent.setVisibility(View.VISIBLE);
        showContentView();
        if (map != null) {
            mIsUseLocal = STRING_TRUE.equals(map.get(SystemDirCodeConstant.IS_USE_LOCAL_CASH));
            mIsUseCloudCash = STRING_TRUE.equals(map.get(SystemDirCodeConstant.TURN_ON_CLOUD_CASH));
        }
        mSwitchWorkModel.setChecked(mIsUseLocal);
    }

    @Override
    public void showLoadDataErrorView(String errorMessage) {
        mContent.setVisibility(View.GONE);
        showErrorView(errorMessage);
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void localCashVersionSupport() {
        saveWorkModelConfig();
    }

    @Override
    public void saveWorkModelConfigSuccess(Boolean isSuccess) {
        if (isSuccess) {
            BaseSpHelper.saveTurnCloudCashTime(getActivity(), System.currentTimeMillis());
            BaseSpHelper.saveWorkMode(getActivity(), mSwitchWorkModel.isChecked());
            //通知主页工作模式改变
            BaseEvents.CommonEvent event = BaseEvents.CommonEvent.EVENT_REFRESH_WORK_MODE;
            event.setObject(mSwitchWorkModel.isChecked());
            EventBusHelper.post(event);

            if (mSwitchWorkModel.isChecked()) {
                showDialogPrompt();
            } else {
                saveFinish();
            }
        }
    }

    private void saveFinish() {
        switch (mFrom) {
            case RouterPathConstant.WorkModel.FROM_LOGIN:
                gotoMainActivity();
                break;
            case RouterPathConstant.WorkModel.FROM_MAIN:
                showToast(getString(R.string.save_success));
                getActivity().finish();
                break;
        }
    }

    private void showDialogPrompt() {
        getDialogUtil().showNoticeDialog(R.string.material_dialog_title
                , getString(R.string.work_model_use_local_cash_prompt)
                , R.string.dialog_hint_know, false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            saveFinish();
                        }
                    }
                });
    }

    public void save() {
        startWork();
    }

    public void getWorkMode() {
        getWorkModelConfig();
    }
}
