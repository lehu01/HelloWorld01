package com.zmsoft.ccd.module.scan.findseat.dagger;

import com.zmsoft.ccd.module.scan.findseat.ScanFindSeatContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 10:44
 */
@Module
public class ScanFindSeatModule {

    private final ScanFindSeatContract.View mView;

    public ScanFindSeatModule(ScanFindSeatContract.View view) {
        mView = view;
    }

    @Provides
    ScanFindSeatContract.View provideScanFindSeatContractView() {
        return mView;
    }
}
