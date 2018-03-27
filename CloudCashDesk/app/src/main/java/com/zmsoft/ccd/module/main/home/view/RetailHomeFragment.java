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
import com.zmsoft.ccd.data.source.home.dagger.DaggerHomeSourceComponent;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.helper.CommonHelper;
import com.zmsoft.ccd.helper.TOpenShopHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerAdapter;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
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
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.softkeyboard.GridItemDividerDecoration;
import com.zmsoft.ccd.module.electronic.ElectronicListActivity;
import com.zmsoft.ccd.module.main.home.adapter.HomeAdapter;
import com.zmsoft.ccd.module.main.home.adapter.items.HomeBizItem;
import com.zmsoft.ccd.module.main.home.dagger.DaggerRetailHomeComponent;
import com.zmsoft.ccd.module.main.home.dagger.RetailHomePresenterModule;
import com.zmsoft.ccd.module.main.home.presenter.RetailHomeContract;
import com.zmsoft.ccd.module.main.home.presenter.RetailHomePresenter;
import com.zmsoft.ccd.module.main.order.afterendpay.RetailAfterEndPayActivity;
import com.zmsoft.ccd.module.shortcutreceipt.RetailShortCutReceiptActivity;

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
 * @create 2017/8/1.
 */

public class RetailHomeFragment extends BaseFragment implements RetailHomeContract.View {

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

    //首页头部按钮
    private final static String ANSWER_EVENT_TYPE_HEADER = "home_page_header";
    //首页中间内容按钮
    private final static String ANSWER_EVENT_TYPE_CONTENT = "home_page_content";
    //快速开单
    private final static String ANSWER_EVENT_NAME_ORDER = "home_page_open_order";
    //快速收款
    private final static String ANSWER_EVENT_NAME_QUICK_PAY = "home_page_quick_pay";
    //已结账单
    private final static String ANSWER_EVENT_NAME_ACCOUNTED = "home_item_accounted";
    //商品估清
    //private final static String ANSWER_EVENT_NAME_FOODS_BALANCE = "home_item_balance";


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
    RetailHomePresenter mPresenter;

    public static RetailHomeFragment newInstance() {
        Bundle args = new Bundle();
        RetailHomeFragment fragment = new RetailHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_retail_home_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        DaggerRetailHomeComponent.builder()
                .homeSourceComponent(DaggerHomeSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .retailHomePresenterModule(new RetailHomePresenterModule(this))
                .build()
                .inject(this);

        mDatas = new ArrayList<>();
        misMixture = UserHelper.getWorkModeIsMixture();
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
                        AnswerEventLogger.log(ANSWER_EVENT_NAME_ORDER, ANSWER_EVENT_TYPE_HEADER);
                        //if (checkOffWorkStatus()) return;
                        if (BatchPermissionHelper.getPermission(Permission.OrderBegin.ACTION_CODE)) {
                            OrderParam param = new OrderParam();
                            param.setSeatCode(RetailOrderHelper.getDefaultRetailSeatCode());
                            MRouter.getInstance().build(RouterPathConstant.RetailMenuList.PATH)
                                    .putSerializable(RouterPathConstant.RetailMenuList.EXTRA_CREATE_ORDER_PARAM, param)
                                    .navigation(getActivity());
                        } else {
                            showToast(getString(R.string.retail_permission_order_begin));
                        }
                    }
                });
        RxView.clicks(mTextShortcutReceipt).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        AnswerEventLogger.log(ANSWER_EVENT_NAME_QUICK_PAY, ANSWER_EVENT_TYPE_HEADER);
                        //if (checkOffWorkStatus()) return;
                        if (BatchPermissionHelper.getPermission(Permission.OrderBegin.ACTION_CODE)) {
                            RetailShortCutReceiptActivity.launchActivity(getActivity());
                        } else {
                            showToast(getString(R.string.retail_permission_order_begin));
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
                            //if (checkOffWorkStatus()) return;
                            AnswerEventLogger.log(ANSWER_EVENT_NAME_ACCOUNTED, ANSWER_EVENT_TYPE_CONTENT);
                            if (BatchPermissionHelper.getPermission(Permission.AccountBills.ACTION_CODE)) {
                                gotoAfterEndPayActivity();
                            } else {
                                showToast(getString(R.string.retail_permission_billaccount));
                            }
                            break;
                        case HomeBizItem.ItemType.ITEM_TAKE_OUT: //外卖
                            gotoTakeoutActivity();
                            break;
                        case HomeBizItem.ItemType.ITEM_HANG_UP_ORDER: //挂起订单
                            MRouter.getInstance().build(RouterPathConstant.RetailHangUpOrderList.PATH).navigation(getActivity());
                            break;
                        case HomeBizItem.ItemType.ITEM_ELECTRONIC_CASH_DETAIL: //电子收款明细
                            startActivity(new Intent(getActivity(), ElectronicListActivity.class));
                            break;
                        case HomeBizItem.ItemType.ITEM_SHOPKEEPER: // 二维火掌柜
                            if (AppUtils.isAppExist(getActivity(), SHOPKEEPER_PACKAGE)) {
                                AppUtils.schemeApp(getActivity(), SHOPKEEPER_PACKAGE);
                            } else {
                                getDialogUtil().showDialog(R.string.prompt, R.string.shopkeeper_install_prompt
                                        , R.string.at_once_install
                                        , R.string.cancel
                                        , true, new SingleButtonCallback() {
                                            @Override
                                            public void onClick(DialogUtilAction which) {
                                                if (which == DialogUtilAction.POSITIVE) {
                                                    AppUtils.openUrl(getActivity(), SHOPKEEPER_DOWNLOAD_PATH);
                                                } else if (which == DialogUtilAction.NEGATIVE) {
                                                    getDialogUtil().dismissDialog();
                                                }
                                            }
                                        });
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
        mTakeOutItem = new HomeBizItem(getString(R.string.retail_home_takeout), R.drawable.ic_takeout
                , HomeBizItem.ItemType.ITEM_TAKE_OUT);
        mHangUpItem = new HomeBizItem(getString(R.string.home_hangup_order), R.drawable.ic_hang_up
                , HomeBizItem.ItemType.ITEM_HANG_UP_ORDER);
        HomeBizItem electronicItem = new HomeBizItem(getString(R.string.home_electronic_cash_detail), R.drawable.ic_electronic
                , HomeBizItem.ItemType.ITEM_ELECTRONIC_CASH_DETAIL);
        HomeBizItem shopkeeperItem = new HomeBizItem(getString(R.string.shopkeeper), R.drawable.icon_shopkeeper
                , HomeBizItem.ItemType.ITEM_SHOPKEEPER);

        mDatas.add(accountedItem);
        if (!misMixture) {
            mDatas.add(mTakeOutItem);
        }
        mDatas.add(mHangUpItem);
        mDatas.add(electronicItem);
        mDatas.add(shopkeeperItem);
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(RetailHomeContract.Presenter presenter) {
        this.mPresenter = (RetailHomePresenter) presenter;
    }

    /**
     * 跳转已结账完毕订单界面
     */
    private void gotoAfterEndPayActivity() {
        startActivity(new Intent(getActivity(), RetailAfterEndPayActivity.class));
    }

    /**
     * 跳转外卖界面
     */
    private void gotoTakeoutActivity() {
        if (BatchPermissionHelper.getPermission(Permission.TakeOut.ACTION_CODE)) {
            MRouter.getInstance().build(RouterPathConstant.RetailTakeoutList.PATH).navigation(getActivity());
        } else {
            showToast(String.format(getResources().getString(R.string.alert_permission_deny)
                    , getString(R.string.retail_home_takeout)));
        }
    }

    @Override
    public void successGetHomeUnreadCount(HomeCount homeCount) {
        //在线外卖
        if (!misMixture) {
            if (homeCount.getTakeOutOrderCount() > 0) {
                mLayoutDelivery.setVisibility(View.VISIBLE);
                SpannableStringUtils.Builder builder = SpannableStringUtils
                        .getBuilder(getActivity(), "");
                builder.append(String.valueOf(homeCount.getTakeOutOrderCount()))
                        .setForegroundColor(ContextCompat.getColor(getActivity(), R.color.accentColor));
                builder.append(getString(R.string.retail_home_takeout_num))
                        .setForegroundColor(ContextCompat.getColor(getActivity(), R.color.tertiaryTextColor));
                mTextTakeoutNum.setText(builder.create());
            } else {
                mLayoutDelivery.setVisibility(View.GONE);
            }
        } else {
            mLayoutDelivery.setVisibility(View.GONE);
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
        mRelativeShopLimit.setVisibility(View.GONE);
    }

    @Override
    public void getShopLimitDaySuccess(ShopLimitVo shopLimitVo) {
        if (shopLimitVo != null) {
            if (Base.INT_TRUE == shopLimitVo.getTrialShop()) {
                mRelativeShopLimit.setVisibility(View.VISIBLE);
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
                mRelativeShopLimit.setVisibility(View.GONE);
            }
        } else {
            BaseSpHelper.saveShopOutOfDate(getActivity(), false);
            mRelativeShopLimit.setVisibility(View.GONE);
        }
    }

    @Override
    public void failGetHomeUnreadCount(String errorMsg) {
    }


    @Override
    public void successGetWorkMode(boolean isMixture) {
        if (isMixture != misMixture) {
            misMixture = isMixture;
            initBizDatas();
        }
    }

    @Subscribe
    public void onReceiveEvent(BaseEvents.CommonEvent event) {
        if (event == BaseEvents.CommonEvent.EVENT_HOME_REFRESH) {
            mPresenter.getHomeUnreadCount();
        } else if (event == BaseEvents.CommonEvent.EVENT_REFRESH_WORK_MODE) {
            misMixture = (boolean) event.getObject();
            initBizDatas();
        } else if (event == BaseEvents.CommonEvent.EVENT_CHECK_SHOP) {
            getWorkMode();
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
    // Event
    //================================================================================
    @Subscribe
    public void refreshByCheckShop(BaseEvents events) {
        //  切店通知刷新
        if (events == BaseEvents.CommonEvent.EVENT_CHECK_SHOP) {
            mPresenter.getShopLimitDay(UserHelper.getEntityId());
        }
    }

    @Subscribe
    public void refreshByUpgradeShop(UpgradeShopSuccessEvent events) {
        mPresenter.getShopLimitDay(UserHelper.getEntityId());
    }

}
