package com.zmsoft.ccd.module.register.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.source.user.UserHttpParamConstant;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.user.SmsCode;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.register.constant.RegisterConstant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 四个页面：注册，忘记密码，验证码登录，微信绑定手机号
 *
 * 微信绑定手机号,发送验证码后有两种结果：
 * 1.手机号未注册，可以绑定
 * 2.手机号已注册
 *
 * @author : heniu@2dfire.com
 * @time : 2017/12/1 16:03.
 */
public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    @BindView(R.id.text_login_mobile_area)
    TextView mTextMobileArea;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_sms_code)
    EditText mEditSmsCode;
    @BindView(R.id.text_send_sms)
    TextView mTextSendSms;
    @BindView(R.id.edit_pass_word)
    EditText mEditPassWord;
    @BindView(R.id.layout_register_password)
    RelativeLayout mLayoutPassword;
    @BindView(R.id.image_pass_word_model)
    ImageView mImagePassWordModel;
    @BindView(R.id.button_register)
    Button mButtonRegister;
    @BindView(R.id.text_prompt)
    TextView mTextPrompt;

    private RegisterPresenter mPresenter;
    private CountDownTimer mTimeCountDown;
    private int passType = RegisterConstant.PASSWORD_TYPE.ENCRYPTION;

    private int mFrom;              // RouterPathConstant.Register.FROM_VALUE
    private String mPhone;
    private String mCountryCode;
    private String mWechatUnionId;  // 微信绑定手机号时，才有使用

    private int mMobileRegisterStatus = RegisterConstant.MOBILE_REGISTER_STATUS.INIT;


    public static RegisterFragment newInstance(int from, String phone, String countryCode, String wechatUnionId) {
        RegisterFragment registerFragment = new RegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RouterPathConstant.Register.FROM, from);
        bundle.putString(RouterPathConstant.Register.PHONE, phone);
        bundle.putString(RouterPathConstant.Register.COUNTRY_CODE, countryCode);
        bundle.putString(RouterPathConstant.Register.WECHAT_UNION_ID, wechatUnionId);
        registerFragment.setArguments(bundle);
        return registerFragment;
    }

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initIntentData();
        updateInitView();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    //================================================================================
    // life cycle
    //================================================================================
    @Override
    public void onDestroy() {
        if (mTimeCountDown != null) {
            mTimeCountDown.cancel();
        }
        super.onDestroy();
    }

    //================================================================================
    // init
    //================================================================================
    private void initIntentData() {
        Bundle bundle = getArguments();
        mFrom = bundle.getInt(RouterPathConstant.Register.FROM);
        mPhone = bundle.getString(RouterPathConstant.Register.PHONE);
        mCountryCode = bundle.getString(RouterPathConstant.Register.COUNTRY_CODE);
        mWechatUnionId = bundle.getString(RouterPathConstant.Register.WECHAT_UNION_ID);
    }

    private void updateInitView() {
        if (!StringUtils.isEmpty(mCountryCode)) {
            mTextMobileArea.setText(mCountryCode);
        }

        switch (mFrom) {
            case RouterPathConstant.Register.FROM_VALUE.REGISTER:
                mTextPrompt.setVisibility(View.VISIBLE);
                mEditPassWord.setHint(R.string.hit_register_pass_word);
                getActivity().setTitle(R.string.register_2dfire);
                mButtonRegister.setText(getString(R.string.register));
                break;
            case RouterPathConstant.Register.FROM_VALUE.FORGET_PASSWORD:
                mTextPrompt.setVisibility(View.GONE);
                mEditPassWord.setHint(R.string.hit_forget_pass_word);
                getActivity().setTitle(R.string.forget_pass_word_title);
                mEditPhone.setText(StringUtils.notNull(mPhone));
                mEditPhone.setSelection(StringUtils.notNull(mPhone).length());
                mButtonRegister.setText(getString(R.string.ok));
                break;
            case RouterPathConstant.Register.FROM_VALUE.VERIFY_CODE_LOGIN:
                mTextPrompt.setVisibility(View.INVISIBLE);
                mLayoutPassword.setVisibility(View.GONE);
                getActivity().setTitle(R.string.verify_code_login);
                mEditPhone.setText(StringUtils.notNull(mPhone));
                mEditPhone.setSelection(StringUtils.notNull(mPhone).length());
                mButtonRegister.setText(getString(R.string.btn_login));
                break;
            case RouterPathConstant.Register.FROM_VALUE.BIND_MOBILE:
                mTextPrompt.setVisibility(View.VISIBLE);
                mLayoutPassword.setVisibility(View.VISIBLE);
                getActivity().setTitle(R.string.wc_set_phone);
                mEditPhone.setText(StringUtils.notNull(mPhone));
                mEditPhone.setSelection(StringUtils.notNull(mPhone).length());
                mButtonRegister.setText(getString(R.string.wc_bind_phone));
                break;
        }
    }

    //================================================================================
    // on click
    //================================================================================
    @OnClick({R.id.button_register, R.id.text_send_sms, R.id.image_pass_word_model})
    void click(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                doOk();
                break;
            case R.id.text_send_sms:
                sendSmsCode();
                break;
            case R.id.image_pass_word_model:
                switchPassWordModel();
                break;
        }
    }

    private void doOk() {
        switch (mFrom) {
            case RouterPathConstant.Register.FROM_VALUE.REGISTER:
                if (mPresenter.check(getPhone(), getVerificationCode(), getPassWord())) {
                    mPresenter.register(getPhone(), getMobileAreaNumber(), getVerificationCode(), getPassWord());
                }
                break;
            case RouterPathConstant.Register.FROM_VALUE.FORGET_PASSWORD:
                if (mPresenter.check(getPhone(), getVerificationCode(), getPassWord())) {
                    mPresenter.findPassWord(getPhone(), getMobileAreaNumber(), getVerificationCode(), getPassWord());
                }
                break;
            case RouterPathConstant.Register.FROM_VALUE.VERIFY_CODE_LOGIN:
                if (RegisterConstant.MOBILE_REGISTER_STATUS.UNREGISTERED == mMobileRegisterStatus) {
                    if (mPresenter.check(getPhone(), getVerificationCode(), getPassWord())) {
                        mPresenter.verifyCodeRegister(getMobileAreaNumber(), getPhone(), getVerificationCode(), getPassWord());
                    }
                } else if (RegisterConstant.MOBILE_REGISTER_STATUS.REGISTERED == mMobileRegisterStatus) {
                    if (mPresenter.check(getPhone(), getVerificationCode())) {
                        mPresenter.verifyCodeLogin(getMobileAreaNumber(), getPhone(), getVerificationCode());
                    }
                } else {
                    if (mPresenter.check(getPhone(), getVerificationCode())) {
                        mPresenter.verifyCodeLogin(getMobileAreaNumber(), getPhone(), getVerificationCode());
                    }
                }
                break;
            case RouterPathConstant.Register.FROM_VALUE.BIND_MOBILE:
                if (mPresenter.check(getPhone(), getVerificationCode(), getPassWord())) {
                    mPresenter.registerAndBindMobile(getMobileAreaNumber(), getPhone(), getVerificationCode(), getPassWord(), mWechatUnionId);
                }
                break;
        }
    }

    private void switchPassWordModel() {
        switch (passType) {
            case RegisterConstant.PASSWORD_TYPE.ENCRYPTION:
                passType = RegisterConstant.PASSWORD_TYPE.NO_ENCRYPTION;
                mEditPassWord.setInputType(InputType.TYPE_CLASS_TEXT);
                mEditPassWord.setSelection(getPassWord().length());
                mImagePassWordModel.setImageResource(R.drawable.icon_no_encryption_pass_word);
                break;
            case RegisterConstant.PASSWORD_TYPE.NO_ENCRYPTION:
                passType = RegisterConstant.PASSWORD_TYPE.ENCRYPTION;
                mEditPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                mEditPassWord.setSelection(getPassWord().length());
                mImagePassWordModel.setImageResource(R.drawable.icon_encryption_pass_word);
                break;
        }
    }

    private void sendSmsCode() {
        if (StringUtils.isEmpty(getPhone())) {
            showToast(R.string.please_input_correct_phone);
            return;
        }

        if (mTimeCountDown == null) {
            mTimeCountDown = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTextSendSms.setText(String.format(getString(R.string.rest_send_format), millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    mTextSendSms.setEnabled(true);
                    mTextSendSms.setText(getString(R.string.rest_send));
                }
            };
        }
        mMobileRegisterStatus = RegisterConstant.MOBILE_REGISTER_STATUS.INIT;

        String isRequireRegister = UserHttpParamConstant.SEND_VERIFY_CODE.IS_REQUIRE_REGISTER_VALUE.TRUE;
        int type = UserHttpParamConstant.SEND_VERIFY_CODE.TYPE_VALUE.MOBILE_UNREGSITERED;
        switch (mFrom) {
            case RouterPathConstant.Register.FROM_VALUE.REGISTER:
                isRequireRegister = UserHttpParamConstant.SEND_VERIFY_CODE.IS_REQUIRE_REGISTER_VALUE.TRUE;
                type = UserHttpParamConstant.SEND_VERIFY_CODE.TYPE_VALUE.MOBILE_UNREGSITERED;
                break;
            case RouterPathConstant.Register.FROM_VALUE.FORGET_PASSWORD:
                isRequireRegister = UserHttpParamConstant.SEND_VERIFY_CODE.IS_REQUIRE_REGISTER_VALUE.TRUE;
                type = UserHttpParamConstant.SEND_VERIFY_CODE.TYPE_VALUE.MOBILE_REGSITERED;
                break;
            case RouterPathConstant.Register.FROM_VALUE.VERIFY_CODE_LOGIN:
                isRequireRegister = UserHttpParamConstant.SEND_VERIFY_CODE.IS_REQUIRE_REGISTER_VALUE.FALSE;
                type = UserHttpParamConstant.SEND_VERIFY_CODE.TYPE_VALUE.MOBILE_REGSITERED;
                break;
            case RouterPathConstant.Register.FROM_VALUE.BIND_MOBILE:
                isRequireRegister = UserHttpParamConstant.SEND_VERIFY_CODE.IS_REQUIRE_REGISTER_VALUE.TRUE;
                type = UserHttpParamConstant.SEND_VERIFY_CODE.TYPE_VALUE.MOBILE_UNREGSITERED;
                break;
        }
        mPresenter.sendVerifyCode(getPhone(),
                getMobileAreaNumber(),
                isRequireRegister,
                type,
                mFrom);
    }

    @OnClick(R.id.text_login_mobile_area)
    void onClickMobileArea() {
        MRouter.getInstance().build(RouterPathConstant.MobileArea.PATH)
                .navigation(getActivity(), RouterPathConstant.Register.REQUEST_CODE_REGISTER_TO_MOBILE_AREA);
    }

    //================================================================================
    // RegisterContract.View
    //================================================================================
    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = (RegisterPresenter) presenter;
    }

    @Override
    public void loginSuccess(User user, String countryCode) {
        UserLocalPrefsCacheSource.saveCountryCode(GlobalVars.context, countryCode);
        UserHelper.saveToSp(user);
        if (user.isNeedCheckShop()) {
            gotoCheckShopActivity();
        } else {
            mPresenter.getConfigSwitchVal(UserHelper.getEntityId()
                    , String.valueOf(SystemDirCodeConstant.TYPE_SYSTEM)
                    , SystemDirCodeConstant.TURN_ON_CLOUD_CASH);
        }
    }

    @Override
    public void findPassWordSuccess() {
        showToast(R.string.find_pass_word_success);
        getActivity().finish();
    }

    @Override
    public void registerSuccess(String data) {
        showToast(R.string.register_success);
        mPresenter.doLogin(getMobileAreaNumber(), getPhone(), getPassWord());
    }

    @Override
    public void sendVerifyCodeSuccess(SmsCode smsCode, int fragmentFromType) {
        if (null == smsCode) {
            return;
        }
        // 判断手机号注册情况
        if (Base.INT_TRUE == smsCode.getIsRegister()) {
            mMobileRegisterStatus = RegisterConstant.MOBILE_REGISTER_STATUS.REGISTERED;
        } else {
            mMobileRegisterStatus = RegisterConstant.MOBILE_REGISTER_STATUS.UNREGISTERED;
        }

        if (RouterPathConstant.Register.FROM_VALUE.VERIFY_CODE_LOGIN == fragmentFromType) {
            if (RegisterConstant.MOBILE_REGISTER_STATUS.UNREGISTERED == mMobileRegisterStatus) {
                final DialogUtil dialogUtil = new DialogUtil(this.getActivity());
                dialogUtil.showDialog(R.string.prompt, R.string.hint_verify_code_login_but_unregister
                        , R.string.request_title
                        , R.string.cancel
                        , true, new SingleButtonCallback() {
                            @Override
                            public void onClick(DialogUtilAction which) {
                                if (which == DialogUtilAction.POSITIVE) {
                                    getActivity().setTitle(R.string.set_password);
                                    mLayoutPassword.setVisibility(View.VISIBLE);
                                    mButtonRegister.setText(R.string.register_and_login);
                                    dialogUtil.dismissDialog();
                                } else if (which == DialogUtilAction.NEGATIVE) {
                                    dialogUtil.dismissDialog();
                                }
                            }
                        });
            }
        } else if (RouterPathConstant.Register.FROM_VALUE.BIND_MOBILE == fragmentFromType) {
            if (RegisterConstant.MOBILE_REGISTER_STATUS.UNREGISTERED == mMobileRegisterStatus) {
                showBindNoticeDialog(R.string.hint_register_and_bind, R.string.desk_msg_iknow);
            } else {
                showBindNoticeDialog(R.string.hint_registered_and_cannot_bind, R.string.desk_msg_iknow);
            }
        }

        if (smsCode.getStatus() == Base.INT_TRUE) {
            // 绑定手机号，并且账号已注册的情况下，不显示toast
            if (!(RouterPathConstant.Register.FROM_VALUE.BIND_MOBILE == fragmentFromType && RegisterConstant.MOBILE_REGISTER_STATUS.REGISTERED == mMobileRegisterStatus)) {
                showToast(R.string.verification_code_sended);
            }
            mTextSendSms.setEnabled(false);
            if (mTimeCountDown != null) {
                mTimeCountDown.start();
            }
        } else {
            showToast(smsCode.getMessage());
        }
    }

    @Override
    public void showToastMessage(String message) {
        showToast(message);
    }

    @Override
    public void workModelSuccess(String data) {
        /***
         * 1.空代表未设置
         * 2.0代表没有在使用云收银，需要重新开启使用云收银
         */
        if (StringUtils.isEmpty(data) || Base.STRING_FALSE.equals(data)) {
            gotoWorkModelActivity();
        } else {
            // 闪页判断需要，不用做接口请求
            BaseSpHelper.saveTurnCloudCashTime(this.getActivity().getApplicationContext(), System.currentTimeMillis());
            gotoMainActivity();
        }
    }

    //================================================================================
    // get view value
    //================================================================================
    @NonNull
    private String getMobileAreaNumber() {
        return mTextMobileArea.getText().toString().trim();
    }

    @NonNull
    private String getPhone() {
        return mEditPhone.getText().toString().trim();
    }

    @NonNull
    private String getVerificationCode() {
        return mEditSmsCode.getText().toString().trim();
    }

    @NonNull
    private String getPassWord() {
        return mEditPassWord.getText().toString().trim();
    }

    //================================================================================
    // update view
    //================================================================================
    public void updateMobileAreaNumber(String mobileAreaNumber) {
        mTextMobileArea.setText(mobileAreaNumber);
    }

    //================================================================================
    // dialog
    //================================================================================
    private void showBindNoticeDialog(@StringRes int contentResId, @StringRes int positiveTextRes) {
        String contentText = GlobalVars.context.getString(contentResId);
        if (StringUtils.isEmpty(contentText)) {
            return;
        }
        final DialogUtil dialogUtil = new DialogUtil(this.getActivity());
        dialogUtil.showNoticeDialog(R.string.prompt, contentText, positiveTextRes, false, new SingleButtonCallback() {
            @Override
            public void onClick(DialogUtilAction which) {
                dialogUtil.dismissDialog();
            }
        });
    }

    //================================================================================
    // goto other activity
    //================================================================================
    private void gotoCheckShopActivity() {
        MRouter.getInstance().build(RouterPathConstant.CheckShop.PATH_CHECK_SHOP_ACTIVITY)
                .putInt(RouterPathConstant.CheckShop.FROM, RouterPathConstant.CheckShop.FROM_LOGIN)
                .navigation(this.getActivity());
        getActivity().finish();
    }

    private void gotoWorkModelActivity() {
        MRouter.getInstance().build(RouterPathConstant.WorkModel.PATH)
                .putInt(RouterPathConstant.WorkModel.FROM, RouterPathConstant.WorkModel.FROM_LOGIN)
                .navigation(this.getActivity());
        getActivity().finish();
    }

    private void gotoMainActivity() {
        MRouter.getInstance().build(RouterPathConstant.PATH_MAIN_ACTIVITY)
                .navigation(this.getActivity());
        getActivity().finish();
    }
}
