package com.zmsoft.ccd.module.menubalance;

import android.os.Bundle;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.menubalance.dagger.DaggerMenuComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.menubalance.dagger.DaggerMenuBalanceComponent;
import com.zmsoft.ccd.module.menubalance.dagger.MenuBalancePresenterModule;

import javax.inject.Inject;

public class MenuBalanceActivity extends ToolBarActivity {

    @Inject
    MenuBalancePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        MenuBalanceFragment fragment = (MenuBalanceFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (fragment == null) {
            fragment = MenuBalanceFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.content);
        }

        DaggerMenuBalanceComponent.builder()
                .menuComponent(DaggerMenuComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .menuBalancePresenterModule(new MenuBalancePresenterModule(fragment))
                .build()
                .inject(this);
    }
}
