package com.zmsoft.ccd.module.menubalance;

import android.os.Bundle;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.menubalance.dagger.DaggerMenuComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.menubalance.Menu;
import com.zmsoft.ccd.module.menubalance.dagger.AddMenuBalancePresenterModule;
import com.zmsoft.ccd.module.menubalance.dagger.DaggerAddMenuBalanceComponent;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.menubalance.AddMenuBalanceActivity.PATH_ADD_MENU_BALANCE;

@Route(path = PATH_ADD_MENU_BALANCE)
public class AddMenuBalanceActivity extends ToolBarActivity {

    public static final String PATH_ADD_MENU_BALANCE = "/main/add_menu_balance";
    public static final String EXTRA_MENU = "menu";
    @Autowired(name = EXTRA_MENU)
    Menu mMenu;
    @Inject
    AddMenuBalancePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        AddMenuBalanceFragment addMenuBalanceFragment = (AddMenuBalanceFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (addMenuBalanceFragment == null) {
            addMenuBalanceFragment = AddMenuBalanceFragment.newInstance(mMenu);
            ActivityHelper.showFragment(getSupportFragmentManager(), addMenuBalanceFragment, R.id.content);
        }

        DaggerAddMenuBalanceComponent.builder()
                .menuComponent(DaggerMenuComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .addMenuBalancePresenterModule(new AddMenuBalancePresenterModule(addMenuBalanceFragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.save);
        menuItem.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            AddMenuBalanceFragment addMenuBalanceFragment = (AddMenuBalanceFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.content);
            if (addMenuBalanceFragment != null) {
                addMenuBalanceFragment.addMenuBalance();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
