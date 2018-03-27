package com.zmsoft.ccd.module.receipt.verification.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ccd.lib.print.util.printer.LocalPrinterUtils;
import com.dfire.mobile.codereader.CodeReader;
import com.dfire.mobile.codereader.OnKeyCodeReadListener;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.dagger.ComponentManager;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.verification.presenter.VerificationCancleContract;
import com.zmsoft.ccd.module.receipt.verification.presenter.VerificationCanclePresenter;
import com.zmsoft.ccd.module.receipt.verification.presenter.dagger.DaggerVerificationCancleComponent;
import com.zmsoft.ccd.module.receipt.verification.presenter.dagger.VerificationCanclePresenterModule;
import com.zmsoft.ccd.receipt.bean.VerificationResponse;
import com.zmsoft.scan.lib.scan.BaseScanActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 核销优惠券
 *
 * @author DangGui
 * @create 2017/5/26.
 */

public class VerificationCancleActivity extends BaseScanActivity implements VerificationCancleContract.View, OnKeyCodeReadListener {
    @BindView(R2.id.text_hint)
    TextView mTextHint;
    @BindView(R2.id.text_fee)
    TextView mTextFee;

    @BindView(R2.id.text_fee_feifan)
    TextView mTextFeiFanFee;
    @BindView(R2.id.text_tip_feifan)
    TextView mTextFeiFanTip;

    @Inject
    VerificationCanclePresenter mPresenter;
    /**
     * 订单ID
     */
    private String mOrderId;

    private CodeReader mCodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_receipt_scan_layout);
        ButterKnife.bind(this);
        DaggerVerificationCancleComponent.builder()
                .receiptSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .verificationCanclePresenterModule(new VerificationCanclePresenterModule(this))
                .build()
                .inject(this);
        if (LocalPrinterUtils.isFeiFanPrinter()) {
            mCodeReader = new CodeReader(this, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LocalPrinterUtils.isFeiFanPrinter()) {
            hideCameraScanShowScanGun();
        }
    }

    @Override
    public boolean shouldInitCamera() {
        return !LocalPrinterUtils.isFeiFanPrinter();
    }

    @Override
    protected void onScanSuccess(String result) {
        mCodeScanView.pauseScan();
        mCodeScanView.hiddenScanRect();
        mTextHint.setVisibility(View.INVISIBLE);
        collectPay(result);
    }

    private void hideCameraScanShowScanGun() {
        mCodeScanView.pauseScan();
        mCodeScanView.hiddenScanRect();
        mCodeScanView.setVisibility(View.GONE);
        mTextHint.setVisibility(View.GONE);
        mTextFeiFanFee.setVisibility(View.VISIBLE);
        mTextFeiFanTip.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTextHint.setVisibility(View.VISIBLE);
    }

    @Override
    protected void init() {
        mOrderId = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
        mTextHint.setText(getString(R.string.module_receipt_verification_toast_please_scan));
        mTextFeiFanTip.setText(getString(R.string.module_receipt_verification_scan_gun_hint));
    }

    @Override
    public void setPresenter(VerificationCancleContract.Presenter presenter) {
        this.mPresenter = (VerificationCanclePresenter) presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
//        showToast(errorMessage);
    }

    @Override
    public void successCollectPay(VerificationResponse verificationResponse) {
        showToast(R.string.module_receipt_verification_success);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void failCollectPay(String errorMessage) {
        getDialogUtil().showNoticeDialog(R.string.material_dialog_title
                , errorMessage, R.string.dialog_hint_know, false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            if (!LocalPrinterUtils.isFeiFanPrinter()) {
                                onStart();
                            }
                        }
                    }
                });
    }

    private void collectPay(String auchCode) {
        mPresenter.collectPay(mOrderId, auchCode);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    public static void launchActivity(Fragment fragment, String orderId) {
        Intent intent = new Intent(fragment.getActivity(), VerificationCancleActivity.class);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        fragment.startActivityForResult(intent, ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mCodeReader != null) {
            return mCodeReader.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onKeyCodeRead(String result, int eventCode, boolean isFinish) {
        if (isFinish && !TextUtils.isEmpty(result)) {
            collectPay(result);
        }
    }
}
