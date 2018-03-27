package com.zmsoft.ccd.data.source.desk.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.desk.DeskRepository;

import dagger.Component;

@ModelScoped
@Component(dependencies = AppComponent.class)
public interface DeskComponent {

    Context getContext();

    DeskRepository getDeskRepository();
}
