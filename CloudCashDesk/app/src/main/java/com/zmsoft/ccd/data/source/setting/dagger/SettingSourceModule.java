package com.zmsoft.ccd.data.source.setting.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.setting.ISettingSource;
import com.zmsoft.ccd.data.source.setting.SettingSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huaixi on 2017/10/24.
 */

@Module
public class SettingSourceModule {
    @Singleton
    @Provides
    @Remote
    ISettingSource getSettingSource() {
        return new SettingSource();
    }
}
