package com.zmsoft.ccd.data.source.user;

import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.data.source.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 18/02/2017.
 */
@Module
public class UserRepoModule {
    @Singleton
    @Provides
    @Remote
    UserDataSource provideUserRemoteDataSource(NetworkService networkService) {
        return new UserRemoteSource(networkService);
    }
}
