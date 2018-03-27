package com.zmsoft.ccd.module.main.order.hangup.fragment;

import com.zmsoft.ccd.data.source.order.OrderSourceRepository;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.order.hangup.HangUpOrder;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/17 11:25
 *     desc  : presenter
 * </pre>
 */
public class HangUpOrderListPresenter implements HangUpOrderListContract.Presenter {

    private HangUpOrderListContract.View mView;
    private final OrderSourceRepository mRepository;

    @Inject
    public HangUpOrderListPresenter(HangUpOrderListContract.View view, OrderSourceRepository createOrUpdateSourceRepository) {
        mView = view;
        mRepository = createOrUpdateSourceRepository;
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
    public void getHangUpOrderList(String entityId) {
        mRepository.getHangUpOrderList(entityId)
                .retryWhen(new RetryWithDelay(3, 500))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<HangUpOrder>>() {
                    @Override
                    public void call(List<HangUpOrder> list) {
                        if (mView == null) {
                            return;
                        }
                        mView.getHangUpOrderListSuccess(list);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.showLoadErrorView(e.getMessage());
                        }
                    }
                });
    }
}
