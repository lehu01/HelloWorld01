package com.zmsoft.ccd.module.instance.updateweight;

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
import com.zmsoft.ccd.module.instance.updateweight.fragment.UpdateInstanceWeightFragment;
import com.zmsoft.ccd.module.instance.updateweight.fragment.UpdateInstanceWeightPresenter;
import com.zmsoft.ccd.module.instance.updateweight.fragment.dagger.DaggerUpdateInstanceWeightPresenterComponent;
import com.zmsoft.ccd.module.instance.updateweight.fragment.dagger.UpdateInstanceWeightPresenterModule;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.instance.updateweight.UpdateInstanceWeightActivity.PATH_UPDATE_INSTANCE_WEIGHT;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 19:19
 */
@Route(path = PATH_UPDATE_INSTANCE_WEIGHT)
public class UpdateInstanceWeightActivity extends ToolBarActivity {

    public static final String PATH_UPDATE_INSTANCE_WEIGHT = "/main/updateInstanceWeight";
    public static final int MENU_ITEM_ONE = 1;

    public static final String EXTRA_INSTANCE = "instance";

    Instance mInstance;

    private UpdateInstanceWeightFragment fragment;

    @Inject
    UpdateInstanceWeightPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_instance_weight);

        mInstance = (Instance) getIntent().getSerializableExtra(EXTRA_INSTANCE);

        fragment = (UpdateInstanceWeightFragment) getSupportFragmentManager()
                .findFragmentById(R.id.linear_update_instance_weight);
        if (fragment == null) {
            fragment = UpdateInstanceWeightFragment.newInstance(mInstance);
            ActivityHelper.replaceFragment(getSupportFragmentManager(), fragment, R.id.linear_update_instance_weight);
        }

        DaggerUpdateInstanceWeightPresenterComponent.builder()
                .instanceSourceComponent(DaggerInstanceSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .updateInstanceWeightPresenterModule(new UpdateInstanceWeightPresenterModule(fragment))
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
