package com.zmsoft.ccd.module.main.message.takeout.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.msgcenter.dagger.MessageComponent;
import com.zmsoft.ccd.module.main.message.takeout.RetailTakeoutMsgDetailActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = MessageComponent.class, modules = RetailTakeoutMsgDetailPresenterModule.class)
public interface RetailTakeoutMsgDetailComponent {

    void inject(RetailTakeoutMsgDetailActivity activity);
}
