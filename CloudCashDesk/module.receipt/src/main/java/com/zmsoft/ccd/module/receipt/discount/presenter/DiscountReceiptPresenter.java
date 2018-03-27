package com.zmsoft.ccd.module.receipt.discount.presenter;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.receipt.model.CashPromotionBillRequest;
import com.zmsoft.ccd.receipt.bean.CashPromotionBillResponse;
import com.zmsoft.ccd.receipt.bean.Promotion;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;

import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class DiscountReceiptPresenter implements DiscountReceiptContract.Presenter {
    private DiscountReceiptContract.View mView;
    private ReceiptRepository mRepository;
    private ICommonSource mBaseSource;

    @Inject
    public DiscountReceiptPresenter(DiscountReceiptContract.View view, ReceiptRepository repository) {
        mView = view;
        this.mRepository = repository;
        mBaseSource = new CommonRemoteSource();
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
    public void getReasonList(String entityId, String dicCode, int systemType) {
        mBaseSource.getReasonSortedList(entityId, dicCode, systemType, new Callback<List<Reason>>() {
            @Override
            public void onSuccess(List<Reason> data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.successGetReasonList(data);
                mView.showContentView();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
                mView.failGetReasonList(body);
            }
        });
    }

    @Override
    public void promoteBill(String orderId, List<Promotion> promotionList) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        CashPromotionBillRequest cashPromotionBillRequest = new CashPromotionBillRequest(orderId,
                UserHelper.getEntityId(), UserHelper.getUserId(), promotionList);
        String requestJson = JsonMapper.toJsonString(cashPromotionBillRequest);
        mRepository.promoteBill(requestJson, new Callback<CashPromotionBillResponse>() {
            @Override
            public void onSuccess(CashPromotionBillResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.successPromoteBill(data);
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
