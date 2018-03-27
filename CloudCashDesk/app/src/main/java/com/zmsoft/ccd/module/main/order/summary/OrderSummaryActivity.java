package com.zmsoft.ccd.module.main.order.summary;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.ordersummary.dagger.DaggerOrderSummarySourceComponent;
import com.zmsoft.ccd.lib.base.activity.NormalToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.summary.filter.OrderSummaryFilterFragment;
import com.zmsoft.ccd.module.main.order.summary.fragment.OrderSummaryFragment;
import com.zmsoft.ccd.module.main.order.summary.dagger.DaggerOrderSummaryComponent;
import com.zmsoft.ccd.module.main.order.summary.dagger.OrderSummaryModule;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/17 09:55.
 */

public class OrderSummaryActivity extends NormalToolBarActivity {

    @BindView(R.id.text_order_summary_toggle_drawer)
    TextView mTextToggleDrawer;
    @BindView(R.id.drawer_layout_order_summary)
    DrawerLayout mDrawerLayout;

    @Inject
    OrderSummaryPresenter mPresenter;

    private OrderSummaryFragment mFragment;
    private OrderSummaryFilterFragment mOrderSummaryFilterFragment;

    private boolean mIsDrawLayoutOpen;

    //================================================================================
    // ToolBarActivity
    //================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        initViews();
        initListener();

        DaggerOrderSummaryComponent.builder()
                .orderSummarySourceComponent(DaggerOrderSummarySourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent()).build())
                .orderSummaryModule(new OrderSummaryModule(mFragment))
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
    // right filter
    //================================================================================
    public void refreshByConditions(String codeDate) {
        mDrawerLayout.closeDrawer(GravityCompat.END);
        mFragment.reloadByFilterCondition(codeDate);
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

        mFragment = (OrderSummaryFragment) getSupportFragmentManager().findFragmentById(R.id.linear_layout_content);
        if (mFragment == null) {
            mFragment = new OrderSummaryFragment();
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_layout_content);
        }

        mOrderSummaryFilterFragment = (OrderSummaryFilterFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
    }

    private void initListener() {
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
                mOrderSummaryFilterFragment.recoverLastSelectedView();
                mIsDrawLayoutOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}
