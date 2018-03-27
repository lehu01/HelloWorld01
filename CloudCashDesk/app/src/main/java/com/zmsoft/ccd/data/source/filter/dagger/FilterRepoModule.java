package com.zmsoft.ccd.data.source.filter.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.filter.FilterSource;
import com.zmsoft.ccd.data.source.filter.IFilterSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 14:08
 */
@Module
public class FilterRepoModule {

    @Singleton
    @Provides
    @Remote
    IFilterSource provideFilterSource() {
        return new FilterSource();
    }
}
