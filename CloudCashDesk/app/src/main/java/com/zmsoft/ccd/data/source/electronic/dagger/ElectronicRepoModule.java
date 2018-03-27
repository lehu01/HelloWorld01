package com.zmsoft.ccd.data.source.electronic.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.electronic.IElectronicSource;
import com.zmsoft.ccd.data.source.electronic.ElectronicRemoteSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 消息中心Model
 *
 * @author DangGui
 * @create 2017/3/4.
 */
@Module
public class ElectronicRepoModule {

    @Singleton
    @Provides
    @Remote
    IElectronicSource provideElectronicRemoteDataSource() {
        return new ElectronicRemoteSource();
    }
}
