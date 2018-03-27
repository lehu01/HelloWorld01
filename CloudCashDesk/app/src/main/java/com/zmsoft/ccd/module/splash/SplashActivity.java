package com.zmsoft.ccd.module.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;

import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.BuildConfig;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.AppEnv;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.constant.SPConstants;
import com.zmsoft.ccd.data.source.splash.dagger.DaggerSplashComponent;
import com.zmsoft.ccd.lib.base.activity.BaseActivity;
import com.zmsoft.ccd.lib.base.constant.BaseConstant;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.user.ChannelInfoRequest;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.AppSettingsDialog;
import com.zmsoft.ccd.lib.widget.dialog.AppPermissionDialog;
import com.zmsoft.ccd.lib.widget.dialog.permission.PermissionUtils;
import com.zmsoft.ccd.module.login.login.LoginActivity;
import com.zmsoft.ccd.module.main.MainActivity;
import com.zmsoft.ccd.module.main.RetailMainActivity;
import com.zmsoft.ccd.module.splash.dagger.DaggerSplashPresenterComponent;
import com.zmsoft.ccd.module.splash.dagger.SplashPresenterModule;
import com.zmsoft.ccd.shop.bean.IndustryType;

import javax.inject.Inject;

/**
 * s闪页
 *
 * @email：danshen@2dfire.com
 * @time : 2016/12/14 19:56
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {

    private static final int REQUEST_CODE_PERMISSION = 1001;    // 开启系统权限

    private boolean isGotoAppSet = false;                       // 是否进入过系统权限页面

    @Inject
    SplashPresenter mPresenter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translucentStatus();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            updateStatusBarColor(android.R.color.transparent);
        } else {
            translucentStatus();
            fitsSystemWindows();
        }
        uploadChannelInfo();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {    // 系统版本小于6.0时，不需要检查权限
            init();
        } else {
            checkPermission();  // 确认权限后，再进入init()
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 进入系统权限页面后，在返回本app，直接进入下一页面
        if (isGotoAppSet) {
            isGotoAppSet = false;
            init();
        }
    }

    private void uploadChannelInfo() {
        boolean firstTime = SPUtils.getInstance(this).getBoolean(SPConstants.FirstTimeOpenApp.FIRST_TIME, true);
        if (!AppEnv.isProduction() || !firstTime) {
            return;
        }
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                AnswerEventLogger answerEventLogger = new AnswerEventLogger();
                answerEventLogger.logChannelInfo(ChannelInfoRequest.EVENT_CODE_ACTIVE, null, BuildConfig.VERSION_NAME);
                SPUtils.getInstance(GlobalVars.context).putBoolean(SPConstants.FirstTimeOpenApp.FIRST_TIME, false);
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initDependence() {
        DaggerSplashPresenterComponent.builder()
                .splashComponent(DaggerSplashComponent.builder()
                        .appComponent(CcdApplication.getInstance()
                                .getAppComponent())
                        .build())
                .splashPresenterModule(new SplashPresenterModule(this))
                .build()
                .inject(this);
    }

    /**
     * 初始化操作
     */
    private void init() {
        User user = UserLocalPrefsCacheSource.getUser();
        checkUser(user);
    }

    private void checkUser(User user) {
        if (user != null) {
            if (user.isNeedCheckShop()) {
                gotoCheckShopActivity();
            } else {
                checkTurnOnCloudCash();
            }
        } else {
            gotoLoginActivity();
        }
    }

    /**
     * 跳转登录界面
     */
    private void gotoLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 跳转选店界面
     */
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
        finish();
    }

    /**
     * 跳转主界面
     */
    private void gotoMainActivity() {
        Intent intent;
        if (UserHelper.getIndustry() == IndustryType.RETAIL) {
            intent = new Intent(this, RetailMainActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        Intent getIntent = getIntent();
        if (null != getIntent) {
            Bundle extrasBundle = getIntent.getExtras();
            if (null != extrasBundle) {
                intent.putExtras(extrasBundle);
            }
        }
        startActivity(intent);
        finish();
    }

    /**
     * </p>
     * go to 工作模式
     */
    private void gotoWorkModelActivity() {
        MRouter.getInstance().build(RouterPathConstant.WorkModel.PATH)
                .putInt(RouterPathConstant.WorkModel.FROM, RouterPathConstant.WorkModel.FROM_LOGIN)
                .navigation(this);
        finish();
    }

    /**
     * 是否开启云收银
     */
    private void checkTurnOnCloudCash() {
        long lastTime = BaseSpHelper.getTurnCloudCashTime(this);
        if (System.currentTimeMillis() - lastTime < BaseConstant.TIME_TURN_CLOUD_CASH) {
            BaseSpHelper.saveTurnCloudCashTime(this, System.currentTimeMillis());
            gotoMainActivity();
            return;
        }
        mPresenter.getConfigSwitchVal(UserLocalPrefsCacheSource.getEntityId(this)
                , String.valueOf(SystemDirCodeConstant.TYPE_SYSTEM)
                , SystemDirCodeConstant.TURN_ON_CLOUD_CASH);

    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        mPresenter = (SplashPresenter) presenter;
    }

    @Override
    public void getTurnOnCloudSuccess(String data) {
        /***
         * 1.data空代表未设置
         * 2.0代表没有在使用云收银
         */
        if (StringUtils.isEmpty(data) || Base.STRING_FALSE.equals(data)) {
            gotoWorkModelActivity();
        } else {
            BaseSpHelper.saveTurnCloudCashTime(this, System.currentTimeMillis());
            gotoMainActivity();
        }
    }

    @Override
    public void getTurnOnCloudSuccessFailure() {
        if (UserLocalPrefsCacheSource.getUser() != null) {
            gotoWorkModelActivity();
        }
    }

    //================================================================================
    // permission
    //================================================================================
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        final PermissionUtils permissionUtils = new PermissionUtils(this.getApplicationContext());
        final String[] unGrantedPermissions = permissionUtils.getUnGrantedPermissions();
        if (unGrantedPermissions == null || unGrantedPermissions.length == 0) {
            init();
            return;
        }
        final AppPermissionDialog dialog = new AppPermissionDialog(this);
        dialog.setUnGrantPermissions(unGrantedPermissions).
                setOnClickBottomListener(new AppPermissionDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        // 弹出系统权限框，点击后进入onRequestPermissionsResult函数
                        SplashActivity.this.requestPermissions(unGrantedPermissions, REQUEST_CODE_PERMISSION);
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        // 点击返回键，直接退出APP
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                            dialog.dismiss();
                            SplashActivity.this.finish();
                        }
                        return false;
                    }
                });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            // 验证权限后，再次弹窗引导用户去设置
            final PermissionUtils permissionUtils = new PermissionUtils(this.getApplicationContext());
            String[] unGrantedPermissions = permissionUtils.getUnGrantedPermissions();
            if (unGrantedPermissions == null || unGrantedPermissions.length == 0) {
                init();
                return;
            }
            showPermissionRationale(R.string.title_settings_dialog,
                    permissionUtils.getPermissionContent(this.getApplicationContext()),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            init();
                        }
                    },
                    new AppSettingsDialog.AfterGotoAppSettingsHook() { // 设置hook原因：进入系统页面后再修改isGotoAppSet
                        @Override
                        public void afterGotoAppSetting() {
                            isGotoAppSet = true;
                        }
                    }
            );
        }
        // 系统设置页面返回
        // 部分手机无法进入该流程，所以需要isGotoAppSet变量配合
        else if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (isGotoAppSet) {
                isGotoAppSet = false;
                init();
            }
        }
    }
}
