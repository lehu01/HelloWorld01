package com.zmsoft.ccd.module.main.order.search.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.dfire.sdk.util.StringUtil;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.event.message.NotifyDataChangeEvent;
import com.zmsoft.ccd.lib.base.constant.AnswerEventConstant;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.searchbar.SearchBarHeader;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindAdapter;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindItem;
import com.zmsoft.ccd.module.main.order.search.OrderSeatSearchContract;
import com.zmsoft.ccd.module.main.order.search.OrderSeatSearchPresenter;
import com.zmsoft.ccd.widget.bottomdailog.BottomDialog;
import com.zmsoft.ccd.widget.recyclerview.FooterViewHolder;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/28 16:25.
 */

public class OrderSeatSearchFragment extends BaseFragment implements OrderSeatSearchContract.View, FooterViewHolder.OnLoadMoreListener {

    private static final int RECYCLER_VIEW_SPAN_COUNT = 3;         // 网格图中每行显示3格

    interface SEARCH_TYPE {
        int SEAT = 0;               // 桌位
        int RETAIL_ORDER = 1;       // 单号
    }

    interface DIALOG_ITEM_POSITION {
        int SEAT = 0;               // 桌位
        int RETAIL_ORDER = 1;       // 单号
    }

    @BindView(R.id.text_order_search_type)
    TextView mTextSearchType;
    @BindView(R.id.search_bar_header_order_seat)
    SearchBarHeader mSearchBarHeaderOrderSeat;
    @BindView(R.id.refresh_layout_order_seat_search)
    SwipeRefreshLayout mRefreshLayoutOrderSeatSearch;
    @BindView(R.id.recycler_order_seat)
    RecyclerView mRecyclerOrderSeat;
    @BindView(R.id.text_empty)
    TextView mTextEmpty;

    private BottomDialog mSearchTypeBottomDialog;
    private String[] mDialogTextArray;

    private int mSearchType;                        // 本次搜索的类型
    private String mKeyword;                        // 订单号，或者桌号

    private OrderFindAdapter mOrderFindAdapter;

    private OrderSeatSearchPresenter mOrderSeatSearchPresenter;

    private boolean mPausingFragment = false;
    private boolean mNeedDoSearch = false;        // 进入onResume后，是否需要重新从网上加载


    //================================================================================
    // life cycle
    //================================================================================
    public static OrderSeatSearchFragment newInstance() {
        Bundle args = new Bundle();
        OrderSeatSearchFragment fragment = new OrderSeatSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPausingFragment = false;
        if (mNeedDoSearch) {
            mNeedDoSearch = false;
            doSearch();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPausingFragment = true;
    }
    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_seat_search;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initFragmentData();
        initSearchBar();
        initSwipeLayout();
        initRecyclerView();
    }

    @Override
    protected void initListener() {
        initListenerSwipeLayout();
        initListenerRecyclerView();
    }

    @Override
    public void unBindPresenterFromView() {
        if (mOrderSeatSearchPresenter != null) {
            mOrderSeatSearchPresenter.unsubscribe();
        }
    }

    //================================================================================
    // OrderSeatSearchContract.View
    //================================================================================
    @Override
    public void loadSearchDataSuccess(List<OrderFindItem> orderFindItems) {
        super.showContentView();
        if (null == orderFindItems || orderFindItems.size() == 0) {
            mTextEmpty.setVisibility(View.VISIBLE);
            if (SEARCH_TYPE.SEAT == mSearchType) {
                mTextEmpty.setText(getString(R.string.order_seat_search_empty_seat));
            } else if (SEARCH_TYPE.RETAIL_ORDER == mSearchType) {
                mTextEmpty.setText(getString(R.string.order_seat_search_empty_order));
            }
        } else {
            mTextEmpty.setVisibility(View.GONE);
        }
        mOrderFindAdapter.updateDataAndNotify(orderFindItems);
        mRefreshLayoutOrderSeatSearch.setRefreshing(false);
    }

    @Override
    public void loadSearchDataError(String errorMessage, boolean showNetErrorView) {
        showErrorView(errorMessage);
        toastMsg(errorMessage);
        mRefreshLayoutOrderSeatSearch.setRefreshing(false);
    }

    @Override
    public void setPresenter(OrderSeatSearchContract.Presenter presenter) {
        mOrderSeatSearchPresenter = (OrderSeatSearchPresenter) presenter;
    }

    //================================================================================
    // OrderFindAdapter.OnLoadMoreListener
    //================================================================================
    @Override
    public void loadMore(int pageIndex) {

    }
    //================================================================================
    // init
    //================================================================================
    private void initFragmentData() {
        mDialogTextArray = getResources().getStringArray(R.array.order_search_type);
        mSearchType = SEARCH_TYPE.SEAT;
        mKeyword = "";
    }

    private void initSearchBar() {
        updateSearBarHeader(mSearchType);
        mSearchBarHeaderOrderSeat.setOnSearchListener(new SearchBarHeader.OnSearchListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent, String key) {
                if (StringUtils.isEmpty(mKeyword)) {
                    if (SEARCH_TYPE.SEAT == mSearchType) {
                        showToast(getString(R.string.please_input_seat_code));
                    } else if (SEARCH_TYPE.RETAIL_ORDER == mSearchType) {
                        showToast(getString(R.string.order_seat_search_order_hit));
                    }
                    return false;
                }
                AnswerEventLogger.log(AnswerEventConstant.Order.ANSWER_EVENT_NAME_SEARCH);
                showLoadingView();
                if (SEARCH_TYPE.SEAT == mSearchType) {
                    mOrderSeatSearchPresenter.seatSearch(mKeyword);
                } else if (SEARCH_TYPE.RETAIL_ORDER == mSearchType) {
                    mOrderSeatSearchPresenter.orderSearch(mKeyword);
                }
                return false;
            }

            @Override
            public void afterTextChanged(String key) {
                mKeyword = key;
            }

            @Override
            public void clear() {
                mKeyword = "";
            }
        });
    }

    private void initSwipeLayout() {
        mRefreshLayoutOrderSeatSearch.setColorSchemeResources(R.color.accentColor, R.color.accentColor, R.color.accentColor, R.color.accentColor);
    }

    private void initRecyclerView() {
        // 初始不需要提示，所以不能使用XRecyclerView.setEmptyView
        mTextEmpty.setVisibility(View.GONE);

        final GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), RECYCLER_VIEW_SPAN_COUNT);
        mRecyclerOrderSeat.setLayoutManager(layoutManager);
        mOrderFindAdapter = new OrderFindAdapter(this.getContext(), this);
        mRecyclerOrderSeat.setAdapter(mOrderFindAdapter);
    }

    //================================================================================
    // init listener
    //================================================================================
    private void initListenerSwipeLayout() {
        mRefreshLayoutOrderSeatSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doSearch();
            }
        });
    }

    private void doSearch() {
        if (StringUtils.isEmpty(mKeyword)) {
            mRefreshLayoutOrderSeatSearch.setRefreshing(false);
            return;
        }
        if (SEARCH_TYPE.SEAT == mSearchType) {
            mOrderSeatSearchPresenter.seatSearch(mKeyword);
        } else if (SEARCH_TYPE.RETAIL_ORDER == mSearchType) {
            mOrderSeatSearchPresenter.orderSearch(mKeyword);
        }
    }

    private void initListenerRecyclerView() {
        mOrderFindAdapter.setOnItemClickListener(new OrderFindAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OrderFindItem orderFindItem) {
                if (orderFindItem == null) {
                    return;
                }
                String orderId = orderFindItem.getOrderId();
                if (!StringUtil.isEmpty(orderId)) {
                    gotoOrderDetailActivity(orderId);
                } else {
                    gotoCreateOrUpdateOrderActivity(orderFindItem.getName(), orderFindItem.getSeatCode());
                }
            }
        });
    }

    //================================================================================
    // update view
    //================================================================================
    private void updateSearBarHeader(int searchType) {
        mSearchBarHeaderOrderSeat.clearEditTextContent();
        if (SEARCH_TYPE.SEAT == searchType) {
            mSearchBarHeaderOrderSeat.setInputType(EditorInfo.TYPE_CLASS_TEXT);
            mSearchBarHeaderOrderSeat.setHint(getString(R.string.please_input_seat_code));
        } else if (SEARCH_TYPE.RETAIL_ORDER == searchType) {
            mSearchBarHeaderOrderSeat.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            mSearchBarHeaderOrderSeat.setHint(getString(R.string.order_seat_search_order_hit));
        }
    }

    private void updateTextSearchType(int searchType) {
        String searchTypeText = "";
        if (DIALOG_ITEM_POSITION.SEAT == searchType) {
            if (DIALOG_ITEM_POSITION.SEAT < mDialogTextArray.length) {
                searchTypeText = mDialogTextArray[DIALOG_ITEM_POSITION.SEAT];
            }
        } else if (DIALOG_ITEM_POSITION.RETAIL_ORDER == searchType) {
            if (DIALOG_ITEM_POSITION.RETAIL_ORDER < mDialogTextArray.length) {
                searchTypeText = mDialogTextArray[DIALOG_ITEM_POSITION.RETAIL_ORDER];
            }
        }
        mTextSearchType.setText(searchTypeText);
    }

    //================================================================================
    // onClick
    //================================================================================
    @OnClick(R.id.text_order_search_type)
    public void onClickSearchType() {
        if (null == mSearchTypeBottomDialog) {
            mSearchTypeBottomDialog = new BottomDialog(getActivity());
            // 设置内容
            mSearchTypeBottomDialog.setItems(mDialogTextArray);
            // 设置点击事件
            mSearchTypeBottomDialog.setItemClickListener(new BottomDialog.PopupWindowItemClickListener(){
                @Override
                public void onItemClick(int position) {
                    updateTextSearchType(position);
                    if (DIALOG_ITEM_POSITION.SEAT == position) {
                        mSearchType = SEARCH_TYPE.SEAT;
                    } else if (DIALOG_ITEM_POSITION.RETAIL_ORDER == position) {
                        mSearchType = SEARCH_TYPE.RETAIL_ORDER;
                    }
                    updateSearBarHeader(mSearchType);

                    mSearchTypeBottomDialog.dismiss();
                }
            });
        }
        if (!mSearchTypeBottomDialog.isShowing()) {
            mSearchTypeBottomDialog.showPopupWindow();
        }
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
            if (mPausingFragment) {
                // 期间可能有多条相同event，汇总成一次刷新
                mNeedDoSearch = true;
            } else {
                doSearch();
            }
        }
    }

    @Subscribe
    public void notifyDataChanged(NotifyDataChangeEvent event) {
        if (event != null) {
            if (mPausingFragment) {
                // 期间可能有多条相同event，汇总成一次刷新
                mNeedDoSearch = true;
            } else {
                doSearch();
            }
        }
    }
}
