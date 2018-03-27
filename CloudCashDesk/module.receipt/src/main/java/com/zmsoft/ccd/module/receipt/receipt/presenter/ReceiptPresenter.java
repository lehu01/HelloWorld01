package com.zmsoft.ccd.module.receipt.receipt.presenter;

import android.text.TextUtils;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.lib.base.bean.EmptyDiscountResponse;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.order.checkoutendpay.AfterEndPay;
import com.zmsoft.ccd.lib.bean.pay.CashLimit;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptMethodRecyclerItem;
import com.zmsoft.ccd.module.receipt.receipt.model.NormalCollectPayRequest;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.receipt.bean.GetCloudCashBillResponse;
import com.zmsoft.ccd.receipt.bean.Refund;
import com.zmsoft.ccd.receipt.bean.RefundResponse;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptPresenter implements ReceiptContract.Presenter {
    private ReceiptContract.View mView;
    private ReceiptRepository mRepository;
    private ICommonSource mBaseSource;
    /**
     * 通联支付收款、退款请求最多重试次数
     */
    private static final int ALL_IN_MAX_RETRY_COUNT = 5;
    /**
     * 通联支付收款、退款请求当前重试次数
     */
    private int mAllInCurrentRetryCount = 0;

    @Inject
    public ReceiptPresenter(ReceiptContract.View view, ReceiptRepository repository) {
        mView = view;
        this.mRepository = repository;
        mBaseSource = new CommonRemoteSource();
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
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
    public void getCloudCash(String orderId) {
        mRepository.getCloudCash(orderId, new Callback<GetCloudCashBillResponse>() {
            @Override
            public void onSuccess(GetCloudCashBillResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.successShowCloudCash(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.failShowCloudCash(body);
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void emptyDiscount(final String orderId) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        mRepository.emptyDiscount(orderId, new Callback<EmptyDiscountResponse>() {
            @Override
            public void onSuccess(EmptyDiscountResponse data) {
                if (null == mView) {
                    return;
                }
                //清空优惠成功后刷新收款界面
                getCloudCash(orderId);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void refund(final String orderId, final List<Refund> refundList) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        mRepository.refund(orderId, refundList, ConfigHelper.hasOpenCashClean(GlobalVars.context), new Callback<RefundResponse>() {
            @Override
            public void onSuccess(RefundResponse data) {
                if (null == mView) {
                    return;
                }
                if (null != data && null != data.getRefunds() && !data.getRefunds().isEmpty()) {
                    Refund refund = data.getRefunds().get(0);
                    if (refund.getStatus() != BusinessHelper.RefundStatus.FAILURE) {
                        mAllInCurrentRetryCount = 0;
                        //退款/删除付款 成功后刷新收款界面
                        getCloudCash(orderId);
                    } else {
                        if (!TextUtils.isEmpty(refund.getMessage())) {
                            onFailed(new ErrorBody(refund.getMessage()));
                        } else {
                            onFailed(new ErrorBody(context.getString(R.string.module_receipt_refund_fail)));
                        }
                    }
                } else {
                    mAllInCurrentRetryCount = 0;
                    //退款/删除付款 成功后刷新收款界面
                    getCloudCash(orderId);
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                //最多重试5次
                if (mAllInCurrentRetryCount >= ALL_IN_MAX_RETRY_COUNT) {
                    mAllInCurrentRetryCount = 0;
                    mView.hideLoading();
                    mView.loadDataError(body.getMessage());
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
                                    refund(orderId, refundList);
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
    public void afterEndPay(String entityId, String operatorId, String orderId, long modifyTime) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        mRepository.afterEndPay(entityId, operatorId, orderId, modifyTime, new Callback<AfterEndPay>() {
            @Override
            public void onSuccess(AfterEndPay data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.showContentView();
                mView.successCompletePay();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void batchCheckPermisson(int systemType, List<String> actionCodeList) {
        mBaseSource.batchCheckPermission(systemType, actionCodeList, new Callback<HashMap<String, Boolean>>() {
            @Override
            public void onSuccess(HashMap<String, Boolean> data) {
                if (null == mView) {
                    return;
                }
                BatchPermissionHelper.saveBatchPermission(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
            }
        });
    }

    @Override
    public void getCashLimit(String entityId, String UserId, final ReceiptMethodRecyclerItem mMethodRecyclerItem
            , final double fee, final double needFee) {
        //如果开启了现金清算
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        mBaseSource.getCashLimit(entityId, UserId)
                .retryWhen(new RetryWithDelay(3, 400))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CashLimit>() {
                    @Override
                    public void call(CashLimit cashLimit) {
                        if (null == mView) {
                            return;
                        }
                        mView.hideLoading();
                        mView.getCashLimitSuccess(cashLimit, mMethodRecyclerItem, fee, needFee);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (null == mView) {
                            return;
                        }
                        mView.hideLoading();
                        if (throwable instanceof ServerException) {
                            ServerException serverException = (ServerException) throwable;
                            mView.getCashLimitFailed(serverException.getErrorCode(), serverException.getMessage());
                        }
                    }
                });
    }

    @Override
    public void collectPay(final String orderId, final List<Fund> fundList) {
        mView.showLoading(context.getString(R.string.module_receipt_cashing), false);
        NormalCollectPayRequest normalCollectPayRequest = new NormalCollectPayRequest(UserHelper.getUserId()
                , orderId, UserHelper.getEntityId(), UserHelper.getUserId());
        normalCollectPayRequest.setFunds(fundList);
        String requestJson = JsonMapper.toJsonString(normalCollectPayRequest);
        mRepository.collectPay(requestJson, new Callback<CloudCashCollectPayResponse>() {
            @Override
            public void onSuccess(CloudCashCollectPayResponse data) {
                if (null == mView) {
                    return;
                }
                mAllInCurrentRetryCount = 0;
                mView.hideLoading();
                mView.successCollectPay(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                //最多重试5次
                if (mAllInCurrentRetryCount >= ALL_IN_MAX_RETRY_COUNT) {
                    mAllInCurrentRetryCount = 0;
                    mView.hideLoading();
                    mView.failCollectPay(body.getMessage());
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
                                    collectPay(orderId, fundList);
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
}
