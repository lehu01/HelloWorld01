package com.zmsoft.ccd.module.receipt.receipt.view;

import android.os.Bundle;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.dagger.ComponentManager;
import com.zmsoft.ccd.module.receipt.receipt.presenter.ReceiptPresenter;
import com.zmsoft.ccd.module.receipt.receipt.presenter.dagger.DaggerReceiptComponent;
import com.zmsoft.ccd.module.receipt.receipt.presenter.dagger.ReceiptPresenterModule;

import javax.inject.Inject;

/**
 * 收款首页
 *
 * @author DangGui
 * @create 2017/5/24.
 */
@Route(path = RouterPathConstant.Receipt.PATH)
public class ReceiptActivity extends ToolBarActivity {
    @Inject
    ReceiptPresenter mPresenter;

    @Autowired(name = RouterPathConstant.Receipt.EXTRA_ORDER_ID)
    String mOrderId;
    @Autowired(name = RouterPathConstant.Receipt.EXTRA_SEAT_CODE)
    String mSeatCode;
    @Autowired(name = RouterPathConstant.Receipt.EXTRA_SEAT_NAME)
    String mSeatName;
    @Autowired(name = RouterPathConstant.Receipt.EXTRA_CODE)
    int mOrderCode;
    /**
     * 来源，普通付款、外卖付款等
     *
     * @see com.zmsoft.ccd.lib.base.constant.RouterPathConstant.Receipt
     */
    @Autowired(name = RouterPathConstant.Receipt.EXTRA_THIRD_TAKEOUT)
    boolean mThirdTakeout;

    private ReceiptFragment mReceiptFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        mReceiptFragment = (ReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (mReceiptFragment == null) {
            mReceiptFragment = ReceiptFragment.newInstance(mOrderId, mSeatCode, mSeatName, mOrderCode
                    , mThirdTakeout);
            ActivityHelper.showFragment(getSupportFragmentManager(), mReceiptFragment, R.id.content);
        }

        DaggerReceiptComponent.builder()
                .receiptSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .receiptPresenterModule(new ReceiptPresenterModule(mReceiptFragment))
                .build()
                .inject(this);
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
        if (mReceiptFragment != null && !mReceiptFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
