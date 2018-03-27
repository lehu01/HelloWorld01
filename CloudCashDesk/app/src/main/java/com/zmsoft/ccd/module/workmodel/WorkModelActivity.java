package com.zmsoft.ccd.module.workmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.workmodel.dagger.DaggerWorkModelSourceComponent;
import com.zmsoft.ccd.helper.LogOutHelper;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.menubalance.MenuItemCallback;
import com.zmsoft.ccd.module.workmodel.dagger.DaggerWorkModelComponent;
import com.zmsoft.ccd.module.workmodel.dagger.WorkModelPresenterModule;
import com.zmsoft.ccd.module.workmodel.fragment.WorkModelFragment;
import com.zmsoft.ccd.module.workmodel.fragment.WorkModelPresenter;

import javax.inject.Inject;


/**
 * Description：工作模式
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/24 11:36
 */
@Route(path = RouterPathConstant.WorkModel.PATH)
public class WorkModelActivity extends ToolBarActivity implements MenuItemCallback {

    public static final int MENU_ITEM_ONE = 1;

    @Inject
    WorkModelPresenter mPresenter;
    private WorkModelFragment mFragment;
    private MenuItem mSaveItem;

    @Autowired(name = RouterPathConstant.WorkModel.FROM)
    int mFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        mFragment = (WorkModelFragment)
                getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = WorkModelFragment.newInstance(mFrom);
            mFragment.setMenuItemCallback(this);
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }

        DaggerWorkModelComponent.builder()
                .workModelSourceComponent(DaggerWorkModelSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance()
                                .getAppComponent())
                        .build())
                .workModelPresenterModule(new WorkModelPresenterModule(mFragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mFrom == RouterPathConstant.WorkModel.FROM_MAIN) {
            mSaveItem = menu.add(0, MENU_ITEM_ONE, MENU_ITEM_ONE, getString(R.string.save));
            mSaveItem.setIcon(R.drawable.icon_save);
            mSaveItem.setVisible(false);
            mSaveItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else if (mFrom == RouterPathConstant.WorkModel.FROM_LOGIN) {
            getMenuInflater().inflate(R.menu.men_item_work_mode, menu);
            final MenuItem menuItem = menu.findItem(R.id.change_shop);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ITEM_ONE) {
            mFragment.save();
            return true;
        } else if (item.getItemId() == R.id.change_shop) {
            gotoCheckShopActivity();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
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

    /**
     * 选店
     */
    private void gotoCheckShopActivity() {
        MRouter.getInstance().build(RouterPathConstant.CheckShop.PATH_CHECK_SHOP_ACTIVITY)
                .putInt(RouterPathConstant.CheckShop.FROM, RouterPathConstant.CheckShop.FROM_LOGIN)
                .navigation(this);
    }

    @Override
    public void setMenuItemVisible(boolean visible) {
        if (mSaveItem != null) {
            mSaveItem.setVisible(visible);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mFragment.getWorkMode();
    }
}
