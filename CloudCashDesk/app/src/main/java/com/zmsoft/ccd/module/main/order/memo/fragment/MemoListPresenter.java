package com.zmsoft.ccd.module.main.order.memo.fragment;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.DataMapLayer;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;

import java.util.List;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 19:33
 */
public class MemoListPresenter implements MemoListContract.Presenter {

    private MemoListContract.View mView;
    private final ICommonSource iCommonSource;

    @Inject
    public MemoListPresenter(MemoListContract.View view) {
        mView = view;
        iCommonSource = new CommonRemoteSource();
    }

    @Inject
    public void setPresenter() {
        mView.setPresenter(this);
    }

    @Override
    public void getMemoList(String entityId, String dicCode, int systemType) {
        iCommonSource.getReasonSortedList(entityId, dicCode, systemType, new Callback<List<Reason>>() {
            @Override
            public void onSuccess(List<Reason> data) {
                if (mView == null) {
                    return;
                }
                mView.showContentView();
                mView.refreshRemarkList(DataMapLayer.getRemarkList(data));
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.refreshRemarkList(null);
                mView.showStateErrorView(body.getMessage());
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
