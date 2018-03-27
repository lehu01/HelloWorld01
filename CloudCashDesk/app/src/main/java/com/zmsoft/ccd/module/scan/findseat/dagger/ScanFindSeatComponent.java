package com.zmsoft.ccd.module.scan.findseat.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.scan.dagger.ScanSourceComponent;
import com.zmsoft.ccd.module.scan.findseat.ScanFindSeatActivity;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 10:44
 */
@PresentScoped
@Component(dependencies = ScanSourceComponent.class, modules = ScanFindSeatModule.class)
public interface ScanFindSeatComponent {

    void inject(ScanFindSeatActivity scanFindSeatActivity);

}
