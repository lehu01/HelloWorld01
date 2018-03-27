package com.zmsoft.ccd.module.printconfig.print;

import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.printconfig.print.fragment.PrintConfigFragment;
import com.zmsoft.ccd.module.printconfig.print.fragment.RetailPrintConfigFragment;
import com.zmsoft.ccd.shop.bean.IndustryType;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/18 14:13
 *     desc  : 打印配置页面
 * </pre>
 */
@Route(path = RouterPathConstant.PrintConfig.PATH)
public class PrintConfigActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        if (UserHelper.getIndustry() == IndustryType.RETAIL) {
            showRetailFragment();
        } else {
            showFragment();
        }
    }

    private void showFragment() {
        PrintConfigFragment mFragment = (PrintConfigFragment)
                getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = PrintConfigFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }
    }

    private void showRetailFragment() {
        RetailPrintConfigFragment mFragment = (RetailPrintConfigFragment)
                getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = RetailPrintConfigFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }
    }
}
