package com.zmsoft.ccd.module.takeout.delivery.presenter;


import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.order.source.TakeoutRepository;
import com.zmsoft.ccd.takeout.bean.DeliverTakeoutRequest;
import com.zmsoft.ccd.takeout.bean.DeliverTakeoutResponse;
import com.zmsoft.ccd.takeout.bean.GetDeliveryTypeRequest;
import com.zmsoft.ccd.takeout.bean.GetDeliveryTypeResponse;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/8/18.
 */
public class DeliveryPresenter implements DeliveryContract.Presenter {

    private DeliveryContract.View mView;
    private TakeoutRepository mTakeoutRepository;

    @Inject
    public DeliveryPresenter(DeliveryContract.View view, TakeoutRepository takeoutRepository) {
        this.mView = view;
        this.mTakeoutRepository = takeoutRepository;
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
    public void getCourierList(List<String> orderIds) {
        GetDeliveryTypeRequest getDeliveryTypeRequest = new GetDeliveryTypeRequest(UserHelper.getEntityId()
                , UserHelper.getUserId(), UserHelper.getUserName(), orderIds);
        mTakeoutRepository.getCourierList(getDeliveryTypeRequest)
                .subscribe(new Action1<GetDeliveryTypeResponse>() {
                    @Override
                    public void call(GetDeliveryTypeResponse response) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        if (null != response && null != response.getDeliveryTypeVos()
                                && !response.getDeliveryTypeVos().isEmpty()) {
                            mView.successGetDeliveryType(response);
                        } else {
                            mView.faileGetDeliveryType(context.getString(R.string.server_no_data));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.faileGetDeliveryType(e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void deliveryTakeout(List<String> orderIds, short type, String platformCode, String carrierName, String carrierPhone
            , String expressCode) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        DeliverTakeoutRequest deliverTakeoutRequest = new DeliverTakeoutRequest(orderIds, type, platformCode
                , carrierName, carrierPhone, UserHelper.getEntityId(), UserHelper.getUserId(), expressCode);
        mTakeoutRepository.deliverTakeout(deliverTakeoutRequest)
                .subscribe(new Action1<DeliverTakeoutResponse>() {
                    @Override
                    public void call(DeliverTakeoutResponse response) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        if (null != response && null != response.getTakeoutDeliveryResultList()
                                && !response.getTakeoutDeliveryResultList().isEmpty()) {
                            mView.successDeliveryTakeout(response);
                        } else {
                            mView.faileDeliveryTakeout(context.getString(R.string.server_no_data));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.faileDeliveryTakeout(e.getMessage());
                        }
                    }
                });
    }
}
