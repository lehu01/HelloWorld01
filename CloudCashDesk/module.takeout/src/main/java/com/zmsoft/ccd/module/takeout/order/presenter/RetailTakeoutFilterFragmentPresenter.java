package com.zmsoft.ccd.module.takeout.order.presenter;

import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.module.takeout.order.source.TakeoutRepository;
import com.zmsoft.ccd.takeout.bean.FilterConditionResponse;
import com.zmsoft.ccd.takeout.bean.OrderStatusRequest;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/22.
 */

public class RetailTakeoutFilterFragmentPresenter implements RetailTakeoutFilterFragmentContract.Presenter {

    private RetailTakeoutFilterFragmentContract.View mView;
    private final TakeoutRepository mOrderRepository;

    @Inject
    RetailTakeoutFilterFragmentPresenter(RetailTakeoutFilterFragmentContract.View view, TakeoutRepository orderRepository) {
        mView = view;
        mOrderRepository = orderRepository;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void getFilterConfigs(OrderStatusRequest requestOrderStatus) {
        mOrderRepository.getOrderConditions(requestOrderStatus)
                .subscribe(new Action1<FilterConditionResponse>() {
                    @Override
                    public void call(FilterConditionResponse response) {
                        if (mView == null) {
                            return;
                        }
                        mView.getFilterConfigsSuccess(response);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null && mView != null) {
                            mView.getFilterConfigsFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }
}
