package com.zmsoft.ccd.data.source.workmodel.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.workmodel.IWorkModelSource;
import com.zmsoft.ccd.data.source.workmodel.WorkModelSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/31 10:18
 */
@Module
public class WorkModelSourceModule {

    @Singleton
    @Provides
    @Remote
    IWorkModelSource getWorkModelSource() {
        return new WorkModelSource();
    }
}
