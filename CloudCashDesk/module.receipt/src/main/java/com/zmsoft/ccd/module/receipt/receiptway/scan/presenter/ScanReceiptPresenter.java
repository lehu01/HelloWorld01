package com.zmsoft.ccd.module.receipt.receiptway.scan.presenter;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.module.receipt.receipt.model.ThirdCollectPayRequest;
import com.zmsoft.ccd.module.receipt.receipt.model.ThirdFund;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;

import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class ScanReceiptPresenter implements ScanReceiptContract.Presenter {
    private ScanReceiptContract.View mView;
    private ReceiptRepository mRepository;

    @Inject
    public ScanReceiptPresenter(ScanReceiptContract.View view, ReceiptRepository repository) {
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
    public void collectPay(String orderId, List<ThirdFund> fundList) {
        mView.showLoading(context.getString(R.string.module_receipt_cashing), false);
        ThirdCollectPayRequest thirdCollectPayRequest = new ThirdCollectPayRequest(UserHelper.getUserId()
                , orderId, UserHelper.getEntityId(), UserHelper.getUserId());
        thirdCollectPayRequest.setFunds(fundList);
        String requestJson = JsonMapper.toJsonString(thirdCollectPayRequest);
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
                mView.failCollectPay(body.getMessage());
            }
        });
    }
}
