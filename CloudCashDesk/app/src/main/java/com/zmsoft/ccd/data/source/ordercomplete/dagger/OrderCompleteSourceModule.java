package com.zmsoft.ccd.data.source.ordercomplete.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.ordercomplete.IOrderComplete;
import com.zmsoft.ccd.data.source.ordercomplete.OrderCompleteSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/27 10:42.
 */
@Module
public class OrderCompleteSourceModule {

    @Singleton
    @Provides
    @Remote
    IOrderComplete getOrderCompleteSourceRepository() {
        return new OrderCompleteSource();
    }
}
