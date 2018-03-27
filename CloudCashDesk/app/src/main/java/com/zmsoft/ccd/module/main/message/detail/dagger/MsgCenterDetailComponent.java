package com.zmsoft.ccd.module.main.message.detail.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.msgcenter.dagger.MessageComponent;
import com.zmsoft.ccd.module.main.message.detail.MessageDetailActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = MessageComponent.class, modules = MsgCenterDetailPresenterModule.class)
public interface MsgCenterDetailComponent {

    void inject(MessageDetailActivity activity);
}
