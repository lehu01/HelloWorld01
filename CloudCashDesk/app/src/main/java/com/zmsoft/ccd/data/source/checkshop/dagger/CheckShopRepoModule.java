package com.zmsoft.ccd.data.source.checkshop.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.checkshop.CheckShopSource;
import com.zmsoft.ccd.data.source.checkshop.ICheckShopSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 18:13
 */
@Module
public class CheckShopRepoModule {

    @Singleton
    @Provides
    @Remote
    ICheckShopSource provideCheckShopRemoteDataSource() {
        return new CheckShopSource();
    }

}
