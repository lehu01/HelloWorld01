package com.zmsoft.ccd.data.source.setting.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.setting.SettingSourceRepository;

import dagger.Component;

/**
 * Created by huaixi on 2017/10/24.
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface SettingSourceComponent {

    Context getContext();

    SettingSourceRepository getSettingSourceRepository();
}
