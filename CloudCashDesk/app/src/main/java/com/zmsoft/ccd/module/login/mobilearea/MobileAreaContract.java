package com.zmsoft.ccd.module.login.mobilearea;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.login.mobilearea.MobileArea;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/22 16:41.
 */

public class MobileAreaContract {

    public interface View extends BaseView<Presenter> {
        void loadDataSuccess(List<MobileArea> MobileAreas);
        void loadDataError(String errorMessage);
    }

    public interface Presenter extends BasePresenter {
        void loadMobileArea();
    }
}
