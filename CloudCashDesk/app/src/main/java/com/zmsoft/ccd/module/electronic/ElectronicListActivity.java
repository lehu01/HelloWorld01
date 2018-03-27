package com.zmsoft.ccd.module.electronic;

import android.os.Bundle;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.electronic.dagger.DaggerElectronicSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.electronic.dagger.DaggerElectronicComponent;
import com.zmsoft.ccd.module.electronic.dagger.ElectronicPresenterModule;

import javax.inject.Inject;

/**
 * 电子收款明细列表页
 *
 * @author DangGui
 * @create 2017/08/12.
 */
public class ElectronicListActivity extends ToolBarActivity {
    @Inject
    ElectronicPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        ElectronicListFragment fragment = (ElectronicListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (fragment == null) {
            fragment = ElectronicListFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.content);
        }

        DaggerElectronicComponent.builder()
                .electronicSourceComponent(
                        DaggerElectronicSourceComponent.builder()
                                .appComponent(CcdApplication.getInstance().getAppComponent())
                                .build())
                .electronicPresenterModule(new ElectronicPresenterModule(fragment))
                .build()
                .inject(this);
    }
}
