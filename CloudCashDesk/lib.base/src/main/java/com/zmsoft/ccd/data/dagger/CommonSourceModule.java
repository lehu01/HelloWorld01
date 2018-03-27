package com.zmsoft.ccd.data.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.Remote;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonSourceModule {

    @ModelScoped
    @Provides
    @Remote
    ICommonSource provideRemoteDataSource() {
        return new CommonRemoteSource();
    }
}
