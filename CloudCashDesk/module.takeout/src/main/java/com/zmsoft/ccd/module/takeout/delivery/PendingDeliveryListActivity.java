package com.zmsoft.ccd.module.takeout.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.takeout.DaggerCommentManager;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.delivery.dagger.DaggerPendingDeliveryComponent;
import com.zmsoft.ccd.module.takeout.delivery.dagger.PendingDeliveryPresenterModule;
import com.zmsoft.ccd.module.takeout.delivery.presenter.PendingDeliveryPresenter;

import javax.inject.Inject;

/**
 * 配送页
 *
 * @author DangGui
 * @create 2017/8/18.
 */
public class PendingDeliveryListActivity extends ToolBarActivity {
    @Inject
    PendingDeliveryPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        PendingDeliveryListFragment fragment = (PendingDeliveryListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (fragment == null) {
            fragment = PendingDeliveryListFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.content);
        }
        DaggerPendingDeliveryComponent.builder()
                .pendingDeliveryPresenterModule(new PendingDeliveryPresenterModule(fragment))
                .takeoutSourceComponent(DaggerCommentManager.get().getTakeoutSourceComponent())
                .build()
                .inject(this);
    }

    public static void launchActivity(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), PendingDeliveryListActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }
}
