package com.zmsoft.ccd.module.receipt.vipcard.input.dagger;

import com.zmsoft.ccd.module.receipt.vipcard.input.fragment.InputVipCardContract;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Description：界面module
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 19:53
 */
@Module
public class InputVipCardPresenterModule {

    private final InputVipCardContract.View mView;

    @Inject
    public InputVipCardPresenterModule(InputVipCardContract.View mView) {
        this.mView = mView;
    }

    @Provides
    InputVipCardContract.View provideInputVipCardContractView() {
        return mView;
    }

}
