package com.zmsoft.ccd.module.personal.profile.modifypwd;

import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.DaggerUserComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.personal.profile.modifypwd.dagger.DaggerModifyPwdComponent;
import com.zmsoft.ccd.module.personal.profile.modifypwd.dagger.ModifyPwdPresenterModule;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.personal.profile.modifypwd.ModifyPwdActivity.PATH_MODIFY_PWD;

/**
 * 个人中心——修改密码
 *
 * @author DangGui
 * @create 2016/12/16.
 */
@Route(path = PATH_MODIFY_PWD)
public class ModifyPwdActivity extends ToolBarActivity {
    public static final String PATH_MODIFY_PWD = "/main/modify_pwd";
    @Inject
    ModifyPwdPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_framelayout);

        ModifyPwdFragment modifyPwdFragment = (ModifyPwdFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (modifyPwdFragment == null) {
            modifyPwdFragment = ModifyPwdFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), modifyPwdFragment, R.id.content);
        }

        DaggerModifyPwdComponent.builder()
                .userComponent(DaggerUserComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .modifyPwdPresenterModule(new ModifyPwdPresenterModule(modifyPwdFragment))
                .build()
                .inject(this);
    }
}
