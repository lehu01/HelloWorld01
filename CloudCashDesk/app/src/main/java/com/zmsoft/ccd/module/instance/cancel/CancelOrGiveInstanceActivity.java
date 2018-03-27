package com.zmsoft.ccd.module.instance.cancel;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.instance.dagger.DaggerInstanceSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.module.instance.cancel.fragment.CancelOrGiveInstanceFragment;
import com.zmsoft.ccd.module.instance.cancel.fragment.CancelOrGiveInstancePresenter;
import com.zmsoft.ccd.module.instance.cancel.fragment.dagger.CancelOrGiveInstancePresenterModule;
import com.zmsoft.ccd.module.instance.cancel.fragment.dagger.DaggerCancelOrGiveInstancePresenterComponent;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.instance.cancel.CancelOrGiveInstanceActivity.PATH_CANCEL_INSTANCE_ACTIVITY;

/**
 * 功能描述：退菜
 *
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 19:16
 */
@Route(path = PATH_CANCEL_INSTANCE_ACTIVITY)
public class CancelOrGiveInstanceActivity extends ToolBarActivity {

    public static final String PATH_CANCEL_INSTANCE_ACTIVITY = "/main/cancelInstance";

    public static final String EXTRA_FROM = "from";
    public static final String EXTRA_FROM_CANCEL_INSTANCE = "from_cancel_instance";
    public static final String EXTRA_FROM_GIVE_INSTANCE = "from_give_instance";
    public static final String EXTRA_INSTANCE = "instance";
    public static final int MENU_ITEM_ONE = 1;

    private CancelOrGiveInstanceFragment fragment;

    Instance mInstance;
    @Autowired(name = EXTRA_FROM)
    String mFrom;

    @Inject
    CancelOrGiveInstancePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_instance);

        mInstance = (Instance) getIntent().getSerializableExtra(EXTRA_INSTANCE);

        fragment = (CancelOrGiveInstanceFragment) getSupportFragmentManager()
                .findFragmentById(R.id.linear_cancel_instance);

        if (fragment == null) {
            fragment = CancelOrGiveInstanceFragment.newInstance(mInstance, mFrom);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_cancel_instance);
        }

        DaggerCancelOrGiveInstancePresenterComponent.builder()
                .instanceSourceComponent(DaggerInstanceSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .cancelOrGiveInstancePresenterModule(new CancelOrGiveInstancePresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0,MENU_ITEM_ONE,MENU_ITEM_ONE,getString(R.string.save));
        item.setIcon(R.drawable.icon_save);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == MENU_ITEM_ONE) {
            fragment.ok();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
