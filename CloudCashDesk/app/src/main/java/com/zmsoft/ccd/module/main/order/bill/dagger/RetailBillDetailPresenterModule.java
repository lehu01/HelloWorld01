package com.zmsoft.ccd.module.main.order.bill.dagger;

import com.zmsoft.ccd.module.main.order.bill.RetailBillDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huaixi on 2017/11/01.
 */
@Module
public class RetailBillDetailPresenterModule {

    private final RetailBillDetailContract.View mView;

    public RetailBillDetailPresenterModule(RetailBillDetailContract.View view) {
        mView = view;
    }

    @Provides
    RetailBillDetailContract.View provideRetailBillDetailContractView() {
        return mView;
    }
}
