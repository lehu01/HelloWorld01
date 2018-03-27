package com.zmsoft.ccd.module.main.order.cancel.fragment;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.ordercancel.CancelOrderSourceRepository;
import com.zmsoft.ccd.lib.bean.order.op.CancelOrder;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.List;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/17 16:03
 */
public class CancelOrderPresenter implements CancelOrderContract.Presenter {

    private CancelOrderContract.View mView;
    private final CancelOrderSourceRepository mCancelOrderSourceRepository;

    @Inject
    public CancelOrderPresenter(CancelOrderContract.View view, CancelOrderSourceRepository cancelOrderSourceRepository) {
        mView = view;
        mCancelOrderSourceRepository = cancelOrderSourceRepository;
    }

    @Inject
    void setPresenter() {
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
    public void cancelOrder(String entityId, String orderId, String opUserId, long modifyTime, String reason) {
        mView.showLoading(GlobalVars.context.getString(R.string.canceling), true);
        mCancelOrderSourceRepository.cancelOrder(entityId, orderId, opUserId, modifyTime, reason, new Callback<CancelOrder>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_cancel_order));
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }

            @Override
            public void onSuccess(CancelOrder data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.cancelOrderSuccess(data);
            }
        });
    }

    @Override
    public void getReasonList(String entityId, String dicCode, int systemType) {
        mCancelOrderSourceRepository.getReasonList(entityId, dicCode, systemType, new Callback<List<Reason>>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }

            @Override
            public void onSuccess(List<Reason> data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.cancelOrderReasonListSuccess(data);
            }
        });
    }
}
