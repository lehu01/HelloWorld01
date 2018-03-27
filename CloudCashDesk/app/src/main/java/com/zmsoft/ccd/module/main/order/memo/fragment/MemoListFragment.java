package com.zmsoft.ccd.module.main.order.memo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.remark.UpdateRemarkCheckEvent;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.remark.Memo;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.main.order.create.CreateOrUpdateOrderActivity;
import com.zmsoft.ccd.module.main.order.memo.MemoListActivity;
import com.zmsoft.ccd.module.main.order.memo.adapter.MemoListAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 16:54
 */
public class MemoListFragment extends BaseListFragment implements MemoListContract.View {

    @BindView(R.id.edit_remark)
    EditText mEditRemark;
    @BindView(R.id.linear_content)
    FrameLayout mLinearContent;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private String mMemo;
    private List<Memo> mMemoList = new ArrayList<>();
    private MemoListAdapter mMemoListAdapter;

    @Inject
    MemoListPresenter mMemoListPresenter;

    public static MemoListFragment newInstance(String memo) {
        MemoListFragment fragment = new MemoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MemoListActivity.EXTRA_MEMO, memo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_remark;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        initBundleData();
        disableAutoRefresh();
    }

    private void initBundleData() {
        Bundle bundle = getArguments();
        mMemo = bundle.getString(MemoListActivity.EXTRA_MEMO);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {
        mMemoListPresenter.unsubscribe();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        setContentView(false);
        showLoadingView();
    }

    @Override
    protected void loadListData() {
        getMemoList();
    }

    @Override
    protected BaseListAdapter createAdapter() {
        mMemoListAdapter = new MemoListAdapter(getActivity(), null);
        return mMemoListAdapter;
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        getMemoList();
    }

    private void getMemoList() {
        mMemoListPresenter.getMemoList(UserHelper.getEntityId()
                , SystemDirCodeConstant.SERVICE_CUSTOMER
                , SystemDirCodeConstant.TYPE_SYSTEM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
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
    public void setPresenter(MemoListContract.Presenter presenter) {
        mMemoListPresenter = (MemoListPresenter) presenter;
    }

    @Override
    public void showStateErrorView(String errorMessage) {
        loadListFailed();
        setContentView(false);
        showErrorView(errorMessage);
    }

    @Override
    public void refreshRemarkList(List<Memo> list) {
        setContentView(true);
        showContentView();
        if (list != null) {
            if (!StringUtils.isEmpty(mMemo)) {
                initCheckRemark(mMemo, list);
            }
            mMemoList.clear();
            mMemoList.addAll(list);
        }
        cleanAll();
        renderListData(mMemoList);
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Subscribe
    public void updateRemarkCheckList(UpdateRemarkCheckEvent event) {
        if (event != null) {
            mMemoList.get(event.getPosition()).setCheck(event.isCheck());
        }
    }

    /**
     * 默认设置选中的备注
     *
     * @param remark 备注字符串拼接
     * @param list   数据列表
     */
    private void initCheckRemark(String remark, List<Memo> list) {
        StringBuilder builder = new StringBuilder();
        String[] marks = remark.split(getString(R.string.comma));
        for (int i = 0; i <= marks.length - 1; i++) {
            boolean tag = false;
            for (Memo m : list) {
                String name = m.getName();
                if (name.equals(marks[i])) {
                    m.setCheck(true);
                    tag = true;
                    break;
                }
            }
            if (!tag) {
                builder.append(marks[i]);
            }
        }
        mEditRemark.setText(builder.toString().trim());
    }

    /**
     * 保存，将数据返回给开单界面
     */
    public void save() {
        // 选中备注
        StringBuilder builder = new StringBuilder();
        for (Memo remark : mMemoList) {
            if (remark.isCheck()) {
                builder.append(remark.getName());
                builder.append(getString(R.string.comma));
            }
        }
        // 输入备注
        String editRemark = mEditRemark.getText().toString().trim();
        mMemo = builder.append(editRemark).toString();
        // 处理数据
        if (!StringUtils.isEmpty(mMemo)) {
            if (mMemo.endsWith(getString(R.string.comma))) {
                mMemo = mMemo.substring(0, mMemo.length() - 1);
            }
        }
        Intent intent = new Intent();
        intent.putExtra(MemoListActivity.EXTRA_MEMO, mMemo);
        getActivity().setResult(CreateOrUpdateOrderActivity.RESULT_OK, intent);
    }

    private void setContentView(boolean isShow) {
        mLinearContent.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
