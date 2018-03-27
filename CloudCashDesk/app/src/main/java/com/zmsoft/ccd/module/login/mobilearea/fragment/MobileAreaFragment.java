package com.zmsoft.ccd.module.login.mobilearea.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.adapter.DividerDecoration;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.bean.login.mobilearea.MobileArea;
import com.zmsoft.ccd.module.login.mobilearea.MobileAreaContract;
import com.zmsoft.ccd.module.login.mobilearea.adapter.MobileAreaAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/21 14:58.
 */

public class MobileAreaFragment extends BaseListFragment implements MobileAreaContract.View {

    public static final String EXTRA_MOBILE_AREA_NUMBER = "EXTRA_MOBILE_AREA_NUMBER";

    private MobileAreaAdapter mAdapter;
    private MobileAreaContract.Presenter mPresenter;

    private AlphaAnimation mRecyclerViewAnimation;

    public static MobileAreaFragment newInstance() {
        MobileAreaFragment mobileAreaFragment = new MobileAreaFragment();
        Bundle bundle = new Bundle();
        mobileAreaFragment.setArguments(bundle);
        return mobileAreaFragment;
    }

    //================================================================================
    // life cycle
    //================================================================================
    @Override
    public void onPause() {
        super.onPause();
        if (null != mRecyclerViewAnimation) {
            mRecyclerViewAnimation.cancel();
        }
    }

    //================================================================================
    // BaseListFragment
    //================================================================================
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        getRecyclerView().addItemDecoration(new DividerDecoration(this.getContext(), R.drawable.shape_recyclerview_divider_grey));
        disableRefresh();
        showContentView();
    }

    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    protected void loadListData() {
        mPresenter.loadMobileArea();
    }

    @Override
    protected BaseListAdapter createAdapter() {
        mAdapter = new MobileAreaAdapter(getActivity());
        return mAdapter;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (data instanceof MobileArea) {
                    setResultAndFinish((MobileArea)data);
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        mPresenter.loadMobileArea();
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    //================================================================================
    // BaseListFragment
    //================================================================================
    private void setResultAndFinish(MobileArea mobileArea) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MOBILE_AREA_NUMBER, mobileArea.getNumber());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    //================================================================================
    // MobileAreaContract.View
    //================================================================================
    @Override
    public void setPresenter(MobileAreaContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadDataSuccess(List<MobileArea> MobileAreas) {
        showContentView();
        getRootView().setBackgroundColor(getResources().getColor(android.R.color.white));
        renderListData(MobileAreas);

        mRecyclerViewAnimation = new AlphaAnimation(0.0f, 1.0f);
        mRecyclerViewAnimation.setDuration(500);
        getRecyclerView().startAnimation(mRecyclerViewAnimation);
    }

    @Override
    public void loadDataError(String errorMessage) {
        showErrorView(errorMessage);
        getRootView().setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
}
