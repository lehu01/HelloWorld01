package com.zmsoft.ccd.module.electronic.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.electronic.dagger.ElectronicSourceComponent;
import com.zmsoft.ccd.module.electronic.ElectronicListActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = ElectronicSourceComponent.class, modules = ElectronicPresenterModule.class)
public interface ElectronicComponent {

    void inject(ElectronicListActivity activity);
}
