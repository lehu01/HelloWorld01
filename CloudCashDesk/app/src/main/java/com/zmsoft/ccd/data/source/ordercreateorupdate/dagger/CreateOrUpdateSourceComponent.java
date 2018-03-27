package com.zmsoft.ccd.data.source.ordercreateorupdate.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.ordercreateorupdate.CreateOrUpdateSourceRepository;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/22 15:45
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface CreateOrUpdateSourceComponent {

    Context getContext();

    CreateOrUpdateSourceRepository getCreateOrUpdateSourceRepository();
}
