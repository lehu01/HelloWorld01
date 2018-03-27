package com.zmsoft.ccd.module.personal.attention;


import android.os.Bundle;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.desk.dagger.DaggerDeskComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.personal.attention.dagger.DaggerAddDeskComponent;
import com.zmsoft.ccd.module.personal.attention.dagger.DeskPresenterModule;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.personal.attention.AddAttenDeskActivity.PATH_ADD_DESK;

/**
 * @author DangGui
 * @create 2016/12/15.
 */
@Route(path = PATH_ADD_DESK)
public class AddAttenDeskActivity extends ToolBarActivity {

    public static final String PATH_ADD_DESK = "/main/add_desk";

    @Inject
    AddAttenDeskPresenter mAddAttenDeskPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        AddAttenDeskFragment addAttenDeskFragment = (AddAttenDeskFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (addAttenDeskFragment == null) {
            addAttenDeskFragment = AddAttenDeskFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), addAttenDeskFragment, R.id.content);
        }

        DaggerAddDeskComponent.builder()
                .deskComponent(DaggerDeskComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .deskPresenterModule(new DeskPresenterModule(addAttenDeskFragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AddAttenDeskFragment addAttenDeskFragment = (AddAttenDeskFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (addAttenDeskFragment != null && !addAttenDeskFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
