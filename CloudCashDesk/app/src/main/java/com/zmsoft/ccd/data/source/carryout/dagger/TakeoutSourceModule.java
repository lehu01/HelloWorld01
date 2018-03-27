package com.zmsoft.ccd.data.source.carryout.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.carryout.ITakeoutRemoteSource;
import com.zmsoft.ccd.data.source.carryout.TakeoutRemoteSource;

import dagger.Module;
import dagger.Provides;

@Module
public class TakeoutSourceModule {

    @ModelScoped
    @Provides
    @Remote
    ITakeoutRemoteSource provideRemoteDataSource() {
        return new TakeoutRemoteSource();
    }
}
