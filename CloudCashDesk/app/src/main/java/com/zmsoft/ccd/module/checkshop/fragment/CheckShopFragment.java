package com.zmsoft.ccd.module.checkshop.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.LogOutHelper;
import com.zmsoft.ccd.helper.TOpenShopHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.shop.Shop;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.checkshop.adapter.CheckShopAdapter;
import com.zmsoft.ccd.module.main.MainActivity;
import com.zmsoft.ccd.module.main.RetailMainActivity;
import com.zmsoft.ccd.module.workmodel.WorkModelActivity;
import com.zmsoft.ccd.shop.bean.IndustryType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import phone.rest.zmsoft.tdfopenshopmodule.activity.OpenShopActivity;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/7 16:41
 */
public class CheckShopFragment extends BaseListFragment implements CheckShopContract.View {

    @BindView(R.id.linear_no_work_shop)
    LinearLayout mLinearNoWorkShop;
    @BindView(R.id.button_create_shop)
    Button mButtonCreateShop;
    CheckShopPresenter mCheckShopPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private List<Shop> mShopList = new ArrayList<>();
    private CheckShopAdapter mShopAdapter;
    private MenuItem mMenuItem;

    /**
     * 切店之前的entityId和usrId
     */
    private String mOldUserId;
    private int mFrom;

    public static CheckShopFragment newInstance(int from) {
        CheckShopFragment fragment = new CheckShopFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RouterPathConstant.CheckShop.FROM, from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.men_item_work_mode, menu);
        mMenuItem = menu.findItem(R.id.change_shop);
        mMenuItem.setTitle(getString(R.string.immediately_create_shop));
        mMenuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_shop) {
            gotoOpenShopActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //==================================================================
    // init
    //==================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_shop;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        disableAutoRefresh();
        init();
    }

    private void init() {
        initIntentData();
        initListener();
    }

    private void initIntentData() {
        mOldUserId = UserHelper.getUserId();
        mFrom = getArguments().getInt(RouterPathConstant.CheckShop.FROM);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        setContentView(false);
        showLoadingView();
    }

    @Override
    protected void loadListData() {
        getBindShopList();
    }

    @Override
    protected BaseListAdapter createAdapter() {
        mShopAdapter = new CheckShopAdapter(getActivity(), null);
        return mShopAdapter;
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        getBindShopList();
    }

    @Override
    protected void initListener() {
        mShopAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (data != null && data instanceof Shop) {
                    Shop shop = (Shop) data;
                    mCheckShopPresenter.bindShop(UserHelper.getMemberId(), shop.getUserId(), shop.getEntityId()
                            , mOldUserId);
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
        mCheckShopPresenter.unsubscribe();
    }

    private void updateView(List<Shop> list) {
        if (list != null && mShopList != null) {
            mShopList.clear();
            mShopList.addAll(list);
        }
        // 没有工作中的店铺，添加引导
        if (list == null || list.size() == 0) {
            mLinearNoWorkShop.setVisibility(View.VISIBLE);
        } else {
            mLinearNoWorkShop.setVisibility(View.GONE);
            if (mMenuItem != null) {
                mMenuItem.setVisible(true);
            }
        }
        cleanAll();
        renderListData(mShopList);
    }

    private void setContentView(boolean isShow) {
        mRecyclerView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mLinearNoWorkShop.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mRefreshLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //==================================================================
    // update view
    //==================================================================
    @Override
    public void showLoadErrorView(String message) {
        loadListFailed();
        setContentView(false);
        showErrorView(message);
    }

    @Override
    public void workModelSuccess(String data) {
        if (StringUtils.isEmpty(data) || Base.STRING_FALSE.equals(data)) {
            gotoWorkModelActivity();
            BaseSpHelper.removeTurnCloudCashTime(getActivity());
            RouterActivityManager.get().finishAllActivityExcept(WorkModelActivity.class);
        } else {
            BaseSpHelper.saveTurnCloudCashTime(getActivity(), System.currentTimeMillis());
            notifyRefreshByAll();
        }
    }

    @Override
    public void refreshBindShopList(List<Shop> list) {
        showContentView();
        setContentView(true);
        updateView(list);
    }

    @Override
    public void bindShop(User user) {
//        if (!StringUtils.isEmpty(mOldUserId) && !StringUtils.isEmpty(mOldEntityId) && !user.getEntityId().equals(mOldEntityId)) {
//            mCheckShopPresenter.setWorkingStatus(mOldEntityId, 3, mOldUserId, PersonalCenterFragment.END_WORKING);
//        }
        LogOutHelper.logOut(getActivity(), 0);
        saveUser(user);
        getConfigSwitchVal();
    }

    @Override
    public void loadDataError(String errorMessage) {
        toastMsg(errorMessage);
    }

    @Override
    public void setPresenter(CheckShopContract.Presenter presenter) {
        mCheckShopPresenter = (CheckShopPresenter) presenter;
    }

    //==================================================================
    // update data
    //==================================================================
    private void saveUser(User user) {
        UserHelper.saveToSp(user);
    }

    public void refreshCheckShopList() {
        init();
    }

    private void notifyRefreshByAll() {
        switch (mFrom) {
            case RouterPathConstant.CheckShop.FROM_LOGIN:
                gotoMainActivity();
                break;
            case RouterPathConstant.CheckShop.FROM_MAIN:
                if (UserHelper.getIndustry() == IndustryType.RETAIL) {
                    Intent intent = new Intent(getActivity(), RetailMainActivity.class);
                    startActivity(intent);
                    RouterActivityManager.get().finishAllActivityExcept(RouterPathConstant.PATH_RETAIL_MAIN_ACTIVITY);
                } else {
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
                break;
        }
    }

    //==================================================================
    // do some request api
    //==================================================================
    private void getConfigSwitchVal() {
        mCheckShopPresenter.getConfigSwitchVal(UserHelper.getEntityId()
                , String.valueOf(SystemDirCodeConstant.TYPE_SYSTEM)
                , SystemDirCodeConstant.TURN_ON_CLOUD_CASH);
    }

    private void getBindShopList() {
        mCheckShopPresenter.getBindShopList(UserHelper.getMemberId());
    }

    //==================================================================
    // do some click
    //==================================================================
    @OnClick(R.id.button_create_shop)
    void doClick(View view) {
        switch (view.getId()) {
            case R.id.button_create_shop:
                gotoOpenShopActivity();
                break;
        }
    }

    //==================================================================
    // go to other activity
    //==================================================================
    private void gotoOpenShopActivity() {
        TOpenShopHelper.initOpenShopSDK();
        Intent intent = new Intent(getActivity(), OpenShopActivity.class);
        startActivity(intent);
    }

    private void gotoWorkModelActivity() {
        MRouter.getInstance().build(RouterPathConstant.WorkModel.PATH)
                .putInt(RouterPathConstant.WorkModel.FROM, RouterPathConstant.WorkModel.FROM_LOGIN)
                .navigation(getActivity());
    }

    private void gotoMainActivity() {
        Intent intent;
        if (UserHelper.getIndustry() == IndustryType.RETAIL) {
            intent = new Intent(getActivity(), RetailMainActivity.class);
            startActivity(intent);
            RouterActivityManager.get().finishAllActivityExcept(RouterPathConstant.PATH_RETAIL_MAIN_ACTIVITY);
        } else {
            intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            RouterActivityManager.get().finishAllActivityExcept(RouterPathConstant.PATH_MAIN_ACTIVITY);
        }
    }
}
