package com.zmsoft.ccd.module.scan.findorder.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.scan.dagger.ScanSourceComponent;
import com.zmsoft.ccd.module.scan.findorder.ScanFindOrderActivity;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 10:44
 */
@PresentScoped
@Component(dependencies = ScanSourceComponent.class, modules = ScanFindOrderModule.class)
public interface ScanFindOrderComponent {

    void inject(ScanFindOrderActivity scanFindOrderActivity);

}
