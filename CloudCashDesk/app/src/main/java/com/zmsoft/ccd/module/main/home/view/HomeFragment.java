package com.zmsoft.ccd.module.main.home.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.business.QuickOpenOrder;
import com.zmsoft.ccd.data.source.home.dagger.DaggerHomeSourceComponent;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.event.main.RefreshMainGuideEvent;
import com.zmsoft.ccd.helper.CommonHelper;
import com.zmsoft.ccd.helper.TOpenShopHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerAdapter;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.RetailOrderHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.home.HomeCount;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.bean.shop.ShopLimitVo;
import com.zmsoft.ccd.lib.utils.AppUtils;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;
import com.zmsoft.ccd.lib.utils.language.LanguageUtil;
import com.zmsoft.ccd.lib.widget.softkeyboard.GridItemDividerDecoration;
import com.zmsoft.ccd.module.electronic.ElectronicListActivity;
import com.zmsoft.ccd.module.main.home.adapter.HomeAdapter;
import com.zmsoft.ccd.module.main.home.adapter.items.HomeBizItem;
import com.zmsoft.ccd.module.main.home.dagger.DaggerHomeComponent;
import com.zmsoft.ccd.module.main.home.dagger.HomePresenterModule;
import com.zmsoft.ccd.module.main.home.presenter.HomeContract;
import com.zmsoft.ccd.module.main.home.presenter.HomePresenter;
import com.zmsoft.ccd.module.main.order.complete.OrderCompleteActivity;
import com.zmsoft.ccd.module.menubalance.MenuBalanceActivity;
import com.zmsoft.ccd.module.shortcutreceipt.ShortCutReceiptActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import phone.rest.zmsoft.tdfopenshopmodule.activity.UpgradeOfficialShopActivity;
import phone.rest.zmsoft.tdfopenshopmodule.activity.UpgradeShopSuccessEvent;
import rx.functions.Action1;


/**
 * @author DangGui
 * @created 2017/8/1.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private static final String SHOPKEEPER_PACKAGE = "zmsoft.rest.phone";
    private static final String SHOPKEEPER_DOWNLOAD_PATH = "http://www.2dfire.com/cardcase/indexs.html";

    @BindView(R.id.text_open_order)
    TextView mTextOpenOrder;
    @BindView(R.id.text_shortcut_receipt)
    TextView mTextShortcutReceipt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.text_takeout_num)
    TextView mTextTakeoutNum;
    @BindView(R.id.layout_delivery)
    LinearLayout mLayoutDelivery;
    @BindView(R.id.text_update_shop_official)
    TextView mTextUpdateShopOfficial;
    @BindView(R.id.shop_apply)
    TextView mShopApply;
    @BindView(R.id.relative_shop_limit)
    RelativeLayout mRelativeShopLimit;
    @BindView(R.id.layout_root)
    LinearLayout mLayoutRoot;

    private HomeAdapter mHomeAdapter;
    private ArrayList<HomeBizItem> mDatas;
    /**
     * 外卖
     */
    private HomeBizItem mTakeOutItem;
    /**
     * 挂单
     */
    private HomeBizItem mHangUpItem;
    /**
     * 工作模式，是否是混合使用
     */
    private boolean misMixture;

    @Inject
    HomePresenter mPresenter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        DaggerHomeComponent.builder()
                .homeSourceComponent(DaggerHomeSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .homePresenterModule(new HomePresenterModule(this))
                .build()
                .inject(this);

        mDatas = new ArrayList<>();
        misMixture = UserHelper.getWorkModeIsMixture();
        postShowOnlineTakeoutEvent(!misMixture);
        initBizDatas();
        mHomeAdapter = new HomeAdapter(getActivity(), mRecyclerView, mDatas);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.addItemDecoration(new GridItemDividerDecoration(1, ContextCompat.getColor(getActivity()
                , R.color.softkeyboard_divider)));
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            mPresenter.getHomeUnreadCount();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getWorkMode();
    }

    public void refreshByCheckShop() {
        getWorkMode();
        getShopLimitDay();
    }

    private void getWorkMode() {
        List<String> list = new ArrayList<>(1);
        list.add(SystemDirCodeConstant.IS_USE_LOCAL_CASH);
        mPresenter.getWorkModel(UserHelper.getEntityId(), list);
    }

    private void getShopLimitDay() {
        mPresenter.getShopLimitDay(UserHelper.getEntityId());
    }

    public void initListener() {
        RxView.clicks(mTextOpenOrder).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (checkOffWorkStatus()) return;
                        OrderParam param = new OrderParam();
                        param.setSeatCode(RetailOrderHelper.getDefaultRetailSeatCode());
                        // 是否快速开单开关
                        if (BaseSpHelper.isQuickOpenOrder(getActivity())) {
                            new QuickOpenOrder().doQuickOpenOrder(getActivity(), param);
                            return;
                        }
                        MRouter.getInstance().build(RouterPathConstant.CreateOrUpdateOrder.PATH)
                                .putString(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM
                                        , RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE)
                                .putSerializable(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM, param)
                                .navigation(getActivity());
                    }
                });
        RxView.clicks(mTextShortcutReceipt).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (checkOffWorkStatus()) return;
                        if (BatchPermissionHelper.getPermission(Permission.CheckOut.ACTION_CODE)) {
                            ShortCutReceiptActivity.launchActivity(getActivity());
                        } else {
                            showToast(getString(R.string.permission_checkout));
                        }
                    }
                });
        RxView.clicks(mLayoutDelivery).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        gotoTakeoutActivity();
                    }
                });
        RxView.clicks(mTextUpdateShopOfficial).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        gotoShopOfflineActivity();
                    }
                });
        mHomeAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (null != data && data instanceof HomeBizItem) {
                    HomeBizItem homeBizItem = (HomeBizItem) data;
                    switch (homeBizItem.getItemType()) {
                        case HomeBizItem.ItemType.ITEM_ACCOUNTED: //已结账单
                            if (!BatchPermissionHelper.getPermission(Permission.AccountBills.ACTION_CODE)) {
                                showToast(getString(R.string.permission_account_bill));
                                return;
                            }
                            if (checkOffWorkStatus()) return;
                            MRouter.getInstance().build(OrderCompleteActivity.PATH_ORDER_COMPLETE).navigation(getActivity());
                            break;
                        case HomeBizItem.ItemType.ITEM_TAKE_OUT: //外卖
                            gotoTakeoutActivity();
                            break;
                        case HomeBizItem.ItemType.ITEM_HANG_UP_ORDER: //挂起订单
                            MRouter.getInstance().build(RouterPathConstant.HangUpOrderList.PATH).navigation(getActivity());
                            break;
                        case HomeBizItem.ItemType.ITEM_ELECTRONIC_CASH_DETAIL: //电子收款明细
                            startActivity(new Intent(getActivity(), ElectronicListActivity.class));
                            break;
                        case HomeBizItem.ItemType.ITEM_FOODS_BALANCE: //商品沽清
                            if (checkOffWorkStatus()) return;
                            startActivity(new Intent(getActivity(), MenuBalanceActivity.class));
                            break;
                        case HomeBizItem.ItemType.ITEM_SHOPKEEPER: // 二维火掌柜
                            if (AppUtils.isAppExist(getActivity(), SHOPKEEPER_PACKAGE)) {
                                AppUtils.schemeApp(getActivity(), SHOPKEEPER_PACKAGE);
                            } else {
                                // 暂定没安装掌柜直接跳转至掌柜下载页面
                                AppUtils.openUrl(getActivity(), SHOPKEEPER_DOWNLOAD_PATH);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    private boolean checkOffWorkStatus() {
        if (!UserHelper.getWorkStatus()) {
            //如果是已下班状态，弹框提示
            CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
            return true;
        }
        return false;
    }


    /**
     * 初始化数据源
     */

    private void initBizDatas() {
        if (null != mDatas) {
            mDatas.clear();
        }
        // 创建item
        HomeBizItem accountedItem = new HomeBizItem(getString(R.string.end_pay_order), R.drawable.ic_accounted
                , HomeBizItem.ItemType.ITEM_ACCOUNTED);
        mTakeOutItem = new HomeBizItem(getString(R.string.home_takeout), R.drawable.ic_takeout
                , HomeBizItem.ItemType.ITEM_TAKE_OUT);
        mHangUpItem = new HomeBizItem(getString(R.string.home_hangup_order), R.drawable.ic_hang_up
                , HomeBizItem.ItemType.ITEM_HANG_UP_ORDER);
        HomeBizItem electronicItem = new HomeBizItem(getString(R.string.home_electronic_cash_detail), R.drawable.ic_electronic
                , HomeBizItem.ItemType.ITEM_ELECTRONIC_CASH_DETAIL);
        HomeBizItem foodsBalanceItem = new HomeBizItem(getString(R.string.menu_balance), R.drawable.ic_foods_balance
                , HomeBizItem.ItemType.ITEM_FOODS_BALANCE);
        HomeBizItem shopkeeperItem = new HomeBizItem(getString(R.string.shopkeeper), R.drawable.icon_shopkeeper
                , HomeBizItem.ItemType.ITEM_SHOPKEEPER);

        mDatas.add(accountedItem);
        if (!misMixture) {
            mDatas.add(mTakeOutItem);
        }
        mDatas.add(mHangUpItem);
        mDatas.add(electronicItem);
        mDatas.add(foodsBalanceItem);
        mDatas.add(shopkeeperItem);
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.mPresenter = (HomePresenter) presenter;
    }

    /**
     * 跳转外卖界面
     */
    private void gotoTakeoutActivity() {
        if (BatchPermissionHelper.getPermission(Permission.TakeOut.ACTION_CODE)) {
            MRouter.getInstance().build(RouterPathConstant.TakeoutList.PATH).navigation(getActivity());
        } else {
            showToast(String.format(getResources().getString(R.string.alert_permission_deny)
                    , getString(R.string.home_takeout)));
        }
    }

    @Override
    public void successGetHomeUnreadCount(HomeCount homeCount) {
        //在线外卖
        if (!misMixture) {
            if (homeCount.getTakeOutOrderCount() > 0) {
                if (!BatchPermissionHelper.getPermission(Permission.TakeOut.ACTION_CODE)) {
                    updateLayoutDeliveryVisibility(false);
                } else {
                    updateLayoutDeliveryVisibility(true);
                    SpannableStringUtils.Builder builder = SpannableStringUtils
                            .getBuilder(getActivity(), "");
                    builder.append(String.valueOf(homeCount.getTakeOutOrderCount()))
                            .setForegroundColor(ContextCompat.getColor(getActivity(), R.color.accentColor));
                    builder.append(" ");
                    builder.append(getString(R.string.home_takeout_num))
                            .setForegroundColor(ContextCompat.getColor(getActivity(), R.color.tertiaryTextColor));
                    mTextTakeoutNum.setText(builder.create());
                }
            } else {
                updateLayoutDeliveryVisibility(false);
            }
        } else {
            updateLayoutDeliveryVisibility(false);
        }
        //挂单
        if (null != mHangUpItem && null != mHomeAdapter) {
            mHangUpItem.setUnReadNum(homeCount.getRetainOrderCount());
            mHomeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getShopLimitDayFailure() {
        BaseSpHelper.saveShopOutOfDate(getActivity(), false);
        updateRelativeShopLimitVisibility(false);
    }

    @Override
    public void getShopLimitDaySuccess(ShopLimitVo shopLimitVo) {
        if (shopLimitVo != null) {
            if (Base.INT_TRUE == shopLimitVo.getTrialShop()) {
                updateRelativeShopLimitVisibility(true);
                // +1 包含今天
                int day = (int) (shopLimitVo.getMilliSecondsToExpire() / (TimeUtils.DAY * 1000)) + 1;
                if (day <= 0) {
                    mShopApply.setTextColor(getResources().getColor(R.color.red));
                    mShopApply.setText(getString(R.string.shop_employ_end));
                    BaseSpHelper.saveShopOutOfDate(getActivity(), true);
                } else {
                    SpannableStringUtils.Builder builder = SpannableStringUtils.getBuilder(getActivity(), "");
                    builder.append(getString(R.string.shop_employ_0)).setForegroundColor(ContextCompat.getColor(getActivity(), R.color.primaryColor));
                    builder.append(String.format(getString(R.string.shop_employ_1), day)).setForegroundColor(Color.RED);
                    builder.append(getString(R.string.shop_employ_2)).setForegroundColor(ContextCompat.getColor(getActivity(), R.color.primaryColor));
                    mShopApply.setText(builder.create());
                    BaseSpHelper.saveShopOutOfDate(getActivity(), false);
                }
            } else {
                BaseSpHelper.saveShopOutOfDate(getActivity(), false);
                updateRelativeShopLimitVisibility(false);
            }
        } else {
            BaseSpHelper.saveShopOutOfDate(getActivity(), false);
            updateRelativeShopLimitVisibility(false);
        }
    }

    @Override
    public void failGetHomeUnreadCount(String errorMsg) {
    }


    @Override
    public void successGetWorkMode(boolean isMixture) {
        if (isMixture != misMixture) {
            misMixture = isMixture;
            postShowOnlineTakeoutEvent(!isMixture);
            initBizDatas();
        }
    }

    @Subscribe
    public void onReceiveEvent(BaseEvents.CommonEvent event) {
        if (event == BaseEvents.CommonEvent.EVENT_HOME_REFRESH) {
            mPresenter.getHomeUnreadCount();
        } else if (event == BaseEvents.CommonEvent.EVENT_REFRESH_WORK_MODE) {
            misMixture = (boolean) event.getObject();
            postShowOnlineTakeoutEvent(!misMixture);
            initBizDatas();
        }
    }

    private void gotoShopOfflineActivity() {
        TOpenShopHelper.initOpenShopSDK();
        Intent intent = new Intent(getActivity(), UpgradeOfficialShopActivity.class);
        startActivity(intent);
    }

    //================================================================================
    // event bus
    //================================================================================
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
    // Event Bus
    //================================================================================
    @Subscribe
    public void refreshByUpgradeShop(UpgradeShopSuccessEvent events) {
        mPresenter.getShopLimitDay(UserHelper.getEntityId());
    }

    //================================================================================
    // update view and post event
    //================================================================================
    private void updateLayoutDeliveryVisibility(boolean isVisible) {
        EventBusHelper.post(new RefreshMainGuideEvent(isVisible, RefreshMainGuideEvent.VIEW_TYPE.DELIVERY));
        mLayoutDelivery.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void updateRelativeShopLimitVisibility(boolean isVisible) {
        EventBusHelper.post(new RefreshMainGuideEvent(isVisible, RefreshMainGuideEvent.VIEW_TYPE.SHOP_LIMIT));
        mRelativeShopLimit.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    //================================================================================
    // post event
    //================================================================================
    private void postShowOnlineTakeoutEvent(boolean isVisible) {
        EventBusHelper.post(new RefreshMainGuideEvent(isVisible, RefreshMainGuideEvent.VIEW_TYPE.ONLINE_TAKEOUT_ITEM));
    }
}
