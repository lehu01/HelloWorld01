package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.dagger.ComponentManager;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.items.UnitRecyclerItem;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.presenter.UnitReceiptPresenter;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.presenter.dagger.DaggerUnitReceiptComponent;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.presenter.dagger.UnitReceiptPresenterModule;

import javax.inject.Inject;

/**
 * 挂账——选择挂账单位（人）
 *
 * @author DangGui
 * @create 2017/5/26.
 */

public class UnitReceiptActivity extends ToolBarActivity {
    @Inject
    UnitReceiptPresenter mPresenter;

    private UnitReceiptFragment mNormalReceiptFragment;
    /**
     * 支付类型id
     */
    private String mKindPayId;
    /**
     * 选中的挂账单位（人）ITEM
     */
    private UnitRecyclerItem mSelectedUnitRecyclerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        mKindPayId = getIntent().getStringExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID);
        mSelectedUnitRecyclerItem = getIntent().getParcelableExtra(ExtraConstants.OnAccountReceipt.EXTRA_SELECT_UNIT);
        mNormalReceiptFragment = (UnitReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (mNormalReceiptFragment == null) {
            mNormalReceiptFragment = UnitReceiptFragment.newInstance(mKindPayId, mSelectedUnitRecyclerItem);
            ActivityHelper.showFragment(getSupportFragmentManager(), mNormalReceiptFragment, R.id.content);
        }
        DaggerUnitReceiptComponent.builder()
                .receiptSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .unitReceiptPresenterModule(new UnitReceiptPresenterModule(mNormalReceiptFragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Fragment fragment, String kindPayId, UnitRecyclerItem selectedUnitRecyclerItem) {
        Intent intent = new Intent(fragment.getActivity(), UnitReceiptActivity.class);
        intent.putExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID, kindPayId);
        intent.putExtra(ExtraConstants.OnAccountReceipt.EXTRA_SELECT_UNIT, selectedUnitRecyclerItem);
        fragment.startActivityForResult(intent, ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mNormalReceiptFragment != null && !mNormalReceiptFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
