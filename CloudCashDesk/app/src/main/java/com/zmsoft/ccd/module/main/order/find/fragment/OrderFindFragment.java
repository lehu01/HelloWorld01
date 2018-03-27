package com.zmsoft.ccd.module.main.order.find.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.dfire.sdk.util.StringUtil;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.business.QuickOpenOrder;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.event.message.NotifyDataChangeEvent;
import com.zmsoft.ccd.helper.CommonHelper;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.RetailOrderHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.module.main.order.find.OrderFindContract;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindAdapter;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindItem;
import com.zmsoft.ccd.module.main.order.find.viewmodel.OrderFindMenuViewModel;
import com.zmsoft.ccd.module.main.order.find.viewmodel.OrderFindViewModel;
import com.zmsoft.ccd.module.main.order.search.OrderSeatSearchActivity;
import com.zmsoft.ccd.module.personal.attention.AddAttenDeskActivity;
import com.zmsoft.ccd.module.scan.findorder.ScanFindOrderActivity;
import com.zmsoft.ccd.widget.recyclerview.FooterViewHolder;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/20 14:28.
 */

public class OrderFindFragment extends BaseFragment implements OrderFindContract.View, FooterViewHolder.OnLoadMoreListener {

    private static final int RECYCLER_VIEW_SPAN_COUNT = 3;              // 网格图中每行显示3格
    private static final int REFRESH_EAT_TIME_INTERVAL = 1;             // 用餐时间刷新间隔,单位分钟
    private static final long PUSH_REFRESH_TIME_INTERVAL = 1000L;       // 推送消息刷新间隔,单位毫秒

    @BindView(R.id.top_bar_order_find)
    LinearLayout mLayoutTopBar;
    @BindView(R.id.tab_layout_order_find)
    TabLayout mTabLayoutOrderFind;
    @BindView(R.id.image_button_add_attention)
    ImageButton mImageButtonAddAttention;
    @BindView(R.id.refresh_layout_order_find)
    SwipeRefreshLayout mRefreshLayoutOrderFind;
    @BindView(R.id.recycler_desk)
    RecyclerView mRecyclerDesk;
    @BindView(R.id.text_create_order)
    TextView mTextCreateOrder;

    private OrderFindContract.Presenter mPresenter;

    private OrderFindAdapter mOrderFindAdapter;

    private Subscription mUpdateEatTimeInterval;
    private boolean mIsFirstResume = true;
    private boolean mPausingFragment = false;
    private boolean mNeedReloadSelectedAreaData = false;        // 进入onResume后，是否需要重新从网上加载指定区域的订单信息
    private boolean mNeedShowRetailArea = false;                // 进入onResume后，是否需要切换到零售单栏，并重新加载（已经判断当前关注过零售单）
    private long mLastPushRefreshTimeStamp = 0L;

    //================================================================================
    // life cycle
    //================================================================================
    public static OrderFindFragment newInstance() {
        Bundle args = new Bundle();
        OrderFindFragment fragment = new OrderFindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPausingFragment = false;
        if (mIsFirstResume) {
            mIsFirstResume = false;
            showLoadingView();
            loadWatchedArea();
        } else {
            if (mNeedShowRetailArea) {
                mNeedShowRetailArea = false;
                realShowRetailArea();
                // 切换到零售单会触发一次刷新，所以不需要再次刷新区域
                mNeedReloadSelectedAreaData = false;
            } else if (mNeedReloadSelectedAreaData) {   // 在后台时，订单状态有所变动
                mNeedReloadSelectedAreaData = false;
                fillSelectedAreaAndDownload();
            } else {
                // 立即更新一次就餐时间的数据
                updateEatTimeAndRefresh();
            }
        }
        resumeUpdateEatTime();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPausingFragment = true;
        pauseUpdateEatTime();
    }

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_find;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initFragmentView();
    }

    @Override
    protected void initListener() {
        initListenerTabLayout();
        initListenerRecyclerView();
    }

    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadWatchedArea();
    }

    @Override
    protected void clickEmptyView() {
        super.clickEmptyView();
        gotoAddAttentionDeskActivity();
    }

    //================================================================================
    // OrderFindContract.View
    //================================================================================
    @Override
    public void setPresenter(OrderFindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void performShowRetailAreaChecked() {
        if (null == mTabLayoutOrderFind || 0 == mTabLayoutOrderFind.getTabCount()) {
            return;
        }
        if (mPausingFragment) {
            // 设置标签，在onResume时触发
            mNeedShowRetailArea = true;
        } else {
            realShowRetailArea();
        }
    }

    @Override
    public void loadWatchAreaSuccess(OrderFindViewModel orderFindViewModel) {
        updateFragmentView(orderFindViewModel);
    }

    @Override
    public void loadOrderFindDataSuccess(OrderFindViewModel orderFindViewModel) {
        mRefreshLayoutOrderFind.setRefreshing(false);
        mOrderFindAdapter.setHaveMoreData(orderFindViewModel.isHaveMoreData());
        mOrderFindAdapter.increasePageIndex();
        List<OrderFindItem> orderFindItemList = mPresenter.getSelectedData();
        mOrderFindAdapter.updateDataAndNotify(orderFindItemList);
    }

    @Override
    public void loadOrderFindDataError(String errorMessage, boolean showNetErrorView) {
        mRefreshLayoutOrderFind.setRefreshing(false);
        setContentViewVisible(false);
        showErrorView(errorMessage);
        toastMsg(errorMessage);
    }

    //================================================================================
    // OrderFindAdapter.OnLoadMoreListener
    //================================================================================
    @Override
    public void loadMore(int pageIndex) {
        mPresenter.loadSelectedAreaData(pageIndex);
    }

    //================================================================================
    // init
    //================================================================================
    private void initFragmentView() {
        showContentView();
        if (null != super.mStateView) {
            super.mStateView.setEmptyResource(R.layout.layout_no_attention);
        }

        if (mRefreshLayoutOrderFind != null) {
            mRefreshLayoutOrderFind.setColorSchemeResources(R.color.accentColor, R.color.accentColor, R.color.accentColor, R.color.accentColor);
            mRefreshLayoutOrderFind.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fillSelectedAreaAndDownload();
                }
            });
        }

        final GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), RECYCLER_VIEW_SPAN_COUNT);
        mRecyclerDesk.setLayoutManager(layoutManager);
        mOrderFindAdapter = new OrderFindAdapter(this.getContext(), this);
        mRecyclerDesk.setAdapter(mOrderFindAdapter);
    }

    //================================================================================
    // loading
    //================================================================================
    // 会触发mTabLayoutOrderFind和mRecyclerDesk的重新加载
    public void loadWatchedArea() {
        mPresenter.loadWatchedArea();
    }

    // 更新tabLayout，会自动触发刷新recycler view
    private void updateTabLayout(OrderFindViewModel orderFindViewModel) {
        List<OrderFindMenuViewModel> orderFindMenuViewModelList = orderFindViewModel.getOrderFindMenuViewModelList();
        String selectMenuText = orderFindViewModel.getSelectedAreaName();

        // addTab会自动触发select，所以暂时移除
        mTabLayoutOrderFind.clearOnTabSelectedListeners();

        int previousTabLayoutCount = mTabLayoutOrderFind.getTabCount();
        int presentTabLayoutCount = orderFindMenuViewModelList.size();
        int minCount = previousTabLayoutCount < presentTabLayoutCount ? previousTabLayoutCount : presentTabLayoutCount;
        // 更新原有个数的Tab
        for (int i = 0; i < minCount; i++) {
            TabLayout.Tab tab = mTabLayoutOrderFind.getTabAt(i);
            if (tab != null) {
                OrderFindMenuViewModel orderFindMenuViewModel = orderFindMenuViewModelList.get(i);
                String menuText = orderFindMenuViewModel.getAreaName();
                tab.setText(menuText);
            }
        }
        // 本次刷新后,Tab数量变多
        if (presentTabLayoutCount > minCount) {
            for (int i = minCount; i < presentTabLayoutCount; i++) {
                OrderFindMenuViewModel orderFindMenuViewModel = orderFindMenuViewModelList.get(i);
                String menuText = orderFindMenuViewModel.getAreaName();
                mTabLayoutOrderFind.addTab(mTabLayoutOrderFind.newTab().setText(menuText));
            }
        } else if (previousTabLayoutCount > minCount) { // 本次刷新后,Tab数量变少
            for (int i = previousTabLayoutCount - 1; i >= minCount; i--) {
                mTabLayoutOrderFind.removeTabAt(i);
            }
        }

        initListenerTabLayout();
        // 设置本次选中的tab
        if (presentTabLayoutCount == 0) {
            return;
        }
        int selectPosition = 0;
        for (int i = 0; i < orderFindMenuViewModelList.size(); i++) {
            OrderFindMenuViewModel orderFindMenuViewModel = orderFindMenuViewModelList.get(i);
            if (orderFindMenuViewModel.getAreaName().equals(selectMenuText)) {
                selectPosition = i;
                break;
            }
        }
        TabLayout.Tab selectedTab = mTabLayoutOrderFind.getTabAt(selectPosition);
        if (selectedTab != null) {
            selectedTab.select();
        }
    }

    private void tabLayoutTabSelected(TabLayout.Tab tab) {
        mPresenter.onTabSelected((String) tab.getText());
        fillSelectedAreaAndDownload();
    }

    //================================================================================
    // update data and refresh
    //================================================================================
    // 填充选中区域原有数据，并且重新加载
    private void fillSelectedAreaAndDownload() {
        // 先显示原有数据
        mOrderFindAdapter.setHaveMoreData(false);   // 后面根据本次加载的条数再设置
        List<OrderFindItem> orderFindItemList = mPresenter.getSelectedData();
        mOrderFindAdapter.updateDataAndNotify(orderFindItemList);
        // 然后重新加载
        mPresenter.loadSelectedAreaData(OrderFindAdapter.PAGE_INDEX_INITIAL);
        mOrderFindAdapter.resetPageIndex();
    }

    private void updateEatTimeAndRefresh() {
        int timeOutMinutes = BaseSpHelper.getLimitTimeEnd(GlobalVars.context);
        mPresenter.updateEatTime(timeOutMinutes);
        mOrderFindAdapter.updateEatTimeAndNotify(timeOutMinutes);
    }

    //================================================================================
    // update view
    //================================================================================
    private void updateFragmentView(OrderFindViewModel orderFindViewModel) {
        mRefreshLayoutOrderFind.setRefreshing(false);
        boolean isContentViewEmpty = orderFindViewModel.isContentViewEmpty();
        setContentViewVisible(!isContentViewEmpty);
        if (isContentViewEmpty) {
            super.showEmptyView(R.id.button_add);
        } else {
            super.showContentView();
        }
        updateTabLayout(orderFindViewModel);
    }

    private void setContentViewVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mLayoutTopBar.setVisibility(visibility);
        mRefreshLayoutOrderFind.setVisibility(visibility);
        mTextCreateOrder.setVisibility(visibility);
    }

    private void realShowRetailArea() {
        // 已经通过条件检查，说明当前有关注零售单。第一个项即是零售单
        TabLayout.Tab selectedTab = mTabLayoutOrderFind.getTabAt(0);
        if (selectedTab != null) {
            selectedTab.select();
        }
    }

    //================================================================================
    // time interval
    //================================================================================
    private void resumeUpdateEatTime() {
        pauseUpdateEatTime();
        mUpdateEatTimeInterval = Observable.interval(REFRESH_EAT_TIME_INTERVAL, TimeUnit.MINUTES)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        updateEatTimeAndRefresh();
                    }
                });
    }

    private void pauseUpdateEatTime() {
        if (mUpdateEatTimeInterval != null && !mUpdateEatTimeInterval.isUnsubscribed()) {
            mUpdateEatTimeInterval.unsubscribe();
        }
    }

    //================================================================================
    // init listener
    //================================================================================
    private void initListenerTabLayout() {
        mTabLayoutOrderFind.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayoutTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tabLayoutTabSelected(tab);
            }
        });
    }

    private void initListenerRecyclerView() {
        mOrderFindAdapter.setOnItemClickListener(new OrderFindAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OrderFindItem orderFindItem) {
                if (orderFindItem.isEmpty()) {
                    return;
                }
                String orderId = orderFindItem.getOrderId();
                if (!StringUtil.isEmpty(orderId)) {
                    gotoOrderDetailActivity(orderId);
                } else {
                    quickOpenOrder(orderFindItem);
                }
            }
        });
    }

    @OnClick(R.id.image_button_add_attention)
    public void imageButtonAddAttentionClicked() {
        gotoAddAttentionDeskActivity();
    }

    @OnClick(R.id.text_create_order)
    public void createOrder() {
        if (!UserHelper.getWorkStatus()) {
            CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
            return;
        }
        OrderParam param = new OrderParam();
        param.setSeatCode(RetailOrderHelper.getDefaultRetailSeatCode());
        // 是否快速开单开关
        if (BaseSpHelper.isQuickOpenOrder(getActivity())) {
            new QuickOpenOrder().doQuickOpenOrder(getActivity(), param);
            return;
        }

        MRouter.getInstance().build(RouterPathConstant.CreateOrUpdateOrder.PATH)
                .putString(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM, RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE)
                .putSerializable(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM, param)
                .navigation(getActivity());
    }

    private void quickOpenOrder(OrderFindItem orderFindItem) {
        if (BaseSpHelper.isQuickOpenOrder(getActivity())) {
            OrderParam param = new OrderParam();
            param.setSeatName(orderFindItem.getName());
            param.setSeatCode(orderFindItem.getSeatCode());
            param.setOriginNumber(orderFindItem.getAdviseNum());
            param.setMemo("");
            param.setWait(false);
            new QuickOpenOrder().doQuickOpenOrder(getActivity(), param);
            return;
        }
        gotoCreateOrUpdateOrderActivity(orderFindItem.getName(), orderFindItem.getSeatCode());
    }

    //================================================================================
    // goto the other activity
    //================================================================================
    private void gotoOrderDetailActivity(String orderId) {
        MRouter.getInstance().build(RouterPathConstant.OrderDetail.PATH)
                .putInt(RouterPathConstant.OrderDetail.EXTRA_FROM, RouterPathConstant.OrderDetail.EXTRA_FROM_SEAT_LIST)
                .putString(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID, orderId)
                .navigation(getActivity());
    }

    private void gotoCreateOrUpdateOrderActivity(String seatName, String seatCode) {
        OrderParam param = new OrderParam();
        param.setSeatName(seatName);
        param.setSeatCode(seatCode);

        MRouter.getInstance().build(RouterPathConstant.CreateOrUpdateOrder.PATH)
                .putString(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM, RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE)
                .putSerializable(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM, param)
                .navigation(getActivity());
    }

    private void gotoAddAttentionDeskActivity() {
        MRouter.getInstance().build(AddAttenDeskActivity.PATH_ADD_DESK).navigation(OrderFindFragment.this.getActivity());
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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mScanItem = menu.findItem(R.id.save);
        mScanItem.setVisible(true);
        mScanItem.setTitle(R.string.sweep);
        mScanItem.setIcon(R.drawable.icon_menu_scan);
        mScanItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        MenuItem mSearchItem = menu.findItem(R.id.setting);
        mSearchItem.setVisible(true);
        mSearchItem.setTitle(R.string.main_activity_menu_item_search);
        mSearchItem.setIcon(R.drawable.icon_menu_search);
        mSearchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            MRouter.getInstance().build(ScanFindOrderActivity.PATH).navigation(this);
        } else if (item.getItemId() == R.id.setting) {
            MRouter.getInstance().build(OrderSeatSearchActivity.PATH).navigation(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void refreshByPush(RouterBaseEvent event) {
        if (event != null && event == RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST) {
            if (mPausingFragment) {
                // 期间可能有多条相同event，汇总成一次刷新
                mNeedReloadSelectedAreaData = true;
            } else {
                // 当前停留在找单页面时，对同一单进行多次操作，可能会发送多次推送。现在在指定间隔内只刷新一次
                long currentTimeStamp = System.currentTimeMillis();
                if (currentTimeStamp - mLastPushRefreshTimeStamp < PUSH_REFRESH_TIME_INTERVAL) {
                    return;
                }
                mLastPushRefreshTimeStamp = currentTimeStamp;
                fillSelectedAreaAndDownload();
            }
        }
    }

    @Subscribe
    public void notifyDataChanged(NotifyDataChangeEvent event) {
        if (event != null) {
            if (mPausingFragment) {
                // 期间可能有多条相同event，汇总成一次刷新
                mNeedReloadSelectedAreaData = true;
            } else {
                fillSelectedAreaAndDownload();
            }
        }
    }

    @Subscribe
    public void refreshByCheckShop(BaseEvents events) {
        if (events == BaseEvents.CommonEvent.EVENT_ATTENTION_DESK_BIND_SUCCESS) {
            loadWatchedArea();
        }
    }

    @Subscribe
    public void switchOrderList(BaseEvents.CommonEvent event) {
        if (event != null && event == BaseEvents.CommonEvent.EVENT_SWITCH_WATCHED_RETAIL) {
            mPresenter.handleShowRetailArea();
        }
    }

    public void refreshByCheckShop() {
        loadWatchedArea();
    }
}
