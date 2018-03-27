package com.zmsoft.ccd.module.receipt.vipcard.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.module.receipt.vipcard.source.IVipCardSource;
import com.zmsoft.ccd.module.receipt.vipcard.source.VipCardRemoteSource;

import dagger.Module;
import dagger.Provides;

/**
 * Description：提供接口实现类对象
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 17:43
 */
@Module
public class VipCardSourceModule {

    @ModelScoped
    @Provides
    @Remote
    IVipCardSource provideVipCardRemoteSource() {
        return new VipCardRemoteSource();
    }
}
