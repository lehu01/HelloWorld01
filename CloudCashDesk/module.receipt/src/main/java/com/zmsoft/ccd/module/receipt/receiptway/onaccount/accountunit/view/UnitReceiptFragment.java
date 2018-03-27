package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.widget.searchbar.SearchBarHeader;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.events.BaseEvents;
import com.zmsoft.ccd.module.receipt.receipt.helper.DataMapLayer;
import com.zmsoft.ccd.receipt.bean.GetSignBillSingerResponse;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.UnitAdapter;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.items.UnitRecyclerItem;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.presenter.UnitReceiptContract;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 挂账——选择挂账单位（人）
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class UnitReceiptFragment extends BaseListFragment implements UnitReceiptContract.View {

    @BindView(R2.id.header_search)
    SearchBarHeader mHeaderSearch;
    @BindView(R2.id.layout_search_empty)
    LinearLayout mSearchEmptyLayout;
    private UnitReceiptContract.Presenter mPresenter;
    private List<UnitRecyclerItem> mUnitRecyclerItemList;
    /**
     * 支付类型id
     */
    private String mKindPayId;

    /**
     * 选中的挂账单位（人）ITEM
     */
    private UnitRecyclerItem mSelectedUnitRecyclerItem;

    /**
     * 搜索的关键字
     */
    private String mKey;

    public static UnitReceiptFragment newInstance(String kindPayId, UnitRecyclerItem selectedUnitRecyclerItem) {
        Bundle args = new Bundle();
        args.putString(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID, kindPayId);
        args.putParcelable(ExtraConstants.OnAccountReceipt.EXTRA_SELECT_UNIT, selectedUnitRecyclerItem);
        UnitReceiptFragment fragment = new UnitReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_unit_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        disableAutoRefresh();
        disableRefresh();
        Bundle bundle = getArguments();
        if (null != bundle) {
            mKindPayId = bundle.getString(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID);
            mSelectedUnitRecyclerItem = bundle.getParcelable(ExtraConstants.OnAccountReceipt.EXTRA_SELECT_UNIT);
        }
    }

    @Override
    protected void initListener() {
        mHeaderSearch.setOnSearchListener(new SearchBarHeader.OnSearchListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent, String key) {
                mKey = key;
                showLoading(getString(R.string.dialog_waiting), false);
                loadListData();
                return false;
            }

            @Override
            public void afterTextChanged(String key) {
            }

            @Override
            public void clear() {
                mKey = "";
                showLoading(getString(R.string.dialog_waiting), false);
                loadListData();
            }
        });
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

    @Override
    protected void lazyLoad() {
        mUnitRecyclerItemList = new ArrayList<>();
        getAdapter().setList(mUnitRecyclerItemList);
        super.lazyLoad();
        showLoadingView();
        if (null != mStateView) {
            mStateView.setEmptyResource(R.layout.module_receipt_unit_empty_layout);
        }
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    protected void loadListData() {
        //暂时一次性获取所有的数据，不分页
        mPresenter.getSignUnit(mKindPayId, mKey, Integer.MAX_VALUE, 1);
    }

    @Override
    protected int getAccentColor() {
        return R.color.accentColor;
    }

    @Override
    protected BaseListAdapter createAdapter() {
        return new UnitAdapter(getActivity(), null);
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadListData();
    }

    @Override
    public void setPresenter(UnitReceiptContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void notifyDataChange() {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void successGetSignInfo(GetSignBillSingerResponse getSignBillSingerResponse) {
        if (null != getSignBillSingerResponse && null != getSignBillSingerResponse.getSignInfoVos()
                && !getSignBillSingerResponse.getSignInfoVos().isEmpty()) {
            enableRefresh();
            cleanAll();
            mUnitRecyclerItemList.addAll(DataMapLayer.getSignUnitItemList(getSignBillSingerResponse, mSelectedUnitRecyclerItem));
            notifyDataChange();
            mHeaderSearch.setVisibility(View.VISIBLE);
            mSearchEmptyLayout.setVisibility(View.GONE);
            showContentView();
        } else {
            if (TextUtils.isEmpty(mKey)) {
                mSearchEmptyLayout.setVisibility(View.GONE);
                showEmptyView();
            } else {
                disableRefresh();
                cleanAll();
                mSearchEmptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void failGetData(ErrorBody errorBody) {
        if (getCurrentCount() <= 0) {
            showErrorView(errorBody.getMessage());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onBackPressed() {
        if (null != mUnitRecyclerItemList) {
            int selectedIndex = -1;
            for (int i = 0; i < mUnitRecyclerItemList.size(); i++) {
                if (mUnitRecyclerItemList.get(i).isChecked()) {
                    selectedIndex = i;
                    break;
                }
            }
            Intent intent = new Intent();
            if (selectedIndex >= 0) {
                intent.putExtra(ExtraConstants.OnAccountReceipt.EXTRA_SELECT_UNIT, mUnitRecyclerItemList.get(selectedIndex));
            }
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
        return false;
    }


    @Subscribe
    public void onReceiveEvent(BaseEvents.CommonEvent event) {
        if (event == BaseEvents.CommonEvent.RECEIPT_UNIT_CHECKED_ITEM_EVENT) {
            if (null != event.getObject()) {
                UnitRecyclerItem unitRecyclerItem = (UnitRecyclerItem) event.getObject();
                if (null != unitRecyclerItem) {
                    mPresenter.checkUnitItem(mUnitRecyclerItemList, unitRecyclerItem);
                    //选中某一条后，如果选中的ITEM是之前已经被选中的，则只是将其反选，并不关闭页面(如果再次将反选后的ITEM选中，则关闭页面)，否则将选中的ITEM回传给上个页面，关闭当前页面
                    int selectedIndex = -1;
                    boolean isValid = false;
                    if (null != mSelectedUnitRecyclerItem) {
                        isValid = (!unitRecyclerItem.getKindPayDetailOptionId().equals(mSelectedUnitRecyclerItem.getKindPayDetailOptionId()));
                    }
                    if (isValid || unitRecyclerItem.isChecked()) {
                        if (null != mUnitRecyclerItemList) {
                            for (int i = 0; i < mUnitRecyclerItemList.size(); i++) {
                                if (mUnitRecyclerItemList.get(i).isChecked()) {
                                    selectedIndex = i;
                                    break;
                                }
                            }
                        }
                        if (selectedIndex >= 0) {
                            Intent intent = new Intent();
                            intent.putExtra(ExtraConstants.OnAccountReceipt.EXTRA_SELECT_UNIT, mUnitRecyclerItemList.get(selectedIndex));
                            getActivity().setResult(Activity.RESULT_OK, intent);
                            getActivity().finish();
                        }
                    }
                }
            }
        }
    }
}
