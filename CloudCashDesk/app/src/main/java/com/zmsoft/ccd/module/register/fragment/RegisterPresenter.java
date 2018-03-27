package com.zmsoft.ccd.module.register.fragment;

import com.zmsoft.ccd.BuildConfig;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.UserDataRepository;
import com.zmsoft.ccd.helper.RegularlyHelper;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.user.ChannelInfoRequest;
import com.zmsoft.ccd.lib.bean.user.SmsCode;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.bean.user.unified.UnifiedLoginResponse;
import com.zmsoft.ccd.lib.utils.StringUtils;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/12/1 16:03.
 */
public class RegisterPresenter implements RegisterContract.Presenter {
    private static final int LENGTH_SMS_CODE = 6;
    private RegisterContract.View mView;
    private final UserDataRepository mRepository;

    @Inject
    public RegisterPresenter(RegisterContract.View view, UserDataRepository repository) {
        this.mView = view;
        this.mRepository = repository;
    }

    @Inject
    void setPresenter() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void getConfigSwitchVal(String entityId, String systemTypeId, String code) {
        mView.showLoading(true);
        mRepository.getConfigSwitchVal(entityId, systemTypeId, code)
                .retryWhen(new RetryWithDelay(3, 500))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String data) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        mView.workModelSuccess(data);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        if (throwable instanceof ServerException) {
                            ServerException e = (ServerException) throwable;
                            mView.showToastMessage(e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void doLogin(final String countryCode, String userName, String passWord) {
        mView.showLoading(true);
        mRepository.login(countryCode, userName, passWord, new Callback<User>() {
            @Override
            public void onSuccess(User data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (data != null) {
                    mView.loginSuccess(data, countryCode);
                    AnswerEventLogger answerEventLogger = new AnswerEventLogger();
                    answerEventLogger.logChannelInfo(ChannelInfoRequest.EVENT_CODE_REGISTER, data, BuildConfig.VERSION_NAME);
                } else {
                    mView.showToastMessage(GlobalVars.context.getString(R.string.server_no_data));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.showToastMessage(body.getMessage());
            }
        });
    }

    @Override
    public void verifyCodeLogin(final String countryCode, String mobile, String verifyCode) {
        mView.showLoading(true);

        mRepository.verifyCodeLogin(countryCode, mobile, verifyCode)
                .subscribe(new Action1<UnifiedLoginResponse>() {
                    @Override
                    public void call(UnifiedLoginResponse unifiedLoginResponse) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        doAfterSuccess(unifiedLoginResponse, countryCode);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (null != e) {
                            mView.showToastMessage(e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void findPassWord(String mobile, String phoneArea, String code, String passWord) {
        mView.showLoading(true);
        mRepository.findPassWord(mobile, phoneArea, code, passWord, new Callback<CommonResult>() {
            @Override
            public void onSuccess(CommonResult data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.findPassWordSuccess();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.showToastMessage(body.getMessage());
            }
        });
    }

    @Override
    public void register(String mobile, String phoneArea, String code, String passWord) {
        mView.showLoading(true);
        mRepository.register(mobile, phoneArea, code, passWord, new Callback<String>() {
            @Override
            public void onSuccess(String data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.registerSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.showToastMessage(body.getMessage());
            }
        });
    }

    @Override
    public boolean check(String phone, String code) {
        if (StringUtils.isEmpty(phone)) {
            mView.showToastMessage(GlobalVars.context.getString(R.string.please_input_correct_phone));
            return false;
        }
        if (code.length() != LENGTH_SMS_CODE) {
            mView.showToastMessage(GlobalVars.context.getString(R.string.please_input_sms_code));
            return false;
        }
        return true;
    }

    @Override
    public boolean check(String phone, String code, String passWord) {
        if(!check(phone, code)) {
            return false;
        }
        if (StringUtils.isEmpty(passWord)) {
            mView.showToastMessage(GlobalVars.context.getString(R.string.toast_no_user_pwd));
            return false;
        }
        if (!RegularlyHelper.isPwdValid(passWord)) {
            mView.showToastMessage(GlobalVars.context.getString(R.string.pass_word_length));
            return false;
        }
        return true;
    }

    @Override
    public void sendVerifyCode(String mobile, String phoneArea, String isCheckRegister, int type, final int fragmentFromType) {
        mView.showLoading(true);
        mRepository.sendVerifyCode(mobile, phoneArea, isCheckRegister, type, RouterPathConstant.Register.FROM_VALUE.BIND_MOBILE == fragmentFromType, new Callback<SmsCode>() {
            @Override
            public void onSuccess(SmsCode data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.sendVerifyCodeSuccess(data, fragmentFromType);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.showToastMessage(body.getMessage());
            }
        });
    }

    @Override
    public void registerAndBindMobile(final String countryCode, String mobile, String verifyCode, String password, String wechatUnionId) {
        mView.showLoading(true);
        mRepository.registerAndBindMobile(countryCode, mobile, verifyCode, password, wechatUnionId)
                .subscribe(new Action1<UnifiedLoginResponse>() {
                    @Override
                    public void call(UnifiedLoginResponse unifiedLoginResponse) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        doAfterSuccess(unifiedLoginResponse, countryCode);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (!StringUtils.isEmpty(e.getMessage())) {
                            mView.showToastMessage(e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void verifyCodeRegister(final String countryCode, String mobile, String verifyCode, String password) {
        mView.showLoading(true);
        mRepository.verifyCodeRegister(countryCode, mobile, verifyCode, password)
                .subscribe(new Action1<UnifiedLoginResponse>() {
                    @Override
                    public void call(UnifiedLoginResponse unifiedLoginResponse) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        doAfterSuccess(unifiedLoginResponse, countryCode);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (null != e) {
                            mView.showToastMessage(e.getMessage());
                        }
                    }
                });
    }

    //================================================================================
    // 登录，绑定，注册并绑定成功
    //================================================================================
    private void doAfterSuccess(UnifiedLoginResponse unifiedLoginResponse, String countryCode) {
        String maleText = GlobalVars.context.getString(R.string.male);
        String femaleText = GlobalVars.context.getString(R.string.female);
        String unknownText = GlobalVars.context.getString(R.string.unknown);
        User user = unifiedLoginResponse.becomeUser(maleText, femaleText, unknownText);
        mView.loginSuccess(user, countryCode);
    }
}
