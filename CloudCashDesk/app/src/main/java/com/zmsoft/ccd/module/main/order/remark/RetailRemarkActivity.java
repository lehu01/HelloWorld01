package com.zmsoft.ccd.module.main.order.remark;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.systemdircode.dagger.DaggerSystemDirCodeComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.remark.dagger.DaggerRetailRemarkPresenterComponent;
import com.zmsoft.ccd.module.main.order.remark.dagger.RetailRemarkPresenterModule;
import com.zmsoft.ccd.module.main.order.remark.fragment.RetailRemarkFragment;
import com.zmsoft.ccd.module.main.order.remark.fragment.RetailRemarkPresenter;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 15:52
 */
@Route(path = RouterPathConstant.RetailRemark.PATH_REMARK)
public class RetailRemarkActivity extends ToolBarActivity {

    public static final int MENU_ITEM_ONE = 1;

    @Autowired(name = RouterPathConstant.RetailRemark.EXTRA_REMARK)
    String mRemark;
    @Autowired(name = RouterPathConstant.RetailRemark.EXTRA_SEAT)
    String mSeat;

    @Inject
    RetailRemarkPresenter mPresenter;

    private RetailRemarkFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark);

        fragment = (RetailRemarkFragment) getSupportFragmentManager().findFragmentById(R.id.linear_remark);
        if (fragment == null) {
            fragment = RetailRemarkFragment.newInstance(mRemark, mSeat);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_remark);
        }

        DaggerRetailRemarkPresenterComponent.builder()
                .systemDirCodeComponent(DaggerSystemDirCodeComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .retailRemarkPresenterModule(new RetailRemarkPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, MENU_ITEM_ONE, MENU_ITEM_ONE, getString(R.string.save));
        item.setIcon(R.drawable.icon_save);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ITEM_ONE) {
            fragment.save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
