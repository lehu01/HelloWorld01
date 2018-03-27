package com.zmsoft.ccd.module.printconfig.smallticket.dagger;


import com.zmsoft.ccd.module.printconfig.smallticket.fragment.SmallTicketPrintConfigContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@Module
public class SmallTicketPrintConfigPresenterModule {

    private final SmallTicketPrintConfigContract.View mView;

    public SmallTicketPrintConfigPresenterModule(SmallTicketPrintConfigContract.View view) {
        mView = view;
    }

    @Provides
    SmallTicketPrintConfigContract.View providePrintConfigContractView() {
        return mView;
    }

}
