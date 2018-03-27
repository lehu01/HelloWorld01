package com.zmsoft.ccd.module.receipt.receiptway.onaccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.dagger.ComponentManager;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.presenter.OnAccountReceiptPresenter;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.presenter.dagger.DaggerOnAccountReceiptComponent;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.presenter.dagger.OnAccountReceiptPresenterModule;

import javax.inject.Inject;

/**
 * 挂账
 *
 * @author DangGui
 * @create 2017/5/26.
 */

public class OnAccountReceiptActivity extends ToolBarActivity {
    @Inject
    OnAccountReceiptPresenter mPresenter;

    private OnAccountReceiptFragment mNormalReceiptFragment;
    /**
     * 订单ID
     */
    private String mOrderId;
    /**
     * 支付类型id
     */
    private String mKindPayId;
    /**
     * 代金券付款方式的类型名称（代金券）
     */
    private String mTitle;

    //应收金额(单位 元)
    private double mReceiveableFee;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        mOrderId = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
        mFee = getIntent().getDoubleExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, 0);
        mReceiveableFee = getIntent().getDoubleExtra(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, 0);
        mTitle = getIntent().getStringExtra(ExtraConstants.NormalReceipt.EXTRA_TITLE);
        mKindPayId = getIntent().getStringExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID);
        mNormalReceiptFragment = (OnAccountReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (mNormalReceiptFragment == null) {
            mNormalReceiptFragment = OnAccountReceiptFragment.newInstance(mFee, mReceiveableFee, mTitle, mKindPayId, mOrderId);
            ActivityHelper.showFragment(getSupportFragmentManager(), mNormalReceiptFragment, R.id.content);
        }
        DaggerOnAccountReceiptComponent.builder()
                .receiptSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .onAccountReceiptPresenterModule(new OnAccountReceiptPresenterModule(mNormalReceiptFragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Fragment fragment, double fee, double needFee, String title, String kindPayId, String orderId) {
        Intent intent = new Intent(fragment.getActivity(), OnAccountReceiptActivity.class);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, needFee);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_TITLE, title);
        intent.putExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID, kindPayId);
        fragment.startActivityForResult(intent, ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY);
    }
}
