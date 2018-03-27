package com.zmsoft.ccd.data.source.splash;

import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.user.ChannelInfoRequest;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 14:02
 */
@Singleton
public class SplashSourceRepository implements ISplashSource {

    private final ISplashSource iSplashSource;
    private final ICommonSource iCommonSource;

    @Inject
    public SplashSourceRepository(@Remote ISplashSource checkShopSource) {
        iSplashSource = checkShopSource;
        iCommonSource = new CommonRemoteSource();
    }

    public Observable<String> getConfigSwitchVal(String entityId, String systemTypeId, String code) {
        return iCommonSource.getConfigSwitchVal(entityId, systemTypeId, code);
    }

    public void uploadChannelInfo(ChannelInfoRequest channelInfoRequest){
        iCommonSource.uploadChannelInfo(channelInfoRequest);
    }
}
