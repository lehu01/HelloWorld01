package com.zmsoft.ccd.module.electronic.detail;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.electronic.ElePaymentDetail;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentResponse;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.electronic.helper.DataMapLayer;
import com.zmsoft.ccd.module.electronic.helper.ElectronicHelper;
import com.zmsoft.ccd.receipt.bean.Refund;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 电子收款明细详情页
 *
 * @author DangGui
 * @create 2017/08/12.
 */
public class ElectronicDetailFragment extends BaseFragment implements ElectronicDetailContract.View {

    @BindView(R.id.text_receipt_source)
    TextView mTextReceiptSource;
    @BindView(R.id.text_fee)
    TextView mTextFee;
    @BindView(R.id.text_status)
    TextView mTextStatus;
    @BindView(R.id.text_vip_name)
    TextView mTextVipName;
    @BindView(R.id.text_order_id)
    TextView mTextOrderId;
    @BindView(R.id.text_serial_no)
    TextView mTextSerialNo;
    @BindView(R.id.text_order_code)
    TextView mTextOrderCode;
    @BindView(R.id.text_pay_time_label)
    TextView mTextPayTimeLabel;
    @BindView(R.id.text_pay_time)
    TextView mTextPayTime;
    @BindView(R.id.text_refund)
    TextView mTextRefund;
    @BindView(R.id.layout_content)
    LinearLayout mLayoutContent;
    @BindView(R.id.view_refund_divider)
    View mViewRefundDivider;
    @BindView(R.id.text_order_code_label)
    TextView mTextOrderCodeLabel;

    private ElectronicDetailPresenter mPresenter;
    /**
     * 支付id
     */
    private String mPayId;
    /**
     * 支付code
     */
    private String mCode;
    /**
     * 电子收款凭证
     */
    private ElePaymentDetail mElePaymentDetail;

    public static ElectronicDetailFragment newInstance(String payId, String code) {
        Bundle args = new Bundle();
        args.putString(ElectronicHelper.ExtraParams.PARAM_PAY_ID, payId);
        args.putString(ElectronicHelper.ExtraParams.PARAM_CODE, code);
        ElectronicDetailFragment fragment = new ElectronicDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.electronic_detail_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mPayId = bundle.getString(ElectronicHelper.ExtraParams.PARAM_PAY_ID);
            mCode = bundle.getString(ElectronicHelper.ExtraParams.PARAM_CODE);
        }
        mLayoutContent.setVisibility(View.GONE);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        startLoad();
    }

    @Override
    protected void initListener() {
        RxView.clicks(mTextRefund).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (mElePaymentDetail.getPayStatus() != ElectronicHelper.ReceiptStatus.PAY_SUCCESS) {
                            return;
                        }
                        if (!BatchPermissionHelper.getPermission(Permission.EPayRefund.ACTION_CODE)) {
                            showToast(String.format(getResources().getString(R.string.alert_permission_deny)
                                    , getString(R.string.module_receipt_permission_online_pay_refund)));
                            return;
                        }
                        getDialogUtil().showDialog(R.string.material_dialog_title,
                                R.string.home_electronic_detail_refund_alert,
                                true, new SingleButtonCallback() {
                                    @Override
                                    public void onClick(DialogUtilAction which) {
                                        if (which == DialogUtilAction.POSITIVE) {
                                            refund();
                                        }
                                    }
                                });
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public void setPresenter(ElectronicDetailContract.Presenter presenter) {
        this.mPresenter = (ElectronicDetailPresenter) presenter;
    }

    @Override
    public void successGetElePayment(GetElePaymentResponse getElePaymentResponse) {
        mElePaymentDetail = DataMapLayer.getElePaymentDetail(getElePaymentResponse);
        showContentView();
        mLayoutContent.setVisibility(View.VISIBLE);
        initViewByResult();
    }

    @Override
    public void failGetElePayment(String errorMsg) {
        showErrorView(errorMsg);
    }

    @Override
    public void successRefund() {
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST); //通知找单页刷新数据
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    private void startLoad() {
        showLoadingView();
        mPresenter.getElePaymentDetail(mPayId);
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        startLoad();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    private void initViewByResult() {
        if (!TextUtils.isEmpty(mElePaymentDetail.getReceiptName())) {
            mTextReceiptSource.setText(mElePaymentDetail.getReceiptName());
        } else {
            mTextReceiptSource.setText("");
        }
        switch (mElePaymentDetail.getPayType()) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_WEIXIN:
                mTextReceiptSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_wechat, 0, 0, 0);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALIPAY:
                mTextReceiptSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_alipay, 0, 0, 0);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_QQ:
                mTextReceiptSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_pay_from_qq, 0, 0, 0);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP:
                mTextReceiptSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_pay_from_vip_car, 0, 0, 0);
                mTextOrderCodeLabel.setText(R.string.home_electronic_detail_order_code_vip);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_PROMOTION_MARKET_CENTER:
                mTextReceiptSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_local_promotion, 0, 0, 0);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_PROMOTION_MARKET_EXCHANGE_SINGLE_COUPON:
                mTextReceiptSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_single_coupon, 0, 0, 0);
                break;
            default:
                mTextReceiptSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_pay_from_default, 0, 0, 0);
                break;
        }
        if (!TextUtils.isEmpty(mElePaymentDetail.getPayFee())) {
            mTextFee.setVisibility(View.VISIBLE);
            mTextFee.setText(mElePaymentDetail.getPayFee());
        } else {
            mTextFee.setVisibility(View.GONE);
        }
        if (mElePaymentDetail.getPayStatus() == ElectronicHelper.ReceiptStatus.PAY_SUCCESS
                || mElePaymentDetail.getPayStatus() == ElectronicHelper.ReceiptStatus.PAY_SUCCESS_BUT_REFUNDED) {
            mTextStatus.setVisibility(View.VISIBLE);
            mTextStatus.setText(R.string.electronic_receipt_pay_success);
            mTextPayTimeLabel.setText(R.string.electronic_receipt_pay_time);
        } else if (mElePaymentDetail.getPayStatus() == ElectronicHelper.ReceiptStatus.REFUND_SUCCESS) {
            mTextStatus.setVisibility(View.VISIBLE);
            mTextStatus.setText(R.string.electronic_receipt_refund_success);
            mTextPayTimeLabel.setText(R.string.electronic_receipt_refund_time);
        } else {
            mTextStatus.setVisibility(View.GONE);
            mTextPayTimeLabel.setText("-");
        }
        if (!TextUtils.isEmpty(mElePaymentDetail.getMemberName())) {
            mTextVipName.setText(mElePaymentDetail.getMemberName());
        } else {
            mTextVipName.setText("-");
        }
        if (!TextUtils.isEmpty(mElePaymentDetail.getOrderNo())) {
            mTextOrderId.setText(mElePaymentDetail.getOrderNo());
        } else {
            mTextOrderId.setText("-");
        }
        if (!TextUtils.isEmpty(mElePaymentDetail.getSeatCode())) {
            mTextSerialNo.setText(mElePaymentDetail.getSeatCode());
        } else {
            mTextSerialNo.setText("-");
        }

        if (mElePaymentDetail.getPayType() == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP) {
            if (!TextUtils.isEmpty(mElePaymentDetail.getCardNo())) {
                mTextOrderCode.setText(mElePaymentDetail.getCardNo());
            } else {
                mTextOrderCode.setText("-");
            }
        } else {
            if (!TextUtils.isEmpty(mElePaymentDetail.getPayCode())) {
                mTextOrderCode.setText(mElePaymentDetail.getPayCode());
            } else {
                mTextOrderCode.setText("-");
            }
        }
        if (!TextUtils.isEmpty(mElePaymentDetail.getTime())) {
            mTextPayTime.setText(mElePaymentDetail.getTime());
        } else {
            mTextPayTime.setText("-");
        }
        mTextRefund.setVisibility(mElePaymentDetail.isCanRefund() ? View.VISIBLE : View.GONE);
        mViewRefundDivider.setVisibility(mElePaymentDetail.isCanRefund() ? View.VISIBLE : View.GONE);
    }

    /**
     * 退款
     */
    private void refund() {
        List<Refund> refundList = new ArrayList<>(1);
        Refund refund = new Refund();
        refund.setType(mElePaymentDetail.getPayType());
        refund.setPayId(mCode);
        refundList.add(refund);
        mPresenter.refund(mPayId, mElePaymentDetail.getOrderId(), refundList);
    }
}
