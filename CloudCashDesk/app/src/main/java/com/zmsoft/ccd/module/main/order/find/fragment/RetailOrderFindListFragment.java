package com.zmsoft.ccd.module.main.order.find.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.FocusOrderRequest;
import com.zmsoft.ccd.lib.bean.order.Order;
import com.zmsoft.ccd.lib.bean.order.OrderConstants;
import com.zmsoft.ccd.lib.bean.order.OrderListResult;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.widget.searchbar.RetailSearchBarHeader;
import com.zmsoft.ccd.module.main.order.find.RetailOrderFindContract;
import com.zmsoft.ccd.module.main.order.find.RetailOrderFindPresenter;
import com.zmsoft.ccd.module.main.order.find.recyclerview.RetailOrderAdapter;
import com.zmsoft.ccd.shop.IndustryTypeUtils;
import com.zmsoft.ccd.shop.bean.IndustryType;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huaixi on 2017/10/26.
 */

public class RetailOrderFindListFragment extends BaseListFragment implements RetailOrderFindContract.View, BaseListAdapter.AdapterClick {

    @BindView(R.id.header_search_order_code)
    RetailSearchBarHeader mHeaderSearchOrderCode;

    RetailOrderFindPresenter mPresenter;
    private String mKeyword; // 订单号
    private boolean mIsSearch = false;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        initSearchBar();

        initItemListener();
    }

    private void initItemListener() {
        getAdapter().setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (data instanceof Order) {
                    Order order = (Order) data;
                    gotoOrderDetailActivity(order.getOrderId());
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    protected void loadListData() {
        getOrderList(mKeyword);
    }

    @Override
    protected BaseListAdapter createAdapter() {
        return new RetailOrderAdapter(getActivity(), null, this, R.layout.layout_empty_find_order);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        disableAutoRefresh();
        showLoadingView();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        getOrderList(mKeyword);
    }

    private void initSearchBar() {
        mHeaderSearchOrderCode.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        mHeaderSearchOrderCode.setHint(getString(R.string.retail_order_list_search_hit));
        mHeaderSearchOrderCode.setOnSearchListener(new RetailSearchBarHeader.OnSearchListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent, String key) {
                if (StringUtils.isEmpty(mKeyword)) {
                    showToast(getString(R.string.retail_order_list_search_hit));
                    return false;
                } else {
                    String reg = "^\\d+$";
                    if (!mKeyword.matches(reg)) {
                        return false;
                    }
                }
                mIsSearch = true;
                cleanAll();
                startRefresh();
                return false;
            }

            @Override
            public void afterTextChanged(String key) {
                mKeyword = key;
            }

            @Override
            public void clear() {
                mKeyword = "";
                mIsSearch = false;
                cleanAll();
                startRefresh();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_retail_order_find_list;
    }

    @Override
    public void setPresenter(RetailOrderFindContract.Presenter presenter) {
        this.mPresenter = (RetailOrderFindPresenter) presenter;
    }

    @Override
    public void loadOrderFindDataSuccess(OrderListResult orderListResult) {
        showContentView();

        boolean empty = (orderListResult == null || orderListResult.getBasicOrderVos() == null
                || orderListResult.getBasicOrderVos().isEmpty());
        renderOrderListData(empty ? null : orderListResult.getBasicOrderVos());
    }

    @Override
    public void loadOrderFindDataError(String errorMessage, boolean showNetErrorView) {
        loadListFailed();
        //  如果数据为空，展示stateView
        if (getAdapter() != null && getAdapter().getListCount() == 0) {
            getAdapter().hideEmpty();
            showErrorView(errorMessage);
        } else {
            ToastUtils.showShortToast(getContext(), errorMessage);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        finishRefresh();
    }

    @Override
    protected boolean refreshThreshold() {
        return true;
    }

    @Override
    public void onAdapterClick(int type, View view, Object data) {
    }

    @Override
    public void onAdapterClickEdit(int type, View view, Object data) {

    }

    private void gotoOrderDetailActivity(String orderId) {
        MRouter.getInstance().build(RouterPathConstant.RetailOrderDetail.PATH)
                .putInt(RouterPathConstant.RetailOrderDetail.EXTRA_FROM, RouterPathConstant.RetailOrderDetail.EXTRA_FROM_SEAT_LIST)
                .putString(RouterPathConstant.RetailOrderDetail.EXTRA_ORDER_ID, orderId)
                .navigation(getActivity());
    }

    private void renderOrderListData(List<Order> order) {
        if (order == null || order.isEmpty()) {
            renderListData(null);
        } else {
            renderListData(order, order.size());
        }
    }

    private void getOrderList(String code) {
        FocusOrderRequest focusOrderRequest = new FocusOrderRequest();
        focusOrderRequest.setOrderCategory(OrderConstants.OrderType.ORDER_TYPE_BY_RETAIL);
        focusOrderRequest.setEntityId(UserHelper.getEntityId());
        focusOrderRequest.setOpUserId(UserHelper.getUserId());
        focusOrderRequest.setOpUserName(UserHelper.getUserName());
        focusOrderRequest.setCode(code);
        focusOrderRequest.setCheckout(false);
        focusOrderRequest.setPageIndex(getPageIndex());
        focusOrderRequest.setPageSize(getPageCount());
        focusOrderRequest.setIndustryCode((int) IndustryTypeUtils.getIndustryType(IndustryType.RETAIL));
        mPresenter.loadOrderSource(focusOrderRequest);
    }

    public void refreshByCheckShop() {
        startRefresh();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshOrderList(RouterBaseEvent event) {
        if (null == event) return;
        if (event == RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST) {
            startRefresh(true, false);
        }
    }

    @Subscribe
    public void refreshByCheckShop(BaseEvents events) {
        if (null == events) return;
        //  切店通知刷新
        if (events == BaseEvents.CommonEvent.EVENT_CHECK_SHOP) {
            startRefresh(true, false);
        }
    }
}
