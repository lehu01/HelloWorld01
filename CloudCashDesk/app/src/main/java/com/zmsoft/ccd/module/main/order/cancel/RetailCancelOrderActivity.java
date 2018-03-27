package com.zmsoft.ccd.module.main.order.cancel;

import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.ordercancel.dagger.DaggerCancelOrderSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.cancel.dagger.DaggerRetailCancelOrderPresenterComponent;
import com.zmsoft.ccd.module.main.order.cancel.dagger.RetailCancelOrderPresenterModule;
import com.zmsoft.ccd.module.main.order.cancel.fragment.RetailCancelOrderFragment;
import com.zmsoft.ccd.module.main.order.cancel.fragment.RetailCancelOrderPresenter;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/17 16:02
 */
@Route(path = RouterPathConstant.RetailCancelOrder.PATH)
public class RetailCancelOrderActivity extends ToolBarActivity {

    @Autowired(name = RouterPathConstant.RetailCancelOrder.EXTRA_ORDER_ID)
    String mOrderId;
    @Autowired(name = RouterPathConstant.RetailCancelOrder.EXTRA_SEAT_NAME)
    String mSeatName;
    @Autowired(name = RouterPathConstant.RetailCancelOrder.EXTRA_ORDER_SERIAL_NUMBER)
    String mSerialNumber;
    @Autowired(name = RouterPathConstant.RetailCancelOrder.EXTRA_ORDER_NUMBER)
    int mOrderNumber;
    @Autowired(name = RouterPathConstant.RetailCancelOrder.EXTRA_MODIFY_TIME)
    long mModifyTime;

    @Inject
    RetailCancelOrderPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_cancel_order);

        RetailCancelOrderFragment fragment = (RetailCancelOrderFragment) getSupportFragmentManager()
                .findFragmentById(R.id.linear_cancel_order);

        if (fragment == null) {
            fragment = RetailCancelOrderFragment.newInstance(mOrderId, mSeatName, mOrderNumber, mModifyTime, mSerialNumber);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_cancel_order);
        }

        DaggerRetailCancelOrderPresenterComponent.builder()
                .cancelOrderSourceComponent(DaggerCancelOrderSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .retailCancelOrderPresenterModule(new RetailCancelOrderPresenterModule(fragment))
                .build()
                .inject(this);
    }
}
