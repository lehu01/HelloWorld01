package com.zmsoft.ccd.data.source.instance.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.instance.IInstanceSource;
import com.zmsoft.ccd.data.source.instance.InstanceSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:31
 */
@Module
public class InstanceSourceModule {

    @Singleton
    @Provides
    @Remote
    IInstanceSource getInstanceSource() {
        return new InstanceSource();
    }

}
