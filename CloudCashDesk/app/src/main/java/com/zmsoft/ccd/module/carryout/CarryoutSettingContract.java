package com.zmsoft.ccd.module.carryout;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.carryout.TakeoutMobile;

import java.util.List;


public class CarryoutSettingContract {

    public interface Presenter extends BasePresenter {

        void loadCarryoutPhoneList(String entityId);

        void updateCarryoutPhoneSetting(String entityId, String mobile, boolean openFlag);

    }

    public interface View extends BaseView<CarryoutSettingContract.Presenter> {

        void loadCarryoutPhoneListSuccess(List<TakeoutMobile> list);

        void loadCarryoutPhoneListFailed(String errorCode, String errorMsg);


        void updateCarryoutPhoneSettingSuccess(Boolean success);

        void updateCarryoutPhoneSettingFailed(String errorCode, String errorMsg);


    }


}
