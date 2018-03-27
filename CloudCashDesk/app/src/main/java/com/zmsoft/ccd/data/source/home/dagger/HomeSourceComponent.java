package com.zmsoft.ccd.data.source.home.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.home.HomeRepository;

import dagger.Component;

@ModelScoped
@Component(dependencies = AppComponent.class)
public interface HomeSourceComponent {

    Context getContext();

    HomeRepository getHomeRepository();
}
