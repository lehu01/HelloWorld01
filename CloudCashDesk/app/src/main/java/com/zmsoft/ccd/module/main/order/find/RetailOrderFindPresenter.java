package com.zmsoft.ccd.module.main.order.find;

import com.zmsoft.ccd.data.source.order.OrderSourceRepository;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.bean.order.FocusOrderRequest;
import com.zmsoft.ccd.lib.bean.order.OrderListResult;
import com.zmsoft.ccd.module.main.order.find.viewmodel.OrderFindViewModel;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by huaixi on 2017/10/26.
 */

public class RetailOrderFindPresenter implements RetailOrderFindContract.Presenter {

    private RetailOrderFindContract.View mView;
    private final OrderSourceRepository mOrderSourceRepository;

    private final OrderFindViewModel mOrderFindViewModel;

    @Inject
    public RetailOrderFindPresenter(RetailOrderFindContract.View mView, OrderSourceRepository orderSourceRepository) {
        this.mView = mView;
        this.mOrderSourceRepository = orderSourceRepository;
        this.mOrderFindViewModel = new OrderFindViewModel();
    }

    @Inject
    void setOrderFindPresenter() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() { }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    // 通过OrderSourceRepository获取零售单信息
    @Override
    public void loadOrderSource(FocusOrderRequest focusOrderRequest) {
        mOrderSourceRepository.getFocusOrderList(focusOrderRequest)
                .subscribe(new Action1<OrderListResult>() {
                    @Override
                    public void call(OrderListResult orderListResult) {
                        if (mView == null) {
                            return;
                        }
                        mView.loadOrderFindDataSuccess(orderListResult);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.loadOrderFindDataError(e.getMessage(), true);
                        }
                    }
                });
    }
}
