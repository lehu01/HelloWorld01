package com.zmsoft.ccd.data.source.system.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.system.IAppSystemSource;
import com.zmsoft.ccd.data.source.system.AppSystemSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/20 14:18
 */
@Module
public class AppSystemRepoModule {

    @Singleton
    @Provides
    @Remote
    IAppSystemSource provideISplashSource() {
        return new AppSystemSource();
    }
}
