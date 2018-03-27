package com.zmsoft.ccd.module.main.order.cancel;

import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.ordercancel.dagger.DaggerCancelOrderSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.cancel.dagger.CancelOrderPresenterModule;
import com.zmsoft.ccd.module.main.order.cancel.dagger.DaggerCancelOrderPresenterComponent;
import com.zmsoft.ccd.module.main.order.cancel.fragment.CancelOrderFragment;
import com.zmsoft.ccd.module.main.order.cancel.fragment.CancelOrderPresenter;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.main.order.cancel.CancelOrderActivity.PATH;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/17 16:02
 */
@Route(path = PATH)
public class CancelOrderActivity extends ToolBarActivity {

    public static final String PATH = "/main/cancelOrder";

    public static final String EXTRA_ORDER_ID = "orderId";
    public static final String EXTRA_SEAT_NAME = "seatName";
    public static final String EXTRA_ORDER_NUMBER = "orderNumber";
    public static final String EXTRA_MODIFY_TIME = "modifyTime";

    @Autowired(name = EXTRA_ORDER_ID)
    String mOrderId;
    @Autowired(name = EXTRA_SEAT_NAME)
    String mSeatName;
    @Autowired(name = EXTRA_ORDER_NUMBER)
    int mOrderNumber;
    @Autowired(name = EXTRA_MODIFY_TIME)
    long mModifyTime;

    @Inject
    CancelOrderPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        CancelOrderFragment fragment = (CancelOrderFragment) getSupportFragmentManager()
                .findFragmentById(R.id.linear_cancel_order);

        if (fragment == null) {
            fragment = CancelOrderFragment.newInstance(mOrderId, mSeatName, mOrderNumber, mModifyTime);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_cancel_order);
        }

        DaggerCancelOrderPresenterComponent.builder()
                .cancelOrderSourceComponent(DaggerCancelOrderSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .cancelOrderPresenterModule(new CancelOrderPresenterModule(fragment))
                .build()
                .inject(this);
    }
}
