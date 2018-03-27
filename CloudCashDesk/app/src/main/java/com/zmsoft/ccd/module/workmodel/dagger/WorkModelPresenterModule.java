package com.zmsoft.ccd.module.workmodel.dagger;

import com.zmsoft.ccd.module.workmodel.fragment.WorkModelContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/27 19:00
 */
@Module
public class WorkModelPresenterModule {

    private final WorkModelContract.View mView;

    public WorkModelPresenterModule(WorkModelContract.View view) {
        mView = view;
    }

    @Provides
    WorkModelContract.View provideWorkModelContractView() {
        return mView;
    }

}
