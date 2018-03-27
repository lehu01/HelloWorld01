package com.zmsoft.ccd.module.checkshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.checkshop.dagger.DaggerShopComponent;
import com.zmsoft.ccd.helper.LogOutHelper;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.module.checkshop.dagger.DaggerRetailCheckShopComponent;
import com.zmsoft.ccd.module.checkshop.dagger.RetailCheckShopPresenterModule;
import com.zmsoft.ccd.module.checkshop.fragment.RetailCheckShopFragment;
import com.zmsoft.ccd.module.checkshop.fragment.RetailCheckShopPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import phone.rest.zmsoft.tdfopenshopmodule.activity.OpenShopSuccessEvent;


/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/13 14:38
 */
@Route(path = RouterPathConstant.RetailCheckShop.PATH_CHECK_SHOP_ACTIVITY)
public class RetailCheckShopActivity extends ToolBarActivity {

    @Inject
    RetailCheckShopPresenter mCheckShopPresenter;

    private final static String OPEN_SHOP_FROM_SDK = "OPEN_SHOP_FROM_SDK";
    public static final int RC_CHECK_SHOP = 601;

    private RetailCheckShopFragment mFragment;

    @Autowired(name = RouterPathConstant.RetailCheckShop.FROM)
    int mFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        mFragment = (RetailCheckShopFragment)
                getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = RetailCheckShopFragment.newInstance(mFrom);
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }

        EventBusHelper.register(this);
        DaggerRetailCheckShopComponent.builder()
                .shopComponent(DaggerShopComponent.builder()
                    .appComponent(CcdApplication.getInstance().getAppComponent())
                    .build())
                .retailCheckShopPresenterModule(new RetailCheckShopPresenterModule(mFragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (RouterActivityManager.get().getActivityCount() == 1) {
                LogOutHelper.logOut(this);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (RouterActivityManager.get().getActivityCount() == 1) {
            LogOutHelper.logOut(this);
            return;
        }
        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openShopSuccess(OpenShopSuccessEvent openShopSuccessEvent) {
        Logger.d("longyi open shop success");
        AnswerEventLogger.log(OPEN_SHOP_FROM_SDK);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mFragment.refreshCheckShopList();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unregister(this);
    }
}
