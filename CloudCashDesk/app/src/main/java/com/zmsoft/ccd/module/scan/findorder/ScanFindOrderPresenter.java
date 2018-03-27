package com.zmsoft.ccd.module.scan.findorder;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.scan.ScanRepository;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;

import javax.inject.Inject;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 10:43
 */
public class ScanFindOrderPresenter implements ScanFindOrderContract.Presenter {

    private ScanFindOrderContract.View mScanView;
    private final ScanRepository mScanRepository;

    @Inject
    public ScanFindOrderPresenter(ScanFindOrderContract.View view, ScanRepository scanRepository) {
        mScanView = view;
        mScanRepository = scanRepository;
    }

    @Inject
    void setScanPresenter() {
        mScanView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mScanView = null;
    }

    @Override
    public void getOrderDetailBySeatCode(String entityId, String seatCode, String customerId) {
        if (mScanView != null) {
            mScanView.showLoading(true);
        }
        mScanRepository.getOrderDetailBySeatCode(entityId, seatCode, customerId, new Callback<OrderDetail>() {
            @Override
            public void onSuccess(OrderDetail data) {
                if (mScanView == null) {
                    return;
                }
                mScanView.hideLoading();
                mScanView.loadOrderDetailSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mScanView == null) {
                    return;
                }
                mScanView.hideLoading();
                mScanView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void getSeatBySeatCode(String entityId, String seatCode) {
        if (mScanView != null) {
            mScanView.showLoading(true);
        }
        mScanRepository.getSeatBySeatCode(entityId, seatCode, new Callback<com.zmsoft.ccd.lib.bean.desk.Seat>() {

            @Override
            public void onSuccess(com.zmsoft.ccd.lib.bean.desk.Seat data) {
                if (mScanView == null) {
                    return;
                }
                mScanView.hideLoading();
                mScanView.loadSeatBySeatCodeSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mScanView == null) {
                    return;
                }
                mScanView.hideLoading();
                mScanView.loadDataError(body.getMessage());
            }
        });
    }
}
