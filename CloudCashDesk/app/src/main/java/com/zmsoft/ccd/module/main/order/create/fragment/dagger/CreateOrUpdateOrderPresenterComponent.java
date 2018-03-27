package com.zmsoft.ccd.module.main.order.create.fragment.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.ordercreateorupdate.dagger.CreateOrUpdateSourceComponent;
import com.zmsoft.ccd.module.main.order.create.CreateOrUpdateOrderActivity;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:34
 */
@PresentScoped
@Component(dependencies = CreateOrUpdateSourceComponent.class, modules = CreateOrUpdateOrderPresenterModule.class)
public interface CreateOrUpdateOrderPresenterComponent {

    void inject(CreateOrUpdateOrderActivity createOrUpdateOrderActivity);

}
