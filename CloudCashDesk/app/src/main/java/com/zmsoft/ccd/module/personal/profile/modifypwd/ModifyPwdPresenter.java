package com.zmsoft.ccd.module.personal.profile.modifypwd;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.UserDataRepository;

import javax.inject.Inject;

/**
 * @author DangGui
 * @create 2016/12/16.
 */

public class ModifyPwdPresenter implements ModifyPwdContract.Presenter {
    private ModifyPwdContract.View mView;

    private final UserDataRepository mUserDataRepository;

    @Inject
    ModifyPwdPresenter(ModifyPwdContract.View view, UserDataRepository userDataRepository) {
        this.mView = view;
        this.mUserDataRepository = userDataRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void modifyPwdTask(String oldPwd, String newPwd) {
        mView.showLoading(GlobalVars.context.getString(R.string.dialog_waiting), false);
        mUserDataRepository.modifyPwd(oldPwd, newPwd, new com.zmsoft.ccd.data.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                if (data) {
                    mView.modifySuccess(GlobalVars.context.getString(R.string.person_profile_modifypwd_success));
                    mView.finishView();
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
