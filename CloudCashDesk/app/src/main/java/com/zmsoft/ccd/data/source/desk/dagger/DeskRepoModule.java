package com.zmsoft.ccd.data.source.desk.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.desk.DeskRemoteSource;
import com.zmsoft.ccd.data.source.desk.IDeskSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/2/20.
 */
@Module
public class DeskRepoModule {

    @Singleton
    @Provides
    @Remote
    IDeskSource provideDeskRemoteDataSource() {
        return new DeskRemoteSource();
    }
}
