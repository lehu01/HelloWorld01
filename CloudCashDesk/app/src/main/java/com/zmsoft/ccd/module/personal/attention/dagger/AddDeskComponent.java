package com.zmsoft.ccd.module.personal.attention.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.desk.dagger.DeskComponent;
import com.zmsoft.ccd.module.personal.attention.AddAttenDeskActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = DeskComponent.class, modules = DeskPresenterModule.class)
public interface AddDeskComponent {

    void inject(AddAttenDeskActivity addAttenDeskActivity);
}
