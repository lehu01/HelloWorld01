package com.zmsoft.ccd.module.electronic;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.electronic.ElectronicRepository;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentListResponse;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2016/12/17.
 */
public class ElectronicPresenter implements ElectronicContract.Presenter {

    private ElectronicContract.View mView;
    private ElectronicRepository mElectronicRepository;

    @Inject
    public ElectronicPresenter(ElectronicContract.View view, ElectronicRepository electronicRepository) {
        this.mView = view;
        this.mElectronicRepository = electronicRepository;
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
    public void getElePaymentList(int pageIndex, int pageSize) {
        mElectronicRepository.getElePaymentList(pageIndex, pageSize, new com.zmsoft.ccd.data.Callback<GetElePaymentListResponse>() {
            @Override
            public void onSuccess(GetElePaymentListResponse getElePaymentListResponse) {
                if (null == mView) {
                    return;
                }
                if (null != getElePaymentListResponse) {
                    mView.getElectronicListSuccess(getElePaymentListResponse);
                } else {
                    mView.getElectronicListFail(context.getString(R.string.server_no_data));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.getElectronicListFail(body.getMessage());
            }
        });
    }
}
