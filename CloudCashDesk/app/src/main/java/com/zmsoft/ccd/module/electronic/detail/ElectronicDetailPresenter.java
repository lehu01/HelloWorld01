package com.zmsoft.ccd.module.electronic.detail;

import android.text.TextUtils;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.zmsoft.ccd.BusinessConstant;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.receipt.source.IReceiptSource;
import com.zmsoft.ccd.data.source.electronic.ElectronicRepository;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentResponse;
import com.zmsoft.ccd.receipt.bean.Refund;
import com.zmsoft.ccd.receipt.bean.RefundResponse;

import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2016/12/17.
 */
public class ElectronicDetailPresenter implements ElectronicDetailContract.Presenter {

    private ElectronicDetailContract.View mView;
    private ElectronicRepository mElectronicRepository;
    @Autowired(name = BusinessConstant.ReceiptSource.RECEIPT_SOURCE)
    IReceiptSource mReceiptSource;

    @Inject
    public ElectronicDetailPresenter(ElectronicDetailContract.View view, ElectronicRepository electronicRepository) {
        this.mView = view;
        this.mElectronicRepository = electronicRepository;
        MRouter.getInstance().inject(this);
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void getElePaymentDetail(String payId) {
        mElectronicRepository.getElePaymentDetail(payId, new com.zmsoft.ccd.data.Callback<GetElePaymentResponse>() {
            @Override
            public void onSuccess(GetElePaymentResponse getElePaymentResponse) {
                if (null == mView) {
                    return;
                }
                if (null != getElePaymentResponse) {
                    mView.successGetElePayment(getElePaymentResponse);
                } else {
                    mView.failGetElePayment(context.getString(R.string.server_no_data));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.failGetElePayment(body.getMessage());
            }
        });
    }

    @Override
    public void refund(final String payId, String orderId, List<Refund> refundList) {
        if (null == mReceiptSource) {
            mView.loadDataError(GlobalVars.context.getString(R.string.error_load_other_module_sourceimp));
            return;
        }
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        mReceiptSource.refund(orderId, refundList, ConfigHelper.hasOpenCashClean(GlobalVars.context), new Callback<RefundResponse>() {
            @Override
            public void onSuccess(RefundResponse data) {
                if (null == mView) {
                    return;
                }
                if (null != data && null != data.getRefunds() && !data.getRefunds().isEmpty()) {
                    Refund refund = data.getRefunds().get(0);
                    switch (refund.getStatus()) {
                        case BusinessHelper.RefundStatus.SUCCESS:
                            mView.successRefund();
                            break;
                        case BusinessHelper.RefundStatus.REFUNDING:
                            if (!TextUtils.isEmpty(refund.getMessage())) {
                                onFailed(new ErrorBody(refund.getMessage()));
                            } else {
                                onFailed(new ErrorBody(context.getString(R.string.refund_ing)));
                            }
                            break;
                        case BusinessHelper.RefundStatus.FAILURE:
                            if (!TextUtils.isEmpty(refund.getMessage())) {
                                onFailed(new ErrorBody(refund.getMessage()));
                            } else {
                                onFailed(new ErrorBody(context.getString(R.string.refund_fail)));
                            }
                            break;
                        default:
                            onFailed(new ErrorBody(context.getString(R.string.server_no_data)));
                            break;
                    }
                } else {
                    onFailed(new ErrorBody(context.getString(R.string.server_no_data)));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }
}
