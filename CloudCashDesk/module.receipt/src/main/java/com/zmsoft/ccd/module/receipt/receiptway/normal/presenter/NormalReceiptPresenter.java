package com.zmsoft.ccd.module.receipt.receiptway.normal.presenter;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.module.receipt.receipt.model.GetKindDetailInfoRequest;
import com.zmsoft.ccd.receipt.bean.GetKindDetailInfoResponse;
import com.zmsoft.ccd.module.receipt.receipt.model.NormalCollectPayRequest;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;

import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class NormalReceiptPresenter implements NormalReceiptContract.Presenter {
    private NormalReceiptContract.View mView;
    private ReceiptRepository mRepository;

    @Inject
    public NormalReceiptPresenter(NormalReceiptContract.View view, ReceiptRepository repository) {
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
    public void getKindDetailInfo(String name, int mode, String kindPayId) {
        GetKindDetailInfoRequest getKindDetailInfoRequest = new GetKindDetailInfoRequest(kindPayId
                , UserHelper.getEntityId(), name, mode);
        String requestJson = JsonMapper.toJsonString(getKindDetailInfoRequest);
        mRepository.getKindDetailInfo(requestJson, new Callback<GetKindDetailInfoResponse>() {
            @Override
            public void onSuccess(GetKindDetailInfoResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.successGetKindDetailInfo(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
                mView.failGetKindDetailInfo(body);
            }
        });
    }

    @Override
    public void collectPay(String orderId, String payName, int orderCode, String seatCode, String seatName, List<Fund> fundList) {
        mView.showLoading(context.getString(R.string.module_receipt_cashing), false);
        NormalCollectPayRequest normalCollectPayRequest = new NormalCollectPayRequest(UserHelper.getUserId()
                , orderId, UserHelper.getEntityId(), UserHelper.getUserId());
        normalCollectPayRequest.setFunds(fundList);
        normalCollectPayRequest.setCheckLimit(ConfigHelper.hasOpenCashClean(GlobalVars.context));
        normalCollectPayRequest.setUserName(UserHelper.getUserName());
        normalCollectPayRequest.setSeatCode(seatCode);
        normalCollectPayRequest.setSeatName(seatName);
        normalCollectPayRequest.setPayName(payName);
        normalCollectPayRequest.setCode(orderCode);
        String requestJson = JsonMapper.toJsonString(normalCollectPayRequest);
        mRepository.collectPay(requestJson, new Callback<CloudCashCollectPayResponse>() {
            @Override
            public void onSuccess(CloudCashCollectPayResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
//                mView.showContentView();
                mView.successCollectPay(data);
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
