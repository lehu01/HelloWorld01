package com.zmsoft.ccd.data.source.seat;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.table.Seat;
import com.zmsoft.ccd.lib.bean.table.SeatQrCode;
import com.zmsoft.ccd.lib.bean.table.SeatStatus;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 18:54
 */
public interface ISeatSource {

    /**
     * 首页-获取关注的桌位列表
     *
     * @param entityId 实体id
     * @param userId   用户id
     */
    void getSeatList(String entityId, String userId, int pageIndex, int pageSize, Callback<List<Seat>> callback);

    /**
     * 首页-获取关注的桌位列表
     *
     * @param entityId 实体id
     * @param userId   用户id
     * @param areaId   区域
     */
    void getWatchedSeatList(String entityId, String userId, String areaId,int pageIndex, int pageSize, Callback<List<Seat>> callback);

    /**
     * 首页-获取桌位列表二维码(获取多个，如果是一个就传递一个seatCode参数)
     *
     * @param entityId     实体id
     * @param seatCodeList 座位id list
     */
    void getQrCode(String entityId, List<String> seatCodeList, Callback<List<SeatQrCode>> callback);

    /**
     * 首页-根据区域获取座位列表
     *
     * @param entityId  实体id
     * @param areaId    用户id
     * @param pageIndex 页码
     * @param pageSize  数量
     */
    void getSeatListByAreaId(String entityId, String areaId, int pageIndex, int pageSize, Callback<List<Seat>> callback);

    /**
     * 首页-根据座位号搜索桌位
     *
     * @param entityId 实体id
     * @param seatName 座位号
     */
    void getSeatBySeatName(String entityId, String seatName, Callback<Seat> callback);

    /**
     * 根据seatcode获取座位
     *
     * @param entityId 实体id
     * @param seatCode 座位code
     * @param callback 回调
     */
    void getSeatBySeatCode(String entityId, String seatCode, Callback<com.zmsoft.ccd.lib.bean.desk.Seat> callback);

    /**
     * 根据座位code获取座位状态
     *
     * @param entityId 实体id
     * @param seatCode 座位code
     */
    void getSeatStatusBySeatCode(String entityId, String seatCode, Callback<SeatStatus> callback);

}
