package com.zmsoft.ccd.module.takeout.delivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;
import com.zmsoft.ccd.module.takeout.delivery.adapter.DeliveryAdapter;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryOrderListItem;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryOrderPendingItem;
import com.zmsoft.ccd.module.takeout.delivery.helper.DataMapLayer;
import com.zmsoft.ccd.module.takeout.delivery.helper.DeliveryHelper;
import com.zmsoft.ccd.module.takeout.delivery.presenter.PendingDeliveryContract;
import com.zmsoft.ccd.module.takeout.delivery.presenter.PendingDeliveryPresenter;
import com.zmsoft.ccd.takeout.bean.GetDeliveryOrderListResponse;
import com.zmsoft.ccd.takeout.bean.Takeout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 配送页
 *
 * @author DangGui
 * @create 2017/8/18.
 */
public class PendingDeliveryListFragment extends BaseListFragment implements PendingDeliveryContract.View, BaseListAdapter.AdapterClick {
    @BindView(R2.id.fab_checknone)
    ImageView mFabChecknone;
    @BindView(R2.id.fab_checkall)
    ImageView mFabCheckall;
    @BindView(R2.id.layout_check)
    LinearLayout mLayoutCheck;
    /**
     * 每页20条
     */
    public static final int SIZE_PAGE = 10;
    private static final int REQUEST_CODE_DELIVERY = 1;
    private PendingDeliveryPresenter mPresenter;
    private boolean mCanLoadMore;
    private MenuItem sureMenuItem;

    public static PendingDeliveryListFragment newInstance() {
        Bundle args = new Bundle();
        PendingDeliveryListFragment fragment = new PendingDeliveryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_takeout_delivery_list_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        disableAutoRefresh();
        showLoadingView();
        if (null != mStateView) {
            mStateView.setEmptyResource(R.layout.empty_pending_delivery_list);
        }
    }

    @Override
    protected void initListener() {
        getAdapter().setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (null != data && data instanceof DeliveryOrderListItem) {
                    DeliveryOrderListItem deliveryOrderListItem = (DeliveryOrderListItem) data;
                    if (deliveryOrderListItem.getItemType() == DeliveryAdapter.ItemType.ITEM_TYPE_PENDING_DELEVIRY) {
                        DeliveryOrderPendingItem pendingItem = deliveryOrderListItem.getPendingItem();
                        pendingItem.setChecked(!pendingItem.isChecked());
                        getAdapter().notifyDataSetChanged();
                        toggleSureMenuItem();
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
        //防止按钮重复点击
        RxView.clicks(mFabCheckall).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        checkAllPendingItem(true);
                    }
                });
        RxView.clicks(mFabChecknone).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        checkAllPendingItem(false);
                    }
                });
    }

    @Override
    protected void loadListData() {
        mPresenter.getDeliveryOrderList(getPageIndex(), SIZE_PAGE);
    }

    @Override
    protected BaseListAdapter createAdapter() {
        return new DeliveryAdapter(getActivity(), null, this);
    }

    @Override
    protected boolean canLoadMore() {
        return mCanLoadMore;
    }

    @Override
    public void setPresenter(PendingDeliveryContract.Presenter presenter) {
        this.mPresenter = (PendingDeliveryPresenter) presenter;
    }

    @Override
    public void successGetDeliveryOrderList(GetDeliveryOrderListResponse response) {
        if (getPageIndex() == 1) {
            showContentView();
            if (null != sureMenuItem) {
                sureMenuItem.setVisible(false);
            }
        }
        mCanLoadMore = null != response.getDeliveryOrderVos()
                && response.getDeliveryOrderVos().size() >= SIZE_PAGE - 1;
        int actualCount = 0;
        if (null != response.getDeliveryOrderVos()) {
            actualCount = response.getDeliveryOrderVos().size();
        }
        renderListData(DataMapLayer.getDeliveryPendingOrderList(response, getPageIndex()), actualCount);

        if (!mCanLoadMore && getCurrentCount() > 0) {
            renderListData(DataMapLayer.getBottomJagItem(), 0);
        }
        if (getCurrentCount() > 0) {
            mLayoutCheck.setVisibility(View.VISIBLE);
        } else {
            mLayoutCheck.setVisibility(View.GONE);
            showEmptyView();
        }
    }

    @Override
    public void faileGetDeliveryOrderList(String errorMsg) {
        loadListFailed();
        mLayoutCheck.setVisibility(View.GONE);
        if (getPageIndex() == 1) {
            mCanLoadMore = false;
            getAdapter().removeAll();
            showErrorView(errorMsg);
        }
    }


    /**
     * 全选/反全选待配送订单
     *
     * @param checkAll
     */
    private void checkAllPendingItem(boolean checkAll) {
        for (int i = 0; i < getAdapter().getList().size(); i++) {
            DeliveryOrderListItem deliveryOrderListItem = (DeliveryOrderListItem) getAdapter().getList().get(i);
            if (deliveryOrderListItem.getItemType() == DeliveryAdapter.ItemType.ITEM_TYPE_PENDING_DELEVIRY) {
                DeliveryOrderPendingItem pendingItem = deliveryOrderListItem.getPendingItem();
                pendingItem.setChecked(checkAll);
            }
        }
        getAdapter().notifyDataSetChanged();
        if (null != sureMenuItem) {
            sureMenuItem.setVisible(checkAll);
        }
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadListData();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void onAdapterClick(int type, View view, Object data) {
        switch (type) {
            case DeliveryHelper.RecyclerViewHolderClickType.DELIVERY_METHOD:
                break;
            default:
                break;
        }
    }

    @Override
    public void onAdapterClickEdit(int type, View view, Object data) {

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        sureMenuItem = menu.findItem(R.id.save);
        sureMenuItem.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getCourierList();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_DELIVERY) {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }

    /**
     * 获取已选中的订单ID
     */
    private void getCourierList() {
        ArrayList<String> orderCodes = new ArrayList<>();
        ArrayList<String> orderIds = new ArrayList<>();
        //如果只选中了一下，被选中item的index，只有orderIds.size() == 1时才用得到该值
        int checkedIndex = -1;
        for (int i = 0; i < getAdapter().getList().size(); i++) {
            DeliveryOrderListItem deliveryOrderListItem = (DeliveryOrderListItem) getAdapter().getList().get(i);
            if (deliveryOrderListItem.getItemType() == DeliveryAdapter.ItemType.ITEM_TYPE_PENDING_DELEVIRY) {
                DeliveryOrderPendingItem pendingItem = deliveryOrderListItem.getPendingItem();
                if (pendingItem.isChecked()) {
                    orderCodes.add(pendingItem.getOrderCode());
                    orderIds.add(pendingItem.getOrderId());
                    checkedIndex = i;
                }
            }
        }
        //如果选中的订单只有一个，则创建takeout对象给配送页面
        if (orderCodes.size() == 1 && checkedIndex >= 0) {
            DeliveryOrderPendingItem pendingItem = ((DeliveryOrderListItem) getAdapter().getList().get(checkedIndex)).getPendingItem();
            Takeout takeout = new Takeout();
            takeout.setName(pendingItem.getUserName());
            takeout.setMobile(pendingItem.getUserPhone());
            takeout.setAddress(pendingItem.getAddress());
            takeout.setOrderId(pendingItem.getOrderId());
            DeliveryActivity.launchActivity(this, takeout, null, null, REQUEST_CODE_DELIVERY);
        } else {
            DeliveryActivity.launchActivity(this, null, orderCodes, orderIds, REQUEST_CODE_DELIVERY);
        }
    }

    /**
     * 确定按钮显示/隐藏
     */
    private void toggleSureMenuItem() {
        boolean hasSomeOneChecked = false;
        for (int i = 0; i < getAdapter().getList().size(); i++) {
            DeliveryOrderListItem deliveryOrderListItem = (DeliveryOrderListItem) getAdapter().getList().get(i);
            if (deliveryOrderListItem.getItemType() == DeliveryAdapter.ItemType.ITEM_TYPE_PENDING_DELEVIRY) {
                DeliveryOrderPendingItem pendingItem = deliveryOrderListItem.getPendingItem();
                if (pendingItem.isChecked()) {
                    hasSomeOneChecked = true;
                    break;
                }
            }
        }
        if (null != sureMenuItem) {
            sureMenuItem.setVisible(hasSomeOneChecked);
        }
    }
}
