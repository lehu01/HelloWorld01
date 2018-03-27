package com.zmsoft.ccd.data.source.print.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.print.IPrintConfigSource;
import com.zmsoft.ccd.data.source.print.PrintConfigSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@Module
public class PrintConfigSourceModule {

    @Singleton
    @Provides
    @Remote
    IPrintConfigSource getWorkModelSource() {
        return new PrintConfigSource();
    }
}
