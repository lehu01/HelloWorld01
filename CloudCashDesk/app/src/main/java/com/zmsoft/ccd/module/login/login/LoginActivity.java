package com.zmsoft.ccd.module.login.login;

import android.content.Intent;
import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.DaggerUserComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.login.login.dagger.DaggerLoginComponent;
import com.zmsoft.ccd.module.login.login.dagger.LoginPresentModule;
import com.zmsoft.ccd.module.login.login.fragment.LoginFragment;
import com.zmsoft.ccd.module.login.mobilearea.fragment.MobileAreaFragment;

import javax.inject.Inject;

import static com.zmsoft.ccd.lib.base.constant.RouterPathConstant.Login.PATH_LOGIN_ACTIVITY;


/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/30 16:27.
 */
@Route(path = PATH_LOGIN_ACTIVITY)
public class LoginActivity extends ToolBarActivity {

    private LoginFragment mFragment;

    @Inject
    LoginPresenter mPresenter;
    @Autowired(name = RouterPathConstant.Login.EXTRA_CODE)
    String mErrorCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        // 隐藏返回按钮
        super.setNavigationIcon(null);

        mFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = LoginFragment.newInstance(mErrorCode);
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }

        DaggerLoginComponent.builder()
                .userComponent(DaggerUserComponent.builder()
                        .appComponent(CcdApplication.getInstance()
                                .getAppComponent())
                        .build())
                .loginPresentModule(new LoginPresentModule(mFragment))
                .build()
                .inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == mFragment || !mFragment.isAdded()) {
            return;
        }
        if (resultCode == RESULT_OK) {
            if (RouterPathConstant.Login.REQUEST_CODE_LOGIN_TO_MOBILE_AREA == requestCode) {
                String mobileAreaNumber = data.getStringExtra(MobileAreaFragment.EXTRA_MOBILE_AREA_NUMBER);
                if (null == mobileAreaNumber || StringUtils.isEmpty(mobileAreaNumber)) {
                    return;
                }
                mFragment.updateMobileArea(mobileAreaNumber);
            }
        }
    }
}
