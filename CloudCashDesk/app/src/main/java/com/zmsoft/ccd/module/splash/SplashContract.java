package com.zmsoft.ccd.module.splash;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/19 16:18
 */
public class SplashContract {

    public interface Presenter extends BasePresenter {

        // 获取单个开关
        void getConfigSwitchVal(String entityId, String systemTypeId, String code);
    }

    public interface View extends BaseView<Presenter> {

        // 获取是否开启云收银
        void getTurnOnCloudSuccess(String data);

        // 获取开关失败
        void getTurnOnCloudSuccessFailure();

    }
}
