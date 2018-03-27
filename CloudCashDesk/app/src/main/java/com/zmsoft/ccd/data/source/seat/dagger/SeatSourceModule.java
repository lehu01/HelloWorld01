package com.zmsoft.ccd.data.source.seat.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.seat.ISeatSource;
import com.zmsoft.ccd.data.source.seat.SeatSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 19:15
 */
@Module
public class SeatSourceModule {

    @Singleton
    @Provides
    @Remote
    ISeatSource getSeatSource() {
        return new SeatSource();
    }
}
