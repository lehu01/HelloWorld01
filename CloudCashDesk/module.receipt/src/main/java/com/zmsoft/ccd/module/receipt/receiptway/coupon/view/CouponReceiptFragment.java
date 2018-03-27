package com.zmsoft.ccd.module.receipt.receiptway.coupon.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
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
import com.zmsoft.ccd.module.receipt.receipt.helper.DataMapLayer;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptIndustryHelper;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.receiptway.coupon.model.CouponItem;
import com.zmsoft.ccd.module.receipt.receiptway.coupon.presenter.CouponReceiptContract;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.GetVoucherInfoResponse;
import com.zmsoft.ccd.receipt.bean.VoucherFund;
import com.zmsoft.ccd.receipt.bean.VoucherInfoVo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 挂账
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class CouponReceiptFragment extends BaseFragment implements CouponReceiptContract.View {

    @BindView(R2.id.text_receivable)
    TextView mTextReceivable;
    @BindView(R2.id.layout_receivable_rate)
    RelativeLayout mLayoutReceivableRate;
    @BindView(R2.id.layout_coupon_fee_items)
    LinearLayout mLayoutCouponFeeItems;
    @BindView(R2.id.edit_coupon_num)
    EditFoodNumberView mEditCouponNum;
    @BindView(R2.id.layout_coupon_num)
    RelativeLayout mLayoutCouponNum;
    @BindView(R2.id.text_actual_received)
    TextView mTextActualReceived;
    @BindView(R2.id.layout_actual_received)
    RelativeLayout mLayoutActualReceived;
    @BindView(R2.id.text_discount_fee)
    TextView mTextDiscountFee;
    @BindView(R2.id.layout_discount)
    RelativeLayout mLayoutDiscount;
    @BindView(R2.id.button_sure)
    Button mButtonSure;
    @BindView(R2.id.linear_root)
    LinearLayout mRootView;

    private List<CouponItem> mCouponItemList;
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
    //实收金额
    private double mActualReceiveFee;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;
    /**
     * 代金券最小数量是1
     */
    private static final int MIN_COUPON_NUM = 1;
    /**
     * 当前输入的代金券数量
     */
    private int mCouponNum = MIN_COUPON_NUM;
    /**
     * 当前选中的代金券
     */
    private VoucherInfoVo mSelectedVoucherInfoVo;
    private CouponReceiptContract.Presenter mPresenter;

    public static CouponReceiptFragment newInstance(double fee, double needFee, String title, String kindPayId
            , String orderId) {
        Bundle args = new Bundle();
        args.putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        args.putDouble(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, needFee);
        args.putDouble(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        args.putString(ExtraConstants.NormalReceipt.EXTRA_TITLE, title);
        args.putString(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID, kindPayId);
        CouponReceiptFragment fragment = new CouponReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_coupon_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mOrderId = bundle.getString(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
            mFee = bundle.getDouble(ExtraConstants.NormalReceipt.EXTRA_FEE);
            mReceiveableFee = bundle.getDouble(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE);
            mTitle = bundle.getString(ExtraConstants.NormalReceipt.EXTRA_TITLE);
            mKindPayId = bundle.getString(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID);
        }
        if (TextUtils.isEmpty(mTitle)) {
            getActivity().setTitle(R.string.module_receipt_receipt);
        } else {
            getActivity().setTitle(mTitle);
        }
        CustomViewUtil.initEditViewFocousAll(mEditCouponNum.getEditText());
        mEditCouponNum.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    protected void initListener() {
        RxView.clicks(mLayoutCouponNum).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mEditCouponNum.requestFocus();
                        mEditCouponNum.getEditText().setSelection(mEditCouponNum.getEditText().length());
                        KeyboardUtils.showSoftInput(getActivity(), mEditCouponNum.getEditText());
                    }
                });
        mEditCouponNum.setOnEditTextChangeListener(new EditFoodNumberView.OnEditTextChangeListener() {
            @Override
            public void OnEditTextChange(double numberValue) {
                // 代金券数量修改后，实收金额、优惠金额要跟着相应做变化
                mCouponNum = (int) numberValue;
                initCouponView(false);
            }
        });
        RxView.clicks(mButtonSure).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String couponNumStr = mEditCouponNum.getEditText().getText().toString();
                        int couponNum = Integer.parseInt(couponNumStr);
                        if (couponNum <= 0) {
                            ToastUtils.showShortToast(getActivity(), R.string.module_receipt_coupon_num_error_alert);
                            return;
                        }
                        collectPay();
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
        if (null != mStateView) {
            mStateView.setEmptyResource(R.layout.module_receipt_coupon_empty_layout);
        }
        loadData();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    protected void loadData() {
        //初始化 代金券——面额列表
        mCouponItemList = new ArrayList<>();
        mPresenter.getVoucherInfo(mKindPayId);
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadData();
    }

    @Override
    public void setPresenter(CouponReceiptContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void successGetVoucherInfo(GetVoucherInfoResponse getVoucherInfoResponse) {
        mCouponItemList = DataMapLayer.getCouponItemList(getVoucherInfoResponse, mTitle);
        initCouponFeeListView(mCouponItemList);
    }

    @Override
    public void failGetData(ErrorBody errorBody) {
        if (mRootView.getVisibility() == View.GONE) {
            showErrorView(errorBody.getMessage());
        }
    }

    @Override
    public void successCollectPay(CloudCashCollectPayResponse cloudCashCollectPayResponse) {
        if (null == cloudCashCollectPayResponse) {
            failCollectPay(getString(R.string.module_receipt_fail));
        } else {
            if (cloudCashCollectPayResponse.getStatus() == ReceiptHelper.PayStatus.SUCCESS) {
                //实收 >= 应收 代表已付清,进入收款完成界面；否则回到收款界面
                if (mActualReceiveFee - mReceiveableFee >= 0) {
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

    /**
     * 收款
     */
    private void collectPay() {
        if (null != mSelectedVoucherInfoVo) {
            List<VoucherFund> fundList = new ArrayList<>(1);
            VoucherFund fund = new VoucherFund();
            fund.setType((short) ReceiptHelper.ReceiptFund.TYPE_COUPON);
            fund.setKindPayId(mKindPayId);
            fund.setClassName(ReceiptHelper.ReceiptFund.CLASS_COUPON_FUND);
            fund.setFee((int) Double.parseDouble(FeeHelper.getDecimalFeeByYuan(mActualReceiveFee)));
            fund.setVoucherId(mSelectedVoucherInfoVo.getVoucherId());
            fund.setCouponNum(mCouponNum);
            fund.setCouponFee(mSelectedVoucherInfoVo.getCouponFee());
            fund.setCouponCost(mSelectedVoucherInfoVo.getCouponCost());
            fund.setSign(mSelectedVoucherInfoVo.getSign());
            fundList.add(fund);
            mPresenter.collectPay(mOrderId, fundList);
        }
    }

    /**
     * 初始化应收、实收、优惠金额等ITEM
     *
     * @param isInit 是否是初始化
     */
    private void initCouponView(boolean isInit) {
        //如果是初始化（第一次加载，需要处理应收、代金券数量）
        if (isInit) {
            //应收金额
            mTextReceivable.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFee(mReceiveableFee)));
            //代金券数量
            mEditCouponNum.setNumberText(mCouponNum);
        }
        //实收金额，实收金额数值=代金券抵扣的面额*{代金券数量}。例如代金券45.0抵50.0，数量为2，则实收金额为50 X 2=100
        if (null != mSelectedVoucherInfoVo) {
            mActualReceiveFee = mSelectedVoucherInfoVo.getCouponFee() * mCouponNum;
        }
        mTextActualReceived.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                , FeeHelper.getDecimalFee(mActualReceiveFee)));
        //优惠金额，优惠金额=（代金券抵扣的面额-代金券面额）*{代金券数量}。例如代金券45.0抵50.0，数量为2，则优惠金额为（50-45） X 2=10
        double discountFee = 0;
        if (null != mSelectedVoucherInfoVo) {
            discountFee = (mSelectedVoucherInfoVo.getCouponFee() - mSelectedVoucherInfoVo.getCouponCost()) * mCouponNum;
        }
        mTextDiscountFee.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                , FeeHelper.getDecimalFee(discountFee)));
    }

    /**
     * 初始化  代金券——面额列表
     *
     * @param couponItemList
     */
    private void initCouponFeeListView(final List<CouponItem> couponItemList) {
        if (null != couponItemList && !couponItemList.isEmpty()) {
            mRootView.setVisibility(View.VISIBLE);
            mLayoutCouponFeeItems.removeAllViews();
            mLayoutCouponFeeItems.setVisibility(View.VISIBLE);
            for (int i = 0; i < couponItemList.size(); i++) {
                final CouponItem staff = couponItemList.get(i);
                View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.module_receipt_coupon_subitem
                        , null);
                TextView nameView = (TextView) itemView.findViewById(R.id.text_name);
                final RadioButton radioButton = (RadioButton) itemView.findViewById(R.id.checkbox_staff);
                radioButton.setTag(staff.getName());
                radioButton.setChecked(staff.isChecked());
                if (staff.isChecked()) {
                    mSelectedVoucherInfoVo = staff.getVoucherInfoVo();
                }
                nameView.setText(staff.getName());
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((RadioButton) v).isChecked();
                        staff.setChecked(checked);
                        if (checked) {
                            mSelectedVoucherInfoVo = staff.getVoucherInfoVo();
                        }
                        RxUtils.fromCallable(new Callable<List<CouponItem>>() {
                            @Override
                            public List<CouponItem> call() {
                                return couponItemList;
                            }
                        }).flatMap(new Func1<List<CouponItem>, Observable<CouponItem>>() {
                            @Override
                            public Observable<CouponItem> call(List<CouponItem> staffs) {
                                return Observable.from(staffs);
                            }
                        }).filter(new Func1<CouponItem, Boolean>() {
                            @Override
                            public Boolean call(CouponItem rxCouponItem) {
                                return !rxCouponItem.getName().equals(staff.getName());
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<CouponItem>() {
                                    @Override
                                    public void call(CouponItem subCouponItem) {
                                        subCouponItem.setChecked(false);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                }, new Action0() {
                                    @Override
                                    public void call() {
                                        initCouponFeeListView(couponItemList);
                                    }
                                });
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radioButton.performClick();
                    }
                });
                mLayoutCouponFeeItems.addView(itemView);
            }
            showContentView();
        } else {
            showEmptyView();
        }
        initCouponView(true);
    }
}
