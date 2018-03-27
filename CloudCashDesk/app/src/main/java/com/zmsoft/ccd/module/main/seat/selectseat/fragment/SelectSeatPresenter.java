package com.zmsoft.ccd.module.main.seat.selectseat.fragment;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.desk.DeskRepository;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 10:04
 */
public class SelectSeatPresenter implements SelectSeatContract.Presenter {

    private SelectSeatContract.View mView;
    private final DeskRepository mDeskRepository;

    private final List<SeatArea> responseSearAreaList;

    @Inject
    public SelectSeatPresenter(SelectSeatContract.View view, DeskRepository deskRepository) {
        mView = view;
        mDeskRepository = deskRepository;
        responseSearAreaList = new ArrayList<>();
    }

    @Inject
    void setPresenter() {
        mView.setPresenter(this);
    }

    @Override
    public void downloadSeatData() {
        mDeskRepository.loadAllDeskList(new Callback<List<SeatArea>>() {
            @Override
            public void onSuccess(List<SeatArea> data) {
                if (mView == null) {
                    return;
                }
                responseSearAreaList.clear();
                if (null != data) {
                    responseSearAreaList.addAll(data);
                }
                mView.hideLoading();
                mView.loadDataSuccess(responseSearAreaList, true);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                responseSearAreaList.clear();
                mView.loadStateErrorView(body.getMessage());
            }
        });
    }

    @Override
    public List<SeatArea> getDownloadData() {
        return responseSearAreaList;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
