package com.zmsoft.ccd.data.source.msgcenter.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.msgcenter.MsgCenterRemoteSource;
import com.zmsoft.ccd.data.source.msgcenter.IMsgCenterSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 消息中心Model
 *
 * @author DangGui
 * @create 2017/3/4.
 */
@Module
public class MsgCenterRepoModule {

    @Singleton
    @Provides
    @Remote
    IMsgCenterSource provideMsgCenterRemoteDataSource() {
        return new MsgCenterRemoteSource();
    }
}
