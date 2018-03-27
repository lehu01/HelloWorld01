package com.zmsoft.ccd.module.receipt.vipcard.detail.dagger;

import com.zmsoft.ccd.module.receipt.vipcard.detail.fragment.VipCardDetailContract;

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
public class VipCardDetailPresenterModule {

    private final VipCardDetailContract.View mView;

    @Inject
    public VipCardDetailPresenterModule(VipCardDetailContract.View mView) {
        this.mView = mView;
    }

    @Provides
    VipCardDetailContract.View provideVipCardDetailContractView() {
        return mView;
    }

}
