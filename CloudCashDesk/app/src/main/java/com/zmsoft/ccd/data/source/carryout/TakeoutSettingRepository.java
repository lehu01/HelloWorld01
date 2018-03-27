package com.zmsoft.ccd.data.source.carryout;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.carryout.TakeoutMobile;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/18.
 */

public class TakeoutSettingRepository implements ITakeoutRemoteSource {


    private final ITakeoutRemoteSource mCarryoutSource;

    @Inject
    public TakeoutSettingRepository(@Remote ITakeoutRemoteSource carryoutSource) {
        this.mCarryoutSource = carryoutSource;
    }


    @Override
    public Observable<List<TakeoutMobile>> getTakeoutMobileList(String entityId) {
        return mCarryoutSource.getTakeoutMobileList(entityId);
    }

    @Override
    public Observable<Boolean> saveTakeoutMobileConfig(String entityId, String mobile, boolean openFlag) {
        return mCarryoutSource.saveTakeoutMobileConfig(entityId, mobile, openFlag);
    }

    @Override
    public Observable<Boolean> sendTakeoutMobile(String entityId, String mobile) {
        return mCarryoutSource.sendTakeoutMobile(entityId, mobile);
    }
}
