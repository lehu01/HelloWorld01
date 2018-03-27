package com.zmsoft.ccd.data.source.systemdircode.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.systemdircode.ISystemDirCodeSource;
import com.zmsoft.ccd.data.source.systemdircode.SystemDirCodeSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 15:01
 */
@Module
public class SystemDirCodeRepoModule {

    @Singleton
    @Provides
    @Remote
    ISystemDirCodeSource provideSystemDirCodeSource() {
        return new SystemDirCodeSource();
    }
}
