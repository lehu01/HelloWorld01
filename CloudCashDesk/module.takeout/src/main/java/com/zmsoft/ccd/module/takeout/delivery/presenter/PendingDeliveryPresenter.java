package com.zmsoft.ccd.module.takeout.delivery.presenter;


import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.order.source.TakeoutRepository;
import com.zmsoft.ccd.takeout.bean.GetDeliveryOrderListRequest;
import com.zmsoft.ccd.takeout.bean.GetDeliveryOrderListResponse;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/8/18.
 */
public class PendingDeliveryPresenter implements PendingDeliveryContract.Presenter {

    private PendingDeliveryContract.View mView;
    private TakeoutRepository mTakeoutRepository;

    @Inject
    public PendingDeliveryPresenter(PendingDeliveryContract.View view, TakeoutRepository takeoutRepository) {
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
    public void getDeliveryOrderList(int pageIndex, int pageSize) {
        GetDeliveryOrderListRequest getDeliveryOrderListRequest = new GetDeliveryOrderListRequest(UserHelper.getEntityId()
                , pageSize, pageIndex);
        mTakeoutRepository.getDeliveryOrderList(getDeliveryOrderListRequest)
                .subscribe(new Action1<GetDeliveryOrderListResponse>() {
                    @Override
                    public void call(GetDeliveryOrderListResponse response) {
                        if (mView == null) {
                            return;
                        }
                        if (null != response) {
                            mView.successGetDeliveryOrderList(response);
                        } else {
                            mView.faileGetDeliveryOrderList(context.getString(R.string.server_no_data));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mView.faileGetDeliveryOrderList(e.getMessage());
                        }
                    }
                });
    }
}
