package com.zmsoft.ccd.module.workmodel.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.workmodel.dagger.WorkModelSourceComponent;
import com.zmsoft.ccd.module.workmodel.WorkModelActivity;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/27 19:00
 */
@PresentScoped
@Component(dependencies = WorkModelSourceComponent.class, modules = WorkModelPresenterModule.class)
public interface WorkModelComponent {

    void inject(WorkModelActivity workModelActivity);

}
