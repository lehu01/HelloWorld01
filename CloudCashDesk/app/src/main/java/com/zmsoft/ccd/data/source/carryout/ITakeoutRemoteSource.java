package com.zmsoft.ccd.data.source.carryout;

import com.zmsoft.ccd.lib.bean.carryout.TakeoutMobile;

import java.util.List;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/18.
 */

public interface ITakeoutRemoteSource {


    Observable<List<TakeoutMobile>> getTakeoutMobileList(String entityId);


    Observable<Boolean> saveTakeoutMobileConfig(String entityId, String mobile, boolean openFlag);

    Observable<Boolean> sendTakeoutMobile(String entityId, String mobile);

}
