package com.zmsoft.ccd.module.menu.menu.presenter;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRepository;
import com.zmsoft.ccd.lib.base.constant.Permission;

import javax.inject.Inject;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/5/5.
 */

public class MenuListActivityPresenter implements MenuListActivityContract.Presenter {

    private MenuListActivityContract.View mView;
    private CommonRepository mBaseRemoteSource;


    @Inject
    MenuListActivityPresenter(MenuListActivityContract.View view, CommonRepository permissionRemoteSouce) {
        mView = view;
        mBaseRemoteSource = permissionRemoteSouce;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void checkCustomFoodPermission() {
        mBaseRemoteSource.checkPermission(Permission.CustomFood.SYSTEM_TYPE
                , Permission.CustomFood.ACTION_CODE, new Callback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        if (mView == null) {
                            return;
                        }
                        mView.checkCustomFoodPermissionSuccess(data);
                    }

                    @Override
                    public void onFailed(ErrorBody body) {
                        mView.checkCustomFoodPermissionFailed(body.getErrorCode(), body.getMessage());
                    }
                });
    }
}
