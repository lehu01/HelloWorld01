package com.zmsoft.ccd.data.dagger;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/5/5.
 */

public class CommonComponentManager {


    private static CommonComponentManager instance;

    private CommonSourceComponent mCommonSourceComponent;

    private CommonComponentManager() {
    }

    public static synchronized CommonComponentManager get() {
        if (instance == null) {
            instance = new CommonComponentManager();
        }
        return instance;
    }

    public CommonSourceComponent getMenuSourceComponent() {
        if (mCommonSourceComponent == null) {
            mCommonSourceComponent = DaggerCommonSourceComponent.builder().build();
        }
        return mCommonSourceComponent;
    }

}
