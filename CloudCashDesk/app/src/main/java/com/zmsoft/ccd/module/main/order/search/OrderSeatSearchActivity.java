package com.zmsoft.ccd.module.main.order.search;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.ordersearch.DaggerOrderSeatSearchComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.search.dagger.DaggerOrderSeatSearchPresenterComponent;
import com.zmsoft.ccd.module.main.order.search.dagger.OrderSeatSearchPresenterModule;
import com.zmsoft.ccd.module.main.order.search.fragment.OrderSeatSearchFragment;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.main.order.search.OrderSeatSearchActivity.PATH;


/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/28 14:32.
 */
@Route(path = PATH)
public class OrderSeatSearchActivity extends ToolBarActivity {
    public static final String PATH = "/main/orderSeatFind";

    @Inject
    OrderSeatSearchPresenter mOrderSeatSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OrderSeatSearchFragment orderSeatSearchFragment = (OrderSeatSearchFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (orderSeatSearchFragment == null) {
            orderSeatSearchFragment = OrderSeatSearchFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
            ActivityHelper.showFragment(fragmentManager, orderSeatSearchFragment, R.id.content);
        }
        setContentView(R.layout.activity_framelayout);

        DaggerOrderSeatSearchPresenterComponent.builder()
                .orderSeatSearchComponent(DaggerOrderSeatSearchComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .orderSeatSearchPresenterModule(new OrderSeatSearchPresenterModule(orderSeatSearchFragment))
                .build()
                .inject(this);
    }
}
