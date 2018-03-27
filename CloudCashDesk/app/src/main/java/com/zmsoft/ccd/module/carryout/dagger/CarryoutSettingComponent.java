package com.zmsoft.ccd.module.carryout.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.carryout.dagger.TakeoutSourceComponent;
import com.zmsoft.ccd.module.carryout.CarryoutSettingFragment;

import dagger.Component;

@PresentScoped
@Component(dependencies = TakeoutSourceComponent.class, modules = CarryoutSettingPresenterModule.class)
public interface CarryoutSettingComponent {

    void inject(CarryoutSettingFragment fragment);
}
