package com.zmsoft.ccd.module.scan.findseat;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.scan.ScanRepository;
import com.zmsoft.ccd.lib.bean.desk.Seat;

import javax.inject.Inject;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 17:28
 */
public class ScanFindSeatPresenter implements ScanFindSeatContract.Presenter {

    private ScanFindSeatContract.View mView;
    private final ScanRepository mRepository;

    @Inject
    public ScanFindSeatPresenter(ScanFindSeatContract.View view, ScanRepository scanRepository) {
        mView = view;
        mRepository = scanRepository;
    }

    @Inject
    void setScanPresenter() {
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
    public void getSeatBySeatCode(String entityId, String seatCode) {
        mView.showLoading(true);
        mRepository.getSeatBySeatCode(entityId, seatCode, new Callback<Seat>() {

            @Override
            public void onSuccess(com.zmsoft.ccd.lib.bean.desk.Seat data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.loadSeatBySeatCodeSuccess(data);
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
}
