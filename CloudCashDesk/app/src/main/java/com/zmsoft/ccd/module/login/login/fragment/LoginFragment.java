package com.zmsoft.ccd.module.login.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.constant.WeChatConstant;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.AppUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.login.EnvSwitcher;
import com.zmsoft.ccd.module.login.login.LoginContract;
import com.zmsoft.ccd.module.login.login.LoginPresenter;
import com.zmsoft.ccd.module.main.MainActivity;
import com.zmsoft.ccd.module.main.RetailMainActivity;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.shop.bean.IndustryType;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/30 16:27.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View {

    @BindView(R.id.text_login_mobile_area)
    TextView mTextMobileArea;
    @BindView(R.id.edit_user_name)
    EditText mEditUserName;
    @BindView(R.id.edit_pass_word)
    EditText mEditPassWord;
    @BindView(R.id.button_login)
    Button mButtonLogin;
    @BindView(R.id.text_register)
    TextView mTextRegister;
    @BindView(R.id.text_forget_pass_word)
    TextView mTextForgetPassWord;

    @Inject
    public LoginPresenter mPresenter;

    private boolean mOnResumeNeedCheckWechatLogin;      // OnResume时是否要检查微信登录成功

    //================================================================================
    // singleton
    //================================================================================
    public static LoginFragment newInstance(String errorCode) {
        Bundle bundle = new Bundle();
        bundle.putString(RouterPathConstant.Login.EXTRA_CODE, errorCode);

        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        // 校验token
        Bundle bundle = getArguments();
        if (null != bundle) {
            String errorCode = bundle.getString(RouterPathConstant.Login.EXTRA_CODE);
            if (!StringUtils.isEmpty(errorCode)) {
                mPresenter.checkTokenValidate(errorCode);
            }
        }

        initFragmentData();
        initCountryCode();
        initUserName();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    //================================================================================
    // life cycle
    //================================================================================
    @Override
    public void onResume() {
        super.onResume();
        if (mOnResumeNeedCheckWechatLogin) {
            mOnResumeNeedCheckWechatLogin = false;
            checkWechatLoginSuccess();
        }
    }

    //================================================================================
    // init
    //================================================================================
    private void initFragmentData() {
        mOnResumeNeedCheckWechatLogin = false;
    }

    private void initCountryCode() {
        String countryCode = mPresenter.getLastCountryCode();
        if (!StringUtils.isEmpty(countryCode)) {
            mTextMobileArea.setText(countryCode);
        }
    }

    private void initUserName() {
        String userName = mPresenter.getLastUserInfo();
        if (!StringUtils.isEmpty(userName)) {
            mEditUserName.setText(userName);
            mEditUserName.setSelection(userName.length());
        }
    }

    //================================================================================
    // update view
    //================================================================================
    public void updateMobileArea(String mobileAreaNumber) {
        mTextMobileArea.setText(mobileAreaNumber);
    }

    //================================================================================
    // LoginContract.View
    //================================================================================
    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = (LoginPresenter) presenter;
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

    @Override
    public void loginSuccess(User user, String countryCode) {
        if (user != null) {
            mPresenter.saveUser(user);
            mPresenter.saveLastCountryCode(countryCode);
            if (user.isNeedCheckShop()) {
                gotoCheckShopActivity();
            } else {
                mPresenter.getConfigSwitchVal(UserHelper.getEntityId()
                        , String.valueOf(SystemDirCodeConstant.TYPE_SYSTEM)
                        , SystemDirCodeConstant.TURN_ON_CLOUD_CASH);
            }
        }
    }

    @Override
    public void loadDataError(String errorMessage) {
        toastMsg(errorMessage);
    }

    @Override
    public void showTokenError(String errorCode) {
        if (!TextUtils.isEmpty(errorCode)) {
            if (errorCode.equals(HttpHelper.HttpErrorCode.ERR_PUB200001)
                    || errorCode.equals(HttpHelper.HttpErrorCode.ERR_PUB200002)
                    || errorCode.equals(HttpHelper.HttpErrorCode.ERR_PUB200003)) {
                String dialogContent = getString(R.string.token_expired_logout_hint);
                if (!errorCode.equals(HttpHelper.HttpErrorCode.ERR_PUB200002)) {
                    dialogContent = getString(R.string.token_exception_logout_hint);
                }
                getDialogUtil().showNoticeDialog(R.string.material_dialog_title
                        , dialogContent
                        , R.string.material_dialog_sure
                        , false
                        , null);
            }
        }
    }

    @Override
    public void showInvalidateMsg(int messageId) {
        showToast(getString(messageId));
    }

    @Override
    public void wechatBindMobile(String wechatUnionId) {
        String mobileAreaNumber = mTextMobileArea.getText().toString().trim();
        String phone = mEditUserName.getText().toString().trim();
        int from = RouterPathConstant.Register.FROM_VALUE.BIND_MOBILE;
        MRouter.getInstance().build(RouterPathConstant.Register.PATH)
                .putInt(RouterPathConstant.Register.FROM, from)
                .putString(RouterPathConstant.Register.WECHAT_UNION_ID, wechatUnionId)
                .putString(RouterPathConstant.Register.COUNTRY_CODE, mobileAreaNumber)
                .putString(RouterPathConstant.Register.PHONE, phone)
                .navigation(this.getActivity());
    }

    //================================================================================
    // click
    //================================================================================
    @OnClick(R.id.button_login)
    void doLogin() {
        String mobileAreaNumber = mTextMobileArea.getText().toString().trim();
        String userName = mEditUserName.getText().toString().trim();
        String passWord = mEditPassWord.getText().toString().trim();
        if (mPresenter.checkLoginValidate(userName, passWord)) {
            mPresenter.doLogin(mobileAreaNumber, userName, passWord);
        }
    }

    @OnLongClick(R.id.button_login)
    boolean doFakeLogin() {
        new EnvSwitcher().show(this.getActivity());
        return true;
    }

    @OnClick({R.id.text_register, R.id.text_forget_pass_word, R.id.text_verify_code_login})
    void processClick(View view) {
        switch (view.getId()) {
            case R.id.text_register:
                gotoRegisterActivity(RouterPathConstant.Register.FROM_VALUE.REGISTER);
                break;
            case R.id.text_forget_pass_word:
                gotoRegisterActivity(RouterPathConstant.Register.FROM_VALUE.FORGET_PASSWORD);
                break;
            case R.id.text_verify_code_login:
                gotoRegisterActivity(RouterPathConstant.Register.FROM_VALUE.VERIFY_CODE_LOGIN);
                break;
        }
    }

    @OnClick(R.id.text_login_mobile_area)
    void onClickMobileArea() {
        MRouter.getInstance().build(RouterPathConstant.MobileArea.PATH)
                .navigation(this.getActivity(), RouterPathConstant.Login.REQUEST_CODE_LOGIN_TO_MOBILE_AREA);
    }

    @OnClick(R.id.text_wechat_login)
    void doWeChatLogin() {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(this.getActivity().getApplicationContext(), WeChatConstant.APP_WECHAT_ID, true);
        boolean isWechatInstalled = wxapi.isWXAppInstalled();
        wxapi.detach();
        // 未安装微信
        if (!isWechatInstalled) {
            final DialogUtil dialogUtil = new DialogUtil(this.getActivity());
            dialogUtil.showDialog(R.string.prompt, R.string.wechat_uninstalled_hint
                    , R.string.at_once_install
                    , R.string.cancel
                    , true, new SingleButtonCallback() {
                        @Override
                        public void onClick(DialogUtilAction which) {
                            if (which == DialogUtilAction.POSITIVE) {
                                AppUtils.openUrl(getActivity(), WeChatConstant.URL_DOWNLOAD_WECHAT);
                            } else if (which == DialogUtilAction.NEGATIVE) {
                                dialogUtil.dismissDialog();
                            }
                        }
                    });
            return;
        }
        showLoading(GlobalVars.context.getString(R.string.wechat_login), true);
        // 打开该Activity即发起微信授权请求，最后结果保存在sp中
        MRouter.getInstance().build(RouterPathConstant.WxEntry.PATH)
                .navigation(this.getActivity());
        mOnResumeNeedCheckWechatLogin = true;
    }

    //================================================================================
    // wechat login
    //================================================================================
    private void checkWechatLoginSuccess() {
        boolean isWechatLoginSuccess = BaseSpHelper.isWechatLoginSuccess(GlobalVars.context);
        if (isWechatLoginSuccess) {
            String wechatCode = BaseSpHelper.getWechatLoginCode(GlobalVars.context);
            BaseSpHelper.saveWechatLoginSuccess(GlobalVars.context, false);
            if (wechatCode != null) {
                mPresenter.wechatLogin(wechatCode);
            }
        } else {
            // 微信登录失败，返回后取消load view
            hideLoading();
        }
    }

    //================================================================================
    // goto the other activity
    //================================================================================
    // 注册、忘记密码、验证码登录
    private void gotoRegisterActivity(int from) {
        String mobileAreaNumber = mTextMobileArea.getText().toString().trim();
        String phone = mEditUserName.getText().toString().trim();

        MRouter.getInstance().build(RouterPathConstant.Register.PATH)
                .putInt(RouterPathConstant.Register.FROM, from)
                .putString(RouterPathConstant.Register.COUNTRY_CODE, mobileAreaNumber)
                .putString(RouterPathConstant.Register.PHONE, phone)
                .navigation(this.getActivity());
    }

    // 选店
    private void gotoCheckShopActivity() {
        if (UserHelper.getIndustry() == IndustryType.RETAIL) {
            MRouter.getInstance().build(RouterPathConstant.RetailCheckShop.PATH_CHECK_SHOP_ACTIVITY)
                    .putInt(RouterPathConstant.RetailCheckShop.FROM, RouterPathConstant.RetailCheckShop.FROM_LOGIN)
                    .navigation(this);
        } else {
            MRouter.getInstance().build(RouterPathConstant.CheckShop.PATH_CHECK_SHOP_ACTIVITY)
                    .putInt(RouterPathConstant.CheckShop.FROM, RouterPathConstant.CheckShop.FROM_LOGIN)
                    .navigation(this);
        }
        getActivity().finish();
    }

    // 工作模式
    private void gotoWorkModelActivity() {
        MRouter.getInstance().build(RouterPathConstant.WorkModel.PATH)
                .putInt(RouterPathConstant.WorkModel.FROM, RouterPathConstant.WorkModel.FROM_LOGIN)
                .navigation(this.getActivity());
        getActivity().finish();
    }

    private void gotoMainActivity() {
        Intent intent;
        if (UserHelper.getIndustry() == IndustryType.RETAIL) {
            intent = new Intent(getActivity(), RetailMainActivity.class);
        } else {
            intent = new Intent(getActivity(), MainActivity.class);
        }
        startActivity(intent);
        getActivity().finish();
    }
}
