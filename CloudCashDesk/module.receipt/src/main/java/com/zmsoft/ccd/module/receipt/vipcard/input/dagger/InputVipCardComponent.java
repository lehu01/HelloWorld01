package com.zmsoft.ccd.module.receipt.vipcard.input.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.vipcard.input.InputVipCardActivity;
import com.zmsoft.ccd.module.receipt.vipcard.source.dagger.VipCardSourceComponent;

import dagger.Component;

/**
 * Description：界面注入[接口+view]
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 19:53
 */
@PresentScoped
@Component(dependencies = VipCardSourceComponent.class, modules = InputVipCardPresenterModule.class)
public interface InputVipCardComponent {

    void inject(InputVipCardActivity inputVipCardActivity);

}
