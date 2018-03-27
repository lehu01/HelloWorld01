package com.zmsoft.ccd.data.source.desk;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;
import com.zmsoft.ccd.lib.bean.desk.TabMenuVO;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;

import java.util.List;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/2/20.
 */

public interface IDeskSource {

    /**
     * 加载所有关注桌列表
     *
     * @param callback 回调
     */
    void loadAllDeskList(Callback<List<SeatArea>> callback);

    /**
     * 加载关注桌列表
     *
     * @param callback 回调
     */
    void loadWatchDeskList(Callback<List<SeatArea>> callback);


    /**
     *
     * @param entityId
     */
    Observable<List<TabMenuVO>> loadWatchAreaList(String entityId, String userId);

    /**
     * 调用接口传入选中的桌位id json串
     *
     * @param jsonString
     */
    void bindCheckedSeats(String jsonString, Callback<String> callback);


    void unBindSeats(List<ViewHolderSeat> deskList, String unBindjsonString, Callback<String> callback);
}
