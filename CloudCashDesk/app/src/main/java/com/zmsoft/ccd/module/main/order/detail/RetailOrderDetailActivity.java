package com.zmsoft.ccd.module.main.order.detail;

import android.content.Intent;
import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.orderdetail.dagger.DaggerOrderDetailSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;
import com.zmsoft.ccd.module.main.order.detail.dagger.DaggerRetailOrderDetailPresenterComponent;
import com.zmsoft.ccd.module.main.order.detail.dagger.RetailOrderDetailPresenterModule;
import com.zmsoft.ccd.module.main.order.detail.fragment.RetailOrderDetailFragment;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/16 17:38
 */
@Route(path = RouterPathConstant.RetailOrderDetail.PATH)
public class RetailOrderDetailActivity extends ToolBarActivity {

    // Result
    public static final int RESULT_UPDATE_ORDER = 4001; // 改单
    public static final int RESULT_CANCEL_ORDER = 4002; // 撤单

    OrderDetail mOrder;
    @Autowired(name = RouterPathConstant.RetailOrderDetail.EXTRA_FROM)
    int mFrom;
    @Autowired(name = RouterPathConstant.RetailOrderDetail.EXTRA_ORDER_ID)
    String mOrderId;

    @Inject
    RetailOrderDetailPresenter mPresenter;

    private RetailOrderDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_order_detail);

        mOrder = (OrderDetail) getIntent().getSerializableExtra(RouterPathConstant.OrderDetail.EXTRA_ORDER_DETAIL);

        fragment = (RetailOrderDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_order_detail);

        if (fragment == null) {
            fragment = RetailOrderDetailFragment.newInstance(mFrom, mOrderId, mOrder);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.frame_order_detail);
        }

        DaggerRetailOrderDetailPresenterComponent.builder()
                .orderDetailSourceComponent(DaggerOrderDetailSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .retailOrderDetailPresenterModule(new RetailOrderDetailPresenterModule(fragment))
                .build()
                .inject(this);
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
}
