package com.zmsoft.ccd.module.takeout.order.presenter;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.zmsoft.ccd.BusinessConstant;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.module.takeout.order.source.TakeoutRepository;
import com.zmsoft.ccd.takeout.bean.CancelTakeoutOrderRequest;
import com.zmsoft.ccd.takeout.bean.CancelTakeoutOrderResponse;
import com.zmsoft.ccd.takeout.bean.DeliveryInfoRequest;
import com.zmsoft.ccd.takeout.bean.DeliveryInfoResponse;
import com.zmsoft.ccd.takeout.bean.OperateOrderRequest;
import com.zmsoft.ccd.takeout.bean.OperateOrderResponse;
import com.zmsoft.ccd.takeout.bean.OrderListRequest;
import com.zmsoft.ccd.takeout.bean.OrderListResponse;
import com.zmsoft.ccd.takeout.bean.SearchOrderRequest;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/23.
 */

public class RetailTakeoutListFragmentPresenter implements RetailTakeoutListFragmentContract.Presenter {


    private RetailTakeoutListFragmentContract.View mView;

    private final TakeoutRepository mOrderRepository;

    @Autowired(name = BusinessConstant.CommonSource.COMMON_SOURCE)
    ICommonSource mCommonSource;

    @Inject
    RetailTakeoutListFragmentPresenter(RetailTakeoutListFragmentContract.View view, TakeoutRepository orderRepository) {
        mView = view;
        mOrderRepository = orderRepository;
        MRouter.getInstance().inject(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void getOrderList(OrderListRequest orderListRequest) {
        mOrderRepository.getOrderList(orderListRequest)
                .subscribe(new Action1<OrderListResponse>() {
                    @Override
                    public void call(OrderListResponse response) {
                        if (mView == null) {
                            return;
                        }
                        mView.getOrderListSuccess(response);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.getOrderListFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void getCancelReason() {
        mCommonSource.getReasonList(UserHelper.getEntityId(), SystemDirCodeConstant.CZ_REASON, SystemDirCodeConstant.TYPE_SYSTEM)
                .subscribe(new Action1<List<Reason>>() {
                    @Override
                    public void call(List<Reason> reasons) {
                        if (mView == null) {
                            return;
                        }
                        mView.getCancelReasonSuccess(reasons);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.getCancelReasonFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void cancelOrder(CancelTakeoutOrderRequest request) {
        mOrderRepository.cancelTakeoutOrder(request)
                .subscribe(new Action1<CancelTakeoutOrderResponse>() {
                    @Override
                    public void call(CancelTakeoutOrderResponse response) {
                        if (mView == null) {
                            return;
                        }
                        mView.cancelOrderSuccess(response);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.cancelOrderFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void changeOrderStatus(OperateOrderRequest request) {
        mOrderRepository.changeOrderStatus(request)
                .subscribe(new Action1<OperateOrderResponse>() {
                    @Override
                    public void call(OperateOrderResponse response) {
                        if (mView == null) {
                            return;
                        }
                        mView.changeOrderStatusSuccess(response);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.changeOrderStatusFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void getDeliveryInfo(DeliveryInfoRequest request) {
        mOrderRepository.getDeliveryInfo(request)
                .subscribe(new Action1<DeliveryInfoResponse>() {
                    @Override
                    public void call(DeliveryInfoResponse response) {
                        if (mView == null) {
                            return;
                        }
                        mView.getDeliveryInfoSuccess(response);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.getDeliveryInfoFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }


    @Override
    public void searchOrder(SearchOrderRequest searchOrderRequest) {
        mOrderRepository.searchOrder(searchOrderRequest)
                .subscribe(new Action1<OrderListResponse>() {
                    @Override
                    public void call(OrderListResponse response) {
                        if (mView == null) {
                            return;
                        }
                        mView.searchOrderSuccess(response);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.searchOrderFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }


}
