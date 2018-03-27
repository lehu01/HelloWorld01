package com.zmsoft.ccd.data.source.desk;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;
import com.zmsoft.ccd.lib.bean.desk.TabMenuVO;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/2/20.
 */
@Singleton
public class DeskRepository implements IDeskSource {

    private final IDeskSource remoteSource;

    @Inject
    DeskRepository(@Remote IDeskSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @Override
    public void loadAllDeskList(Callback<List<SeatArea>> callback) {
        remoteSource.loadAllDeskList(callback);
    }

    @Override
    public void loadWatchDeskList(Callback<List<SeatArea>> callback) {
        remoteSource.loadWatchDeskList(callback);
    }

    @Override
    public Observable<List<TabMenuVO>> loadWatchAreaList(String entityId, String userId) {
        return remoteSource.loadWatchAreaList(entityId, userId);
    }

    @Override
    public void bindCheckedSeats(String jsonString, Callback<String> callback) {
        remoteSource.bindCheckedSeats(jsonString, callback);
    }

    @Override
    public void unBindSeats(List<ViewHolderSeat> deskList, String unBindjsonString, Callback<String> callback) {
        remoteSource.unBindSeats(deskList, unBindjsonString, callback);
    }
}
