package com.zmsoft.ccd.module.main.seat.selectseat.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.desk.dagger.DeskComponent;
import com.zmsoft.ccd.module.main.seat.selectseat.fragment.SelectSeatFragment;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 10:05
 */
@PresentScoped
@Component(dependencies = DeskComponent.class, modules = SelectSeatPresenterModule.class)
public interface SelectSeatPresenterComponent {

    void inject(SelectSeatFragment selectSeatFragment);
}
