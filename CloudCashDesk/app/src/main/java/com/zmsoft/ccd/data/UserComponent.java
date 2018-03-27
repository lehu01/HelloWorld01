package com.zmsoft.ccd.data;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;

import dagger.Component;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 08/03/2017.
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface UserComponent {

    Context getContext();

    UserDataRepository getUserDataRepository();
}
