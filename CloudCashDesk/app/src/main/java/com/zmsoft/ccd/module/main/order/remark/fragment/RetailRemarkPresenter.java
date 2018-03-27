package com.zmsoft.ccd.module.main.order.remark.fragment;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.DataMapLayer;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.order.IOrderSource;
import com.zmsoft.ccd.data.source.order.OrderSource;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.List;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 19:33
 */
public class RetailRemarkPresenter implements RetailRemarkContract.Presenter {

    private RetailRemarkContract.View mView;
    private final ICommonSource iCommonSource;
    private final IOrderSource mRepository;

    @Inject
    public RetailRemarkPresenter(RetailRemarkContract.View view) {
        mView = view;
        iCommonSource = new CommonRemoteSource();
        mRepository = new OrderSource();
    }

    @Inject
    public void setPresenter() {
        mView.setPresenter(this);
    }

    @Override
    public void getRemarkList(String entityId, String dicCode, int systemType) {
        mView.showLoading(true);
        iCommonSource.getReasonSortedList(entityId, dicCode, systemType, new Callback<List<Reason>>() {
            @Override
            public void onSuccess(List<Reason> data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.refreshRemarkList(DataMapLayer.getRemarkList(data));
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.refreshRemarkList(null);
                mView.showStateErrorView(body.getMessage());
            }
        });
    }

    @Override
    public void changeOrderByShopCar(String entityId, String userId, String oldSeatCode, String newSeatCode, int peopleCount, String memo, boolean isWait) {
        mView.showLoading(GlobalVars.context.getString(R.string.updating), true);
        mRepository.changeOrderByShopCar(entityId, userId, oldSeatCode, newSeatCode, peopleCount, memo, isWait, new Callback<Boolean>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_change_order));
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }

            @Override
            public void onSuccess(Boolean data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.changeOrderSuccessByShopCar();
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
