package com.zmsoft.ccd.module.receipt.receiptway.normal.view;

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
import com.zmsoft.ccd.module.receipt.receiptway.normal.presenter.NormalReceiptPresenter;
import com.zmsoft.ccd.module.receipt.receiptway.normal.presenter.dagger.DaggerNormalReceiptComponent;
import com.zmsoft.ccd.module.receipt.receiptway.normal.presenter.dagger.NormalReceiptPresenterModule;

import javax.inject.Inject;

/**
 * 普通收款方式 包括现金收款/银行卡/免单/由客房结算/优惠券
 *
 * @author DangGui
 * @create 2017/5/26.
 */

public class NormalReceiptActivity extends ToolBarActivity {
    @Inject
    NormalReceiptPresenter mPresenter;

    private NormalReceiptFragment mNormalReceiptFragment;
    /**
     * 订单ID
     */
    private String mOrderId;
    /**
     * 付款方式名称
     */
    private String mPayName;
    /**
     * 订单编号
     */
    private int mOrderCode;
    /**
     * 桌位编码
     */
    private String mSeatCode;
    /**
     * 桌位名称
     */
    private String mSeatName;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;
    /**
     * 还需收款(单位 元)
     */
    private double mNeedFee;
    /**
     * 普通付款方式的类型（现金支付、银行卡支付、免单支付等）
     */
    private int mType;
    /**
     * 支付类型id
     */
    private String mKindPayId;
    /**
     * 普通付款方式的类型名称（现金支付、银行卡支付、免单支付等）
     */
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        mFee = getIntent().getDoubleExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, 0);
        mNeedFee = getIntent().getDoubleExtra(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, 0);
        mOrderId = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
        mType = getIntent().getIntExtra(ExtraConstants.NormalReceipt.EXTRA_TYPE, 0);
        mTitle = getIntent().getStringExtra(ExtraConstants.NormalReceipt.EXTRA_TITLE);
        mKindPayId = getIntent().getStringExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID);
        mPayName = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_PAY_NAME);
        mOrderCode = getIntent().getIntExtra(RouterPathConstant.Receipt.EXTRA_CODE, 0);
        mSeatCode = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_SEAT_CODE);
        mSeatName = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_SEAT_NAME);
        mNormalReceiptFragment = (NormalReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (mNormalReceiptFragment == null) {
            mNormalReceiptFragment = NormalReceiptFragment.newInstance(mOrderId, mFee, mNeedFee, mType, mTitle, mKindPayId
                    , mPayName, mSeatCode, mSeatName, mOrderCode);
            ActivityHelper.showFragment(getSupportFragmentManager(), mNormalReceiptFragment, R.id.content);
        }
        DaggerNormalReceiptComponent.builder()
                .receiptSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .normalReceiptPresenterModule(new NormalReceiptPresenterModule(mNormalReceiptFragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Fragment fragment, String orderId, double fee, double needFee, int type, String title
            , String kindPayId, String payName, String seatCode, String seatName, int orderCode) {
        Intent intent = new Intent(fragment.getActivity(), NormalReceiptActivity.class);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, needFee);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_TYPE, type);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_TITLE, title);
        intent.putExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID, kindPayId);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_PAY_NAME, payName);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_SEAT_CODE, seatCode);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_SEAT_NAME, seatName);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_CODE, orderCode);
        fragment.startActivityForResult(intent, ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY);
    }
}
