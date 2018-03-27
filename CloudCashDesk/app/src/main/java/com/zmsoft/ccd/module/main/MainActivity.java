package com.zmsoft.ccd.module.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.dfire.mobile.cashupdate.CashUpdateManager;
import com.dfire.mobile.cashupdate.bean.CashUpdateInfoDO;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.BuildConfig;
import com.zmsoft.ccd.CrashHandler;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.AppEnv;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.constant.SPConstants;
import com.zmsoft.ccd.data.source.orderfind.DaggerOrderFindComponent;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.helper.LocalPushMsgHelper;
import com.zmsoft.ccd.helper.PushMsgDispatchHelper;
import com.zmsoft.ccd.helper.PushSettingHelper;
import com.zmsoft.ccd.helper.TOpenShopHelper;
import com.zmsoft.ccd.lib.base.activity.NormalToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.AnswerEventConstant;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.constant.PhoneSpValue;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.BatchSwitchHelper;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.LocationHelper;
import com.zmsoft.ccd.lib.base.helper.PhoneSpHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.carryout.TakeoutMobile;
import com.zmsoft.ccd.lib.bean.filter.FilterItem;
import com.zmsoft.ccd.lib.bean.shop.ShopLimitVo;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.LocationUtils;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.ccd.lib.widget.dialog.permission.PermissionUtils;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.utils.tts.TTSUtils;
import com.zmsoft.ccd.module.checkshop.CheckShopActivity;
import com.zmsoft.ccd.module.login.login.LoginActivity;
import com.zmsoft.ccd.module.main.home.view.HomeFragment;
import com.zmsoft.ccd.module.main.message.MessageListFragment;
import com.zmsoft.ccd.module.main.order.find.OrderFindPresenter;
import com.zmsoft.ccd.module.main.order.find.dagger.DaggerOrderFindPresenterComponent;
import com.zmsoft.ccd.module.main.order.find.dagger.OrderFindPresenterModule;
import com.zmsoft.ccd.module.main.order.find.fragment.OrderFindFragment;
import com.zmsoft.ccd.module.personal.personalcenter.PersonalCenterFragment;
import com.zmsoft.ccd.module.personal.personalcenter.PersonalWorkStatusImp;
import com.zmsoft.ccd.network.CommonConstant;
import com.zmsoft.ccd.shop.bean.IndustryType;
import com.zmsoft.missile.MissileConsoles;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import phone.rest.zmsoft.tdfopenshopmodule.activity.UpgradeOfficialShopActivity;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/14 20:09
 */

@Route(path = RouterPathConstant.PATH_MAIN_ACTIVITY)
public class MainActivity extends NormalToolBarActivity implements MainContract.View, PersonalWorkStatusImp {
    public static final String TAG_ORDER_FIND = "orderFind";
    public static final String TAG_MESSAGE_LIST = "messageList";
    public static final String TAG_TAB_MAIN = "mainTabFragment";
    public static final String TAG_TAB_SET = "setFragment";

    /**
     * 底部Tab选项
     */
    public static final int TAB_ORDER_FIND = 0;     // 找单
    public static final int TAB_MSG_CENTER = 1;     // 消息中心
    public static final int TAB_MAIN = 3;           // 主页
    public static final int TAB_SET = 4;            // 设置
    private static final int RC_LOCATION = 123;

    @BindView(R.id.view_stub_main_activity_green_hand)
    ViewStub mViewStubGreenHand;
    // mViewStubGreenHand.inflate后的对象
    private LinearLayout mLayoutMainGreenHand;

    private MainActivityModel mMainActivityModel;


    @BindView(R.id.tv_title)
    TextView mTextTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.relative_tab_main)
    RelativeLayout mRelativeTabMain;
    @BindView(R.id.radio_button_find_order)
    RelativeLayout mRadioButtonFindOrder;
    @BindView(R.id.radio_button_msg_center)
    RelativeLayout mRadioButtonMessage;
    @BindView(R.id.radio_button_set)
    RelativeLayout mRadioButtonSet;

    @BindView(R.id.image_tab_main)
    ImageView mImageTabMain;
    @BindView(R.id.text_tab_main)
    TextView mTextTabMain;
    @BindView(R.id.image_tab_findorder)
    ImageView mImageTabFindOrder;
    @BindView(R.id.text_tab_findorder)
    TextView mTextTabFindOrder;
    @BindView(R.id.image_tab_msg_center)
    ImageView mImageTabMsgCenter;
    @BindView(R.id.text_tab_msg_center)
    TextView mTextTabMsgCenter;
    @BindView(R.id.image_msg_center_unread)
    ImageView mImageMsgCenterUnread;
    @BindView(R.id.text_tab_set)
    TextView mTextTabSet;
    @BindView(R.id.image_tab_set)
    ImageView mImageTabSet;

    @BindView(R.id.content)
    LinearLayout mLinearContent;
    @BindView(R.id.text_filter)
    TextView mTextFilter;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.frame_tab_msg_center)
    FrameLayout mFrameTabMsgCenter;

    // 消息筛选
    @BindView(R.id.text_customer_new_message)
    TextView mTextCustomerNewMessage;
    @BindView(R.id.text_handled_message)
    TextView mTextHandledMessage;
    @BindView(R.id.text_all_new_message)
    TextView mTextAllNewMessage;

    @Inject
    OrderFindPresenter mOrderFindPresenter;
    //    @Inject
    MainPresenter mPresenter;

    private OrderFindFragment mOrderFindFragment;
    private MessageListFragment mMessageListFragment;
    private PersonalCenterFragment mSetFragment;
    private HomeFragment mHomeFragment;

    private long mExitTime = 0;
    /**
     * 底部tab默认是找单
     */
    private int mCurrentTabItem = TAB_MAIN;

    /**
     * 用户
     */
    private User mUser;
    private static final int FILTER_TYPE_NEW_MESSAGE = 1;
    private static final int FILTER_TYPE_ALL_NEW_MESSAGE = 2;
    private static final int FILTER_TYPE_HANDLE_MESSAGE = 3;
    private int mMessageListFilterType = FILTER_TYPE_NEW_MESSAGE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (gotoMainActivity()) {
            return;
        }
        setContentView(R.layout.activity_drawer_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);
        initActionBar();
        EventBusHelper.register(this);
        mPresenter = new MainPresenter(this);
        // 检测用户是否登录，否则直接跳转到登录界面
        if (!initLogin()) {
            return;
        }
        mUser = UserLocalPrefsCacheSource.getUser();
        // 请求定位
        requestLocationApi();
        // 设置推送相关信息，比如别名、标签等
        initPush(false);
        initFilter();
        setCrashlytics();
        initView(savedInstanceState);
        initPresenter();
        initData();
        initGreenHandView();
        loadNetWorkData();
    }

    private void loadNetWorkData() {
        getBatchPermission();
        getSwitchByList();
        checkAppUpdate();
        getPrintConfigByUserId();
        checkTakeOutBindSeat();
        //获取试用店铺或者上班状态
        getShopLimitDay();
        initTtsUtil();
        getCurrencySymbol();
    }

    /**
     * 跳转零售主界面
     */
    private boolean gotoMainActivity() {
        if (UserHelper.getIndustry() == IndustryType.RETAIL) {
            Intent intent;
            intent = new Intent(this, RetailMainActivity.class);
            Intent getIntent = getIntent();
            if (null != getIntent) {
                Bundle extrasBundle = getIntent.getExtras();
                if (null != extrasBundle) {
                    intent.putExtras(extrasBundle);
                }
            }
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private void initPresenter() {
        DaggerOrderFindPresenterComponent.builder()
                .orderFindComponent(DaggerOrderFindComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .orderFindPresenterModule(new OrderFindPresenterModule(mOrderFindFragment))
                .build()
                .inject(this);
    }

    /**
     * 检测用户为空
     */
    private boolean initLogin() {
        if (!UserLocalPrefsCacheSource.isLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return false;
        }
        return true;
    }

    /**
     * 设置统计数据用户信息
     */
    private void setCrashlytics() {
        CrashHandler.updateCrashReportUserInfo();
    }

    /**
     * 初始化引导
     */
    public void initGreenHandView() {
        if (PhoneSpValue.VALUE_GREEN_HAND_VERSION_MAIN_ACTIVITY.VERSION_01_06_00 > PhoneSpHelper.getGreenHandVersionMainActivity(this)) {
            mLayoutMainGreenHand = (LinearLayout) mViewStubGreenHand.inflate();
            mMainActivityModel.setGreenHandStage(MainActivityModel.GREEN_HAND_STAGE.SHOW);
            super.updateStatusBarColor(R.color.transparent70);
        } else {
            updateStatusBarColor(R.color.transparent);
        }
    }

    private void initFilter() {
        final int mDrawerWidth = getResources().getDimensionPixelSize(R.dimen.message_list_filter_height);
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mTextFilter.setTranslationX(-slideOffset * mDrawerWidth);
            }
        });
        mTextFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
    }

    private void initActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayUseLogoEnabled(false);
        }
    }

    /**
     * 设置推送相关信息，比如别名、标签等
     */
    private void initPush(boolean forceRegister) {
        if (null != mUser) {
            String tag = null;
            String alias = null;
            if (!TextUtils.isEmpty(mUser.getUserId())) {
                tag = mUser.getUserId();
            }
            if (!TextUtils.isEmpty(mUser.getEntityId())) {
                alias = PushSettingHelper.ALIAS_PREFIX + mUser.getEntityId();
            }
            PushSettingHelper.setTagAndAlias(tag, alias, forceRegister);
            initMissile(mUser.getUserId(), mUser.getEntityId());
        }
        //删除本地数据库中存留时间超过一天的消息
        LocalPushMsgHelper.trimLocalMsg();
    }

    /**
     * 初始化TTS
     */
    private void initTtsUtil() {
        TTSUtils.initTts(getApplication());
    }

    /**
     * 初始化fragment
     *
     * @param savedInstanceState
     */
    private void initView(Bundle savedInstanceState) {
        updateTitleAndTabItem();
        if (savedInstanceState != null) {
            mOrderFindFragment = (OrderFindFragment) getSupportFragmentManager().findFragmentByTag(TAG_ORDER_FIND);
            mMessageListFragment = (MessageListFragment) getSupportFragmentManager().findFragmentByTag(TAG_MESSAGE_LIST);
            mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_TAB_MAIN);
            mSetFragment = (PersonalCenterFragment) getSupportFragmentManager().findFragmentByTag(TAG_TAB_SET);
        }
        if (mOrderFindFragment == null) {
            mOrderFindFragment = new OrderFindFragment();
        }
        if (mMessageListFragment == null) {
            mMessageListFragment = new MessageListFragment();
        }
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), mHomeFragment, R.id.content, TAG_TAB_MAIN);
        }
        if (mSetFragment == null) {
            mSetFragment = PersonalCenterFragment.newInstance();
        }
        // 禁止drawerLayout滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        // 更新筛选icon显示
        updateFilterIcon();
    }

    /**
     * 更新右侧筛选图标显示和隐藏
     */
    private void updateFilterIcon() {
        if (mCurrentTabItem == TAB_ORDER_FIND) {
            mTextFilter.setVisibility(View.INVISIBLE);
        } else if (mCurrentTabItem == TAB_MSG_CENTER) {
            mTextFilter.setVisibility(View.VISIBLE);
            mTextFilter.setText(getString(R.string.message_type));
        } else if (mCurrentTabItem == TAB_MAIN) {
            mTextFilter.setVisibility(View.INVISIBLE);
        } else if (mCurrentTabItem == TAB_SET) {
            mTextFilter.setVisibility(View.INVISIBLE);
        }
    }

    private void initData() {
        Intent getIntent = getIntent();
        handleNewIntent(getIntent);
        mMainActivityModel = new MainActivityModel();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleNewIntent(intent);
    }

    /**
     * 处理前一个页面传递来的intent
     * <p>
     * 1、打开notification传递 <br />
     * 2、splash页面传递
     * </p>
     *
     * @param newIntent
     */
    private void handleNewIntent(Intent newIntent) {
        if (null != newIntent) {
            Bundle bundle = newIntent.getExtras();
            if (null != bundle) {
                boolean notificationToMsgCenter = bundle.getBoolean(PushMsgDispatchHelper.EXTRA_NOTIFICATION_TO_MSGCENTER);
                if (notificationToMsgCenter) {
                    mRadioButtonMessage.performClick();
                }
                if (bundle.getInt(RouterPathConstant.Main.EXTRA_SWITCH_TAB_PARAM, -1) == RouterPathConstant.Main.SWITCH_TO_FIND_ORDER) {
                    switchFindOrderList();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        LocationUtils.unregister();
        EventBusHelper.unregister(this);
        super.onDestroy();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.mPresenter = (MainPresenter) presenter;
    }

    @Override
    public void getShopLimitDayFailure() {
        mHomeFragment.getShopLimitDayFailure();
        loadWorkingStatus();
    }

    @Override
    public void getShopLimitDaySuccess(ShopLimitVo shopLimitVo) {
        mHomeFragment.getShopLimitDaySuccess(shopLimitVo);
        // 店铺试用期到期
        if (BaseSpHelper.isShopOutOfDate(this)) {
            mSetFragment.notifyWorkStatus(false);
            showUpgradeShop();
        } else {
            loadWorkingStatus();
        }
    }

    //================================================================================
    // MainContract.BaseView
    //================================================================================
    @Override
    public void checkAppUpdateSuccess(CashUpdateInfoDO data) {
        SPUtils.getInstance(this).putLong(SPConstants.CheckUpdate.LAST_TIME, System.currentTimeMillis());
        if (data != null) {
            if (data.getProperty() == CashUpdateInfoDO.FORCE_UPDATE_VERSION ||
                    data.getProperty() == CashUpdateInfoDO.NEW_TRIAL_VERSION) {
                CashUpdateManager.getInstance().handlerUpdate(this, data, new CashUpdateManager.CashUpdateDialogListener() {
                    @Override
                    public void showForceUpdateDialog(final CashUpdateInfoDO cashUpdateInfo) {
                        activityShowForceUpdateDialog(cashUpdateInfo);
                    }

                    @Override
                    public void showUpdateDialog(final CashUpdateInfoDO cashUpdateInfo) {
                        activityShowUpdateDialog(cashUpdateInfo);
                    }

                    @Override
                    public void showNoAdviceUpdateDialog(CashUpdateInfoDO cashUpdateInfo) {

                    }

                    @Override
                    public void noUpdate() {

                    }
                });
            }
        }
    }


    @Override
    public void showPersonalCenterMenu() {
        mDrawerLayout.openDrawer(GravityCompat.END);
    }

    @Override
    public void loadConfigSuccess(boolean hasOpenCarryoutPhoneCall) {
        mSetFragment.setCarryoutSettingVisible(hasOpenCarryoutPhoneCall);
    }

    @Override
    public void loadConfigFailed(boolean hasOpenCarryoutPhoneCall) {
        mSetFragment.setCarryoutSettingVisible(hasOpenCarryoutPhoneCall);
    }

    @Override
    public void loadCarryoutPhoneListSuccess(List<TakeoutMobile> list) {
        if (list != null && !list.isEmpty()) {
            ConfigHelper.saveReceiveCarryoutPhoneSetting(this,
                    UserHelper.getMobile().equals(list.get(0).getMobile()));
        } else {
            ConfigHelper.saveReceiveCarryoutPhoneSetting(this, false);
        }
    }

    @Override
    public void checkTakeOutBindSeat() {
        mPresenter.checkTakeOutBindSeat(mUser.getEntityId(), mUser.getUserId());
    }

    @Override
    public void loadCarryoutPhoneListFailed(String errorCode, String errorMsg) {

    }

    @Override
    public void loadWorkingStatusFailure() {
        mSetFragment.notifyWorkStatus(false);
        showReloadWorkStatusDialog();
    }

    @Override
    public void loadWorkingStatusSuccess(boolean status) {
        if (!status) {
            showGotoWorking();
        }
        mSetFragment.notifyWorkStatus(status);
    }

    @Override
    public void setWorkingSuccess(int data, int type) {
        boolean isWorking = false;
        switch (type) {
            case PersonalCenterFragment.GOTO_WORKING:
                isWorking = true;
                showToast(getString(R.string.goto_work_success));
                break;
            case PersonalCenterFragment.END_WORKING:
                isWorking = false;
                showToast(getString(R.string.end_work_success));
                break;
        }
        mSetFragment.notifyWorkStatus(isWorking);
    }

    @Override
    public void showToastMessage(String message) {
        showToast(message);
    }

    private void showReloadWorkStatusDialog() {
        if (MainActivityModel.GREEN_HAND_STAGE.SHOW == mMainActivityModel.getGreenHandStage()) {
            mMainActivityModel.setNeedShowReloadWorkStatusDialog(true);
            return;
        } else if (MainActivityModel.GREEN_HAND_STAGE.FINISH == mMainActivityModel.getGreenHandStage()) {
            if (mMainActivityModel.isNeedShowReloadWorkStatusDialog()) {
                mMainActivityModel.setNeedShowReloadWorkStatusDialog(false);
            } else {
                return;
            }
        }

        final DialogUtil dialogUtil = new DialogUtil(this);
        dialogUtil.showDialog(R.string.prompt, R.string.get_work_status_failure
                , R.string.retry
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            loadWorkingStatus();
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            dialogUtil.dismissDialog();
                        }
                    }
                });
    }


    //================================================================================
    // switch fragment
    //================================================================================
    void showFindOrder() {
        mCurrentTabItem = TAB_ORDER_FIND;
        updateTitleAndTabItem();
        showOrderFindFragment();
        updateFilterIcon();
    }

    void showMessage() {
        mCurrentTabItem = TAB_MSG_CENTER;
        updateTitleAndTabItem();
        mTextFilter.setText(getString(R.string.message_type));
        doRefreshCustomerNewMessage();
        showMessageListFragment();
        updateFilterIcon();
    }

    void showTabMain() {
        mCurrentTabItem = TAB_MAIN;
        updateTitleAndTabItem();
        //通知主页刷新未读数量
        EventBusHelper.post(BaseEvents.CommonEvent.EVENT_HOME_REFRESH);
        showTabMainFragment();
        updateFilterIcon();
    }

    void showTabSet() {
        mCurrentTabItem = TAB_SET;
        updateTitleAndTabItem();
        showTabSetFragment();
        updateFilterIcon();
    }

    /**
     * 更新tab item显示
     */
    private void updateTitleAndTabItem() {
        switch (mCurrentTabItem) {
            case TAB_ORDER_FIND:
                setTitle(getString(R.string.main_tab_find_order));
                break;
            case TAB_MSG_CENTER:
                setTitle(getString(R.string.main_tab_msg_center));
                break;
            case TAB_MAIN:
                if (null != mUser) {
                    setTitle(mUser.getShopName());
                }
                break;
            case TAB_SET:
                setTitle(getString(R.string.main_tab_set));
                break;
            default:
                break;
        }
        updateTabItem(mCurrentTabItem);
    }

    public void updateTabItem(int tabItem) {
        switch (tabItem) {
            case TAB_ORDER_FIND:
                mImageTabFindOrder.setImageResource(R.drawable.icon_tab_find_order_selected);
                mTextTabFindOrder.setTextColor(ContextCompat.getColor(this, R.color.primaryColor));
                mImageTabMsgCenter.setImageResource(R.drawable.icon_tab_message_normal);
                mTextTabMsgCenter.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                mImageTabMain.setImageResource(R.drawable.icon_tab_home_normal);
                mTextTabMain.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                mImageTabSet.setImageResource(R.drawable.icon_tab_set_normal);
                mTextTabSet.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                break;
            case TAB_MSG_CENTER:
                mImageTabFindOrder.setImageResource(R.drawable.icon_tab_find_order_normal);
                mTextTabFindOrder.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                mImageTabMsgCenter.setImageResource(R.drawable.icon_tab_message_selected);
                mTextTabMsgCenter.setTextColor(ContextCompat.getColor(this, R.color.primaryColor));
                mImageTabMain.setImageResource(R.drawable.icon_tab_home_normal);
                mTextTabMain.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                mImageTabSet.setImageResource(R.drawable.icon_tab_set_normal);
                mTextTabSet.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                break;
            case TAB_MAIN:
                mImageTabFindOrder.setImageResource(R.drawable.icon_tab_find_order_normal);
                mTextTabFindOrder.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                mImageTabMsgCenter.setImageResource(R.drawable.icon_tab_message_normal);
                mTextTabMsgCenter.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                mImageTabMain.setImageResource(R.drawable.icon_tab_home_selected);
                mTextTabMain.setTextColor(ContextCompat.getColor(this, R.color.primaryColor));
                mImageTabSet.setImageResource(R.drawable.icon_tab_set_normal);
                mTextTabSet.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                break;
            case TAB_SET:
                mImageTabFindOrder.setImageResource(R.drawable.icon_tab_find_order_normal);
                mTextTabFindOrder.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                mImageTabMsgCenter.setImageResource(R.drawable.icon_tab_message_normal);
                mTextTabMsgCenter.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                mImageTabMain.setImageResource(R.drawable.icon_tab_home_normal);
                mTextTabMain.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
                mImageTabSet.setImageResource(R.drawable.icon_tab_set_selected);
                mTextTabSet.setTextColor(ContextCompat.getColor(this, R.color.primaryColor));
                break;
            default:
                break;
        }
    }


    private void showOrderFindFragment() {
        hideFragment(mMessageListFragment);
        hideFragment(mHomeFragment);
        hideFragment(mSetFragment);
        ActivityHelper.showFragment(getSupportFragmentManager(), mOrderFindFragment, R.id.content, TAG_ORDER_FIND);
    }

    private void showMessageListFragment() {
        hideFragment(mOrderFindFragment);
        hideFragment(mHomeFragment);
        hideFragment(mSetFragment);
        ActivityHelper.showFragment(getSupportFragmentManager(), mMessageListFragment, R.id.content, TAG_MESSAGE_LIST);
    }

    private void showTabMainFragment() {
        hideFragment(mOrderFindFragment);
        hideFragment(mMessageListFragment);
        hideFragment(mSetFragment);
        ActivityHelper.showFragment(getSupportFragmentManager(), mHomeFragment, R.id.content, TAG_TAB_MAIN);
    }

    private void showTabSetFragment() {
        hideFragment(mOrderFindFragment);
        hideFragment(mMessageListFragment);
        hideFragment(mHomeFragment);
        ActivityHelper.showFragment(getSupportFragmentManager(), mSetFragment, R.id.content, TAG_TAB_SET);
    }

    private void hideFragment(Fragment fragment) {
        ActivityHelper.hideFragment(getSupportFragmentManager(), fragment, false);
    }

    /**
     * 收到消息中心有未读新消息的通知
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMsgCenterUnreadView(BaseEvents.CommonEvent event) {
        if (event == BaseEvents.CommonEvent.EVENT_MSG_CENTER_UNREAD) {
            boolean hasNewMsg = (boolean) event.getObject();
            if (null != mImageMsgCenterUnread) {
                mImageMsgCenterUnread.setVisibility(hasNewMsg ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime < 2000) {
            RouterActivityManager.get().finishAllActivity();
        } else {
            mExitTime = System.currentTimeMillis();
            showToast(R.string.exit_app);
        }
    }

    @Override
    protected void unBindPresenterFromView() {
        super.unBindPresenterFromView();
        if (mPresenter != null)
            mPresenter.unsubscribe();
    }


    @OnClick({R.id.radio_button_find_order, R.id.radio_button_msg_center, R.id.relative_tab_main, R.id.radio_button_set
            , R.id.text_customer_new_message, R.id.text_handled_message, R.id.text_all_new_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_button_find_order:
                if (mCurrentTabItem != TAB_ORDER_FIND) {
                    AnswerEventLogger.log(AnswerEventConstant.MainTab.ANSWER_EVENT_TYPE_TAB_SWITCH, AnswerEventConstant.MainTab.ANSWER_EVENT_NAME_FIND);
                    showFindOrder();
                }
                break;
            case R.id.radio_button_msg_center:
                if (mCurrentTabItem != TAB_MSG_CENTER) {
                    AnswerEventLogger.log(AnswerEventConstant.MainTab.ANSWER_EVENT_TYPE_TAB_SWITCH, AnswerEventConstant.MainTab.ANSWER_EVENT_NAME_MSG);
                    showMessage();
                }
                break;
            case R.id.relative_tab_main:
                if (mCurrentTabItem != TAB_MAIN) {
                    AnswerEventLogger.log(AnswerEventConstant.MainTab.ANSWER_EVENT_TYPE_TAB_SWITCH, AnswerEventConstant.MainTab.ANSWER_EVENT_NAME_MAIN);
                    showTabMain();
                }
                break;
            case R.id.radio_button_set:
                if (mCurrentTabItem != TAB_SET) {
                    AnswerEventLogger.log(AnswerEventConstant.MainTab.ANSWER_EVENT_TYPE_TAB_SWITCH, AnswerEventConstant.MainTab.ANSWER_EVENT_NAME_SET);
                    showTabSet();
                }
                break;
            // 消息筛选
            case R.id.text_customer_new_message:
                if (mMessageListFragment != null && mMessageListFragment.isAdded() && mMessageListFilterType != FILTER_TYPE_NEW_MESSAGE) {
                    doRefreshCustomerNewMessage();
                }
                break;
            case R.id.text_handled_message:
                if (mMessageListFragment != null && mMessageListFragment.isAdded() && mMessageListFilterType != FILTER_TYPE_HANDLE_MESSAGE) {
                    mMessageListFilterType = FILTER_TYPE_HANDLE_MESSAGE;
                    updateMessageFilterItem();
                    notifyMessageListByFilter(FilterItem.MENU_ITEM_MESSAGE_DEAL_WITH);
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }
                break;
            case R.id.text_all_new_message:
                if (mMessageListFragment != null && mMessageListFragment.isAdded() && mMessageListFilterType != FILTER_TYPE_ALL_NEW_MESSAGE) {
                    mMessageListFilterType = FILTER_TYPE_ALL_NEW_MESSAGE;
                    updateMessageFilterItem();
                    notifyMessageListByFilter(FilterItem.MENU_ITEM_MESSAGE_ALL);
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                }
                break;
        }
    }

    //================================================================================
    // refresh message by filter
    //================================================================================
    private void notifyMessageListByFilter(String code) {
        FilterItem filterItem = new FilterItem();
        filterItem.setCode(code);
        mMessageListFragment.changeMsgList(filterItem);
    }

    private void updateMessageFilterItem() {
        switch (mMessageListFilterType) {
            case FILTER_TYPE_ALL_NEW_MESSAGE:
                mTextAllNewMessage.setTextColor(getResources().getColor(R.color.accentColor));
                mTextCustomerNewMessage.setTextColor(getResources().getColor(R.color.secondaryTextColor));
                mTextHandledMessage.setTextColor(getResources().getColor(R.color.secondaryTextColor));
                break;
            case FILTER_TYPE_NEW_MESSAGE:
                mTextCustomerNewMessage.setTextColor(getResources().getColor(R.color.accentColor));
                mTextAllNewMessage.setTextColor(getResources().getColor(R.color.secondaryTextColor));
                mTextHandledMessage.setTextColor(getResources().getColor(R.color.secondaryTextColor));
                break;
            case FILTER_TYPE_HANDLE_MESSAGE:
                mTextHandledMessage.setTextColor(getResources().getColor(R.color.accentColor));
                mTextAllNewMessage.setTextColor(getResources().getColor(R.color.secondaryTextColor));
                mTextCustomerNewMessage.setTextColor(getResources().getColor(R.color.secondaryTextColor));
                break;
        }
    }

    private void doRefreshCustomerNewMessage() {
        if (mMessageListFragment != null && mMessageListFragment.isAdded()) {
            mMessageListFilterType = FILTER_TYPE_NEW_MESSAGE;
            updateMessageFilterItem();
            notifyMessageListByFilter(FilterItem.MENU_ITEM_MESSAGE_USER);
            mDrawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    private void initMissile(String useId, String entityId) {
        if (TextUtils.isEmpty(useId) || TextUtils.isEmpty(entityId)) {
            return;
        }
        MissileConsoles.instance().prepare(new MissileConsoles
                .Config()
                .serverName(AppEnv.getMissileApi())
                .serverPort(AppEnv.getMissilePort())
                .userId(useId)
                .entityId(entityId)
                .missileKey(AppEnv.MISSILE_KEY)
                .missileSecret(AppEnv.MISSILE_SECRET)
                .version(BuildConfig.VERSION_NAME)
                .foreground(true)
                .notification(getNotification())
                .packageName(getApplication().getPackageName())
                .context(getApplicationContext()));
        MissileConsoles.instance().startPush();
    }

    private Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent intent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
                .setContentTitle(getString(R.string.app_name))
                .setSmallIcon(R.drawable.logo_48x48)
                .setContentText(getString(R.string.cloudcash_running))
                .setWhen(System.currentTimeMillis());
        return builder.build();
    }

    /**
     * 批量获取应用内所需权限
     */
    private void getBatchPermission() {
        mPresenter.batchCheckPermisson(Permission.EmptyDiscount.SYSTEM_TYPE, BatchPermissionHelper.getAllPermissionCode());
    }

    /**
     * 获取打印开关
     * 1.自动打印客户联
     * 2.自动打印财务联
     * 3.自动打印点菜单
     */
    private void getSwitchByList() {
        mPresenter.getFielCodeByList(UserHelper.getEntityId(), BatchSwitchHelper.getBusinessModelSwitchList());
        mPresenter.getSwitchByList(UserHelper.getEntityId(), BatchSwitchHelper.getCommonSwitchList());
    }

    /**
     * 获取打印配置
     * 1.小票打印配置
     * 2.标签打印配置
     */
    private void getPrintConfigByUserId() {
        mPresenter.getPrintConfig(UserHelper.getEntityId(), UserHelper.getUserId());
        mPresenter.getLabelPrintConfig(UserHelper.getEntityId(), UserHelper.getUserId());
    }

    /**
     * 检测更新
     */
    private void checkAppUpdate() {
        long lastTime = SPUtils.getInstance(this).getLong(SPConstants.CheckUpdate.LAST_TIME);
        if (System.currentTimeMillis() - lastTime < CommonConstant.CHECK_UPDATE_TIME_GAP) {
            return;
        }
        mPresenter.checkAppUpdate(UserHelper.getEntityId(), CommonConstant.APP_CODE, BuildConfig.VERSION_CODE);
    }

    //================================================================================
    // location
    //================================================================================
    private void requestLocationApi() {
        if (PermissionUtils.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            initLocation();
        } else {
            requestLocationPermission();
        }
    }

    private void initLocation() {
        LocationUtils.register(MainActivity.this, LocationUtils.MIN_TIME, LocationUtils.MIN_DISTANCE, new LocationUtils.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                if (location != null) {
                    LocationHelper.saveToSp(location.getLatitude(), location.getLongitude());
                    LocationUtils.unregister();
                }
            }

            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    LocationHelper.saveToSp(location.getLatitude(), location.getLongitude());
                    LocationUtils.unregister();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestLocationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RC_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.d("grantResults = " + Arrays.toString(grantResults));
        if (requestCode == RC_LOCATION && grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initLocation();
        } else {
            showLocationRationale();
        }
    }

    private void showLocationRationale() {
        if (MainActivityModel.GREEN_HAND_STAGE.SHOW == mMainActivityModel.getGreenHandStage()) {
            mMainActivityModel.setNeedShowPermissionRationale(true);
            return;
        } else if (MainActivityModel.GREEN_HAND_STAGE.FINISH == mMainActivityModel.getGreenHandStage()) {
            if (mMainActivityModel.isNeedShowPermissionRationale()) {
                mMainActivityModel.setNeedShowPermissionRationale(false);
            } else {
                return;
            }
        }

        showPermissionRationale(R.string.title_location_permission_dialog);
    }

    //================================================================================
    // event bus
    //================================================================================
    @Subscribe
    public void switchOrderList(BaseEvents.CommonEvent event) {
        if (event != null && event == BaseEvents.CommonEvent.EVENT_SWITCH_WATCHED_RETAIL) {
            if (mCurrentTabItem == TAB_ORDER_FIND) {
                return;
            }
            showFindOrder();
        }
    }

    private void switchFindOrderList() {
        if (mCurrentTabItem == TAB_ORDER_FIND) {
            return;
        }
        showFindOrder();
    }

    //================================================================================
    // PersonalWorkStatusImp
    //================================================================================
    @Override
    public void loadWorkingStatus() {
        mPresenter.getWorkingStatus(mUser.getEntityId(), 3, mUser.getUserId());
    }

    @Override
    public void setWorking(int status) {
        // 店铺试用期已过，不能上班
        if (BaseSpHelper.isShopOutOfDate(this)) {
            showUpgradeShop();
        } else {
            mPresenter.setWorkingStatus(mUser.getEntityId(), 3, mUser.getUserId(), status);
        }
    }

    //================================================================================
    // work status
    //================================================================================
    private void showGotoWorking() {
        if (MainActivityModel.GREEN_HAND_STAGE.SHOW == mMainActivityModel.getGreenHandStage()) {
            mMainActivityModel.setNeedShowGotoWorkingDialog(true);
            return;
        } else if (MainActivityModel.GREEN_HAND_STAGE.FINISH == mMainActivityModel.getGreenHandStage()) {
            if (mMainActivityModel.isNeedShowGotoWorkingDialog()) {
                mMainActivityModel.setNeedShowGotoWorkingDialog(false);
            } else {
                return;
            }
        }

        getDialogUtil().showDialog(R.string.prompt
                , R.string.goto_work_dialog_content
                , R.string.start_work
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            setWorking(PersonalCenterFragment.GOTO_WORKING);
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            getDialogUtil().dismissDialog();
                        }
                    }
                });
    }

    /**
     * 升级正式店铺弹窗
     */
    private void showUpgradeShop() {
        if (MainActivityModel.GREEN_HAND_STAGE.SHOW == mMainActivityModel.getGreenHandStage()) {
            mMainActivityModel.setNeedShowUpgradeShopDialog(true);
            return;
        } else if (MainActivityModel.GREEN_HAND_STAGE.FINISH == mMainActivityModel.getGreenHandStage()) {
            if (mMainActivityModel.isNeedShowUpgradeShopDialog()) {
                mMainActivityModel.setNeedShowUpgradeShopDialog(false);
            } else {
                return;
            }
        }

        getDialogUtil().showDialog(R.string.prompt
                , R.string.shop_employ_end_content
                , R.string.at_once_upgrade
                , R.string.know
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            gotoShopOfflineActivity();
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            getDialogUtil().dismissDialog();
                        }
                    }
                });
    }

    /**
     * 获取试用店铺过期时间
     */
    private void getShopLimitDay() {
        if (mUser != null) {
            if (mUser.getTrialShop() == Base.INT_TRUE) {
                mPresenter.getShopLimitDay(UserHelper.getEntityId());
            } else {
                BaseSpHelper.saveShopOutOfDate(this, false);
                loadWorkingStatus();
            }
        }
    }

    /**
     * 获取货币符号
     */
    private void getCurrencySymbol() {
        if (!TextUtils.isEmpty(UserHelper.getCurrencySymbol())) {
            return;
        }
        mPresenter.getConfigSwitchVal(UserHelper.getEntityId()
                , String.valueOf(SystemDirCodeConstant.TYPE_SYSTEM)
                , SystemDirCodeConstant.DEFAULT_CURRENCY_SYMBOL);
    }

    /**
     * go to 升级正式店铺
     */
    private void gotoShopOfflineActivity() {
        TOpenShopHelper.initOpenShopSDK();
        Intent intent = new Intent(this, UpgradeOfficialShopActivity.class);
        startActivity(intent);
    }

    //================================================================================
    // green hand
    //================================================================================
    public void finishGreenHand() {
        if (null == mLayoutMainGreenHand) {
            return;
        }
        PhoneSpHelper.saveGreenHandVersionMainActivity(this, PhoneSpValue.VALUE_GREEN_HAND_VERSION_MAIN_ACTIVITY.VERSION_01_06_00);

        mLayoutMainGreenHand.setVisibility(View.GONE);

        super.updateStatusBarColor(R.color.transparent);

        // 显示新手引导后，才开始弹框
        mMainActivityModel.setGreenHandStage(MainActivityModel.GREEN_HAND_STAGE.FINISH);
        activityShowForceUpdateDialog(mMainActivityModel.getCashUpdateInfo());
        activityShowUpdateDialog(mMainActivityModel.getCashUpdateInfo());
        showReloadWorkStatusDialog();
        showLocationRationale();
        showGotoWorking();
        showUpgradeShop();
        mMainActivityModel.setGreenHandStage(MainActivityModel.GREEN_HAND_STAGE.INIT);
    }

    //================================================================================
    // app update
    //================================================================================
    private void activityShowForceUpdateDialog(final CashUpdateInfoDO cashUpdateInfo) {
        if (MainActivityModel.GREEN_HAND_STAGE.SHOW == mMainActivityModel.getGreenHandStage()) {
            mMainActivityModel.setNeedShowForceUpdateDialog(true);
            mMainActivityModel.setCashUpdateInfo(cashUpdateInfo);
            return;
        } else if (MainActivityModel.GREEN_HAND_STAGE.FINISH == mMainActivityModel.getGreenHandStage()) {
            if (mMainActivityModel.isNeedShowForceUpdateDialog()) {
                mMainActivityModel.setNeedShowForceUpdateDialog(false);
            } else {
                return;
            }
        }

        if (null == cashUpdateInfo) {
            return;
        }
        getDialogUtil().showNoticeDialog(R.string.check_update_title,
                cashUpdateInfo.getContent(), R.string.ok, false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            CashUpdateManager.getInstance().startDialogUpdate(MainActivity.this, cashUpdateInfo.getUrl());
                        }
                    }
                });
    }

    private void activityShowUpdateDialog(final CashUpdateInfoDO cashUpdateInfo) {
        if (MainActivityModel.GREEN_HAND_STAGE.SHOW == mMainActivityModel.getGreenHandStage()) {
            mMainActivityModel.setNeedShowUpdateDialog(true);
            mMainActivityModel.setCashUpdateInfo(cashUpdateInfo);
            return;
        } else if (MainActivityModel.GREEN_HAND_STAGE.FINISH == mMainActivityModel.getGreenHandStage()) {
            if (mMainActivityModel.isNeedShowUpdateDialog()) {
                mMainActivityModel.setNeedShowUpdateDialog(false);
            } else {
                return;
            }
        }

        if (null == cashUpdateInfo) {
            return;
        }
        getDialogUtil().showDialog(getString(R.string.check_update_title)
                , cashUpdateInfo.getContent(), false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.NEGATIVE) {

                        } else if (which == DialogUtilAction.POSITIVE) {
                            CashUpdateManager.getInstance().startDialogUpdate(MainActivity.this, cashUpdateInfo.getUrl());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CheckShopActivity.RC_CHECK_SHOP:
                    refreshByCheckShop();
                    break;
            }
        }
    }

    //================================================================================
    // 切店刷新
    //================================================================================
    private void refreshByCheckShop() {
        mUser = UserLocalPrefsCacheSource.getUser();
        if (mHomeFragment != null && mHomeFragment.isAdded()) {
            mHomeFragment.refreshByCheckShop();
        }
        if (mMessageListFragment != null && mMessageListFragment.isAdded()) {
            mMessageListFragment.refreshByCheckShop();
        }
        if (mOrderFindFragment != null && mOrderFindFragment.isAdded()) {
            mOrderFindFragment.refreshByCheckShop();
        }
        if (mSetFragment != null && mSetFragment.isAdded()) {
            mSetFragment.refreshByCheckShop();
        }
        updateTitleAndTabItem();
        getBatchPermission();
        getSwitchByList();
        getPrintConfigByUserId();
        getShopLimitDay();
        //反注册推送消息
        PushSettingHelper.unRegisterPush();
        initPush(true);
    }
}
