package com.zmsoft.ccd.module.menubalance;

import android.os.Bundle;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.menubalance.dagger.DaggerMenuComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.menubalance.dagger.DaggerSelectMenuBalanceComponent;
import com.zmsoft.ccd.module.menubalance.dagger.SelectMenuBalancePresenterModule;

import javax.inject.Inject;

public class SelectMenuBalanceActivity extends ToolBarActivity {
    @Inject
    SelectMenuBalancePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        SelectMenuBalanceFragment fragment = (SelectMenuBalanceFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (fragment == null) {
            fragment = SelectMenuBalanceFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.content);
        }

        DaggerSelectMenuBalanceComponent.builder()
                .menuComponent(DaggerMenuComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .selectMenuBalancePresenterModule(new SelectMenuBalancePresenterModule(fragment))
                .build()
                .inject(this);
    }
}
