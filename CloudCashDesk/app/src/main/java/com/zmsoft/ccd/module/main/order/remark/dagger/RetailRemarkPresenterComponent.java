package com.zmsoft.ccd.module.main.order.remark.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.systemdircode.dagger.SystemDirCodeComponent;
import com.zmsoft.ccd.module.main.order.remark.RetailRemarkActivity;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 19:37
 */
@PresentScoped
@Component(dependencies = SystemDirCodeComponent.class, modules = RetailRemarkPresenterModule.class)
public interface RetailRemarkPresenterComponent {

    void inject(RetailRemarkActivity remarkActivity);

}
