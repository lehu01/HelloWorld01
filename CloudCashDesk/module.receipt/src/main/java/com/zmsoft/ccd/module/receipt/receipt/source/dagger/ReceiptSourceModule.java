package com.zmsoft.ccd.module.receipt.receipt.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.receipt.source.IReceiptSource;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRemoteSource;

import dagger.Module;
import dagger.Provides;

@Module
public class ReceiptSourceModule {

    @ModelScoped
    @Provides
    @Remote
    IReceiptSource provideRemoteDataSource() {
        return new ReceiptRemoteSource();
    }
}
