package com.zmsoft.ccd.module.takeout.order.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.annotation.Route;
import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.activity.NormalToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.widget.CustomViewPager;
import com.zmsoft.ccd.module.takeout.DaggerCommentManager;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;
import com.zmsoft.ccd.module.takeout.delivery.PendingDeliveryListActivity;
import com.zmsoft.ccd.module.takeout.order.presenter.TakeoutListActivityContract;
import com.zmsoft.ccd.module.takeout.order.presenter.TakeoutListActivityPresenter;
import com.zmsoft.ccd.module.takeout.order.presenter.dagger.DaggerTakeoutListActivityComponent;
import com.zmsoft.ccd.module.takeout.order.presenter.dagger.TakeoutListActivityPresenterModule;
import com.zmsoft.ccd.module.takeout.order.utils.TakeoutUtils;
import com.zmsoft.ccd.takeout.bean.Config;
import com.zmsoft.ccd.takeout.bean.OrderListRequest;
import com.zmsoft.ccd.takeout.bean.OrderStatusRequest;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/14.
 */
@Route(path = RouterPathConstant.TakeoutList.PATH)
public class TakeoutListActivity extends NormalToolBarActivity implements TakeoutListActivityContract.View
        , TakeoutListFragment.TakeoutListener {

    private static final int START_PAGE = 4;

    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R2.id.text_other_status)
    TextView mTextStatusOther;
    @BindView(R2.id.fl_more)
    FrameLayout mFlMore;
    @BindView(R2.id.rl_tabs)
    RelativeLayout mRlTabs;

    @BindView(R2.id.view_pager)
    CustomViewPager mViewPager;
    @BindView(R2.id.text_toggle_drawer)
    TextView mTextToggleDrawer;
    @BindView(R2.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R2.id.text_delivery_batch)
    TextView mTextDeliveryBatch;
    private TextView mTextNum;


    private PopupWindow mPopWindow;
    private List<Config> mOrderStatusList;
    private List<Config> mOtherStatus;
    private MyPagerAdapter mMyPagerAdapter;

    private OrderListRequest filterParams;

    @Inject
    TakeoutListActivityPresenter takeoutListPresenter;

    TakeoutListFilterFragment filterFragment;

    /**
     * “待配送”tab的index
     */
    private int mSelectIndex = -1;
    /**
     * “待配送”列表是否为空
     */
    private boolean mPendingDeliveryEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_takeout_activity_takeout_list);
        ButterKnife.bind(this);

        DaggerTakeoutListActivityComponent.builder()
                .takeoutListActivityPresenterModule(new TakeoutListActivityPresenterModule(this))
                .takeoutSourceComponent(DaggerCommentManager.get().getTakeoutSourceComponent())
                .build()
                .inject(this);

        filterFragment = (TakeoutListFilterFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);

        initViews();
        initListener();
        showLoadingView();
        takeoutListPresenter.getOrderStatusList(OrderStatusRequest.createForCatering(UserHelper.getEntityId()));
    }

    private void initViews() {

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mRlTabs.getBackground().mutate().setAlpha((int) (255 * 0.85));

        final int mDrawerWidth = getResources().getDimensionPixelSize(R.dimen.module_takeout_drawer_width);
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mTextToggleDrawer.setTranslationX(-slideOffset * mDrawerWidth);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (mSelectIndex == mViewPager.getCurrentItem()) {
                    toggleBatchDeliveryView(false);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (filterFragment != null) {
                    filterFragment.restoreConditions();
                }
                if (mSelectIndex == mViewPager.getCurrentItem()) {
                    toggleBatchDeliveryView(true);
                }
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


    private void initListener() {
        RxView.clicks(mTextDeliveryBatch).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (null == mMyPagerAdapter) {
                            return;
                        }
                        List<TakeoutListFragment> takeoutListFragments = mMyPagerAdapter.getFs();
                        if (null != takeoutListFragments && !takeoutListFragments.isEmpty()) {
                            for (int i = 0; i < takeoutListFragments.size(); i++) {
                                if (takeoutListFragments.get(i).getStatus() == TakeoutConstants.OrderStatus.WAITING_DISPATCH) {
                                    takeoutListFragments.get(i).batchDelivery();
                                    return;
                                }
                            }
                        }
                    }
                });
    }

    private void renderViews() {

        if (mOrderStatusList == null) {
            return;
        }

        mRlTabs.setVisibility(View.VISIBLE);

        if (mOrderStatusList.size() > START_PAGE) {
            mViewPager.setCannotFlingPageIndex(START_PAGE - 1);
        }

        //1,2,3,4,|,5,6
        final List<Config> showData;

        if (mOrderStatusList.size() > START_PAGE) {
            mOtherStatus = new ArrayList<>();
            mOtherStatus.addAll(mOrderStatusList.subList(START_PAGE, mOrderStatusList.size()));
            showData = new ArrayList<>();
            showData.addAll(mOrderStatusList.subList(0, START_PAGE));
            showData.add(new Config());
        } else {
            showData = mOrderStatusList;
        }

        mFlMore.setVisibility(mOtherStatus == null ? View.GONE : View.VISIBLE);


        for (int i = 0; i < showData.size(); i++) {
            Config config = showData.get(i);
            if (config.getValue() == 0) {
                mSelectIndex = i;
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position != showData.size() - 1) {
                    mTextStatusOther.setText(R.string.module_takeout_order_status_more);
                }
                if (!mPendingDeliveryEmpty) {
                    toggleBatchDeliveryView(position == mSelectIndex);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        for (Config config : showData) {
            TabLayout.Tab tab = mTabLayout.newTab().setCustomView(R.layout.module_takeout_tab_layout);
            TextView tvTitle = (TextView) tab.getCustomView().findViewById(R.id.text_tab_title);
            TextView tvNum = (TextView) tab.getCustomView().findViewById(R.id.text_tab_count);
            if (TakeoutConstants.OrderStatus.WAITING_DISPATCH == config.getValue()) {
                mTextNum = tvNum;
            }
            tvNum.setVisibility(View.GONE);
            tvTitle.setText(config.getDesc());
            mTabLayout.addTab(tab);
        }

        int moreWidth = getResources().getDisplayMetrics().widthPixels / showData.size();
        RelativeLayout.LayoutParams rll = (RelativeLayout.LayoutParams) mFlMore.getLayoutParams();
        rll.width = moreWidth;
        mFlMore.setLayoutParams(rll);
        mFlMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(v);
            }
        });

        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), showData);
        mViewPager.setAdapter(mMyPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        if (mSelectIndex != -1) {
            mViewPager.setCurrentItem(mSelectIndex);
        }

        List<TakeoutListFragment> takeoutListFragments = mMyPagerAdapter.getFs();
        if (null != takeoutListFragments && !takeoutListFragments.isEmpty()) {
            for (int i = 0; i < takeoutListFragments.size(); i++) {
                if (takeoutListFragments.get(i).getStatus() == TakeoutConstants.OrderStatus.WAITING_DISPATCH) {
                    takeoutListFragments.get(i).setTakeoutListener(TakeoutListActivity.this);
                    return;
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 1, 1, getString(R.string.module_takeout_bar_menu_tip_search));
        item.setIcon(R.drawable.module_takeout_ic_search);
        item.setVisible(true);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            startActivity(new Intent(this, SearchTakeoutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showPop(View anchorView) {
        if (mOtherStatus == null) {
            return;
        }
        if (mPopWindow == null) {
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.module_takeout_pop_order_status, null);
            for (Config config : mOtherStatus) {
                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(llp);
                textView.setTextSize(12);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(getResources().getColor(R.color.black));
                int padding = getResources().getDimensionPixelSize(R.dimen.module_takeout_status_other_item);
                textView.setPadding(0, padding, 0, padding);
                textView.setText(config.getDesc());
                textView.setTag(config);
                viewGroup.addView(textView);

                View viewDivider = new View(getApplicationContext());
                LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , getResources().getDimensionPixelOffset(R.dimen.module_takeout_status_other_divider_height));
                llp2.leftMargin = getResources().getDimensionPixelOffset(R.dimen.module_takeout_status_other_item_margin);
                llp2.rightMargin = llp2.leftMargin;
                viewDivider.setLayoutParams(llp2);
                viewDivider.setBackgroundResource(R.color.module_takeout_white);
                viewGroup.addView(viewDivider);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopWindow.dismiss();
                        TextView textView = (TextView) v;
                        mTextStatusOther.setText(textView.getText());
                        if (textView.getTag() instanceof Config) {
                            Config c = (Config) textView.getTag();
                            TakeoutListFragment lastFragment = mMyPagerAdapter.getFs().get(START_PAGE);
                            if (mViewPager.getCurrentItem() == START_PAGE) {
                                if (c.getValue() == lastFragment.getStatus()) {
                                    return;
                                }
                                lastFragment.updateParams(c.getValue(), c.getDesc());
                                lastFragment.startRefresh(true);
                            } else {
                                if (lastFragment.getAdapter() != null) {
                                    lastFragment.getAdapter().removeAll();
                                }
                                lastFragment.updateParams(c.getValue(), c.getDesc());
                                mViewPager.setCurrentItem(START_PAGE, false);
                            }
                        }
                    }
                });

            }
            mPopWindow = new PopupWindow(viewGroup,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPopWindow.setWidth(mFlMore.getWidth() + 20);
            mPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        }
        mPopWindow.showAsDropDown(anchorView, 0, 0);
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void getOrderStatusListSuccess(List<Config> configs) {
        showContentView();
        if (configs == null || configs.isEmpty()) {
            return;
        }
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        mTextToggleDrawer.setVisibility(View.VISIBLE);
        mOrderStatusList = configs;
        renderViews();
    }

    @Override
    public void getOrderStatusListFailed(String errorCode, String errorMsg) {
        Log.e("Test", errorCode + "," + errorMsg);
        mTextToggleDrawer.setVisibility(View.GONE);
        showErrorView(getString(R.string.module_takeout_list_load_failed));
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        showLoadingView();
        takeoutListPresenter.getOrderStatusList(OrderStatusRequest.createForCatering(UserHelper.getEntityId()));
    }

    public void refreshByConditions(OrderListRequest selectedRequest) {
        mDrawerLayout.closeDrawer(GravityCompat.END);
        if (filterParams == null) {
            filterParams = OrderListRequest.create();
        }
        TakeoutUtils.copyFilterToRequest(selectedRequest, filterParams);
        int position = mViewPager.getCurrentItem();
        TakeoutListFragment fragment = mMyPagerAdapter.getFs().get(position);
        fragment.refreshByConditions(selectedRequest);
    }

    public OrderListRequest getFilterParams() {
        return filterParams;
    }

    public void setWaitNum(int waitNum) {
        if (mTextNum != null) {
            if (waitNum > 0) {
                if (waitNum > 99) {
                    waitNum = 99;
                }
                mTextNum.setVisibility(View.VISIBLE);
                mTextNum.setText(String.valueOf(waitNum));
            } else {
                mTextNum.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showBatchDelivery(boolean isEmpty) {
        mPendingDeliveryEmpty = isEmpty;
        toggleBatchDeliveryView(!isEmpty && mSelectIndex == mViewPager.getCurrentItem());
    }

    private static class MyPagerAdapter extends FragmentPagerAdapter {
        private List<TakeoutListFragment> fs;
        private List<Config> orderStatusList;

        MyPagerAdapter(FragmentManager fragmentManager, List<Config> orderStatusList) {
            super(fragmentManager);
            this.orderStatusList = orderStatusList;
            this.fs = new ArrayList<>();
            for (Config config : orderStatusList) {
                fs.add(TakeoutListFragment.create(config.getValue(), config.getDesc()));
            }
        }

        @Override
        public int getCount() {
            return orderStatusList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fs.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return orderStatusList.get(position).getDesc();
        }

        public List<TakeoutListFragment> getFs() {
            return fs;
        }
    }

    /**
     * 批量配送按钮 显示/隐藏 动画
     *
     * @param show
     */
    private void toggleBatchDeliveryView(boolean show) {
        int delta = mTextDeliveryBatch.getHeight() + getMarginBottom(mTextDeliveryBatch);
        if (!show) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(mTextDeliveryBatch, "translationY", 0, delta);
            animator.setDuration(200);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    //防止在非“待配送”的tab之间来回切换出现下沉动画，在动画结束后，隐藏按钮
                    if (null != mTextDeliveryBatch) {
                        mTextDeliveryBatch.setVisibility(View.GONE);
                    }
                }
            });
            animator.start();
        } else {
            if (mTextDeliveryBatch.getVisibility() == View.VISIBLE) {
                return;
            }
            mTextDeliveryBatch.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(mTextDeliveryBatch, "translationY", delta, 0);
            animator.setDuration(200);
            animator.start();
        }
    }

    /**
     * 计算view距离屏幕底部的高度，计算属性动画用
     *
     * @param view
     * @return
     */
    private int getMarginBottom(View view) {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }
}
