package com.zmsoft.ccd.module.receipt.complete.presenter;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.order.checkoutendpay.AfterEndPay;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;
import com.zmsoft.ccd.receipt.bean.OrderPayListResponse;

import javax.inject.Inject;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class RetailCompleteReceiptPresenter implements RetailCompleteReceiptContract.Presenter {
    private RetailCompleteReceiptContract.View mView;
    private ReceiptRepository mRepository;

    @Inject
    public RetailCompleteReceiptPresenter(RetailCompleteReceiptContract.View view, ReceiptRepository repository) {
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
    public void afterEndPayForRetail(String entityId, String operatorId, String orderId, long modifyTime) {
        mRepository.afterEndPayForRetail(entityId, operatorId, orderId, modifyTime, new Callback<AfterEndPay>() {
            @Override
            public void onSuccess(AfterEndPay data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.showContentView();
                mView.successCompletePay();
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

    @Override
    public void getOrderPayList(String orderId) {
        mRepository.getOrderPayedList(orderId, new Callback<OrderPayListResponse>() {
            @Override
            public void onSuccess(OrderPayListResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.showContentView();
                mView.successGetPayList(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
                mView.failGetPayList(body.getMessage());
            }
        });
    }
}
