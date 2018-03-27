package com.zmsoft.ccd.data.source.scan;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.order.IOrderSource;
import com.zmsoft.ccd.data.source.seat.ISeatSource;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/29 17:04
 */
@Singleton
public class ScanRepository {

    private final IOrderSource mIOrderSource;
    private final ISeatSource mISeatSource;

    @Inject
    public ScanRepository(@Remote IOrderSource orderSource, @Remote ISeatSource iSeatSource) {
        mIOrderSource = orderSource;
        mISeatSource = iSeatSource;
    }

    public void getOrderDetailBySeatCode(String entityId, String seatCode, String customerId, Callback<OrderDetail> callback) {
        mIOrderSource.getOrderDetailBySeatCode(entityId, seatCode, customerId, callback);
    }

    public void getSeatBySeatCode(String entityId, String seatCode, Callback<com.zmsoft.ccd.lib.bean.desk.Seat> callback) {
        mISeatSource.getSeatBySeatCode(entityId, seatCode, callback);
    }

}
