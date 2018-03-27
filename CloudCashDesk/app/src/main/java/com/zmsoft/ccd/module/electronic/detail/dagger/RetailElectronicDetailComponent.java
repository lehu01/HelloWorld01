package com.zmsoft.ccd.module.electronic.detail.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.electronic.dagger.ElectronicSourceComponent;
import com.zmsoft.ccd.module.electronic.detail.RetailElectronicDetailActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = ElectronicSourceComponent.class, modules = RetailElectronicDetailPresenterModule.class)
public interface RetailElectronicDetailComponent {

    void inject(RetailElectronicDetailActivity activity);
}
