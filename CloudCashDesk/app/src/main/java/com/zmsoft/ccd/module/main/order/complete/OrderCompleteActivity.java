package com.zmsoft.ccd.module.main.order.complete;

import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.ordercomplete.dagger.DaggerOrderCompleteSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.complete.dagger.DaggerOrderCompleteComponent;
import com.zmsoft.ccd.module.main.order.complete.dagger.OrderCompleteModule;
import com.zmsoft.ccd.module.main.order.complete.fragment.OrderCompleteFragment;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.main.order.complete.OrderCompleteActivity.PATH_ORDER_COMPLETE;

/**
 * 新版的已结账单页面：新增今日汇总信息，以及账单明细入口
 * 原有的已结账单页面改为现在的账单明细页面
 * Created by heniu on 2017/10/14.
 */
@Route(path = PATH_ORDER_COMPLETE)
public class OrderCompleteActivity extends ToolBarActivity {

    public static final String PATH_ORDER_COMPLETE = "/main/order_complete";

    @Inject
    OrderCompletePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        OrderCompleteFragment fragment = (OrderCompleteFragment) getSupportFragmentManager()
                .findFragmentById(R.id.linear_content);
        if (fragment == null) {
            fragment = new OrderCompleteFragment();
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_content);
        }

        DaggerOrderCompleteComponent.builder()
                .orderCompleteSourceComponent(DaggerOrderCompleteSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .orderCompleteModule(new OrderCompleteModule(fragment))
                .build()
                .inject(this);
    }
}
