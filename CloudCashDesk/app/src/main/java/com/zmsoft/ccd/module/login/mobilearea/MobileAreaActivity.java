package com.zmsoft.ccd.module.login.mobilearea;

import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.DaggerUserComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.login.mobilearea.dagger.DaggerMobileAreaPresenterComponent;
import com.zmsoft.ccd.module.login.mobilearea.dagger.MobileAreaPresenterModule;
import com.zmsoft.ccd.module.login.mobilearea.fragment.MobileAreaFragment;

import javax.inject.Inject;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/21 14:56.
 */
@Route(path = RouterPathConstant.MobileArea.PATH)
public class MobileAreaActivity extends ToolBarActivity {

    private MobileAreaFragment mFragment;

    @Inject
    MobileAreaPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        mFragment = (MobileAreaFragment) getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = MobileAreaFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }

        DaggerMobileAreaPresenterComponent.builder()
                .userComponent(DaggerUserComponent.builder()
                        .appComponent(CcdApplication.getInstance()
                                .getAppComponent()).build())
                .mobileAreaPresenterModule(new MobileAreaPresenterModule(mFragment))
                .build()
                .inject(this);
    }
}
