package com.zmsoft.ccd.module.electronic.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.electronic.dagger.DaggerElectronicSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.electronic.detail.dagger.DaggerElectronicDetailComponent;
import com.zmsoft.ccd.module.electronic.detail.dagger.ElectronicDetailPresenterModule;
import com.zmsoft.ccd.module.electronic.helper.ElectronicHelper;

import javax.inject.Inject;

/**
 * 电子收款明细详情页
 *
 * @author DangGui
 * @create 2017/08/12.
 */
public class ElectronicDetailActivity extends ToolBarActivity {
    @Inject
    ElectronicDetailPresenter mPresenter;
    /**
     * 支付id
     */
    private String mPayId;
    /**
     * 支付code
     */
    private String mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        mPayId = getIntent().getStringExtra(ElectronicHelper.ExtraParams.PARAM_PAY_ID);
        mCode = getIntent().getStringExtra(ElectronicHelper.ExtraParams.PARAM_CODE);

        ElectronicDetailFragment fragment = (ElectronicDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (fragment == null) {
            fragment = ElectronicDetailFragment.newInstance(mPayId, mCode);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.content);
        }
        DaggerElectronicDetailComponent.builder()
                .electronicSourceComponent(
                        DaggerElectronicSourceComponent.builder()
                                .appComponent(CcdApplication.getInstance().getAppComponent())
                                .build())
                .electronicDetailPresenterModule(new ElectronicDetailPresenterModule(fragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Fragment fragment, String payId, String code) {
        Intent intent = new Intent(fragment.getActivity(), ElectronicDetailActivity.class);
        intent.putExtra(ElectronicHelper.ExtraParams.PARAM_PAY_ID, payId);
        intent.putExtra(ElectronicHelper.ExtraParams.PARAM_CODE, code);
        fragment.startActivityForResult(intent, ElectronicHelper.RequestCode.CODE_TO_DETAIL);
    }
}
