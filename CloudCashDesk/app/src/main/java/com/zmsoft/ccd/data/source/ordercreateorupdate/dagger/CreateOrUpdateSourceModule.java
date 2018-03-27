package com.zmsoft.ccd.data.source.ordercreateorupdate.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.ordercreateorupdate.CreateOrUpdateSource;
import com.zmsoft.ccd.data.source.ordercreateorupdate.ICreateOrUpdateSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/22 15:45
 */
@Module
public class CreateOrUpdateSourceModule {

    @Singleton
    @Provides
    @Remote
    ICreateOrUpdateSource getCreateOrUpdateSource() {
        return new CreateOrUpdateSource();
    }
}
