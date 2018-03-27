package com.zmsoft.ccd.data.source.orderdetail.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.orderdetail.IOrderDetailSource;
import com.zmsoft.ccd.data.source.orderdetail.OrderDetailSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:31
 */
@Module
public class OrderDetailSourceModule {

    @Singleton
    @Provides
    @Remote
    IOrderDetailSource getOrderDetailSourceRepository() {
        return new OrderDetailSource();
    }

}
