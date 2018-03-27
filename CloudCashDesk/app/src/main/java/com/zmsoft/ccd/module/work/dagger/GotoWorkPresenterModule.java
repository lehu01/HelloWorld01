package com.zmsoft.ccd.module.work.dagger;

import com.zmsoft.ccd.module.work.fragment.GotoWorkContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@Module
public class GotoWorkPresenterModule {

    private final GotoWorkContract.View mView;

    public GotoWorkPresenterModule(GotoWorkContract.View view) {
        mView = view;
    }

    @Provides
    GotoWorkContract.View provideGotoWorkContract() {
        return mView;
    }

}
