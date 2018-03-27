package com.zmsoft.ccd.module.main.order.complete;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.ordercomplete.OrderCompleteSourceRepository;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.order.complete.CompleteBillVo;
import com.zmsoft.ccd.lib.bean.user.User;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/27 10:54.
 */

public class OrderCompletePresenter implements OrderCompleteContract.Presenter {
    private OrderCompleteContract.View mView;
    private OrderCompleteSourceRepository mOrderCompleteSourceRepository;


    //================================================================================
    // inject
    //================================================================================
    @Inject
    public OrderCompletePresenter(OrderCompleteContract.View mView, OrderCompleteSourceRepository orderCompleteSourceRepository) {
        this.mView = mView;
        this.mOrderCompleteSourceRepository = orderCompleteSourceRepository;
    }

    @Inject
    void setPresenter() {
        mView.setPresenter(this);
    }

    //================================================================================
    // OrderCompleteContract.Presenter
    //================================================================================
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void loadOrderCompleteData(String date) {
        User user = UserLocalPrefsCacheSource.getUser();
        String entityId = user.getEntityId();
        mOrderCompleteSourceRepository.getCompleteBillByDate(entityId, date, date, new Callback<List<CompleteBillVo>>() {

            @Override
            public void onSuccess(List<CompleteBillVo> data) {
                if (null == mView) {
                    return;
                }
                mView.loadOrderCompleteDataSuccess(data.size() == 0 ? null : data.get(0));
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.loadOrderFindDataError(body.getMessage());
            }
        });
    }
}
