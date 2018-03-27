package com.zmsoft.ccd.module.menu.menu.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.module.menu.menu.source.MenuRepository;

import dagger.Component;

@ModelScoped
@Component(modules = {MenuSourceModule.class})
public interface MenuSourceComponent {

    MenuRepository getMenuRepository();
}
