package com.zmsoft.ccd.module.printconfig.smallticket;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.print.dagger.DaggerPrintConfigSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.printconfig.smallticket.dagger.DaggerSmallTicketPrintConfigComponent;
import com.zmsoft.ccd.module.printconfig.smallticket.dagger.SmallTicketPrintConfigPresenterModule;
import com.zmsoft.ccd.module.printconfig.smallticket.fragment.SmallTicketPrintConfigFragment;
import com.zmsoft.ccd.module.printconfig.smallticket.fragment.SmallTicketPrintConfigPresenter;

import javax.inject.Inject;

/**
 * Description：打印配置
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:26
 */
public class SmallTicketPrintConfigActivity extends ToolBarActivity {

    public static final int MENU_ITEM_ONE = 1;

    @Inject
    SmallTicketPrintConfigPresenter mPresenter;
    private SmallTicketPrintConfigFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        showFragment();
        initDependence();
    }

    private void showFragment() {
        mFragment = (SmallTicketPrintConfigFragment)
                getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = SmallTicketPrintConfigFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }
    }

    private void initDependence() {
        DaggerSmallTicketPrintConfigComponent.builder()
                .printConfigSourceComponent(DaggerPrintConfigSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance()
                                .getAppComponent())
                        .build())
                .smallTicketPrintConfigPresenterModule(new SmallTicketPrintConfigPresenterModule(mFragment))
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
            mFragment.save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
