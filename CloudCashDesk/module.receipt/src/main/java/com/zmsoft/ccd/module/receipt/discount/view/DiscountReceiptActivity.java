package com.zmsoft.ccd.module.receipt.discount.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.dagger.ComponentManager;
import com.zmsoft.ccd.module.receipt.discount.presenter.DiscountReceiptPresenter;
import com.zmsoft.ccd.module.receipt.discount.presenter.dagger.DaggerDiscountReceiptComponent;
import com.zmsoft.ccd.module.receipt.discount.presenter.dagger.DiscountReceiptPresenterModule;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;

import javax.inject.Inject;

/**
 * 整单打折
 *
 * @author DangGui
 * @create 2017/5/26.
 */

public class DiscountReceiptActivity extends ToolBarActivity {
    @Inject
    DiscountReceiptPresenter mPresenter;

    private DiscountReceiptFragment mNormalReceiptFragment;
    /**
     * 订单ID
     */
    private String mOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        mOrderId = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
        mNormalReceiptFragment = (DiscountReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (mNormalReceiptFragment == null) {
            mNormalReceiptFragment = DiscountReceiptFragment.newInstance(mOrderId);
            ActivityHelper.showFragment(getSupportFragmentManager(), mNormalReceiptFragment, R.id.content);
        }
        DaggerDiscountReceiptComponent.builder()
                .receiptSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .discountReceiptPresenterModule(new DiscountReceiptPresenterModule(mNormalReceiptFragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Fragment fragment, String orderId) {
        Intent intent = new Intent(fragment.getActivity(), DiscountReceiptActivity.class);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        fragment.startActivityForResult(intent, ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY);
    }
}
