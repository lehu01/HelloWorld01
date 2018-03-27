package com.zmsoft.ccd.module.main.order.afterendpay;

import android.os.Bundle;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.bill.RetailBillDetailListFragment;

/**
 * Created by huaixi on 2017/11/02.
 */
public class RetailAfterEndPayActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        RetailBillDetailListFragment fragment = (RetailBillDetailListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.linear_content);
        if (fragment == null) {
            fragment = new RetailBillDetailListFragment();
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_content);
        }
    }
}
