package com.zmsoft.ccd.module.receipt.complete.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.complete.presenter.CompletReceiptPresenter;
import com.zmsoft.ccd.module.receipt.complete.presenter.dagger.CompletReceiptPresenterModule;
import com.zmsoft.ccd.module.receipt.complete.presenter.dagger.DaggerCompletReceiptComponent;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.dagger.ComponentManager;

import javax.inject.Inject;

/**
 * 收款完成
 *
 * @author DangGui
 * @create 2017/5/26.
 */

public class CompleteReceiptActivity extends ToolBarActivity {
    @Inject
    CompletReceiptPresenter mPresenter;

    private CompletReceiptFragment mNormalReceiptFragment;
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
        mNormalReceiptFragment = (CompletReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (mNormalReceiptFragment == null) {
            mNormalReceiptFragment = CompletReceiptFragment.newInstance(mOrderId, mModifyTime, mReceiveableFee);
            ActivityHelper.showFragment(getSupportFragmentManager(), mNormalReceiptFragment, R.id.content);
        }
        DaggerCompletReceiptComponent.builder()
                .receiptSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .completReceiptPresenterModule(new CompletReceiptPresenterModule(mNormalReceiptFragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Context context, String orderId, long modifyTime, double receiveableFee) {
        Intent intent = new Intent(context, CompleteReceiptActivity.class);
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
