package com.zmsoft.ccd.module.receipt.receiptway.onaccount.presenter;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.receipt.bean.GetSignBillSingerResponse;
import com.zmsoft.ccd.module.receipt.receipt.model.NormalCollectPayRequest;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;

import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class OnAccountReceiptPresenter implements OnAccountReceiptContract.Presenter {
    private OnAccountReceiptContract.View mView;
    private ReceiptRepository mRepository;

    @Inject
    public OnAccountReceiptPresenter(OnAccountReceiptContract.View view, ReceiptRepository repository) {
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
    public void getSignBillSinger(String kindPayId) {
        mRepository.getSignBillSinger(kindPayId, new Callback<GetSignBillSingerResponse>() {
            @Override
            public void onSuccess(GetSignBillSingerResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                if (null == data) {
                    onFailed(new ErrorBody(context.getString(R.string.server_no_data)));
                } else {
                    mView.successGetSignInfo(data);
                }
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
    public void collectPay(String orderId, List<Fund> fundList) {
        mView.showLoading(context.getString(R.string.module_receipt_cashing), false);
        NormalCollectPayRequest normalCollectPayRequest = new NormalCollectPayRequest(UserHelper.getUserId()
                , orderId, UserHelper.getEntityId(), UserHelper.getUserId());
        normalCollectPayRequest.setFunds(fundList);
        String requestJson = JsonMapper.toJsonString(normalCollectPayRequest);
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
