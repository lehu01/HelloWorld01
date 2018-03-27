package com.zmsoft.ccd.module.receipt.vipcard.detail;

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
import com.zmsoft.ccd.module.receipt.vipcard.detail.dagger.DaggerVipCardDetailComponent;
import com.zmsoft.ccd.module.receipt.vipcard.detail.dagger.VipCardDetailPresenterModule;
import com.zmsoft.ccd.module.receipt.vipcard.detail.fragment.VipCardDetailFragment;
import com.zmsoft.ccd.module.receipt.vipcard.detail.fragment.VipCardDetailPresenter;

import javax.inject.Inject;

/**
 * Description：会员卡详情
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 17:28
 */
@Route(path = RouterPathConstant.VipCardDetail.PATH)
public class VipCardDetailActivity extends ToolBarActivity {

    String mCardId;
    String mOrderId;
    String mKindPayId;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;
    @Inject
    VipCardDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_receipt_activity_simple);

        mOrderId = getIntent().getStringExtra(RouterPathConstant.VipCardDetail.ORDER_ID);
        mKindPayId = getIntent().getStringExtra(RouterPathConstant.VipCardDetail.EXTRA_KIND_PAY_ID);
        mCardId = getIntent().getStringExtra(RouterPathConstant.VipCardDetail.CARD_ID);
        mFee = getIntent().getDoubleExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, 0);
        VipCardDetailFragment fragment = (VipCardDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.linear_content);
        if (fragment == null) {
            fragment = VipCardDetailFragment.newInstance(mCardId, mOrderId, mKindPayId, mFee);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_content);
        }

        DaggerVipCardDetailComponent.builder()
                .vipCardSourceComponent(ComponentManager.get().getVipCardSourceComponent())
                .vipCardDetailPresenterModule(new VipCardDetailPresenterModule(fragment))
                .build()
                .inject(this);

    }

    public static void launchActivity(Fragment fragment, String orderId, String kindPayId, String carId
            , double fee) {
        Intent intent = new Intent(fragment.getActivity(), VipCardDetailActivity.class);
        intent.putExtra(RouterPathConstant.VipCardDetail.ORDER_ID, orderId);
        intent.putExtra(RouterPathConstant.VipCardDetail.EXTRA_KIND_PAY_ID, kindPayId);
        intent.putExtra(RouterPathConstant.VipCardDetail.CARD_ID, carId);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        fragment.startActivityForResult(intent, ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY);
    }
}
