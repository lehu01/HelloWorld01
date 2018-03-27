package com.zmsoft.ccd.module.receipt.complete.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.complete.presenter.RetailCompleteReceiptPresenter;
import com.zmsoft.ccd.module.receipt.complete.presenter.dagger.DaggerRetailCompleteReceiptComponent;
import com.zmsoft.ccd.module.receipt.complete.presenter.dagger.RetailCompleteReceiptPresenterModule;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.dagger.ComponentManager;

import javax.inject.Inject;

/**
 * 收款完成
 *
 * @author DangGui
 * @create 2017/5/26.
 */

public class RetailCompleteReceiptActivity extends ToolBarActivity {
    @Inject
    RetailCompleteReceiptPresenter mPresenter;

    private RetailCompleteReceiptFragment mNormalReceiptFragment;

    /**
     * 订单ID
     */
    private String mOrderId;

    //应收金额(单位 元)
    private double mReceiveableFee;
    /**
     * 订单修改时间
     */
    private long mModifyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        mOrderId = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
        mModifyTime = getIntent().getLongExtra(ExtraConstants.CompleteReceipt.EXTRA_MODIFY_TIME, 0);
        mReceiveableFee = getIntent().getDoubleExtra(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, 0);
        mNormalReceiptFragment = (RetailCompleteReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (mNormalReceiptFragment == null) {
            mNormalReceiptFragment = RetailCompleteReceiptFragment.newInstance(mOrderId, mModifyTime, mReceiveableFee);
            ActivityHelper.showFragment(getSupportFragmentManager(), mNormalReceiptFragment, R.id.content);
        }
        DaggerRetailCompleteReceiptComponent.builder()
                .receiptSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .retailCompleteReceiptPresenterModule(new RetailCompleteReceiptPresenterModule(mNormalReceiptFragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Context context, String orderId, long modifyTime, double receiveableFee) {
        Intent intent = new Intent(context, RetailCompleteReceiptActivity.class);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        intent.putExtra(ExtraConstants.CompleteReceipt.EXTRA_MODIFY_TIME, modifyTime);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, receiveableFee);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
