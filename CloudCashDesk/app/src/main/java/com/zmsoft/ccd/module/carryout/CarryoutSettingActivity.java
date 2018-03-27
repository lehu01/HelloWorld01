package com.zmsoft.ccd.module.carryout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;

/**
 * Description：电话外卖设置
 * <br/>
 * Created by kumu on 2017/7/15.
 */

public class CarryoutSettingActivity extends ToolBarActivity {

    private Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carryout_setting);
        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        }
        if (mFragment == null) {
            mFragment = new CarryoutSettingFragment();
        }
        ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.container, false);
    }
}
