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
 * Created by kumu on 2017/8/21.
 */

public class RetailTakeoutListActivityPresenter implements RetailTakeoutListActivityContract.Presenter {

    private RetailTakeoutListActivityContract.View mView;

    private TakeoutRepository mOrderRepository;

    @Inject
    RetailTakeoutListActivityPresenter(RetailTakeoutListActivityContract.View view, TakeoutRepository orderRepository) {
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
    public void getOrderStatusList(OrderStatusRequest orderStatusRequest) {
        mOrderRepository.getOrderStatusList(orderStatusRequest)
                .subscribe(new Action1<FilterConditionResponse>() {
                    @Override
                    public void call(FilterConditionResponse configs) {
                        if (mView == null) {
                            return;
                        }
                        mView.getOrderStatusListSuccess(configs != null ? configs.getConfigVos() : null);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.getOrderStatusListFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }
}
