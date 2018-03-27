package com.zmsoft.ccd.module.instance.cancel.fragment.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.instance.dagger.InstanceSourceComponent;
import com.zmsoft.ccd.module.instance.cancel.CancelOrGiveInstanceActivity;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 15:51
 */
@PresentScoped
@Component(dependencies = InstanceSourceComponent.class, modules = CancelOrGiveInstancePresenterModule.class)
public interface CancelOrGiveInstancePresenterComponent {

    void inject(CancelOrGiveInstanceActivity cancelOrGiveInstanceActivity);
}
