package com.zmsoft.ccd.module.receipt.vipcard.input;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.dagger.ComponentManager;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.vipcard.input.dagger.DaggerInputVipCardComponent;
import com.zmsoft.ccd.module.receipt.vipcard.input.dagger.InputVipCardPresenterModule;
import com.zmsoft.ccd.module.receipt.vipcard.input.fragment.InputVipCardFragment;
import com.zmsoft.ccd.module.receipt.vipcard.input.fragment.InputVipCardPresenter;

import javax.inject.Inject;

/**
 * Description：会员卡输入
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 17:24
 */
@Route(path = RouterPathConstant.InputVipCar.PATH)
public class InputVipCardActivity extends ToolBarActivity {

    @Inject
    InputVipCardPresenter mPresenter;
    private String mOrderId;
    /**
     * 支付类型id
     */
    private String mKindPayId;
    private String mName;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_receipt_activity_simple);

        mOrderId = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
        mKindPayId = getIntent().getStringExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID);
        mName = getIntent().getStringExtra(ExtraConstants.NormalReceipt.EXTRA_TITLE);
        mFee = getIntent().getDoubleExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, 0);
        InputVipCardFragment fragment = (InputVipCardFragment)
                getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (fragment == null) {
            fragment = InputVipCardFragment.newInstance(mOrderId, mKindPayId, mName, mFee);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_content);
        }

        DaggerInputVipCardComponent.builder()
                .vipCardSourceComponent(ComponentManager.get().getVipCardSourceComponent())
                .inputVipCardPresenterModule(new InputVipCardPresenterModule(fragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Fragment fragment, String orderId, String kindPayId, String name, double fee) {
        Intent intent = new Intent(fragment.getActivity(), InputVipCardActivity.class);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        intent.putExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID, kindPayId);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_TITLE, name);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        fragment.startActivityForResult(intent, ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY);
    }
}

