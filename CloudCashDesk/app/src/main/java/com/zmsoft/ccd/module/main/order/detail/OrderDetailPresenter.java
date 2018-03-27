package com.zmsoft.ccd.module.main.order.detail;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.zmsoft.ccd.BusinessConstant;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.receipt.source.IReceiptSource;
import com.zmsoft.ccd.data.source.orderdetail.OrderDetailSourceRepository;
import com.zmsoft.ccd.helper.FeePlanHelper;
import com.zmsoft.ccd.lib.base.bean.EmptyDiscountResponse;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.lib.bean.instance.PersonInstance;
import com.zmsoft.ccd.lib.bean.order.checkoutendpay.AfterEndPay;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;
import com.zmsoft.ccd.lib.bean.order.feeplan.FeePlan;
import com.zmsoft.ccd.lib.bean.order.feeplan.UpdateFeePlan;
import com.zmsoft.ccd.lib.bean.order.op.PushOrder;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.ReverseCheckout;
import com.zmsoft.ccd.lib.bean.pay.Pay;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.receipt.bean.Refund;
import com.zmsoft.ccd.receipt.bean.RefundResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 17:05
 */
public class OrderDetailPresenter implements OrderDetailContract.Presenter {

    private OrderDetailContract.View mView;
    private final OrderDetailSourceRepository mOrderSourceRepository;
    /**
     * 收款接口
     */
    @Autowired(name = BusinessConstant.ReceiptSource.RECEIPT_SOURCE)
    IReceiptSource mReceiptSource;
    /**
     * 通联支付收款、退款请求最多重试次数
     */
    private static final int ALL_IN_MAX_RETRY_COUNT = 5;
    /**
     * 通联支付收款、退款请求当前重试次数
     */
    private int mAllInCurrentRetryCount = 0;

    @Inject
    public OrderDetailPresenter(OrderDetailContract.View view, OrderDetailSourceRepository orderSourceRepository) {
        mView = view;
        mOrderSourceRepository = orderSourceRepository;
        MRouter.getInstance().inject(this);
    }

    @Inject
    void setOrderDetailPresenter() {
        mView.setPresenter(this);
    }

    @Override
    public int getCheckFeePlanIndex(List<FeePlan> list, String feePlanId) {
        int index = 0;
        if (list == null) {
            return index;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(feePlanId)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void clearPay(final String orderId, final Pay pay, final List<Refund> refundList) {
        if (null == mReceiptSource) {
            mView.loadDataError(GlobalVars.context.getString(R.string.error_load_other_module_sourceimp));
            return;
        }
        mView.showLoading(false);
        mReceiptSource.refund(orderId, refundList, new Callback<RefundResponse>() {
            @Override
            public void onSuccess(RefundResponse data) {
                if (mView == null) {
                    return;
                }
                mAllInCurrentRetryCount = 0;
                mView.hideLoading();
                mView.clearPayListSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                //最多重试5次
                if (mAllInCurrentRetryCount >= ALL_IN_MAX_RETRY_COUNT) {
                    mAllInCurrentRetryCount = 0;
                    mView.hideLoading();
                    if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                        if (BusinessHelper.isElectronicPay(pay.getPayType())) {
                            mView.loadDataError(GlobalVars.context.getString(R.string.permission_clear_pay_by_electronic_payment));
                        } else {
                            mView.loadDataError(GlobalVars.context.getString(R.string.permission_clear_pay));
                        }
                    } else {
                        mView.opOrderFailure(body.getMessage());
                    }
                } else {
                    RxUtils.fromCallable(new Callable<Void>() {
                        @Override
                        public Void call() {
                            return null;
                        }
                    }).delay(2, TimeUnit.SECONDS)
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Void>() {
                                @Override
                                public void call(Void aVoid) {
                                    mAllInCurrentRetryCount++;
                                    clearPay(orderId, pay, refundList);
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {

                                }
                            });
                }


            }
        });
    }

    @Override
    public void clearDiscount(String orderId) {
        if (null == mReceiptSource) {
            mView.loadDataError(GlobalVars.context.getString(R.string.error_load_other_module_sourceimp));
            return;
        }
        mView.showLoading(false);
        mReceiptSource.emptyDiscount(orderId, new Callback<EmptyDiscountResponse>() {
            @Override
            public void onSuccess(EmptyDiscountResponse data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.clearDiscountSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_clear_discount));
                } else {
                    mView.opOrderFailure(body.getMessage());
                }
            }
        });
    }

    @Override
    public void getOrderDetail(String entityId, final String orderId, String customerId) {
        mOrderSourceRepository.getOrderDetail(entityId, orderId, customerId, new Callback<OrderDetail>() {
            @Override
            public void onSuccess(OrderDetail data) {
                if (mView == null) {
                    return;
                }
                mView.updateOrderDetailView(data, "");
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.updateOrderDetailView(null, body.getMessage());
            }
        });
    }

    @Override
    public void reverseCheckOut(String entityId, String operatorId, String orderId, String reason, long modifyTime) {
        mView.showLoading(GlobalVars.context.getString(R.string.reversing), false);
        mOrderSourceRepository.reverseCheckOut(entityId, operatorId, orderId, reason, modifyTime, new Callback<ReverseCheckout>() {
            @Override
            public void onSuccess(ReverseCheckout data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.reverseCheckOut();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_reverse_checkout));
                } else {
                    mView.opOrderFailure(body.getMessage());
                }
            }
        });
    }

    @Override
    public void afterEndPay(String entityId, String operatorId, String orderId, long modifyTime) {
        if (null == mReceiptSource) {
            mView.loadDataError(GlobalVars.context.getString(R.string.error_load_other_module_sourceimp));
            return;
        }
        mView.showLoading(false);
        mReceiptSource.afterEndPay(entityId, operatorId, orderId, modifyTime, new Callback<AfterEndPay>() {
            @Override
            public void onSuccess(AfterEndPay data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.afterEndPay();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_end_pay));
                } else {
                    mView.opOrderFailure(body.getMessage());
                }
            }
        });
    }

    @Override
    public void getReverseReasonList(String entityId, final String dicCode, int systemType) {
        mView.showLoading(false);
        mOrderSourceRepository.getReverseReasonList(entityId, dicCode, systemType, new Callback<List<Reason>>() {
            @Override
            public void onSuccess(List<Reason> data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.showReasonDialog(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void getFeePlanListByAreaId(String entityId, String areaId) {
        mView.showLoading(false);
        mOrderSourceRepository.getFeelPlanList(entityId, areaId, new Callback<List<FeePlan>>() {
            @Override
            public void onSuccess(List<FeePlan> data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.showFeePlanDialog(FeePlanHelper.getAddDefaultFeePlanList(data));
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void updateFeePlan(String entityId, String orderId, String feePlanId, String opUserId, int opType, long modifyTime) {
        mView.showLoading(false);
        mOrderSourceRepository.updateFeePlan(entityId, orderId, feePlanId, opUserId, opType, modifyTime, new Callback<UpdateFeePlan>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_update_fee));
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }

            @Override
            public void onSuccess(UpdateFeePlan data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.updateFeePlanSuccess(data);
            }
        });
    }

    @Override
    public void pushInstance(String entityId, String userId, List<String> menuIdList, String customerRegisterId, String seatCode, String orderId) {
        mView.showLoading(false);
        mOrderSourceRepository.pushInstance(entityId, userId, menuIdList, customerRegisterId, seatCode, orderId, new Callback<Boolean>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_push_instance));
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
                mView.pushInstanceSuccess();
            }
        });
    }

    @Override
    public void pushOrder(String entityId, String orderId, String userId, String customerRegisterId, String seatCode) {
        mView.showLoading(false);
        mOrderSourceRepository.pushOrder(entityId, orderId, userId, customerRegisterId, seatCode, new Callback<PushOrder>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_push_order));
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }

            @Override
            public void onSuccess(PushOrder data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.pushOrderSuccess();
            }
        });
    }

    @Override
    public boolean isMustInstance(List<PersonInstance> personInstanceVoList) {
        boolean result = false;
        for (PersonInstance personInstance : personInstanceVoList) {
            boolean tag = false;
            List<Instance> list = personInstance.getInstanceList();
            for (Instance instance : list) {
                if (instance.getOptionalType() == Base.INT_TRUE) {
                    result = true;
                    tag = true;
                    break;
                }
            }
            if (tag) {
                break;
            }
        }
        return result;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

}
