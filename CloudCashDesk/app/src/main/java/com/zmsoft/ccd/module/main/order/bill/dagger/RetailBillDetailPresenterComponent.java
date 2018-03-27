package com.zmsoft.ccd.module.main.order.bill.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.order.dagger.OrderSourceComponent;
import com.zmsoft.ccd.module.main.order.bill.RetailBillDetailListFragment;

import dagger.Component;

/**
 * Created by huaixi on 2017/11/01.
 */
@PresentScoped
@Component(dependencies = OrderSourceComponent.class, modules = RetailBillDetailPresenterModule.class)
public interface RetailBillDetailPresenterComponent {

    void inject(RetailBillDetailListFragment listFragment);

}
