package com.zmsoft.ccd.module.electronic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.electronic.ElePaymentItem;
import com.zmsoft.ccd.lib.bean.electronic.ElePaymentNormalItem;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentListResponse;
import com.zmsoft.ccd.module.electronic.adapter.ElectronicAdapter;
import com.zmsoft.ccd.module.electronic.detail.ElectronicDetailActivity;
import com.zmsoft.ccd.module.electronic.detail.RetailElectronicDetailActivity;
import com.zmsoft.ccd.module.electronic.helper.DataMapLayer;
import com.zmsoft.ccd.module.electronic.helper.ElectronicHelper;
import com.zmsoft.ccd.shop.bean.IndustryType;

import butterknife.BindView;

/**
 * 电子收款明细列表页
 *
 * @author DangGui
 * @create 2017/08/12.
 */
public class ElectronicListFragment extends BaseListFragment implements ElectronicContract.View {
    /**
     * 每页20条
     */
    public static final int SIZE_PAGE = 20;
    @BindView(R.id.text_hint)
    TextView mTextHint;
    private ElectronicPresenter mPresenter;

    public static ElectronicListFragment newInstance() {
        Bundle args = new Bundle();
        ElectronicListFragment fragment = new ElectronicListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.electronic_list_fragment_layout;
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
            mStateView.setEmptyResource(R.layout.empty_electronic_list);
        }
    }

    @Override
    protected void initListener() {
        getAdapter().setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (null != data && data instanceof ElePaymentItem) {
                    ElePaymentNormalItem elePaymentNormalItem = ((ElePaymentItem) data).getElePaymentNormalItem();
                    if (null != elePaymentNormalItem) {
                        gotoElectronicDetailActivity(elePaymentNormalItem);
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    private void gotoElectronicDetailActivity(ElePaymentNormalItem elePaymentNormalItem) {
        if (UserHelper.getIndustry() == IndustryType.RETAIL) {
            RetailElectronicDetailActivity.launchActivity(ElectronicListFragment.this
                    , elePaymentNormalItem.getPayId(), elePaymentNormalItem.getCode());
        } else {
            ElectronicDetailActivity.launchActivity(ElectronicListFragment.this
                    , elePaymentNormalItem.getPayId(), elePaymentNormalItem.getCode());
        }
    }

    @Override
    protected void loadListData() {
        mPresenter.getElePaymentList(getPageIndex(), SIZE_PAGE);
    }

    @Override
    protected int getAccentColor() {
        return R.color.accentColor;
    }

    @Override
    protected BaseListAdapter createAdapter() {
        return new ElectronicAdapter(getActivity(), null);
    }

    @Override
    public void setPresenter(ElectronicContract.Presenter presenter) {
        this.mPresenter = (ElectronicPresenter) presenter;
    }

    @Override
    public void getElectronicListSuccess(GetElePaymentListResponse getElePaymentListResponse) {
        mTextHint.setVisibility(View.VISIBLE);
        if (getPageIndex() == 1) {
            showContentView();
        }
        int actualCount = 0;
        if (null != getElePaymentListResponse.getElePayments()) {
            actualCount = getElePaymentListResponse.getElePayments().size();
        }
        renderListData(DataMapLayer.getElePaymentItemList(getElePaymentListResponse), actualCount);

        if (null == getElePaymentListResponse.getElePayments() || getElePaymentListResponse.getElePayments().isEmpty()) {
            if (getPageIndex() == 1) {
                mTextHint.setVisibility(View.GONE);
                showEmptyView();
            }
        }
    }

    @Override
    public void getElectronicListFail(String errorMsg) {
        loadListFailed();
        if (getPageIndex() == 1) {
            mTextHint.setVisibility(View.GONE);
            getAdapter().removeAll();
            showErrorView(errorMsg);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ElectronicHelper.RequestCode.CODE_TO_DETAIL) {
                getRecyclerView().smoothScrollToPosition(0);
                startRefresh();
            }
        }
    }
}
