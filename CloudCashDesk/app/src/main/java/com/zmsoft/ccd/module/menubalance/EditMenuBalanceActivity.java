package com.zmsoft.ccd.module.menubalance;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.menubalance.dagger.DaggerMenuComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.menubalance.MenuBalanceVO;
import com.zmsoft.ccd.module.menubalance.dagger.DaggerEditMenuBalanceComponent;
import com.zmsoft.ccd.module.menubalance.dagger.EditMenuBalancePresenterModule;

import javax.inject.Inject;

public class EditMenuBalanceActivity extends ToolBarActivity implements MenuItemCallback {

    public static final String EXTRA_MENU_BALANCE = "menu_balance";

    @Autowired(name = EXTRA_MENU_BALANCE)
    MenuBalanceVO balanceVO;
    //private Menu menu;
    private MenuItem menuItem;

    EditMenuBalanceFragment fragment;

    @Inject
    EditMenuBalancePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        if (fragment == null) {
            fragment = new EditMenuBalanceFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("menu_balance", balanceVO);
        fragment.setArguments(bundle);
        fragment.setMenuItemCallback(this);
        ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.content);

        DaggerEditMenuBalanceComponent.builder()
                .menuComponent(DaggerMenuComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .editMenuBalancePresenterModule(new EditMenuBalancePresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        //this.menu = menu;
        menuItem = menu.findItem(R.id.save);
        menuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (fragment != null) {
                fragment.updateMenuBalance();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setMenuItemVisible(boolean visible) {
        if (null != menuItem) {
            menuItem.setVisible(visible);
        }
    }
}
