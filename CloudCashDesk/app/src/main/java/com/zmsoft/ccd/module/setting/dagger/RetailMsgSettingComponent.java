package com.zmsoft.ccd.module.setting.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.setting.dagger.SettingSourceComponent;
import com.zmsoft.ccd.module.setting.RetailMsgSettingActivity;

import dagger.Component;

/**
 * Created by huaixi on 2017/10/24.
 */

@PresentScoped
@Component(dependencies = SettingSourceComponent.class, modules = RetailMsgSettingModule.class)
public interface RetailMsgSettingComponent {

    void inject(RetailMsgSettingActivity retailMsgSettingActivity);

}
