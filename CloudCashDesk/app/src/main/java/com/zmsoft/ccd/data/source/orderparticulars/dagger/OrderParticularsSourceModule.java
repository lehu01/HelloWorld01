package com.zmsoft.ccd.data.source.orderparticulars.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.orderparticulars.IOrderParticulars;
import com.zmsoft.ccd.data.source.orderparticulars.OrderParticularsSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 16:31.
 */
@Module
public class OrderParticularsSourceModule {
    @Singleton
    @Provides
    @Remote
    IOrderParticulars getOrderParticulars() {
        return new OrderParticularsSource();
    }
}
