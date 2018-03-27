package com.zmsoft.ccd.module.receipt.receiptway.onaccount.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.items.UnitRecyclerItem;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.view.UnitReceiptActivity;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.model.SignStaffItem;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.presenter.OnAccountReceiptContract;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.receipt.bean.GetSignBillSingerResponse;
import com.zmsoft.ccd.receipt.bean.PayDetail;

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

public class OnAccountReceiptFragment extends BaseFragment implements OnAccountReceiptContract.View {
    @BindView(R2.id.text_receivable)
    TextView mTextReceivable;
    @BindView(R2.id.edit_actual_received)
    EditFoodNumberView mEditActualReceived;
    @BindView(R2.id.text_actual_received)
    TextView mTextActualReceived;
    @BindView(R2.id.text_on_account)
    TextView mTextOnAccount;
    @BindView(R2.id.layout_on_account)
    RelativeLayout mLayoutOnAccount;
    @BindView(R2.id.layout_sign_staff_items)
    LinearLayout mLayoutSignStaffItems;
    @BindView(R2.id.button_sure)
    Button mButtonSure;
    @BindView(R2.id.layout_receivable_rate)
    RelativeLayout mLayoutReceivableRate;
    @BindView(R2.id.layout_actual_received)
    RelativeLayout mLayoutActualReceived;
    @BindView(R2.id.layout_root)
    LinearLayout mRootView;

    private List<SignStaffItem> mSignStaffItemList;
    private OnAccountReceiptContract.Presenter mPresenter;
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
    //实收金额默认等于应收金额
    private double mActualReceiveFee;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;
    /**
     * 选中的挂账单位（人）ITEM
     */
    private UnitRecyclerItem mSelectedUnitRecyclerItem;

    /**
     * 挂账单位（人）的KindPayDetailId，如果未选择挂账人时才需要该字段
     */
    private String mSignUnitKindPayDetailId;

    public static OnAccountReceiptFragment newInstance(double fee, double needFee, String title, String kindPayId
            , String orderId) {
        Bundle args = new Bundle();
        args.putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        args.putDouble(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        args.putDouble(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, needFee);
        args.putString(ExtraConstants.NormalReceipt.EXTRA_TITLE, title);
        args.putString(ExtraConstants.CouponReceipt.EXTRA_KIND_PAY_ID, kindPayId);
        OnAccountReceiptFragment fragment = new OnAccountReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_normal_receipt_fragment_layout;
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
            mActualReceiveFee = mReceiveableFee;
        }
        if (TextUtils.isEmpty(mTitle)) {
            getActivity().setTitle(R.string.module_receipt_onaccount);
        } else {
            getActivity().setTitle(mTitle);
        }
        mTextActualReceived.setText(UserHelper.getCurrencySymbol());
        mTextReceivable.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                , FeeHelper.getDecimalFee(mReceiveableFee)));
        mEditActualReceived.getEditText().setText(FeeHelper.getDecimalFee(mActualReceiveFee));
        CustomViewUtil.initEditViewFocousAll(mEditActualReceived.getEditText());
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
        RxView.clicks(mLayoutOnAccount).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        goToUnitActivity();
                    }
                });
        RxView.clicks(mButtonSure).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mActualReceiveFee = mEditActualReceived.getNumber();
                        if (mActualReceiveFee <= 0) {
                            ToastUtils.showShortToast(getActivity(), R.string.module_receipt_actual_receive_error_alert);
                            return;
                        }
                        int selectedIndex = -1;
                        if (null != mSignStaffItemList) {
                            for (int i = 0; i < mSignStaffItemList.size(); i++) {
                                if (mSignStaffItemList.get(i).isChecked()) {
                                    selectedIndex = i;
                                    break;
                                }
                            }
                        }
                        collectPay(selectedIndex);
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
        loadData();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    protected void loadData() {
        //初始化 挂账——签字员工列表
        mSignStaffItemList = new ArrayList<>();
        mPresenter.getSignBillSinger(mKindPayId);
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadData();
    }

    @Override
    public void setPresenter(OnAccountReceiptContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void successGetSignInfo(GetSignBillSingerResponse getSignBillSingerResponse) {
        mSignStaffItemList = DataMapLayer.getSignStaffItemList(getSignBillSingerResponse);
        mSignUnitKindPayDetailId = getSignBillSingerResponse.getSignUnitKindPayDetailId();
        initSignStaffListView(mSignStaffItemList);
        mRootView.setVisibility(View.VISIBLE);
        showContentView();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY:
                    mSelectedUnitRecyclerItem = data.getParcelableExtra(ExtraConstants.OnAccountReceipt.EXTRA_SELECT_UNIT);
                    if (null != mSelectedUnitRecyclerItem) {
                        mTextOnAccount.setText(mSelectedUnitRecyclerItem.getUnitName());
                    } else {
                        mTextOnAccount.setText("");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void goToUnitActivity() {
        UnitReceiptActivity.launchActivity(this, mKindPayId, mSelectedUnitRecyclerItem);
    }

    private void collectPay(int selectedIndex) {
        List<Fund> fundList = new ArrayList<>(1);
        Fund fund = new Fund();
        fund.setType((short) ReceiptHelper.ReceiptFund.TYPE_NORMAL_ONACCOUNT);
        fund.setKindPayId(mKindPayId);
        fund.setClassName(ReceiptHelper.ReceiptFund.CLASS_NORMAL_FUND);
        fund.setFee((int) Double.parseDouble(FeeHelper.getDecimalFeeByYuan(mActualReceiveFee)));
        List<PayDetail> payDetailList = new ArrayList<>(2);
        //签字员工
        if (selectedIndex > -1) {
            PayDetail signStaffPayDetail = new PayDetail();
            signStaffPayDetail.setKindPayDetailId(mSignStaffItemList.get(selectedIndex).getSignInfoVo().getKindPayDetailId());
            signStaffPayDetail.setKindPayDetailOptionId(mSignStaffItemList.get(selectedIndex).getSignInfoVo().getKindPayDetailOptionId());
            signStaffPayDetail.setMemo(mSignStaffItemList.get(selectedIndex).getSignInfoVo().getName());
            payDetailList.add(signStaffPayDetail);
        } else if (null != mSignStaffItemList && !mSignStaffItemList.isEmpty()) {
            PayDetail signStaffPayDetail = new PayDetail();
            signStaffPayDetail.setKindPayDetailId(mSignStaffItemList.get(0).getSignInfoVo().getKindPayDetailId());
            payDetailList.add(signStaffPayDetail);
        }
        //挂账单位（人）
        if (null != mSelectedUnitRecyclerItem) {
            PayDetail signUnitPayDetail = new PayDetail();
            signUnitPayDetail.setKindPayDetailId(mSelectedUnitRecyclerItem.getKindPayDetailId());
            signUnitPayDetail.setKindPayDetailOptionId(mSelectedUnitRecyclerItem.getKindPayDetailOptionId());
            signUnitPayDetail.setMemo(mSelectedUnitRecyclerItem.getUnitName());
            payDetailList.add(signUnitPayDetail);
        } else if (!TextUtils.isEmpty(mSignUnitKindPayDetailId)) {
            PayDetail signUnitPayDetail = new PayDetail();
            signUnitPayDetail.setKindPayDetailId(mSignUnitKindPayDetailId);
            payDetailList.add(signUnitPayDetail);
        }
        fund.setPayDetails(payDetailList);
        fundList.add(fund);
        mPresenter.collectPay(mOrderId, fundList);
    }

    /**
     * 初始化 挂账——签字员工列表
     *
     * @param signStaffItemList
     */
    private void initSignStaffListView(final List<SignStaffItem> signStaffItemList) {
        if (null == signStaffItemList) {
            return;
        }
        mLayoutSignStaffItems.removeAllViews();
        mLayoutSignStaffItems.setVisibility(View.VISIBLE);
        for (int i = 0; i < signStaffItemList.size(); i++) {
            final SignStaffItem staff = signStaffItemList.get(i);
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.module_receipt_normal_receipt_sign_staff_subitem
                    , null);
            TextView nameView = (TextView) itemView.findViewById(R.id.text_name);
            final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_staff);
            checkBox.setTag(staff.getName());
            checkBox.setChecked(staff.isChecked());
            nameView.setText(staff.getName());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((CheckBox) v).isChecked();
                    staff.setChecked(checked);
                    RxUtils.fromCallable(new Callable<List<SignStaffItem>>() {
                        @Override
                        public List<SignStaffItem> call() {
                            return signStaffItemList;
                        }
                    }).flatMap(new Func1<List<SignStaffItem>, Observable<SignStaffItem>>() {
                        @Override
                        public Observable<SignStaffItem> call(List<SignStaffItem> staffs) {
                            return Observable.from(staffs);
                        }
                    }).filter(new Func1<SignStaffItem, Boolean>() {
                        @Override
                        public Boolean call(SignStaffItem rxStaff) {
                            return !rxStaff.getName().equals(staff.getName());
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<SignStaffItem>() {
                                @Override
                                public void call(SignStaffItem subStaff) {
                                    subStaff.setChecked(false);
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {

                                }
                            }, new Action0() {
                                @Override
                                public void call() {
                                    initSignStaffListView(signStaffItemList);
                                }
                            });
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.performClick();
                }
            });
            mLayoutSignStaffItems.addView(itemView);
        }
    }
}
