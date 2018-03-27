package com.zmsoft.ccd.module.printconfig.label.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.print.dagger.PrintConfigSourceComponent;
import com.zmsoft.ccd.module.printconfig.label.LabelPrintConfigActivity;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@PresentScoped
@Component(dependencies = PrintConfigSourceComponent.class, modules = LabelPrintConfigPresenterModule.class)
public interface LabelPrintConfigComponent {

    void inject(LabelPrintConfigActivity labelPrintConfigActivity);

}
