package com.zmsoft.ccd.data.source.seat;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.table.Seat;
import com.zmsoft.ccd.lib.bean.table.SeatQrCode;
import com.zmsoft.ccd.lib.bean.table.SeatStatus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 18:58
 */
@Singleton
public class SeatSourceRepository implements ISeatSource {

    private final ISeatSource mSeatSource;

    @Inject
    public SeatSourceRepository(@Remote ISeatSource seatSource) {
        mSeatSource = seatSource;
    }

    @Override
    public void getSeatList(String entityId, String userId, int pageIndex, int pageSize, Callback<List<Seat>> callback) {
        mSeatSource.getSeatList(entityId, userId, pageIndex, pageSize, callback);
    }

    @Override
    public void getWatchedSeatList(String entityId, String userId, String areaId, int pageIndex, int pageSize, Callback<List<Seat>> callback) {
        mSeatSource.getWatchedSeatList(entityId, userId, areaId, pageIndex, pageSize, callback);
    }

    @Override
    public void getQrCode(String entityId, List<String> seatCodeList, Callback<List<SeatQrCode>> callback) {
        mSeatSource.getQrCode(entityId, seatCodeList, callback);
    }

    @Override
    public void getSeatListByAreaId(String entityId, String areaId, int pageIndex, int pageSize, Callback<List<Seat>> callback) {
        mSeatSource.getSeatListByAreaId(entityId, areaId, pageIndex, pageSize, callback);
    }

    @Override
    public void getSeatBySeatName(String entityId, String seatName, Callback<Seat> callback) {
        mSeatSource.getSeatBySeatName(entityId, seatName, callback);
    }

    @Override
    public void getSeatBySeatCode(String entityId, String seatName, Callback<com.zmsoft.ccd.lib.bean.desk.Seat> callback) {
        mSeatSource.getSeatBySeatCode(entityId, seatName, callback);
    }

    @Override
    public void getSeatStatusBySeatCode(String entityId, String seatCode, Callback<SeatStatus> callback) {
        mSeatSource.getSeatStatusBySeatCode(entityId, seatCode, callback);
    }
}
