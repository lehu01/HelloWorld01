package com.zmsoft.ccd.module.main.order.bill;

import com.zmsoft.ccd.data.source.order.OrderSourceRepository;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListRequest;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListResponse;
import com.zmsoft.ccd.lib.bean.order.RetailOrderFromRequest;
import com.zmsoft.ccd.lib.bean.order.RetailOrderFromResponse;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by huaixi on 2017/11/01.
 */

public class RetailBillDetailPresenter implements RetailBillDetailContract.Presenter {

    private RetailBillDetailContract.View mView;
    private final OrderSourceRepository mOrderSourceRepository;

    @Inject
    public RetailBillDetailPresenter(RetailBillDetailContract.View view, OrderSourceRepository orderSourceRepository) {
        mView = view;
        mOrderSourceRepository = orderSourceRepository;
    }

    @Inject
    void setPresenter() {
        mView.setPresenter(this);
    }

    @Override
    public void getRetailOrderFrom(RetailOrderFromRequest retailOrderFromRequest) {
        mOrderSourceRepository.getRetailOrderFrom(retailOrderFromRequest)
                .subscribe(new Action1<RetailOrderFromResponse>() {
                    @Override
                    public void call(RetailOrderFromResponse retailOrderFromResponse) {
                        if (mView == null) return;
                        mView.loadRetailOrderFromSuccess(retailOrderFromResponse);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) return;
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.loadBillDetailListError(e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void getBillDetailList(GetBillDetailListRequest request) {
        mOrderSourceRepository.getBillDetailList(request)
                .subscribe(new Action1<GetBillDetailListResponse>() {
                    @Override
                    public void call(GetBillDetailListResponse response) {
                        if (mView == null) return;
                        mView.loadBillDetailListSuccess(response);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) return;
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.loadDataError(e.getMessage());
                        }
                    }
                });
    }


    @Override
    public void subscribe() {}

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
