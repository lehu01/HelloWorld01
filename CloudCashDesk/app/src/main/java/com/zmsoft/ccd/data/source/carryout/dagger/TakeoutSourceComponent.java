package com.zmsoft.ccd.data.source.carryout.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.carryout.TakeoutSettingRepository;

import dagger.Component;

@ModelScoped
@Component(modules = {TakeoutSourceModule.class})
public interface TakeoutSourceComponent {

    TakeoutSettingRepository getCarryoutRepository();
}
