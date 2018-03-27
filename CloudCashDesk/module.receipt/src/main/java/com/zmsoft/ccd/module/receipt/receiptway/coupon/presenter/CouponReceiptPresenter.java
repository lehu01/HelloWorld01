package com.zmsoft.ccd.module.receipt.receiptway.coupon.presenter;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.module.receipt.receipt.model.CouponCollectPayRequest;
import com.zmsoft.ccd.receipt.bean.GetVoucherInfoResponse;
import com.zmsoft.ccd.receipt.bean.VoucherFund;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;

import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class CouponReceiptPresenter implements CouponReceiptContract.Presenter {
    private CouponReceiptContract.View mView;
    private ReceiptRepository mRepository;

    @Inject
    public CouponReceiptPresenter(CouponReceiptContract.View view, ReceiptRepository repository) {
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
    public void getVoucherInfo(String kindPayId) {
        mRepository.getVoucherInfo(kindPayId, new Callback<GetVoucherInfoResponse>() {
            @Override
            public void onSuccess(GetVoucherInfoResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.successGetVoucherInfo(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.failGetData(body);
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void collectPay(String orderId, List<VoucherFund> fundList) {
        mView.showLoading(context.getString(R.string.module_receipt_cashing), false);
        CouponCollectPayRequest couponCollectPayRequest = new CouponCollectPayRequest(UserHelper.getUserId()
                , orderId, UserHelper.getEntityId(), UserHelper.getUserId());
        couponCollectPayRequest.setFunds(fundList);
        String requestJson = JsonMapper.toJsonString(couponCollectPayRequest);
        mRepository.collectPay(requestJson, new Callback<CloudCashCollectPayResponse>() {
            @Override
            public void onSuccess(CloudCashCollectPayResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.showContentView();
                mView.successCollectPay(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
//                mView.loadDataError(body.getMessage());
                mView.failCollectPay(body.getMessage());
            }
        });
    }
}
