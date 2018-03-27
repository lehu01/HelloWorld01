package com.zmsoft.ccd.module.main.order.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.orderdetail.dagger.DaggerOrderDetailSourceComponent;
import com.zmsoft.ccd.lib.base.activity.NormalToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.PhoneSpValue;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.base.helper.PhoneSpHelper;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;
import com.zmsoft.ccd.module.main.order.detail.dagger.DaggerOrderDetailPresenterComponent;
import com.zmsoft.ccd.module.main.order.detail.dagger.OrderDetailPresenterModule;
import com.zmsoft.ccd.module.main.order.detail.fragment.OrderDetailFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/16 17:38
 */
@Route(path = RouterPathConstant.OrderDetail.PATH)
public class OrderDetailActivity extends NormalToolBarActivity {

    // Result
    public static final int RESULT_UPDATE_ORDER = 4001; // 改单
    public static final int RESULT_CANCEL_ORDER = 4002; // 撤单

    OrderDetail mOrder;
    @Autowired(name = RouterPathConstant.OrderDetail.EXTRA_FROM)
    int mFrom;
    @Autowired(name = RouterPathConstant.OrderDetail.EXTRA_ORDER_ID)
    String mOrderId;

    @BindView(R.id.view_stub_order_detail_guide)
    ViewStub mViewStubGuide;
    // mViewStubGuide.inflate后的对象
    private LinearLayout mLayoutOrderDetailGreenHand;
    // 显示引导时，设置返回键失效
    private boolean mIsShowingGreenHand = false;

    @Inject
    OrderDetailPresenter mPresenter;

    private OrderDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        mOrder = (OrderDetail) getIntent().getSerializableExtra(RouterPathConstant.OrderDetail.EXTRA_ORDER_DETAIL);

        fragment = (OrderDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_order_detail);

        if (fragment == null) {
            fragment = OrderDetailFragment.newInstance(mFrom, mOrderId, mOrder);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.frame_order_detail);
        }

        DaggerOrderDetailPresenterComponent.builder()
                .orderDetailSourceComponent(DaggerOrderDetailSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .orderDetailPresenterModule(new OrderDetailPresenterModule(fragment))
                .build()
                .inject(this);

        initGreenHandView();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_UPDATE_ORDER) { // 改单成功：[重新获取订单数据]
                String orderId = data.getStringExtra(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID);
                fragment.getOrderDetailByChangeOrder(orderId);
            } else if (requestCode == RESULT_CANCEL_ORDER) { // 撤单成功：[通知修改座位或者订单列表,关闭界面]
                fragment.notifyListRefresh();
                finish();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String orderId = intent.getStringExtra(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID);
        if (fragment != null) {
            fragment.getOrderDetailByChangeOrder(orderId);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mIsShowingGreenHand) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    //================================================================================
    // green hand
    //================================================================================
    private void initGreenHandView() {
        if (PhoneSpValue.VALUE_GREEN_HAND_VERSION_ORDER_DETAIL.VERSION_01_06_00 > PhoneSpHelper.getGreenHandVersionOrderDetail(this)) {
            mLayoutOrderDetailGreenHand = (LinearLayout) mViewStubGuide.inflate();
            mIsShowingGreenHand = true;
            super.updateStatusBarColor(R.color.transparent70);
        } else {
            super.updateStatusBarColor(R.color.transparent);
        }
    }

    public void finishGreenHand() {
        if (null == mLayoutOrderDetailGreenHand) {
            return;
        }
        PhoneSpHelper.saveGreenHandVersionOrderDetail(this, PhoneSpValue.VALUE_GREEN_HAND_VERSION_ORDER_DETAIL.VERSION_01_06_00);
        mLayoutOrderDetailGreenHand.setVisibility(View.GONE);
        mIsShowingGreenHand = false;
        super.updateStatusBarColor(R.color.transparent);
    }
}
