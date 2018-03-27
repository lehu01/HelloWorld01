package com.zmsoft.ccd.data.source.ordercancel.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.ordercancel.CancelOrderSource;
import com.zmsoft.ccd.data.source.ordercancel.ICancelOrderSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:31
 */
@Module
public class CancelOrderSourceModule {

    @Singleton
    @Provides
    @Remote
    ICancelOrderSource getCancelOrderSourceRepository() {
        return new CancelOrderSource();
    }

}
