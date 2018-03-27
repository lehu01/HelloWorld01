package com.zmsoft.ccd.data.source.menubalance.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.desk.DeskRemoteSource;
import com.zmsoft.ccd.data.source.desk.IDeskSource;
import com.zmsoft.ccd.data.source.menubalance.IMenuBalanceSource;
import com.zmsoft.ccd.data.source.menubalance.MenuBalanceRemoteSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/2/22.
 */
@Module
public class MenuBalanceRepoModule {

    @Singleton
    @Provides
    @Remote
    IMenuBalanceSource provideRemoteDataSource() {
        return new MenuBalanceRemoteSource();
    }
}
