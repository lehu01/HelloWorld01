package com.zmsoft.ccd.module.login.login;

import com.zmsoft.ccd.BuildConfig;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.UserDataRepository;
import com.zmsoft.ccd.helper.RegularlyHelper;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.user.ChannelInfoRequest;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.bean.user.unified.UnifiedLoginResponse;
import com.zmsoft.ccd.lib.utils.StringUtils;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/16 19:10
 */
public class LoginPresenter implements LoginContract.Presenter {


    private LoginContract.View mLoginView;

    private final UserDataRepository mUserDataRepository;

    @Inject
    LoginPresenter(LoginContract.View loginView, UserDataRepository userDataRepository) {
        mLoginView = loginView;
        mUserDataRepository = userDataRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mLoginView.setPresenter(this);
    }

    @Override
    public void getConfigSwitchVal(String entityId, String systemTypeId, String code) {
        mLoginView.showLoading(true);
        mUserDataRepository.getConfigSwitchVal(entityId, systemTypeId, code)
                .retryWhen(new RetryWithDelay(3, 500))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String data) {
                        if (mLoginView == null) {
                            return;
                        }
                        mLoginView.hideLoading();
                        mLoginView.workModelSuccess(data);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mLoginView == null) {
                            return;
                        }
                        mLoginView.hideLoading();
                        if (throwable instanceof ServerException) {
                            ServerException e = (ServerException) throwable;
                            mLoginView.loadDataError(e.getMessage());
                        }
                    }
                });
    }

    @Override
    public String getLastCountryCode() {
        return UserLocalPrefsCacheSource.readCountryCode(GlobalVars.context);
    }

    @Override
    public void saveLastCountryCode(String countryCode) {
        UserLocalPrefsCacheSource.saveCountryCode(GlobalVars.context, countryCode);
    }

    @Override
    public String getLastUserInfo() {
        return UserLocalPrefsCacheSource.readLastUserName(GlobalVars.context);
    }

    @Override
    public boolean checkLoginValidate(String userName, String passWord) {
        if (StringUtils.isEmpty(userName)) {
            mLoginView.showInvalidateMsg(R.string.toast_no_user_name);
            return false;
        }
        if (StringUtils.isEmpty(passWord)) {
            mLoginView.showInvalidateMsg(R.string.toast_no_user_pwd);
            return false;
        }
        if (!RegularlyHelper.isPwdValid(passWord)) {
            mLoginView.showInvalidateMsg(R.string.pass_word_length);
            return false;
        }
        return true;
    }

    @Override
    public void doLogin(final String countryCode, final String userName, String passWord) {
        mLoginView.showLoading(GlobalVars.context.getString(R.string.dialog_loging), true);
        mUserDataRepository.login(countryCode, userName, passWord, new com.zmsoft.ccd.data.Callback<User>() {
            @Override
            public void onSuccess(User data) {
                if (mLoginView == null) {
                    return;
                }
                mLoginView.hideLoading();
                if (data != null) {
                    mLoginView.loginSuccess(data, countryCode);
                    AnswerEventLogger answerEventLogger = new AnswerEventLogger();
                    answerEventLogger.logChannelInfo(ChannelInfoRequest.EVENT_CODE_LOGIN, data, BuildConfig.VERSION_NAME);
                } else {
                    mLoginView.loadDataError(GlobalVars.context.getString(R.string.server_no_data));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mLoginView == null) {
                    return;
                }
                mLoginView.hideLoading();
                mLoginView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void saveUser(User user) {
        UserHelper.saveToSp(user);
    }

    @Override
    public void checkTokenValidate(String extraCode) {
        mLoginView.showTokenError(extraCode);
    }

    @Override
    public void wechatLogin(final String wechatCode) {
        mUserDataRepository.wechatLogin(wechatCode)
                .subscribe(new Action1<UnifiedLoginResponse>() {
                    @Override
                    public void call(UnifiedLoginResponse unifiedLoginResponse) {
                        if (null == mLoginView) {
                            return;
                        }
                        mLoginView.hideLoading();
                        if (null == unifiedLoginResponse) {
                            return;
                        }
                        if (unifiedLoginResponse.needBindMobile()) {
                            mLoginView.wechatBindMobile(unifiedLoginResponse.getUnionId());
                        } else {
                            String maleText = GlobalVars.context.getString(R.string.male);
                            String femaleText = GlobalVars.context.getString(R.string.female);
                            String unknownText = GlobalVars.context.getString(R.string.unknown);
                            User user = unifiedLoginResponse.becomeUser(maleText, femaleText, unknownText);
                            mLoginView.loginSuccess(user, unifiedLoginResponse.getCountryCode());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null == mLoginView) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        mLoginView.loadDataError(null == e ? "" : e.getMessage());
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mLoginView = null;
    }
}

