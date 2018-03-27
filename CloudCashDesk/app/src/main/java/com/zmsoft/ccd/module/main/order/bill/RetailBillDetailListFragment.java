package com.zmsoft.ccd.module.main.order.bill;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.order.dagger.DaggerOrderSourceComponent;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.event.message.NotifyDataChangeEvent;
import com.zmsoft.ccd.helper.RetailBillDetailHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.BillDetail;
import com.zmsoft.ccd.lib.bean.order.DateBillDetailList;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListRequest;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListResponse;
import com.zmsoft.ccd.lib.bean.order.OrderFrom;
import com.zmsoft.ccd.lib.bean.order.RetailOrderFromRequest;
import com.zmsoft.ccd.lib.bean.order.RetailOrderFromResponse;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.widget.emptylayout.CustomEmptyLayout;
import com.zmsoft.ccd.lib.widget.searchbar.RetailSearchBarHeader;
import com.zmsoft.ccd.module.main.order.bill.adapter.RetailBillDetailAdapter;
import com.zmsoft.ccd.module.main.order.bill.adapter.RetailOrderStatusAdapter;
import com.zmsoft.ccd.module.main.order.bill.dagger.DaggerRetailBillDetailPresenterComponent;
import com.zmsoft.ccd.module.main.order.bill.dagger.RetailBillDetailPresenterModule;
import com.zmsoft.ccd.shop.IndustryTypeUtils;
import com.zmsoft.ccd.shop.bean.IndustryType;
import com.zmsoft.ccd.widget.menu.DropDownMenu;
import com.zmsoft.ccd.widget.menu.DropDownTab;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huaixi on 2017/11/02.
 */

public class RetailBillDetailListFragment extends BaseListFragment implements RetailBillDetailContract.View, BaseListAdapter.AdapterClick {

    @BindView(R.id.tab_drop_down)
    DropDownTab mTabDropDown;
    @BindView(R.id.header_search_order_code)
    RetailSearchBarHeader mHeaderSearchOrderCode;
    @BindView(R.id.linear_order_list_bar)
    LinearLayout mLinearOrderListBar;
    @BindView(R.id.text_order_sate)
    TextView mTextOrderSate;
    @BindView(R.id.layout_empty)
    CustomEmptyLayout layoutEmpty;
    @BindView(R.id.menu_drop_down)
    DropDownMenu mMenuDropDown;

    private final int BillDetailPageCount = 20;
    private final int BillDetailShowAll = 2;

    private RetailOrderStatusAdapter mOrderTypeAdapter;

    /**
     * 筛选组件
     */
    private LinearLayout mLinearOrderStatus;

    List<String> mOrderFromList = new ArrayList<>();
    List<OrderFrom> orderFroms = new ArrayList<OrderFrom>();

    private boolean mIsSearch; // 是否是搜索

    private String lastDate = "";
    private String mKeyword; // 订单号或流水号
    private Integer orderFromType = -1;
    private String orderCode = null;
    private int currentPosition = 0;
    private int ensurePosition = 0;

    @Inject
    RetailBillDetailPresenter mPresenter;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        initDropMenu();
        getRetailOrderFrom();
        initSearchBar();
        initItemClick();
        setPageCount(BillDetailPageCount);
    }

    private void initItemClick() {
        getAdapter().setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (data instanceof BillDetail) {
                    gotoRetailOrderDetailActivity(((BillDetail) data).getOrderId());
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    private void initSource() {
        DaggerRetailBillDetailPresenterComponent.builder()
                .orderSourceComponent(DaggerOrderSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .retailBillDetailPresenterModule(new RetailBillDetailPresenterModule(this)).build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initSource();
        return super.onCreateView(inflater, container, savedInstanceState);
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
                orderCode = mKeyword;
                return false;
            }

            @Override
            public void afterTextChanged(String key) {
                mKeyword = key;
            }

            @Override
            public void clear() {
                mKeyword = "";
                orderCode = null;
                mIsSearch = false;
                cleanAll();
                startRefresh();
            }
        });
    }

    private GridView mGridOrderType;

    private void initDropMenu() {
        View orderView = getActivity().getLayoutInflater().inflate(R.layout.layout_order_select, null);
        mGridOrderType = ButterKnife.findById(orderView, R.id.grid_order_type);
        Button mButtonFilterOk = ButterKnife.findById(orderView, R.id.button_filter_ok);
        Button mButtonFilterCancel = ButterKnife.findById(orderView, R.id.button_filter_cancel);
        mLinearOrderStatus = ButterKnife.findById(orderView, R.id.linear_order_state);
        mButtonFilterOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ensurePosition = currentPosition;
                mMenuDropDown.hide();
                cleanAll();
                startRefresh();
            }
        });
        mButtonFilterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuDropDown.hide();
            }
        });
        mTabDropDown.setTabs(Arrays.asList(getString(R.string.filter)));
        mMenuDropDown.setDropDownMenu(mTabDropDown, Arrays.asList(orderView));
        mMenuDropDown.setOnHideListener(new DropDownMenu.OnHideListener() {
            @Override
            public void onHide() {

            }
        });
        mMenuDropDown.setOnShowListener(new DropDownMenu.OnShowListener() {
            @Override
            public void onShow(int position) {
                if (mOrderTypeAdapter != null)
                    mOrderTypeAdapter.setCheckItem(ensurePosition);
            }
        });
        mLinearOrderStatus.setVisibility(View.GONE);
    }

    private void initDropMenuAdapter(List<String> orderTypeList) {
        /**订单类型**/
        mOrderTypeAdapter = new RetailOrderStatusAdapter(getActivity(), orderTypeList);
        mGridOrderType.setAdapter(mOrderTypeAdapter);
        mGridOrderType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOrderTypeAdapter.setCheckItem(position);
                setOrderType(position);
                currentPosition = position;
            }
        });
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        getBillDetailList(orderCode, orderFromType);
    }

    private void setOrderType(int position) {
        if (orderFroms.size() < position) return;
        orderFromType = (int) orderFroms.get(position).getCode();
    }

    @Override
    public void unBindPresenterFromView() {
        if (this.mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void onAdapterClick(int type, View view, Object data) {
    }

    @Override
    public void onAdapterClickEdit(int type, View view, Object data) {

    }

    @Override
    protected void loadListData() {
        getBillDetailList(orderCode, orderFromType);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_retail_order_list;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        disableAutoRefresh();
        showLoadingView();
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
    protected BaseListAdapter createAdapter() {
        return new RetailBillDetailAdapter(getActivity(), null, this, R.layout.layout_empty_bill_order);
    }

    @Override
    public void setPresenter(RetailBillDetailContract.Presenter presenter) {
        mPresenter = (RetailBillDetailPresenter) presenter;
    }

    @Override
    public void loadRetailOrderFromSuccess(RetailOrderFromResponse retailOrderFromResponse) {
        orderFroms.clear();
        orderFroms.addAll(retailOrderFromResponse.getOrderFroms());
        for (OrderFrom orderFrom : retailOrderFromResponse.getOrderFroms()) {
            mOrderFromList.add(orderFrom.getDesc());
            RetailBillDetailHelper.addRetailOrderFromMap((int)orderFrom.getCode(), orderFrom.getDesc());
        }
        initDropMenuAdapter(mOrderFromList);
    }

    @Override
    public void loadBillDetailListSuccess(GetBillDetailListResponse response) {
        showContentView();
        renderBillDetailData(response.getDateBillDetailList());
    }

    @Override
    public void loadBillDetailListError(String errorMessage) {
        loadListFailed();
        //  如果数据为空，展示stateView
        if (getAdapter() != null && getAdapter().getListCount() == 0) {
            getAdapter().hideEmpty();
            showErrorView(errorMessage);
        } else {
            ToastUtils.showShortToast(getContext(), errorMessage);
        }
    }

    private void renderBillDetailData(List<DateBillDetailList> dateBillDetailLists) {
        if (dateBillDetailLists == null) {
            renderListData(null);
        } else {
            List<Object> objects = new ArrayList<>();
            int size = 0;
            for (DateBillDetailList dateBillDetailList : dateBillDetailLists) {
                if (getPageIndex() == 1) {
                    objects.add(dateBillDetailList.getDate());
                    lastDate = dateBillDetailList.getDate();
                    size++;
                }
                if (!lastDate.equals(dateBillDetailList.getDate())) {
                    objects.add(dateBillDetailList.getDate());
                    lastDate = dateBillDetailList.getDate();
                    size++;
                }
                objects.addAll(dateBillDetailList.getBillDetails());
            }
            renderListData(objects, objects.size() - size);
        }
    }

    @Override
    public void loadDataError(String errorMessage) {
        loadListFailed();
        showErrorView(errorMessage);
    }

    private void getRetailOrderFrom() {
        RetailOrderFromRequest retailOrderFromRequest = new RetailOrderFromRequest();
        retailOrderFromRequest.setEntityId(UserHelper.getEntityId());
        retailOrderFromRequest.setOpUserId(UserHelper.getUserId());
        retailOrderFromRequest.setOpUserName(UserHelper.getUserName());
        mPresenter.getRetailOrderFrom(retailOrderFromRequest);
    }

    private void getBillDetailList(String orderCode, Integer orderFrom) {
        GetBillDetailListRequest request = new GetBillDetailListRequest();
        request.setEntityId(UserHelper.getEntityId());
        request.setOpUserId(UserHelper.getUserId());
        request.setOpUserName(UserHelper.getUserName());
        request.setPageIndex(getPageIndex());
        request.setPageSize(getPageCount());
        request.setIndustryCode((int) IndustryTypeUtils.getIndustryType(IndustryType.RETAIL));
        request.setDate(BillDetailShowAll);
        if (orderCode != null) {
            request.setOrderCode(orderCode);
        }
        if (orderFrom != -1) {
            request.setOrderFrom(orderFrom);
        }
        mPresenter.getBillDetailList(request);
    }

    private void gotoRetailOrderDetailActivity(String orderId) {
        MRouter.getInstance().build(RouterPathConstant.RetailOrderDetail.PATH)
                .putInt(RouterPathConstant.RetailOrderDetail.EXTRA_FROM, RouterPathConstant.RetailOrderDetail.EXTRA_FROM_ORDER_LIST)
                .putString(RouterPathConstant.RetailOrderDetail.EXTRA_ORDER_ID, orderId)
                .navigation(getActivity());
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

    /*******************************************Event Bus 通知**************************************************************/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshOrderList(RouterBaseEvent.CommonEvent event) {
        if (event == RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST) {
            startRefresh();
        }
    }

    @Subscribe
    public void refreshOrderListByMessageList(NotifyDataChangeEvent event) {
        startRefresh();
    }
}