package com.zmsoft.ccd.module.instance.updateprice;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.instance.dagger.DaggerInstanceSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.module.instance.updateprice.fragment.UpdateInstancePriceFragment;
import com.zmsoft.ccd.module.instance.updateprice.fragment.UpdateInstancePricePresenter;
import com.zmsoft.ccd.module.instance.updateprice.fragment.dagger.DaggerUpdateInstancePricePresenterComponent;
import com.zmsoft.ccd.module.instance.updateprice.fragment.dagger.UpdateInstancePricePresenterModule;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.instance.updateprice.UpdateInstancePriceIActivity.PATH_UPDATE_PRICE_ACTIVITY;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 19:18
 */
@Route(path = PATH_UPDATE_PRICE_ACTIVITY)
public class UpdateInstancePriceIActivity extends ToolBarActivity {

    public static final String PATH_UPDATE_PRICE_ACTIVITY = "/main/updateInstancePrice";
    public static final int MENU_ITEM_ONE = 1;

    public static final String EXTRA_INSTANCE = "instance";

    Instance mInstance;

    private UpdateInstancePriceFragment fragment;

    @Inject
    UpdateInstancePricePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_instance_price);

        mInstance = (Instance) getIntent().getSerializableExtra(EXTRA_INSTANCE);

        fragment = (UpdateInstancePriceFragment) getSupportFragmentManager()
                .findFragmentById(R.id.linear_update_instance_price);

        if (fragment == null) {
            fragment = UpdateInstancePriceFragment.newInstance(mInstance);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_update_instance_price);
        }

        DaggerUpdateInstancePricePresenterComponent.builder()
                .instanceSourceComponent(DaggerInstanceSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .updateInstancePricePresenterModule(new UpdateInstancePricePresenterModule(fragment))
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
