package com.zmsoft.ccd.module.printconfig.label.dagger;


import com.zmsoft.ccd.module.printconfig.label.fragment.LabelPrintConfigContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@Module
public class LabelPrintConfigPresenterModule {

    private final LabelPrintConfigContract.View mView;

    public LabelPrintConfigPresenterModule(LabelPrintConfigContract.View view) {
        mView = view;
    }

    @Provides
    LabelPrintConfigContract.View provideLabelPrintConfigContracView() {
        return mView;
    }

}
