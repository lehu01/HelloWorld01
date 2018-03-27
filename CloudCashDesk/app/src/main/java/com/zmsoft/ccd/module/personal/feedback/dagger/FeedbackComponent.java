package com.zmsoft.ccd.module.personal.feedback.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.UserComponent;
import com.zmsoft.ccd.module.personal.feedback.FeedBackActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = UserComponent.class, modules = FeedbackPresenterModule.class)
public interface FeedbackComponent {

    void inject(FeedBackActivity activity);
}
