package com.zmsoft.ccd.module.receipt.receiptway.normal.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.KeyboardUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptIndustryHelper;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.receiptway.normal.presenter.NormalReceiptContract;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.receipt.bean.GetKindDetailInfoResponse;
import com.zmsoft.ccd.receipt.bean.KindPayDetailDto;
import com.zmsoft.ccd.receipt.bean.KindPayDetailOptionDto;
import com.zmsoft.ccd.receipt.bean.PayDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 普通收款方式 包括现金收款/银行卡/代金券/挂账等方式
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class NormalReceiptFragment extends BaseFragment implements NormalReceiptContract.View {

    @BindView(R2.id.text_receivable)
    TextView mTextReceivable;
    @BindView(R2.id.layout_receivable_rate)
    RelativeLayout mLayoutReceivableRate;
    @BindView(R2.id.edit_actual_received)
    EditFoodNumberView mEditActualReceived;
    @BindView(R2.id.text_actual_received)
    TextView mTextActualReceived;
    @BindView(R2.id.layout_actual_received)
    RelativeLayout mLayoutActualReceived;
    @BindView(R2.id.edit_change)
    EditFoodNumberView mEditChange;
    @BindView(R2.id.layout_change)
    RelativeLayout mLayoutChange;
    @BindView(R2.id.edit_reason)
    EditText mEditReason;
    @BindView(R2.id.layout_free_charge)
    LinearLayout mLayoutFreeCharge;
    @BindView(R2.id.button_sure)
    Button mButtonSure;
    @BindView(R2.id.layout_root)
    LinearLayout mRootView;

    private NormalReceiptContract.Presenter mPresenter;
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
     * 普通付款方式的类型（现金支付、银行卡支付、免单支付等）
     */
    private int mType;
    /**
     * 普通付款方式的类型名称（现金支付、银行卡支付、免单支付等）
     */
    private String mTitle;
    /**
     * 支付类型id
     */
    private String mKindPayId;
    //详细信息id(免单需要)
    private String mKindPayDetailId;
    //详细信息选项id(免单需要)
    private String mKindPayDetailOptionId;
    //应收金额(单位 元)
    private double mReceiveableFee;
    //实收金额默认等于应收金额
    private double mActualReceiveFee;
    //找零金额
    private double mChangeFee = 0;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;

    public static NormalReceiptFragment newInstance(String orderId, double fee, double needFee, int type, String title
            , String kindPayId, String payName, String seatCode, String seatName, int orderCode) {
        Bundle args = new Bundle();
        args.putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        args.putDouble(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        args.putDouble(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, needFee);
        args.putInt(ExtraConstants.NormalReceipt.EXTRA_TYPE, type);
        args.putString(ExtraConstants.NormalReceipt.EXTRA_TITLE, title);
        args.putString(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID, kindPayId);
        args.putString(RouterPathConstant.Receipt.EXTRA_PAY_NAME, payName);
        args.putString(RouterPathConstant.Receipt.EXTRA_SEAT_CODE, seatCode);
        args.putString(RouterPathConstant.Receipt.EXTRA_SEAT_NAME, seatName);
        args.putInt(RouterPathConstant.Receipt.EXTRA_CODE, orderCode);
        NormalReceiptFragment fragment = new NormalReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_cash_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mOrderId = bundle.getString(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
            mFee = bundle.getDouble(ExtraConstants.NormalReceipt.EXTRA_FEE);
            mReceiveableFee = bundle.getDouble(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE);
            mType = bundle.getInt(ExtraConstants.NormalReceipt.EXTRA_TYPE);
            mTitle = bundle.getString(ExtraConstants.NormalReceipt.EXTRA_TITLE);
            mKindPayId = bundle.getString(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID);
            mPayName = bundle.getString(RouterPathConstant.Receipt.EXTRA_PAY_NAME);
            mOrderCode = bundle.getInt(RouterPathConstant.Receipt.EXTRA_CODE);
            mSeatCode = bundle.getString(RouterPathConstant.Receipt.EXTRA_SEAT_CODE);
            mSeatName = bundle.getString(RouterPathConstant.Receipt.EXTRA_SEAT_NAME);
            mActualReceiveFee = mReceiveableFee;
        }
        if (TextUtils.isEmpty(mTitle)) {
            getActivity().setTitle(R.string.module_receipt_receipt);
        } else {
            getActivity().setTitle(mTitle);
        }
        switch (mType) {
            case ReceiptHelper.ReceiptFund.TYPE_NORMAL_CASH:
                mLayoutChange.setVisibility(View.VISIBLE);
                break;
            case ReceiptHelper.ReceiptFund.TYPE_NORMAL_FREE:
                mLayoutFreeCharge.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        mTextActualReceived.setText(UserHelper.getCurrencySymbol());
        mTextReceivable.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                , FeeHelper.getDecimalFee(mReceiveableFee)));
        mEditActualReceived.getEditText().setText(FeeHelper.getDecimalFee(mActualReceiveFee));
        CustomViewUtil.initEditViewFocousAll(mEditActualReceived.getEditText());
        CustomViewUtil.initEditViewFocousAll(mEditChange.getEditText());
    }

    @Override
    protected void initListener() {
        RxView.clicks(mLayoutActualReceived).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mEditActualReceived.requestFocus();
                        mEditActualReceived.getEditText().selectAll();
//                        mEditActualReceived.getEditText().setSelection(mEditActualReceived.getEditText().length());
                        KeyboardUtils.showSoftInput(getActivity(), mEditActualReceived.getEditText());
                    }
                });
        RxView.clicks(mLayoutChange).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mEditChange.requestFocus();
                        mEditChange.getEditText().selectAll();
//                        mEditChange.getEditText().setSelection(mEditChange.getEditText().length());
                        KeyboardUtils.showSoftInput(getActivity(), mEditChange.getEditText());
                    }
                });
        mEditActualReceived.setOnEditTextChangeListener(new EditFoodNumberView.OnEditTextChangeListener() {
            @Override
            public void OnEditTextChange(double numberValue) {
                if (mType == ReceiptHelper.ReceiptFund.TYPE_NORMAL_CASH) {
                    handleActualReceiveChange(numberValue);
                }
            }
        });
        RxView.clicks(mButtonSure).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        double actualReceiveFee = mEditActualReceived.getNumber();
                        if (actualReceiveFee <= 0) {
                            ToastUtils.showShortToast(getActivity(), R.string.module_receipt_actual_receive_error_alert);
                            return;
                        }
                        mActualReceiveFee = mEditActualReceived.getNumber();
                        mChangeFee = mEditChange.getNumber();
                        List<Fund> fundList = new ArrayList<>(1);
                        Fund fund = new Fund();
                        fund.setType((short) mType);
                        fund.setKindPayId(mKindPayId);
                        fund.setClassName(ReceiptHelper.ReceiptFund.CLASS_NORMAL_FUND);
                        fund.setFee((int) Double.parseDouble(FeeHelper.getDecimalFeeByYuan(mActualReceiveFee)));
                        //现金收款才需要找零
                        if (mType == ReceiptHelper.ReceiptFund.TYPE_NORMAL_CASH) {
                            fund.setCharge(mChangeFee);
                        } else if (mType == ReceiptHelper.ReceiptFund.TYPE_NORMAL_FREE) {
                            List<PayDetail> payDetailList = new ArrayList<>(1);
                            //免单原因
                            PayDetail reasonPayDetail = new PayDetail();
                            reasonPayDetail.setKindPayDetailId(mKindPayDetailId);
                            reasonPayDetail.setKindPayDetailOptionId(mKindPayDetailOptionId);
                            reasonPayDetail.setMemo(mEditReason.getText().toString().trim());
                            payDetailList.add(reasonPayDetail);
                            fund.setPayDetails(payDetailList);
                        }
                        fundList.add(fund);
                        mPresenter.collectPay(mOrderId, mPayName, mOrderCode, mSeatCode, mSeatName, fundList);
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
        if (mType == ReceiptHelper.ReceiptFund.TYPE_NORMAL_FREE) {
            loadData();
        } else {
            mRootView.setVisibility(View.VISIBLE);
            showContentView();
        }
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    protected void loadData() {
        mPresenter.getKindDetailInfo(getString(R.string.module_receipt_free_charge_reason), ReceiptHelper.KindDetailMode.INPUT
                , mKindPayId);
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        if (mType == ReceiptHelper.ReceiptFund.TYPE_NORMAL_FREE) {
            loadData();
        } else {
            mRootView.setVisibility(View.VISIBLE);
            showContentView();
        }
    }

    @Override
    public void setPresenter(NormalReceiptContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
//        showToast(errorMessage);
    }

    @Override
    public void successGetKindDetailInfo(GetKindDetailInfoResponse getKindDetailInfoResponse) {
        if (null != getKindDetailInfoResponse && null != getKindDetailInfoResponse.getKindPayDetailDtoList()
                && !getKindDetailInfoResponse.getKindPayDetailDtoList().isEmpty()) {
            List<KindPayDetailDto> kindPayDetailDtoList = getKindDetailInfoResponse.getKindPayDetailDtoList();
            KindPayDetailDto kindPayDetailDto = kindPayDetailDtoList.get(0);
            if (null != kindPayDetailDto && null != kindPayDetailDto.getKindPayDetailOptionDtos()
                    && !kindPayDetailDto.getKindPayDetailOptionDtos().isEmpty()) {
                List<KindPayDetailOptionDto> kindPayDetailOptionDtoList = kindPayDetailDto.getKindPayDetailOptionDtos();
                KindPayDetailOptionDto kindPayDetailOptionDto = kindPayDetailOptionDtoList.get(0);
                if (null != kindPayDetailOptionDto) {
                    mKindPayDetailId = kindPayDetailOptionDto.getKindPayDetailId();
                    mKindPayDetailOptionId = kindPayDetailOptionDto.getId();
                }
            }
        }
        mRootView.setVisibility(View.VISIBLE);
        showContentView();

    }

    @Override
    public void failGetKindDetailInfo(ErrorBody errorBody) {
        showErrorView(errorBody.getMessage());
    }

    @Override
    public void successCollectPay(CloudCashCollectPayResponse cloudCashCollectPayResponse) {
        if (null == cloudCashCollectPayResponse) {
            failCollectPay(getString(R.string.module_receipt_fail));
        } else {
            if (cloudCashCollectPayResponse.getStatus() == ReceiptHelper.PayStatus.SUCCESS) {
                //  2017/7/14 现金收款成功，需要调用接口通知服务端这次收款，总共的收款金额，实收金额是mActualReceiveFee,找零是mChangeFee
//                showToast(R.string.module_receipt_success);
                //实收 >= 应收 代表已付清,进入收款完成界面；否则回到收款界面
                if (mActualReceiveFee - (mReceiveableFee + mChangeFee) >= 0) {
                    ReceiptIndustryHelper.gotoCompleteReceipt(getActivity(), mOrderId
                            , cloudCashCollectPayResponse.getModifyTime(), mFee);
                } else {
                    showToast(R.string.module_receipt_success);
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
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
                        }
                    }
                });
    }

    private void handleActualReceiveChange(double actualNumberValue) {
        //如果实收金额小于等于应收金额，则找零金额为0；如果实收金额大于应收金额，则找零金额=实收金额-应收金额
        if (actualNumberValue <= mReceiveableFee) {
            mChangeFee = 0;
        } else {
            mChangeFee = actualNumberValue - mReceiveableFee;
        }
        if (null != mEditChange) {
            mEditChange.setNumberText(mChangeFee);
        }
    }
}
