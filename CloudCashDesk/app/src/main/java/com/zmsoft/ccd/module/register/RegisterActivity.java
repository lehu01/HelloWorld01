package com.zmsoft.ccd.module.register;

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
import com.zmsoft.ccd.module.login.mobilearea.fragment.MobileAreaFragment;
import com.zmsoft.ccd.module.register.dagger.DaggerRegisterComponent;
import com.zmsoft.ccd.module.register.dagger.RegisterPresenterModule;
import com.zmsoft.ccd.module.register.fragment.RegisterFragment;
import com.zmsoft.ccd.module.register.fragment.RegisterPresenter;

import javax.inject.Inject;

/**
 * Description：注册
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/28 11:12
 */
@Route(path = RouterPathConstant.Register.PATH)
public class RegisterActivity extends ToolBarActivity {

    @Inject
    RegisterPresenter mPresenter;

    private RegisterFragment mFragment;

    @Autowired(name = RouterPathConstant.Register.FROM)
    int mFrom;
    @Autowired(name = RouterPathConstant.Register.COUNTRY_CODE)
    String mCountryCode;
    @Autowired(name = RouterPathConstant.Register.PHONE)
    String mPhone;
    @Autowired(name = RouterPathConstant.Register.WECHAT_UNION_ID)
    String mWechatUnionId;     // 只有微信绑定手机时才存在

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        mFragment = (RegisterFragment) getSupportFragmentManager()
                .findFragmentById(R.id.linear_content);
        if (mFragment == null) {
            mFragment = RegisterFragment.newInstance(mFrom, mPhone, mCountryCode, mWechatUnionId);
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.linear_content);
        }

        DaggerRegisterComponent.builder()
                .userComponent(DaggerUserComponent.builder()
                        .appComponent(CcdApplication.getInstance()
                                .getAppComponent())
                        .build())
                .registerPresenterModule(new RegisterPresenterModule(mFragment))
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
            if (requestCode == RouterPathConstant.Register.REQUEST_CODE_REGISTER_TO_MOBILE_AREA) {
                String mobileAreaNumber = data.getStringExtra(MobileAreaFragment.EXTRA_MOBILE_AREA_NUMBER);
                if (null != mobileAreaNumber && !StringUtils.isEmpty(mobileAreaNumber)) {
                    mFragment.updateMobileAreaNumber(mobileAreaNumber);
                }
            }
        }
    }
}
