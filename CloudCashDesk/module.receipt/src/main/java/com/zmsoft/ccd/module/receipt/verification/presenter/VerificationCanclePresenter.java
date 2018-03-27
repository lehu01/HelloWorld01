package com.zmsoft.ccd.module.receipt.verification.presenter;

import android.text.TextUtils;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;
import com.zmsoft.ccd.receipt.bean.VerificationResponse;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class VerificationCanclePresenter implements VerificationCancleContract.Presenter {
    private VerificationCancleContract.View mView;
    private ReceiptRepository mRepository;

    @Inject
    public VerificationCanclePresenter(VerificationCancleContract.View view, ReceiptRepository repository) {
        mView = view;
        this.mRepository = repository;
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
    public void collectPay(String orderId, String promotionCode) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        mRepository.verification(orderId, promotionCode, new Callback<VerificationResponse>() {
            @Override
            public void onSuccess(VerificationResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                if (null != data) {
                    if (null != data.getMarketPromotion()) {
                        if (data.getMarketPromotion().getStatus() == 1) {
                            mView.successCollectPay(data);
                        } else {
                            if (!TextUtils.isEmpty(data.getMarketPromotion().getVerifyMessage())) {
                                onFailed(new ErrorBody(data.getMarketPromotion().getVerifyMessage()));
                            } else {
                                onFailed(new ErrorBody(context.getString(R.string.module_receipt_verification_fail)));
                            }
                        }
                    } else {
                        onFailed(new ErrorBody(context.getString(R.string.module_receipt_verification_fail)));
                    }

                } else {
                    onFailed(new ErrorBody(context.getString(R.string.module_receipt_verification_fail)));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
                mView.failCollectPay(body.getMessage());
            }
        });
    }
}
