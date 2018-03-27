package com.zmsoft.ccd.module.menu.menu.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.module.menu.menu.source.IMenuSource;
import com.zmsoft.ccd.module.menu.menu.source.MenuRemoteSource;

import dagger.Module;
import dagger.Provides;

@Module
public class MenuSourceModule {

    @ModelScoped
    @Provides
    @Remote
    IMenuSource provideRemoteDataSource() {
        return new MenuRemoteSource();
    }
}
