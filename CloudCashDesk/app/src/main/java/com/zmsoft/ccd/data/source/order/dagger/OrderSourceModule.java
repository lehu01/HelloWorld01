package com.zmsoft.ccd.data.source.order.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.order.IOrderSource;
import com.zmsoft.ccd.data.source.order.OrderSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:31
 */
@Module
public class OrderSourceModule {

    @Singleton
    @Provides
    @Remote
    IOrderSource getOrderSource() {
        return new OrderSource();
    }

}
