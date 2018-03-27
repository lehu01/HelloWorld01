package com.zmsoft.ccd.module.receipt.discount.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.utils.KeyboardUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.ListCallbackSingleChoice;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.discount.presenter.DiscountReceiptContract;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.receipt.bean.CashPromotionBillResponse;
import com.zmsoft.ccd.receipt.bean.Promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 整单打折
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class DiscountReceiptFragment extends BaseFragment implements DiscountReceiptContract.View {

    @BindView(R2.id.edit_discount_rate)
    EditFoodNumberView mEditDiscountRate;
    @BindView(R2.id.layout_discount_rate)
    RelativeLayout mLayoutDiscountRate;
    @BindView(R2.id.text_discount_reason)
    TextView mTextDiscountReason;
    @BindView(R2.id.layout_discount_reason)
    RelativeLayout mLayoutDiscountReason;
    @BindView(R2.id.checkbox_discount)
    SwitchCompat mCheckboxDiscount;
    @BindView(R2.id.layout_discount_switch)
    LinearLayout mLayoutDiscountSwitch;
    @BindView(R2.id.button_sure)
    Button mButtonSure;
    @BindView(R2.id.layout_root)
    LinearLayout mRootView;

    private EditText mDiscountEdit;

    private DiscountReceiptContract.Presenter mPresenter;
    /**
     * 订单ID
     */
    private String mOrderId;
    /**
     * 已选中的打折原因在discountReasonList中的index
     */
    private int mSelectedDiscountReasonIndex = -1;
    /**
     * 打折原因列表
     */
    private List<Reason> mReasonList;
    /**
     * 打折原因列表(仅供选择打折原因的弹框使用)
     */
    private List<String> mReasonStringList;

    public static DiscountReceiptFragment newInstance(String orderId) {
        Bundle args = new Bundle();
        args.putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        DiscountReceiptFragment fragment = new DiscountReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_discount_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mOrderId = bundle.getString(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
        }
        mEditDiscountRate.setInputType(InputType.TYPE_CLASS_NUMBER);
        //折扣率默认是100
        mEditDiscountRate.setNumberText(100);
        mDiscountEdit = mEditDiscountRate.getEditText();
        //点击输入框，让输入框内容全部被选中，方便用户直接输入覆盖内容
        CustomViewUtil.initEditViewFocousAll(mDiscountEdit);
    }

    @Override
    protected void initListener() {
        RxView.clicks(mButtonSure).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String discountRateStr = mEditDiscountRate.getEditText().getText().toString();
                        int discountRate = Integer.parseInt(discountRateStr);
                        if (discountRate <= 0) {
                            ToastUtils.showShortToast(getActivity(), R.string.module_receipt_discount_rate_error_alert);
                            return;
                        }
                        if (null != mReasonList && !mReasonList.isEmpty() && mSelectedDiscountReasonIndex < 0) {
                            ToastUtils.showShortToast(getActivity(), R.string.module_receipt_discount_select_reason_error_alert);
                            return;
                        }
                        List<Promotion> promotionList = new ArrayList<>(1);
                        Promotion promotion = new Promotion();
                        promotion.setType((short) ReceiptHelper.ReceiptPromotion.TYPE_DISCOUNT);
                        promotion.setClassName(ReceiptHelper.ReceiptPromotion.CLASS_DISCOUNT);
                        promotion.setRatio((int) mEditDiscountRate.getNumber());
                        if (null != mReasonList && !mReasonList.isEmpty() && mSelectedDiscountReasonIndex >= 0) {
                            promotion.setMemo(mReasonList.get(mSelectedDiscountReasonIndex).getName());
                        }
                        promotion.setForceRatio(mCheckboxDiscount.isChecked());
                        promotionList.add(promotion);
                        mPresenter.promoteBill(mOrderId, promotionList);
                    }
                });
        RxView.clicks(mLayoutDiscountReason).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        boolean isValid = (null != mReasonStringList && !mReasonStringList.isEmpty());
                        if (isValid) {
                            getDialogUtil().showSingleChoiceDialog(R.string.module_receipt_discount_reason
                                    , mReasonStringList, mSelectedDiscountReasonIndex, new ListCallbackSingleChoice() {
                                        @Override
                                        public void onSelection(int which, CharSequence text) {
                                            if (!text.toString().equals(mTextDiscountReason.getText().toString().trim())) {
                                                mSelectedDiscountReasonIndex = which;
                                                mTextDiscountReason.setText(text);
                                            }
                                        }
                                    }, true, new SingleButtonCallback() {
                                        @Override
                                        public void onClick(DialogUtilAction which) {
                                            if (which == DialogUtilAction.POSITIVE) {
                                            }
                                        }
                                    });
                        } else {
                            showToast(R.string.module_receipt_discount_empty_reason_error_alert);
                        }
                    }
                });
        mCheckboxDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                }
            }
        });
        RxView.clicks(mLayoutDiscountRate).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mEditDiscountRate.requestFocus();
                        mEditDiscountRate.getEditText().selectAll();
//                        mEditDiscountRate.getEditText().setSelection(mEditDiscountRate.getEditText().length());
                        KeyboardUtils.showSoftInput(getActivity(), mEditDiscountRate.getEditText());
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
        mReasonList = new ArrayList<>();
        mPresenter.getReasonList(UserHelper.getEntityId(), SystemDirCodeConstant.RATIO_REASON, SystemDirCodeConstant.TYPE_SYSTEM);
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadData();
    }

    @Override
    public void setPresenter(DiscountReceiptContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void successGetReasonList(List<Reason> reasonList) {
        mRootView.setVisibility(View.VISIBLE);
        if (null != reasonList && !reasonList.isEmpty()) {
            mReasonList.clear();
            mReasonList.addAll(reasonList);
            mReasonStringList = new ArrayList<>(mReasonList.size());
            for (int i = 0; i < mReasonList.size(); i++) {
                Reason reason = mReasonList.get(i);
                mReasonStringList.add(reason.getName());
            }
//            mTextDiscountReason.setText(mReasonStringList.get(mSelectedDiscountReasonIndex));
        }
        //自动弹出软键盘
        mLayoutDiscountRate.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLayoutDiscountRate.performClick();
            }
        }, 200);
    }

    @Override
    public void failGetReasonList(ErrorBody errorBody) {
        showErrorView(errorBody.getMessage());
    }

    @Override
    public void successPromoteBill(CashPromotionBillResponse cashPromotionBillResponse) {
        if (null != cashPromotionBillResponse) {
            List<Promotion> promotions = cashPromotionBillResponse.getPromotions();
            if (null != promotions && !promotions.isEmpty()) {
                Promotion promotion = promotions.get(0);
                if (promotion.getStatus() == ReceiptHelper.PromotionStatus.SUCCESS) {
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                } else {
                    showToast(promotion.getVerifyMessage());
                }
            }
        }
    }
}
