package com.zmsoft.ccd.data.source.ordercreateorupdate;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.order.IOrderSource;
import com.zmsoft.ccd.data.source.seat.ISeatSource;
import com.zmsoft.ccd.lib.bean.order.OpenOrderVo;
import com.zmsoft.ccd.lib.bean.order.op.ChangeOrderByTrade;
import com.zmsoft.ccd.lib.bean.table.SeatStatus;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Description：业务Repository
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/22 15:43
 */
@Singleton
public class CreateOrUpdateSourceRepository implements ICreateOrUpdateSource {

    private final IOrderSource mIOrderSource;
    private final ISeatSource mISeatSource;

    @Inject
    public CreateOrUpdateSourceRepository(@Remote IOrderSource orderSource, @Remote ISeatSource iSeatSource) {
        mIOrderSource = orderSource;
        mISeatSource = iSeatSource;
    }

    public void changeOrderByTrade(String entityId, String memo, int peopleCount, String newSeatCode, String opUserId
            , String orderId, int isWait, int opType, long modifyTime, Callback<ChangeOrderByTrade> callback) {
        mIOrderSource.changeOrderByTrade(entityId, memo, peopleCount, newSeatCode, opUserId, orderId, isWait, opType, modifyTime, callback);
    }

    public void changeOrderByShopCar(String entityId, String userId, String oldSeatCode, String newSeatCode, int peopleCount, String memo, boolean isWait, Callback<Boolean> callback) {
        mIOrderSource.changeOrderByShopCar(entityId, userId, oldSeatCode, newSeatCode, peopleCount, memo, isWait, callback);
    }

    public void getSeatStatusBySeatCode(String entityId, String seatCode, Callback<SeatStatus> callback) {
        mISeatSource.getSeatStatusBySeatCode(entityId, seatCode, callback);
    }

    public void createOrder(String entityId, String userId, String seatCode, int peopleCount, String memo, boolean isWait, Callback<OpenOrderVo> callback) {
        mIOrderSource.createOrder(entityId, userId, seatCode, peopleCount, memo, isWait, callback);
    }
}
