package com.zmsoft.ccd.module.personal.feedback.dagger;

import com.zmsoft.ccd.module.personal.feedback.FeedbackContract;

import dagger.Module;
import dagger.Provides;

@Module
public class FeedbackPresenterModule {

    private final FeedbackContract.View mView;

    public FeedbackPresenterModule(FeedbackContract.View view) {
        mView = view;
    }

    @Provides
    FeedbackContract.View provideFeedbackView(){
        return mView;
    }
}
