package com.zmsoft.ccd.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 18/02/2017.
 *
 * 提供全局Application mContext
 * 
 */
@Module
public final class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}