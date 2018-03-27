package com.zmsoft.ccd.data.source.electronic;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentListResponse;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 消息中心Model
 *
 * @author DangGui
 * @create 2017/3/4.
 */
@Singleton
public class ElectronicRepository implements IElectronicSource {

    private final IElectronicSource remoteSource;

    @Inject
    ElectronicRepository(@Remote IElectronicSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @Override
    public void getElePaymentList(int pageIndex, int pageSize, Callback<GetElePaymentListResponse> callback) {
        remoteSource.getElePaymentList(pageIndex, pageSize, callback);
    }

    @Override
    public void getElePaymentDetail(String payId, Callback<GetElePaymentResponse> callback) {
        remoteSource.getElePaymentDetail(payId, callback);
    }
}
