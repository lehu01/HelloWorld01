package com.zmsoft.ccd.module.scan.findorder.dagger;

import com.zmsoft.ccd.module.scan.findorder.ScanFindOrderContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 10:44
 */
@Module
public class ScanFindOrderModule {

    private final ScanFindOrderContract.View mView;

    public ScanFindOrderModule(ScanFindOrderContract.View view) {
        mView = view;
    }

    @Provides
    ScanFindOrderContract.View provideScanFindOrderContractView() {
        return mView;
    }
}
