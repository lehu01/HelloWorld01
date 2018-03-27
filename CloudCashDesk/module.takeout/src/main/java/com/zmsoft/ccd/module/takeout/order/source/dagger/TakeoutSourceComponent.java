package com.zmsoft.ccd.module.takeout.order.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.module.takeout.order.source.TakeoutRepository;

import dagger.Component;

@ModelScoped
@Component(modules = {TakeoutSourceModule.class})
public interface TakeoutSourceComponent {

    TakeoutRepository getRepository();
}
