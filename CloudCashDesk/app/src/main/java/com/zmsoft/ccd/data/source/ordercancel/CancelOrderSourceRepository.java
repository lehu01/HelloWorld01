package com.zmsoft.ccd.data.source.ordercancel;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.order.IOrderSource;
import com.zmsoft.ccd.lib.bean.order.op.CancelOrder;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/17 17:11
 */
@Singleton
public class CancelOrderSourceRepository implements ICancelOrderSource {

    private final IOrderSource mIOrderSource;
    private final ICommonSource mICommonSource;

    @Inject
    public CancelOrderSourceRepository(@Remote IOrderSource orderSource) {
        mIOrderSource = orderSource;
        mICommonSource = new CommonRemoteSource();
    }

    public void getReasonList(String entityId, String dicCode, int systemType, Callback<List<Reason>> callback) {
        mICommonSource.getReasonList(entityId, dicCode, systemType, callback);
    }

    public void cancelOrder(String entityId, String orderId, String opUserId, long modifyTime, String reason, Callback<CancelOrder> callback) {
        mIOrderSource.cancelOrder(entityId, orderId, opUserId, modifyTime, reason, callback);
    }

}
