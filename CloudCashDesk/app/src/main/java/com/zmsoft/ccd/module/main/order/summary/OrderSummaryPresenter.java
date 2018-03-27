package com.zmsoft.ccd.module.main.order.summary;




import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.ordersummary.OrderSummarySourceRepository;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.order.summary.BillSummaryVo;
import com.zmsoft.ccd.lib.bean.user.User;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/17 09:55.
 */

public class OrderSummaryPresenter implements OrderSummaryContract.Presenter {

    private OrderSummaryContract.View mView;
    private OrderSummarySourceRepository mOrderSummarySourceRepository;


    //================================================================================
    // inject
    //================================================================================
    @Inject
    public OrderSummaryPresenter(OrderSummaryContract.View mView, OrderSummarySourceRepository mOrderSummarySourceRepository) {
        this.mView = mView;
        this.mOrderSummarySourceRepository = mOrderSummarySourceRepository;
    }

    @Inject
    void setPresenter() {
        mView.setPresenter(this);
    }

    //================================================================================
    // OrderSummaryFilterContract.Presenter
    //================================================================================
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void loadOrderSummaryData(String startDate, String endDate) {
        User user = UserLocalPrefsCacheSource.getUser();
        String entityId = user.getEntityId();
        mOrderSummarySourceRepository.getBillSummaryDays(entityId, startDate, endDate,  new Callback<List<BillSummaryVo>>() {

            @Override
            public void onSuccess(List<BillSummaryVo> data) {
                if (null == mView) {
                    return;
                }
                mView.loadOrderCompleteDataSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.loadOrderCompleteDataError(body.getMessage());
            }
        });
    }
}
