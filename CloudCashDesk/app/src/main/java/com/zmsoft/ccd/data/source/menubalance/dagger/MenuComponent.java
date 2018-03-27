package com.zmsoft.ccd.data.source.menubalance.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.menubalance.MenuBalanceRepository;

import dagger.Component;

@ModelScoped
@Component(dependencies = AppComponent.class)
public interface MenuComponent {

    Context getContext();

    MenuBalanceRepository getMenuBalanceRepository();
}
