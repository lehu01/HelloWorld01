package com.zmsoft.ccd.data.source.home.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.home.IHomeSource;
import com.zmsoft.ccd.data.source.home.HomeRemoteSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 主页
 *
 * @author DangGui
 * @create 2017/8/15.
 */
@Module
public class HomeRepoModule {

    @Singleton
    @Provides
    @Remote
    IHomeSource provideHomeRemoteDataSource() {
        return new HomeRemoteSource();
    }
}
