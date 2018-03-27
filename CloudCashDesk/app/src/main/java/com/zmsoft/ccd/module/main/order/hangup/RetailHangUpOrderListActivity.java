package com.zmsoft.ccd.module.main.order.hangup;

import android.content.Intent;
import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.order.dagger.DaggerOrderSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.hangup.dagger.DaggerRetailHangUpOrderListComponent;
import com.zmsoft.ccd.module.main.order.hangup.dagger.RetailHangUpOrderListPresenterModule;
import com.zmsoft.ccd.module.main.order.hangup.fragment.RetailHangUpOrderListFragment;
import com.zmsoft.ccd.module.main.order.hangup.fragment.RetailHangUpOrderListPresenter;

import javax.inject.Inject;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/17 10:55
 *     desc  : 挂起订单界面
 * </pre>
 */
@Route(path = RouterPathConstant.RetailHangUpOrderList.PATH)
public class RetailHangUpOrderListActivity extends ToolBarActivity {

    public static final int RESULT_CART = 6001;

    @Inject
    RetailHangUpOrderListPresenter mPresenter;

    RetailHangUpOrderListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        showHangUpFragment();
        initDependence();
    }

    private void showHangUpFragment() {
        mFragment = (RetailHangUpOrderListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = RetailHangUpOrderListFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }
    }

    private void initDependence() {
        DaggerRetailHangUpOrderListComponent.builder()
                .orderSourceComponent(DaggerOrderSourceComponent.builder()
                    .appComponent(CcdApplication.getInstance().getAppComponent())
                    .build())
                .retailHangUpOrderListPresenterModule(new RetailHangUpOrderListPresenterModule(mFragment))
                .build()
                .inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_CART) {
                mFragment.getHangUpOrderList();
            }
        }
    }
}
