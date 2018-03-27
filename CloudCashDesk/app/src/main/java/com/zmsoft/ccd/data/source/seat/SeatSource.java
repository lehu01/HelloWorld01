package com.zmsoft.ccd.data.source.seat;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.lib.bean.desk.Seat;
import com.zmsoft.ccd.lib.bean.table.SeatStatus;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.table.SeatQrCode;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 18:57
 */
@Singleton
public class SeatSource implements ISeatSource {

    @Override
    public void getSeatList(String entityId, String userId, int pageIndex, int pageSize, final Callback<List<com.zmsoft.ccd.lib.bean.table.Seat>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
        paramMap.put(HttpParasKeyConstant.PARA_PAGE_INDEX, pageIndex);
        paramMap.put(HttpParasKeyConstant.PARA_PAGE_SIZE, pageSize);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Seat.METHOD_WATCHED_SEAT_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<com.zmsoft.ccd.lib.bean.table.Seat>>() {

            @Override
            protected void onData(List<com.zmsoft.ccd.lib.bean.table.Seat> listSeat) {
                callback.onSuccess(listSeat);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public void getWatchedSeatList(String entityId, String userId, String areaId, int pageIndex, int pageSize, final Callback<List<com.zmsoft.ccd.lib.bean.table.Seat>> callback){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
        paramMap.put(HttpParasKeyConstant.PARA_AREA_ID, areaId);
        paramMap.put(HttpParasKeyConstant.PARA_PAGE_INDEX, pageIndex);
        paramMap.put(HttpParasKeyConstant.PARA_PAGE_SIZE, pageSize);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Seat.METHOD_WATCHED_SEAT_LIST_BY_AREA);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<com.zmsoft.ccd.lib.bean.table.Seat>>() {

            @Override
            protected void onData(List<com.zmsoft.ccd.lib.bean.table.Seat> listSeat) {
                callback.onSuccess(listSeat);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public void getQrCode(String entityId, List<String> seatCodeList, final Callback<List<SeatQrCode>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_SEAT_CODE_LIST, JsonHelper.toJson(seatCodeList));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Seat.METHOD_WATCHED_SEAT_LIST_CODE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<SeatQrCode>>() {
            @Override
            protected void onData(List<SeatQrCode> seatQrCodes) {
                callback.onSuccess(seatQrCodes);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public void getSeatListByAreaId(String entityId, String areaId, int pageIndex, int pageSize, final Callback<List<com.zmsoft.ccd.lib.bean.table.Seat>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_AREA_ID, areaId);
        paramMap.put(HttpParasKeyConstant.PARA_PAGE_INDEX, pageIndex);
        paramMap.put(HttpParasKeyConstant.PARA_PAGE_SIZE, pageSize);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Seat.METHOD_SEAT_LIST_BY_AREA);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<com.zmsoft.ccd.lib.bean.table.Seat>>() {
            @Override
            protected void onData(List<com.zmsoft.ccd.lib.bean.table.Seat> listSeat) {
                callback.onSuccess(listSeat);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public void getSeatBySeatName(String entityId, String seatName, final Callback<com.zmsoft.ccd.lib.bean.table.Seat> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_SEAT_NAME, seatName);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Seat.METHOD_FIND_SEAT_BY_SEAT_NAME);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<com.zmsoft.ccd.lib.bean.table.Seat>() {
            @Override
            protected void onData(com.zmsoft.ccd.lib.bean.table.Seat seat) {
                callback.onSuccess(seat);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public void getSeatBySeatCode(String entityId, String seatCode, final Callback<Seat> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_CODE, seatCode);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Seat.METHOD_FIND_SEAT_BY_SEAT_CODE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Seat>() {
            @Override
            protected void onData(Seat seat) {
                callback.onSuccess(seat);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public void getSeatStatusBySeatCode(String entityId, String seatCode, final Callback<SeatStatus> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_SEAT_CODE, seatCode);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Seat.METHOD_GET_SEAT_STATUS_BY_SEAT_CODE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<SeatStatus>() {
            @Override
            protected void onData(SeatStatus seatStatus) {
                callback.onSuccess(seatStatus);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }
}
