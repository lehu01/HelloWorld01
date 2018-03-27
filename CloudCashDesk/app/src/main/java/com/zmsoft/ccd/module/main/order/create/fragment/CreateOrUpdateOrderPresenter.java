package com.zmsoft.ccd.module.main.order.create.fragment;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.ordercreateorupdate.CreateOrUpdateSourceRepository;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.order.OpenOrderVo;
import com.zmsoft.ccd.lib.bean.order.op.ChangeOrderByTrade;
import com.zmsoft.ccd.lib.bean.pay.CashLimit;
import com.zmsoft.ccd.lib.bean.table.SeatStatus;
import com.zmsoft.ccd.network.HttpHelper;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/1 15:18
 */
public class CreateOrUpdateOrderPresenter implements CreateOrUpdateOrderContract.Presenter {

    private CreateOrUpdateOrderContract.View mView;
    private final CreateOrUpdateSourceRepository mRepository;
    private final ICommonSource mICommonSource;

    @Inject
    public CreateOrUpdateOrderPresenter(CreateOrUpdateOrderContract.View view, CreateOrUpdateSourceRepository createOrUpdateSourceRepository) {
        mView = view;
        mRepository = createOrUpdateSourceRepository;
        mICommonSource = new CommonRemoteSource();
    }

    @Inject
    void setPresenter() {
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
    public void getCashLimit(String entityId, String userId) {
        mView.showLoading(false);
        mICommonSource.getCashLimit(entityId, userId)
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
                        mView.getCashLimitSuccess(cashLimit);
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
    public void changeOrderByTrade(String entityId, String memo, int peopleCount, String newSeatCode,
                                   String opUserId, String orderId, int isWait, int opType, long modifyTime) {
        mView.showLoading(GlobalVars.context.getString(R.string.updating), true);
        mRepository.changeOrderByTrade(entityId, memo, peopleCount, newSeatCode, opUserId, orderId,
                isWait, opType, modifyTime, new Callback<ChangeOrderByTrade>() {
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
                    public void onSuccess(ChangeOrderByTrade data) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        mView.changeOrderSuccess(data);
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
    public void getSeatStatusBySeatCode(String entityId, String seatCode) {
        mView.showLoading(true);
        mRepository.getSeatStatusBySeatCode(entityId, seatCode, new Callback<SeatStatus>() {
            @Override
            public void onSuccess(SeatStatus data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.getSeatStatusSuccess(data);
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
    public void createOrder(String entityId, String userId, String seatCode, int peopleCount, String memo, boolean isWait) {
        mView.showLoading(GlobalVars.context.getString(R.string.create_ordering), true);
        mRepository.createOrder(entityId, userId, seatCode, peopleCount, memo, isWait, new Callback<OpenOrderVo>() {
            @Override
            public void onSuccess(OpenOrderVo data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.createOrderSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_create_order));
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }
        });
    }

}
