package com.zmsoft.ccd.module.menubalance;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.menubalance.MenuBalanceVO;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.emptylayout.CustomEmptyLayout;
import com.zmsoft.ccd.module.menubalance.adapter.MenuBalanceAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MenuBalanceFragment extends BaseListFragment implements MenuBalanceContract.View {

    private static final int PAGE_SIZE = 20;

    private final int FIRST_REQUEST_CODE = 1;

    private static final int REQUEST_SOURCE_ADD_MENU = 1;
    private static final int REQUEST_SOURCE_EDIT_MENU = 2;
    private static final int REQUEST_SOURCE_CLEAR_MENU = 3;

    private int listItemClickPosition = -1;

    @BindView(R.id.layout_empty)
    CustomEmptyLayout mCustomEmptyLayout;

    @BindView(R.id.add_menu_balance)
    TextView mAddTextView;

    private MenuBalancePresenter mPresenter;
    private MenuBalanceAdapter mMenuBalanceAdapter;
    private MenuItem mClearItem;
    private boolean mIsHasData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static MenuBalanceFragment newInstance() {
        Bundle args = new Bundle();
        MenuBalanceFragment fragment = new MenuBalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initView() {
        getAdapter().setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                listItemClickPosition = position;
                if (BatchPermissionHelper.getPermission(Permission.MenuBalance.ACTION_CODE)) {
                    checkPermissionSuccess(Permission.MenuBalance.ACTION_CODE, true, REQUEST_SOURCE_EDIT_MENU);
                } else {
                    showToast(getString(R.string.permission_menu_balance));
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void clearMenuBalance() {
        if (BatchPermissionHelper.getPermission(Permission.MenuBalance.ACTION_CODE)) {
            checkPermissionSuccess(Permission.MenuBalance.ACTION_CODE, true, REQUEST_SOURCE_CLEAR_MENU);
        } else {
            showToast(getString(R.string.permission_menu_balance));
        }
    }

    public void refreshMenuBalance() {
        mPresenter.getMenuBalanceList(UserHelper.getEntityId(), PAGE_SIZE, getPageIndex());
    }

    @OnClick(R.id.add_menu_balance)
    public void addMenuBalance() {
        if (BatchPermissionHelper.getPermission(Permission.MenuBalance.ACTION_CODE)) {
            checkPermissionSuccess(Permission.MenuBalance.ACTION_CODE, true, REQUEST_SOURCE_ADD_MENU);
        } else {
            showToast(getString(R.string.permission_menu_balance));
        }
    }

    @Override
    public void getMenuBalanceListSuccess(List<MenuBalanceVO> list) {
        enableRefresh();
        showContentView();
        mAddTextView.setVisibility(View.VISIBLE);
        renderListData(list);
        if (null == getAdapter().getList() || getAdapter().getList().isEmpty()) {
            if (getPageIndex() == 1) {
                getRecyclerView().setVisibility(View.GONE);
                mCustomEmptyLayout.setVisibility(View.VISIBLE);
            }
            mIsHasData = false;
        } else {
            getRecyclerView().setVisibility(View.VISIBLE);
            mCustomEmptyLayout.setVisibility(View.GONE);
            mIsHasData = true;
        }
        if (null != mClearItem) {
            mClearItem.setVisible(mIsHasData);
        }
    }

    @Override
    public void getMenuBalanceListFailure(String errorMsg) {
        enableRefresh();
        showErrorView(errorMsg);
        mAddTextView.setVisibility(View.GONE);
        loadListFailed();
    }

    @Override
    public void clearAllMenuBalanceSuccess(int effectCount) {
        if (effectCount > 0) {
            getAdapter().removeAll();
            if (null != mClearItem) {
                mClearItem.setVisible(false);
            }
            getRecyclerView().setVisibility(View.GONE);
            mCustomEmptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void clearAllMenuBalanceFailure(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void checkPermissionSuccess(String actionCode, boolean hasPermission, int requestSource) {
        if (Permission.MenuBalance.ACTION_CODE.equals(actionCode)) { // 沽清
            if (hasPermission) {
                if (requestSource == REQUEST_SOURCE_ADD_MENU) {
                    startActivity(new Intent(getActivity(), SelectMenuBalanceActivity.class));
                } else if (requestSource == REQUEST_SOURCE_EDIT_MENU) {
                    if (listItemClickPosition != -1) {
                        Intent intent = new Intent(getActivity(), EditMenuBalanceActivity.class);
                        intent.putExtra(EditMenuBalanceActivity.EXTRA_MENU_BALANCE, (MenuBalanceVO) getAdapter().getList().get(listItemClickPosition));
                        startActivity(intent);
                        listItemClickPosition = -1;
                    }
                } else if (requestSource == REQUEST_SOURCE_CLEAR_MENU) {
                    if (getAdapter().getList() == null || getAdapter().getList().size() == 0) {
                        return;
                    }
                    getDialogUtil().showDialog(getString(R.string.prompt), getString(R.string.clear_all_menu_balance_warn), true,
                            new SingleButtonCallback() {
                                @Override
                                public void onClick(DialogUtilAction which) {
                                    if (which == DialogUtilAction.POSITIVE) {
                                        List<String> menuIdList = new ArrayList<>();
                                        for (Object menu : getAdapter().getList()) {
                                            MenuBalanceVO menu1 = (MenuBalanceVO) menu;
                                            if (!menu1.getMenuId().equals("-1")) {
                                                menuIdList.add(menu1.getMenuId());
                                            }
                                        }
                                        mPresenter.clearAllMenuBalance(UserHelper.getEntityId(), menuIdList,
                                                UserHelper.getUserId());
                                    }
                                }
                            });
                }
            } else {
                showToast(getString(R.string.permission_menu_balance));
            }
        }
    }

    @Override
    public void noPermission(String errorMsg) {
        showToast(errorMsg);
    }

    public void setPresenter(MenuBalanceContract.Presenter presenter) {
        this.mPresenter = (MenuBalancePresenter) presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menubalance;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        setHasOptionsMenu(true);
        initView();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        disableAutoRefresh();
        disableRefresh();
        showLoadingView();
    }

    @Override
    protected void loadListData() {
        refreshMenuBalance();
    }

    @Override
    protected BaseListAdapter createAdapter() {
        mMenuBalanceAdapter = new MenuBalanceAdapter(getActivity(), null);
        return mMenuBalanceAdapter;
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        refreshMenuBalance();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
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

    @Subscribe
    public void onReceiveEvent(BaseEvents.CommonEvent event) {
        if (event == BaseEvents.CommonEvent.EVENT_BALANCE_REFRESH) {
            startRefresh();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mClearItem = menu.add(0, 1, 1, getString(R.string.menu_balance_clear));
        mClearItem.setIcon(R.drawable.ic_clear);
        mClearItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mClearItem.setVisible(mIsHasData);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            clearMenuBalance();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
