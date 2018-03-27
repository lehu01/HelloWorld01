package com.zmsoft.ccd.data.source.shortcutreceipt.dagger;

import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.shortcutreceipt.ShortCutReceiptRemoteSource;
import com.zmsoft.ccd.data.source.shortcutreceipt.IShortCutReceiptSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author DangGui
 * @create 2017/8/2.
 */
@Module
public class ShortCutReceiptRepoModule {

    @Singleton
    @Provides
    @Remote
    IShortCutReceiptSource provideShortCutReceiptRemoteDataSource() {
        return new ShortCutReceiptRemoteSource();
    }
}
