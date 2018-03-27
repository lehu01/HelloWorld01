package com.zmsoft.ccd.module.receipt.receiptway.scan.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ccd.lib.print.util.printer.LocalPrinterUtils;
import com.dfire.mobile.codereader.CodeReader;
import com.dfire.mobile.codereader.OnKeyCodeReadListener;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NetworkUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.dagger.ComponentManager;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptIndustryHelper;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.receipt.model.ThirdFund;
import com.zmsoft.ccd.module.receipt.receiptway.scan.presenter.ScanReceiptContract;
import com.zmsoft.ccd.module.receipt.receiptway.scan.presenter.ScanReceiptPresenter;
import com.zmsoft.ccd.module.receipt.receiptway.scan.presenter.dagger.DaggerScanReceiptComponent;
import com.zmsoft.ccd.module.receipt.receiptway.scan.presenter.dagger.ScanReceiptPresenterModule;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.scan.lib.scan.BaseScanActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 二维码扫描付款包括 微信、支付宝
 *
 * @author DangGui
 * @create 2017/5/26.
 */

public class ScanReceiptActivity extends BaseScanActivity implements ScanReceiptContract.View, OnKeyCodeReadListener {
    @Inject
    ScanReceiptPresenter mPresenter;
    @BindView(R2.id.text_hint)
    TextView mTextHint;
    @BindView(R2.id.text_fee)
    TextView mTextFee;


    @BindView(R2.id.text_fee_feifan)
    TextView mTextFeiFanFee;
    @BindView(R2.id.text_tip_feifan)
    TextView mTextFeiFanTip;
    /**
     * 订单ID
     */
    private String mOrderId;
    /**
     * 普通付款方式的类型（现金支付、银行卡支付、免单支付等）
     */
    private int mType;
    /**
     * 普通付款方式的类型名称（现金支付、银行卡支付、免单支付等）
     */
    private String mTitle;
    /**
     * 还需收款(单位 元)
     */
    private double mNeedFee;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;
    /**
     * 手机IP地址
     */
    private String localIP;

    /**
     * 支付类型id
     */
    private String mKindPayId;

    private CodeReader mCodeReader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_receipt_scan_layout);
        ButterKnife.bind(this);
        DaggerScanReceiptComponent.builder()
                .receiptSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .scanReceiptPresenterModule(new ScanReceiptPresenterModule(this))
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
        mTextFee.setVisibility(View.INVISIBLE);
        collectPay(result);
    }

    private void hideCameraScanShowScanGun() {
        mCodeScanView.pauseScan();
        mCodeScanView.hiddenScanRect();
        mCodeScanView.setVisibility(View.GONE);
        mTextHint.setVisibility(View.GONE);
        mTextFee.setVisibility(View.GONE);
        mTextFeiFanFee.setVisibility(View.VISIBLE);
        mTextFeiFanTip.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTextHint.setVisibility(View.VISIBLE);
        mTextFee.setVisibility(View.VISIBLE);
    }

    @Override
    protected void init() {
        mFee = getIntent().getDoubleExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, 0);
        mNeedFee = getIntent().getDoubleExtra(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, 0);
        mOrderId = getIntent().getStringExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
        mType = getIntent().getIntExtra(ExtraConstants.NormalReceipt.EXTRA_TYPE, 0);
        mTitle = getIntent().getStringExtra(ExtraConstants.NormalReceipt.EXTRA_TITLE);
        mKindPayId = getIntent().getStringExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID);
        if (!TextUtils.isEmpty(mTitle)) {
            setTitle(mTitle);
        } else {
            setTitle(R.string.module_receipt_receipt);
        }
        if (mType == ReceiptHelper.ReceiptFund.TYPE_THIRD_ALIPAY) {
            mTextHint.setText(String.format(getResources().getString(R.string.module_receipt_scan_hint)
                    , getString(R.string.alipay)));
            mTextFeiFanTip.setText(String.format(getResources().getString(R.string.module_receipt_scan_gun_hint)
                    , getString(R.string.alipay)));
        } else if (mType == ReceiptHelper.ReceiptFund.TYPE_THIRD_WEIXIN) {
            mTextHint.setText(String.format(getResources().getString(R.string.module_receipt_scan_hint)
                    , getString(R.string.weixin)));
            mTextFeiFanTip.setText(String.format(getResources().getString(R.string.module_receipt_scan_gun_hint)
                    , getString(R.string.weixin)));
        } else if (mType == ReceiptHelper.ReceiptFund.TYPE_THIRD_QQ) {
            mTextHint.setText(String.format(getResources().getString(R.string.module_receipt_scan_hint)
                    , getString(R.string.QQ)));
            mTextFeiFanTip.setText(String.format(getResources().getString(R.string.module_receipt_scan_gun_hint)
                    , getString(R.string.QQ)));
        } else {
            mTextHint.setText(String.format(getResources().getString(R.string.module_receipt_scan_hint)
                    , ""));
            mTextFeiFanTip.setText(String.format(getResources().getString(R.string.module_receipt_scan_gun_hint)
                    , ""));
        }
        mTextFee.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                , FeeHelper.getDecimalFee(mNeedFee)));
        mTextFeiFanFee.setText(mTextFee.getText());
        localIP = NetworkUtils.getIPAddress(true);
    }

    @Override
    public void setPresenter(ScanReceiptContract.Presenter presenter) {
        this.mPresenter = (ScanReceiptPresenter) presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void successCollectPay(CloudCashCollectPayResponse cloudCashCollectPayResponse) {
        if (null == cloudCashCollectPayResponse) {
            failCollectPay(getString(R.string.module_receipt_fail));
        } else {
            if (cloudCashCollectPayResponse.getStatus() == ReceiptHelper.PayStatus.SUCCESS) {
                ReceiptIndustryHelper.gotoCompleteReceipt(ScanReceiptActivity.this, mOrderId
                        , cloudCashCollectPayResponse.getModifyTime(), mFee);
            } else if (cloudCashCollectPayResponse.getStatus() == ReceiptHelper.PayStatus.FAILURE) {
                failCollectPay(getString(R.string.module_receipt_fail));
            }
        }
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
        List<ThirdFund> fundList = new ArrayList<>(1);
        ThirdFund fund = new ThirdFund();
        fund.setType((short) mType);
        fund.setKindPayId(mKindPayId);
        fund.setClassName(ReceiptHelper.ReceiptFund.CLASS_THIRD_FUND);
        fund.setFee((int) Double.parseDouble(FeeHelper.getDecimalFeeByYuan(mNeedFee)));
        fund.setAuthCode(auchCode);
        if (!TextUtils.isEmpty(localIP)) {
            fund.setRemoteAddr(localIP);
        }
        fundList.add(fund);
        mPresenter.collectPay(mOrderId, fundList);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    public static void launchActivity(Context context, String orderId, double fee, double needFee, int type, String title
            , String kindPayId) {
        Intent intent = new Intent(context, ScanReceiptActivity.class);
        intent.putExtra(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, needFee);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_TYPE, type);
        intent.putExtra(ExtraConstants.NormalReceipt.EXTRA_TITLE, title);
        intent.putExtra(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID, kindPayId);
        context.startActivity(intent);
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
