package com.zmsoft.ccd.data.source.electronic.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.electronic.ElectronicRepository;

import dagger.Component;

@ModelScoped
@Component(dependencies = AppComponent.class)
public interface ElectronicSourceComponent {

    Context getContext();

    ElectronicRepository getElectronicRepository();
}
