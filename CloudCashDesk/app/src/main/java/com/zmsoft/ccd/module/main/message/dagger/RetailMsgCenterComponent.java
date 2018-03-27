package com.zmsoft.ccd.module.main.message.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.msgcenter.dagger.MessageComponent;
import com.zmsoft.ccd.module.main.message.RetailMessageListFragment;

import dagger.Component;

@PresentScoped
@Component(dependencies = MessageComponent.class, modules = RetailMsgCenterPresenterModule.class)
public interface RetailMsgCenterComponent {

    void inject(RetailMessageListFragment fragment);
}
