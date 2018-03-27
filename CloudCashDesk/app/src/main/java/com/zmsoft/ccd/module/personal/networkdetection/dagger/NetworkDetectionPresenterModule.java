package com.zmsoft.ccd.module.personal.networkdetection.dagger;

import com.zmsoft.ccd.module.personal.networkdetection.NetworkDetectionContract;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkDetectionPresenterModule {

    private NetworkDetectionContract.View mView;

    public NetworkDetectionPresenterModule(NetworkDetectionContract.View view) {
        mView = view;
    }

    @Provides
    NetworkDetectionContract.View provideNetworkDetectionView() {
        return mView;
    }
}
