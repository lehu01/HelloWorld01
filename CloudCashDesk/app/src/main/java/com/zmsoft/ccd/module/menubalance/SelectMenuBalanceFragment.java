package com.zmsoft.ccd.module.menubalance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.menubalance.Menu;
import com.zmsoft.ccd.lib.bean.menubalance.MenuVO;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.menubalance.adapter.InstanceListAdapter;
import com.zmsoft.ccd.widget.searchbar.SearchInputWidget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectMenuBalanceFragment extends BaseFragment implements SelectMenuBalanceContract.View {

    private static final int PAGE_SIZE = 20;

    private final int SECOND_REQUEST_CODE = 2;

    @BindView(R.id.search_input_widget)
    SearchInputWidget searchInputWidget;
    @BindView(R.id.instance_list)
    ExpandableListView instanceList;
    @BindView(R.id.search_view_container)
    LinearLayout mSearchLayout;

    private InstanceListAdapter adapter;
    private List<String> parentList = new ArrayList<>();
    private List<List<Menu>> childList = new ArrayList<>();
    private int pageIndex = 1;
    private boolean noMoreData = false;

    private SelectMenuBalancePresenter mPresenter;

    public static SelectMenuBalanceFragment newInstance() {
        Bundle args = new Bundle();
        SelectMenuBalanceFragment fragment = new SelectMenuBalanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initView() {
        adapter = new InstanceListAdapter(getActivity());
        instanceList.setAdapter(adapter);
        searchInputWidget.setOnSearchViewInputListener(new SearchInputWidget.SearchViewInputListener() {
            @Override
            public void searchViewInputListener(String text) {
                if (!StringUtils.isEmpty(text)) {
                    searchByInput(text);
                } else {
                    adapter.setList(parentList, childList);
                    expandAll(adapter);
                }
            }
        });
        instanceList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), AddMenuBalanceActivity.class);
                intent.putExtra(AddMenuBalanceActivity.EXTRA_MENU, (Menu) adapter.getChild(groupPosition, childPosition));
                startActivityForResult(intent, SECOND_REQUEST_CODE);
                getActivity().finish();
                return false;
            }
        });
        instanceList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (null != searchInputWidget) {
                    searchInputWidget.hideKeyboard();
                }
                return false;
            }
        });
    }

    @OnClick(R.id.cancel_btn)
    void cancelSearch() {
        searchInputWidget.clearInput();
    }

    @Override
    public void getAllMenuSuccess(List<MenuVO> menuVOList) {
        showContentView();
        mSearchLayout.setVisibility(View.VISIBLE);
        if (menuVOList != null) {
            pageIndex++;
            for (int i = 0; i < menuVOList.size(); i++) {
                parentList.add(menuVOList.get(i).getKindMenuName());
                childList.add(menuVOList.get(i).getMenu());
            }
            adapter.setList(parentList, childList);
            expandAll(adapter);
            if (menuVOList.size() < PAGE_SIZE) {
                noMoreData = true;
            }
        } else {
            noMoreData = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_REQUEST_CODE && data != null) {
            getActivity().setResult(Activity.RESULT_FIRST_USER, data);
            getActivity().finish();
        }
    }

    @Override
    public void getAllMenuFailure(String errorMsg) {
        showErrorView(errorMsg);
        mSearchLayout.setVisibility(View.GONE);
//        ToastUtils.showShortToast(getContext(), R.string.get_menu_failure);
    }

    public void setPresenter(SelectMenuBalanceContract.Presenter presenter) {
        this.mPresenter = (SelectMenuBalancePresenter) presenter;
    }

    private void searchByInput(String inputText) {
        List<String> parentListTemp = new ArrayList<>();
        List<List<Menu>> childListTemp = new ArrayList<>();
        for (int i = 0; i < parentList.size(); i++) {
            ArrayList<Menu> list = new ArrayList<>();
            for (int j = 0; j < childList.get(i).size(); j++) {
                Menu menu = childList.get(i).get(j);
                if (menu.getMenuName().contains(inputText)) {
                    list.add(menu);
                }
            }
            if (list.size() > 0) {
                childListTemp.add(list);
                parentListTemp.add(parentList.get(i));
            }
        }
        adapter.setList(parentListTemp, childListTemp);
        expandAll(adapter);
    }

    private void expandAll(InstanceListAdapter adapter) {
        int groupCount = adapter.getGroupCount();
        if (instanceList != null && groupCount > 0) {
            for (int i = 0; i < groupCount; i++) {
                instanceList.expandGroup(i);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_menu_balance;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
        mPresenter.getAllMenu(UserHelper.getEntityId());
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }
}
