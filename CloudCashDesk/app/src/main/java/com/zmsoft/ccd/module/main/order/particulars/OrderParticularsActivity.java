package com.zmsoft.ccd.module.main.order.particulars;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.orderparticulars.dagger.DaggerOrderParticularsSourceComponent;
import com.zmsoft.ccd.lib.base.activity.NormalToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.particulars.dagger.DaggerOrderParticularsPresenterComponent;
import com.zmsoft.ccd.module.main.order.particulars.dagger.OrderParticularsPresenterModule;
import com.zmsoft.ccd.module.main.order.particulars.filter.OrderParticularsFilterFragment;
import com.zmsoft.ccd.module.main.order.particulars.filter.OrderParticularsFilterModel;
import com.zmsoft.ccd.module.main.order.particulars.fragment.OrderParticularsFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 账单明细页面
 * @author : heniu@2dfire.com
 * @time : 2017/10/17 16:34.
 */

public class OrderParticularsActivity extends NormalToolBarActivity {

    @BindView(R.id.text_order_particulars_toggle_drawer)
    TextView mTextToggleDrawer;
    @BindView(R.id.drawer_layout_order_particulars)
    DrawerLayout mDrawerLayout;

    private OrderParticularsFilterFragment mOrderParticularsFilterFragment;
    private OrderParticularsFragment mFragment;

    private boolean mIsDrawLayoutOpen;

    @Inject
    OrderParticularsPresenter mPresenter;

    //================================================================================
    // ToolBarActivity
    //================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_particulars);

        initViews();
        initListener();

        DaggerOrderParticularsPresenterComponent.builder()
                .orderParticularsSourceComponent(DaggerOrderParticularsSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .orderParticularsPresenterModule(new OrderParticularsPresenterModule(mFragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mIsDrawLayoutOpen) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    //================================================================================
    // init
    //================================================================================
    private void initViews() {
        mIsDrawLayoutOpen = false;
        // 设置右侧“筛选”栏
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        final int mDrawerWidth = getResources().getDimensionPixelSize(R.dimen.order_summary_right_drawer_width);
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mTextToggleDrawer.setTranslationX(-slideOffset * mDrawerWidth);
            }
        });
        mTextToggleDrawer.setVisibility(View.VISIBLE);

        mFragment = (OrderParticularsFragment) getSupportFragmentManager().findFragmentById(R.id.linear_layout_content);
        if (mFragment == null) {
            mFragment = new OrderParticularsFragment();
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_layout_content);
        }
        mOrderParticularsFilterFragment = (OrderParticularsFilterFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
    }

    private void initListener() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener(){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mIsDrawLayoutOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mOrderParticularsFilterFragment.recoverLastSelectedView();
                mIsDrawLayoutOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        mTextToggleDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
    }

    //================================================================================
    // right filter
    //================================================================================
    public void refreshByConditions(OrderParticularsFilterModel orderParticularsFilterModel) {
        mDrawerLayout.closeDrawer(GravityCompat.END);
        mFragment.updateOrderParticularsFilterModel(orderParticularsFilterModel);
    }
}
