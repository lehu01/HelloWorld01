package com.zmsoft.ccd.data.source.ordersummary.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.ordersummary.IOrderSummarySource;
import com.zmsoft.ccd.data.source.ordersummary.OrderSummarySource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/15 10:52.
 */

@Module
public class OrderSummarySourceModule {

    @Singleton
    @Provides
    @Remote
    IOrderSummarySource getOrderSummarySourceRepository() {
        return new OrderSummarySource();
    }
}
