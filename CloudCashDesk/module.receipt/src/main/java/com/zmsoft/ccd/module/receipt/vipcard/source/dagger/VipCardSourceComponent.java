package com.zmsoft.ccd.module.receipt.vipcard.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.module.receipt.vipcard.source.VipCardSourceRepository;

import dagger.Component;

/**
 * Description：提供内容
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 17:43
 */
@ModelScoped
@Component(modules = {VipCardSourceModule.class})
public interface VipCardSourceComponent {

    VipCardSourceRepository getVipCardSourceRepository();

}
