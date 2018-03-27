package com.zmsoft.ccd.module.personal.networkdetection.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.personal.networkdetection.NetworkDetectionActivity;

import dagger.Component;

@PresentScoped
@Component(modules = NetworkDetectionPresenterModule.class)
public interface NetworkDetectionComponent {

    void inject(NetworkDetectionActivity activity);
}
