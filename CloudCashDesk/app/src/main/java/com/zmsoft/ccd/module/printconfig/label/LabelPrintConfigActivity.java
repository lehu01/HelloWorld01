package com.zmsoft.ccd.module.printconfig.label;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.print.dagger.DaggerPrintConfigSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.printconfig.label.dagger.DaggerLabelPrintConfigComponent;
import com.zmsoft.ccd.module.printconfig.label.dagger.LabelPrintConfigPresenterModule;
import com.zmsoft.ccd.module.printconfig.label.fragment.LabelPrintConfigFragment;
import com.zmsoft.ccd.module.printconfig.label.fragment.LabelPrintConfigPresenter;

import javax.inject.Inject;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/18 14:53
 *     desc  : 标签打印机
 * </pre>
 */
public class LabelPrintConfigActivity extends ToolBarActivity {

    public static final int MENU_ITEM_ONE = 1;

    @Inject
    LabelPrintConfigPresenter mPresenter;
    private LabelPrintConfigFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        showFragment();
        initDependence();
    }

    private void showFragment() {
        mFragment = (LabelPrintConfigFragment)
                getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = LabelPrintConfigFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }
    }

    private void initDependence() {
        DaggerLabelPrintConfigComponent.builder()
                .printConfigSourceComponent(DaggerPrintConfigSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .labelPrintConfigPresenterModule(new LabelPrintConfigPresenterModule(mFragment))
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
