package com.zmsoft.ccd.module.main.order.particulars.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.constant.AnswerEventConstant;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.searchbar.SearchBarHeader;
import com.zmsoft.ccd.module.main.order.particulars.OrderParticularsContract;
import com.zmsoft.ccd.module.main.order.particulars.filter.OrderParticularsFilterModel;
import com.zmsoft.ccd.module.main.order.particulars.recyclerview.OrderParticularsAdapter;
import com.zmsoft.ccd.module.main.order.particulars.recyclerview.OrderParticularsItem;
import com.zmsoft.ccd.widget.recyclerview.FooterViewHolder;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/17 16:58.
 */

public class OrderParticularsFragment extends BaseFragment implements OrderParticularsContract.View, FooterViewHolder.OnLoadMoreListener {

    private interface DOWNLOAD_TYPE {
        int FILTER_NORMAL = 0;      // 筛选选项
        int FILTER_SEARCH = 1;      // 筛选选项+关键字
    }

    @BindView(R.id.search_bar_header_order_particulars)
    SearchBarHeader mSearchBarHeaderOrderParticulars;
    @BindView(R.id.refresh_layout_order_particulars)
    SwipeRefreshLayout mRefreshLayoutOrderParticulars;
    @BindView(R.id.recycler_order_particulars)
    RecyclerView mRecyclerViewOrderParticulars;

    @BindView(R.id.layout_order_particulars_empty)
    RelativeLayout mLayoutOrderParticularsEmpty;
    @BindView(R.id.text_order_particulars_empty)
    TextView mTextOrderParticularsEmpty;

    private OrderParticularsAdapter mOrderParticularsAdapter;
    private OrderParticularsFilterModel mOrderParticularsFilterModel;

    private int mDownloadType;          // 本次网络请求的方式
    private String mSearchOrderCode;

    private OrderParticularsContract.Presenter mPresenter;

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_particulars;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initDataFragment();
        initEmptyView();
        initViewSearchBar();
        initViewSwipeLayout();
        initViewRecycler();

        showLoadingView();
        reloadFilterData();
    }

    @Override
    protected void initListener() {
        initListenerSearchBar();
        initListenerSwipeLayout();
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
        reloadFilterData();
    }

    //================================================================================
    // FooterViewHolder.OnLoadMoreListener
    //================================================================================
    @Override
    public void loadMore(int pageIndex) {
        if (DOWNLOAD_TYPE.FILTER_NORMAL == mDownloadType) {
            downloadFilterData(pageIndex);
        } else if (DOWNLOAD_TYPE.FILTER_SEARCH == mDownloadType) {
            loadSearchData(mSearchOrderCode, pageIndex);
        }
    }

    //================================================================================
    // init data
    //================================================================================
    private void initDataFragment() {
        mOrderParticularsFilterModel = new OrderParticularsFilterModel();
        mDownloadType = DOWNLOAD_TYPE.FILTER_NORMAL;
        mSearchOrderCode = "";
    }

    //================================================================================
    // init view
    //================================================================================
    private void initEmptyView() {
        showContentView();
        setEmptyViewVisible(false);
    }

    private void initViewSearchBar() {
        mSearchBarHeaderOrderParticulars.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        mSearchBarHeaderOrderParticulars.setHint(getString(R.string.order_particulars_search_hit));
    }

    private void initViewSwipeLayout() {
        mRefreshLayoutOrderParticulars.setColorSchemeResources(R.color.accentColor, R.color.accentColor, R.color.accentColor, R.color.accentColor);
    }

    //================================================================================
    // init listener
    //================================================================================
    private void initListenerSearchBar() {
        mSearchBarHeaderOrderParticulars.setOnSearchListener(new SearchBarHeader.OnSearchListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent, String key) {
                String inputText = textView.getText().toString();
                if (StringUtils.isEmpty(inputText)) {
                    showToast(getString(R.string.order_particulars_search_hit));
                    return false;
                }
                AnswerEventLogger.log(AnswerEventConstant.Order.ANSWER_EVENT_NAME_SEARCH);
                showLoadingView();
                reloadSearchData(inputText);
                return false;
            }

            @Override
            public void afterTextChanged(String key) {
            }

            @Override
            public void clear() {
                reloadFilterData();
            }
        });
    }

    private void initListenerSwipeLayout() {
        mRefreshLayoutOrderParticulars.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadFilterData();
            }
        });
    }

    private void initViewRecycler() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewOrderParticulars.setLayoutManager(layoutManager);
        mOrderParticularsAdapter = new OrderParticularsAdapter(mRecyclerViewOrderParticulars, this);
        mRecyclerViewOrderParticulars.setAdapter(mOrderParticularsAdapter);
    }

    private void initListenerRecyclerView() {
        mOrderParticularsAdapter.setOnItemClickListener(new OrderParticularsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OrderParticularsItem orderParticularsItem) {
                gotoOrderDetailActivity(orderParticularsItem.getOrderId());
            }
        });
    }

    //================================================================================
    // goto the other activity
    //================================================================================
    private void gotoOrderDetailActivity(String orderId) {
        MRouter.getInstance().build(RouterPathConstant.OrderDetail.PATH)
                .putInt(RouterPathConstant.OrderDetail.EXTRA_FROM, RouterPathConstant.OrderDetail.EXTRA_FROM_ORDER_LIST)
                .putString(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID, orderId)
                .navigation(getActivity());
    }

    //================================================================================
    // load data
    //================================================================================
    // 在筛选条件下，搜索关键字
    private void reloadSearchData(String orderCode) {
        mDownloadType = DOWNLOAD_TYPE.FILTER_SEARCH;
        mSearchOrderCode = orderCode;
        mOrderParticularsAdapter.setHaveMoreData(false);   // 后面根据本次加载的条数再设置
        mOrderParticularsAdapter.resetPageIndex();
        loadSearchData(orderCode, OrderParticularsAdapter.PAGE_INDEX_INITIAL);
    }

    private void loadSearchData(String orderCode, int pageIndex) {
        loadNetWorkData(orderCode, pageIndex);
    }

    // 重新加载筛选项的内容，从第一页开始
    private void reloadFilterData() {
        mDownloadType = DOWNLOAD_TYPE.FILTER_NORMAL;
        mSearchBarHeaderOrderParticulars.clearEditTextContent();
        mOrderParticularsAdapter.setHaveMoreData(false);   // 后面根据本次加载的条数再设置
        mOrderParticularsAdapter.resetPageIndex();
        downloadFilterData(OrderParticularsAdapter.PAGE_INDEX_INITIAL);
    }

    private void downloadFilterData(int pageIndex){
        loadNetWorkData(null, pageIndex);
    }

    /**
     * @param orderCode null：非搜索
     * @param pageIndex
     */
    private void loadNetWorkData(String orderCode, int pageIndex) {
        mPresenter.loadOrderParticularsData(orderCode, mOrderParticularsFilterModel.getSourceCode(), mOrderParticularsFilterModel.getCashierCode(), mOrderParticularsFilterModel.getDateCode(), pageIndex);
    }


    //================================================================================
    // OrderParticularsContract.View
    //================================================================================
    @Override
    public void setPresenter(OrderParticularsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadOrderFindDataSuccess(List<OrderParticularsItem> orderParticularsItemList, boolean isFirstPage) {
        updateEmptyText();
        if (isFirstPage) {
            setEmptyViewVisible(null == orderParticularsItemList || 0 == orderParticularsItemList.size());
        }
        mOrderParticularsAdapter.setHaveMoreData(null != orderParticularsItemList && (OrderParticularsAdapter.PAGE_SIZE <= orderParticularsItemList.size()));
        mOrderParticularsAdapter.increasePageIndex();
        mOrderParticularsAdapter.updateDataAndNotify(orderParticularsItemList, isFirstPage);

        setContentViewVisible(true);
        super.showContentView();
        mRefreshLayoutOrderParticulars.setRefreshing(false);
    }

    @Override
    public void loadOrderFindDataError(String errorMessage) {
        setEmptyViewVisible(false);
        setContentViewVisible(false);
        toastMsg(errorMessage);
        showErrorView(errorMessage);
        mRefreshLayoutOrderParticulars.setRefreshing(false);
    }

    //================================================================================
    // update view
    //================================================================================
    public void updateOrderParticularsFilterModel(OrderParticularsFilterModel orderParticularsFilterModel) {
        mOrderParticularsFilterModel.update(orderParticularsFilterModel);
        showLoadingView();
        reloadFilterData();
    }

    private void setContentViewVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mSearchBarHeaderOrderParticulars.setVisibility(visibility);
        mRefreshLayoutOrderParticulars.setVisibility(visibility);
    }

    private void setEmptyViewVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mLayoutOrderParticularsEmpty.setVisibility(visibility);
    }

    private void updateEmptyText() {
        if (DOWNLOAD_TYPE.FILTER_NORMAL == mDownloadType) {
            mTextOrderParticularsEmpty.setText(getString(R.string.order_particulars_empty));
        } else {
            mTextOrderParticularsEmpty.setText(getString(R.string.order_particulars_search_none));
        }
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

    @Subscribe
    public void refreshByPush(RouterBaseEvent event) {
        if (event != null && event == RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST) {
            reloadFilterData();
        }
    }
}
