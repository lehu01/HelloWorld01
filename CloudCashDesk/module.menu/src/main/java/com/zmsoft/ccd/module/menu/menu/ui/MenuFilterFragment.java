package com.zmsoft.ccd.module.menu.menu.ui;

import android.os.Bundle;
import android.view.View;

import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.menu.adapter.MenuFilterAdapter;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuGroupVO;

import java.util.List;

/**
 * Description：菜单列表右侧过滤界面
 * <br/>
 * Created by kumu on 2017/4/18.
 */

public class MenuFilterFragment extends BaseListFragment implements BaseListAdapter.AdapterClick {


    /**
     * 按照菜类分组
     */
    private List<MenuGroupVO> mCategoryList;

    /**
     * 按菜首字母分组
     */
    private List<String> mLetterList;

    private MenuFilterAdapter mFilterAdapter;

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        View frameLayout = view.findViewById(R.id.frame_layout);
        if (frameLayout != null) {
            frameLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
        disableAutoRefresh();
        disableRefresh();
    }


    public void setFilterData(List<MenuGroupVO> categoryList, List<String> letterList) {
        this.mCategoryList = categoryList;
        this.mLetterList = letterList;
    }

    @Override
    protected void loadListData() {
        //createTestData();
        //renderListData(mLetterList);
    }

    @Override
    protected int getAccentColor() {
        return R.color.accentColor;
    }

    public void showCategory() {
        mFilterAdapter.setShowType(MenuFilterAdapter.SHOW_TYPE_CATEGORY);
        cleanAll();
        getAdapter().appendItems(mCategoryList);
        getAdapter().notifyDataSetChanged();
    }

    public void showLetter() {
        mFilterAdapter.setShowType(MenuFilterAdapter.SHOW_TYPE_LETTER);
        cleanAll();
        getAdapter().appendItems(mLetterList);
        getAdapter().notifyDataSetChanged();
    }


    @Override
    protected BaseListAdapter createAdapter() {
        return mFilterAdapter = new MenuFilterAdapter(getActivity(), this);
    }

    @Override
    public void unBindPresenterFromView() {

    }

    @Override
    public void onAdapterClick(int type, View view, Object data) {
        switch (type) {
            case MenuFilterAdapter.SHOW_TYPE_CATEGORY:
                if (getActivity() instanceof IMenuListContract && data instanceof MenuGroupVO) {
                    IMenuListContract menuListActivity = (IMenuListContract) getActivity();
                    menuListActivity.showContentByCategory((MenuGroupVO) data);
                }
                break;
            case MenuFilterAdapter.SHOW_TYPE_LETTER:
                if (getActivity() instanceof IMenuListContract) {
                    IMenuListContract menuListActivity = (IMenuListContract) getActivity();
                    menuListActivity.showContentByLetter(data + "");
                }
                break;
        }
    }

    @Override
    public void onAdapterClickEdit(int type, View view, Object data) {

    }
}
