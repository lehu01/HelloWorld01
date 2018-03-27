package com.zmsoft.ccd.module.main.order.memo.dagger;

import com.zmsoft.ccd.module.main.order.memo.fragment.MemoListContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 19:37
 */
@Module
public class MemoListPresenterModule {

    public final MemoListContract.View mView;

    public MemoListPresenterModule(MemoListContract.View view) {
        mView = view;
    }

    @Provides
    MemoListContract.View provideOrderListContractView() {
        return mView;
    }

}
