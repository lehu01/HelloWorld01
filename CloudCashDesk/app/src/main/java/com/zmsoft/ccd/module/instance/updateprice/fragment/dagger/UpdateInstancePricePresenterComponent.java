package com.zmsoft.ccd.module.instance.updateprice.fragment.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.instance.dagger.InstanceSourceComponent;
import com.zmsoft.ccd.module.instance.updateprice.UpdateInstancePriceIActivity;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 15:51
 */
@PresentScoped
@Component(dependencies = InstanceSourceComponent.class, modules = UpdateInstancePricePresenterModule.class)
public interface UpdateInstancePricePresenterComponent {

    void inject(UpdateInstancePriceIActivity updateInstancePriceIActivity);
}
