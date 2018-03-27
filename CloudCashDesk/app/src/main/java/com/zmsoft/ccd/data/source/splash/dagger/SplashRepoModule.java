package com.zmsoft.ccd.data.source.splash.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.splash.SplashSource;
import com.zmsoft.ccd.data.source.splash.ISplashSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 14:08
 */
@Module
public class SplashRepoModule {

    @Singleton
    @Provides
    @Remote
    ISplashSource provideISplashSource() {
        return new SplashSource();
    }
}
