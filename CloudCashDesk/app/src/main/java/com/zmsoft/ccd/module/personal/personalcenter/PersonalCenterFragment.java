package com.zmsoft.ccd.module.personal.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.sobot.chat.SobotApi;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.customerservice.CustomerServiceUnreadMessageEvent;
import com.zmsoft.ccd.helper.CustomerServiceHelper;
import com.zmsoft.ccd.helper.LogOutHelper;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.module.carryout.CarryoutSettingActivity;
import com.zmsoft.ccd.module.checkshop.CheckShopActivity;
import com.zmsoft.ccd.module.personal.about.AboutActivity;
import com.zmsoft.ccd.module.personal.feedback.FeedBackActivity;
import com.zmsoft.ccd.module.personal.networkdetection.NetworkDetectionActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zmsoft.ccd.module.personal.profile.ProfileActivity.PATH_PROFILE_ACTIVITY;

/**
 * 新版移入到main activity的tab bar中
 *
 * @author heniu
 * @author DangGui
 * @update 2017/9/19.
 * <p>
 * 旧版右侧栏 "个人中心"
 * @create 2017/1/4.
 */
public class PersonalCenterFragment extends BaseFragment {

    public static final int GOTO_WORKING = 1; // 上班
    public static final int END_WORKING = 2; // 下班

    @BindView(R.id.image_userhead)
    ImageView mImageUserhead;
    @BindView(R.id.text_name)
    TextView mTextName;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.relative_header)
    RelativeLayout mRelativeHeader;
    @BindView(R.id.linear_personal_info)
    LinearLayout mLinearPersonalInfo;
    @BindView(R.id.image_customer_service_message_alert)
    ImageView mImageCustomerServiceMessageAlert;
    @BindView(R.id.text_feedback)
    TextView mTextFeedback;
    @BindView(R.id.text_about)
    TextView mTextAbout;
    @BindView(R.id.text_logout)
    TextView mTextLogout;
    @BindView(R.id.linear_check_shop)
    LinearLayout mLinearCheckShop;
    @BindView(R.id.text_work_status)
    TextView mTextWorkStatus;
    @BindView(R.id.text_set_working)
    TextView mTextSetWorking;
    @BindView(R.id.linear_work_model)
    LinearLayout mLinearWorkModel;
    @BindView(R.id.linear_print_config)
    LinearLayout mLinearPrintConfig;
    @BindView(R.id.linear_phone_carryout_setting)
    LinearLayout mLinearPhoneCarryoutSetting;
    @BindView(R.id.divider_carryout_setting)
    View mDividerCarryoutSetting;

    private PersonalWorkStatusImp mPersonalWorkStatusImp;

    private boolean mIsWorking = false;
    private boolean mIsCarryoutSettingVisible = false;


    //================================================================================
    // constructor
    //================================================================================
    public static PersonalCenterFragment newInstance() {
        Bundle args = new Bundle();
        PersonalCenterFragment fragment = new PersonalCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mPersonalWorkStatusImp = (PersonalWorkStatusImp) getActivity();
        updateUserView();
        updateWorkingStatusView();
        updateLinearCarryoutSettingView(mIsCarryoutSettingVisible);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {
        mPersonalWorkStatusImp = null;
    }

    //================================================================================
    // life cycle
    //================================================================================
    @Override
    public void onResume() {
        super.onResume();
        updateCustomerServiceAlert();
    }

    //================================================================================
    // update view
    //================================================================================
    private void updateUserView() {
        User user = UserLocalPrefsCacheSource.getUser();
        if (null != user) {
            if (!TextUtils.isEmpty(user.getMemberName())) {
                mTextName.setText(user.getMemberName());
            }
            StringBuilder titleBuilder = new StringBuilder();
            if (!TextUtils.isEmpty(user.getShopName())) {
                titleBuilder.append(user.getShopName());
            }
            if (!TextUtils.isEmpty(user.getRoleName())) {
                if (titleBuilder.length() != 0) {
                    titleBuilder.append("，");
                }
                titleBuilder.append(user.getRoleName());
            }
            if (titleBuilder.length() != 0) {
                mTextTitle.setText(titleBuilder.toString());
            }
            if (!TextUtils.isEmpty(user.getPicFullPath())) {
                ImageLoaderUtil.getInstance().loadImage(user.getPicFullPath(), mImageUserhead
                        , ImageLoaderOptionsHelper.getCcdAvatarOptions());
            } else {
                ImageLoaderUtil.getInstance().loadImage(R.drawable.icon_user_default
                        , mImageUserhead, ImageLoaderOptionsHelper.getCcdAvatarOptions());
            }
        }
    }

    private void updateWorkingStatusView() {
        if (null == mTextSetWorking || null == mTextWorkStatus) {
            return;
        }

        mTextWorkStatus.setVisibility(View.VISIBLE);
        mTextSetWorking.setVisibility(View.VISIBLE);
        if (mIsWorking) {
            mTextWorkStatus.setText(getString(R.string.personal_working));
            mTextWorkStatus.setBackgroundResource(R.drawable.shape_bg_round_person_center_on_work);
            mTextSetWorking.setText(getString(R.string.end_work));
            mTextSetWorking.setBackgroundResource(R.drawable.selector_button_person_center_go_off_work);
            mTextSetWorking.setTextColor(ContextCompat.getColor(getContext(), R.color.person_center_go_off_work));
        } else {
            mTextWorkStatus.setText(getString(R.string.personal_no_working));
            mTextWorkStatus.setBackgroundResource(R.drawable.shape_bg_round_person_center_off_work);
            mTextSetWorking.setText(getString(R.string.start_work));
            mTextSetWorking.setBackgroundResource(R.drawable.selector_button_person_center_go_to_work);
            mTextSetWorking.setTextColor(ContextCompat.getColor(getContext(), R.color.person_center_go_to_work));
        }
    }

    private void updateLinearCarryoutSettingView(boolean visible) {
        if (null == mLinearPhoneCarryoutSetting) {
            return;
        }
        mLinearPhoneCarryoutSetting.setVisibility(visible ? View.VISIBLE : View.GONE);
        mDividerCarryoutSetting.setVisibility(mLinearPhoneCarryoutSetting.getVisibility());
    }

    //================================================================================
    // on click
    //================================================================================
    @OnClick({R.id.linear_personal_info, R.id.text_feedback, R.id.text_about, R.id.text_logout
            , R.id.linear_check_shop, R.id.text_set_working
            , R.id.linear_work_model, R.id.linear_print_config, R.id.linear_phone_carryout_setting, R.id.layout_customer_service
            ,R.id.text_network_detection})
    public void onClick(View view) {
        if (!isHostActive()) return;
        switch (view.getId()) {
            case R.id.linear_personal_info:
                MRouter.getInstance().build(PATH_PROFILE_ACTIVITY).navigation(getActivity());
                break;
            case R.id.layout_customer_service:
                gotoCustomerService();
                break;
            case R.id.text_feedback:
                MRouter.getInstance().build(FeedBackActivity.PATH_FEEDBACK).navigation(getActivity());
                break;
            case R.id.text_about:
                MRouter.getInstance().build(AboutActivity.PATH_ABOUT).navigation(getActivity());
                break;
            case R.id.text_logout:
                logOut();
                break;
            case R.id.linear_check_shop:
                gotoCheckShopActivity();
                break;
            case R.id.text_set_working:
                if (mIsWorking) { // 是否上班中
                    showEndWorking();
                } else {
                    mPersonalWorkStatusImp.setWorking(GOTO_WORKING);
                }
                break;
            case R.id.linear_work_model:
                MRouter.getInstance().build(RouterPathConstant.WorkModel.PATH)
                        .putInt(RouterPathConstant.WorkModel.FROM, RouterPathConstant.WorkModel.FROM_MAIN)
                        .navigation(getActivity());
                break;
            case R.id.linear_print_config:
                MRouter.getInstance().build(RouterPathConstant.PrintConfig.PATH)
                        .navigation(getActivity());
                break;

            case R.id.linear_phone_carryout_setting:
                startActivity(new Intent(getActivity(), CarryoutSettingActivity.class));
                break;

            case R.id.text_network_detection:
                MRouter.getInstance().build(NetworkDetectionActivity.PATH_NETWORK_DETECTION)
                        .navigation(getActivity());
                break;
        }
    }

    private void logOut() {
        int dialogContent = UserHelper.getWorkStatus() ? R.string.person_logout_and_offwork_hint
                : R.string.person_logout_hint;
        getDialogUtil().showDialog(R.string.material_dialog_title, dialogContent, true, new SingleButtonCallback() {
            @Override
            public void onClick(DialogUtilAction which) {
                if (which == DialogUtilAction.POSITIVE) {
                    if (UserHelper.getWorkStatus()) {
                        mPersonalWorkStatusImp.setWorking(END_WORKING);
                    }
                    LogOutHelper.logOut(getActivity());
                }
            }
        });
    }

    private void gotoCheckShopActivity() {
        MRouter.getInstance().build(RouterPathConstant.CheckShop.PATH_CHECK_SHOP_ACTIVITY)
                .putInt(RouterPathConstant.CheckShop.FROM, RouterPathConstant.CheckShop.FROM_MAIN)
                .navigation(getActivity(), CheckShopActivity.RC_CHECK_SHOP);
    }

    //================================================================================
    // work status
    //================================================================================
    public void notifyWorkStatus(boolean isWorking) {
        this.mIsWorking = isWorking;
        BaseSpHelper.saveWorkStatus(getActivity(), isWorking);
        updateWorkingStatusView();
    }

    private void showEndWorking() {
        final DialogUtil dialogUtil = new DialogUtil(getActivity());
        dialogUtil.showDialog(R.string.prompt, R.string.end_work_dialog_content
                , R.string.ok_end_work
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            mPersonalWorkStatusImp.setWorking(END_WORKING);
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            dialogUtil.dismissDialog();
                        }
                    }
                });
    }

    public void refreshByCheckShop() {
        updateUserView();
    }

    //================================================================================
    // EventBus
    //================================================================================
    @Subscribe
    public void onCustomerServiceUnreadMessageEvent(CustomerServiceUnreadMessageEvent events) {
        if (null == mImageCustomerServiceMessageAlert) {
            return;
        }
        mImageCustomerServiceMessageAlert.setVisibility(View.VISIBLE);
    }

    @Override
    protected void registerEventBus() {
        super.registerEventBus();
        EventBusHelper.register(this);
    }

    @Override
    protected void unRegisterEventBus() {
        super.unRegisterEventBus();
        EventBusHelper.unregister(this);
    }

    //================================================================================
    // customer service
    //=================================================================================
    private void gotoCustomerService() {
        mImageCustomerServiceMessageAlert.setVisibility(View.GONE);
        CustomerServiceHelper.openCustomerService(getActivity().getApplicationContext());
    }

    private void updateCustomerServiceAlert() {
        int unreadMsgCount = SobotApi.getUnreadMsg(getActivity().getApplicationContext());
        if (0 < unreadMsgCount) {
            mImageCustomerServiceMessageAlert.setVisibility(View.VISIBLE);
        } else {
            mImageCustomerServiceMessageAlert.setVisibility(View.GONE);
        }
    }

    //================================================================================
    // setter
    //================================================================================
    public void setCarryoutSettingVisible(boolean visible) {
        this.mIsCarryoutSettingVisible = visible;
        updateLinearCarryoutSettingView(visible);
    }
}
