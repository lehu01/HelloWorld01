package com.zmsoft.ccd.module.takeout.order.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.module.takeout.order.source.ITakeoutSource;
import com.zmsoft.ccd.module.takeout.order.source.TakeoutRemoteSource;

import dagger.Module;
import dagger.Provides;

@Module
public class TakeoutSourceModule {

    @ModelScoped
    @Provides
    @Remote
    ITakeoutSource provideRemoteSource() {
        return new TakeoutRemoteSource();
    }
}
