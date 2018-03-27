package com.zmsoft.ccd.lib.base.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.dfire.mobile.network.Network;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.lib.base.BaseContractView;
import com.zmsoft.ccd.lib.base.R;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.base.widget.LoadingDialog;
import com.zmsoft.ccd.lib.utils.SystemBarTintManager;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.AppSettingsDialog;
import com.zmsoft.ccd.lib.widget.stateview.StateView;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/12 14:31
 */
public class BaseActivity extends AppCompatActivity implements BaseContractView {

    private Dialog progressDialog;
    private DialogUtil mDialogUtil;
    /**
     * 界面加载状态的view
     */
    protected StateView mStateView;

    /**
     * 当前是否已弹框
     */
    private boolean isShowingDialog = false;
    /**
     * 当前页面的网络请求是否已请求完毕
     */
    private boolean isNetCallbackComplete = false;
    /**
     * 弹框Observable的Subscription
     */
    private Subscription mNetDialogSubscription;

    private Unbinder mUnbinder;

    /**
     * 用来标识是否调用了onSavedInstance，如果状态已保存过，就不要再处理onBackPressed
     * ，以此来解决“IllegalStateException: Can not perform this action after onSaveInstanceState”
     */
    private boolean mStateSaved;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStateSaved = false;
        keepFontSize();
        RouterActivityManager.get().add(this);
        MRouter.getInstance().inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            updateStatusBarColor(android.R.color.transparent);
        } else {
            translucentStatus();
            fitsSystemWindows();
        }
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            updateStatusBarColor(android.R.color.transparent);
        } else {
            translucentStatus();
            fitsSystemWindows();
        }
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            updateStatusBarColor(android.R.color.transparent);
        } else {
            translucentStatus();
            fitsSystemWindows();
        }
        mUnbinder = ButterKnife.bind(this);
    }

    private void keepFontSize() {
        Resources res = getResources();
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public void showBackIcon() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mStateSaved = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStateSaved = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStateSaved = false;
        registEventBus();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        mStateSaved = true;
        unRegistEventBus();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        hideProgressDialog();
        Network.cancel(this);//取消当前正在执行的网络请求
        unBindPresenterFromView();
        super.onDestroy();
        RouterActivityManager.get().finishActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mStateSaved) {
                return true;
            }
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void showProgressDialog(boolean cancelAble) {
        showProgressDialog(getString(R.string.dialog_loading), cancelAble);
    }

    protected void showProgressDialog(String content, boolean cancelAble) {
        showProgressDialog(null, content, cancelAble);
    }

    public void showProgressDialog(String title, String content, boolean cancelAble) {
//        showProgressDialogInternal(title, content, cancelAble);
        lazyShowDialog(title, content, cancelAble);
    }

    private void showProgressDialogInternal(String title, String content, boolean cancelAble) {
        isShowingDialog = true;
        if (!isActivityEnabled()) {
            return;
        }
        if (TextUtils.isEmpty(content)) {
            content = getString(R.string.dialog_loading);
        }
//        getDialogUtil().showIndeterminateProgressDialog(content, cancelAble);
        try {
            if (progressDialog != null) {
                progressDialog.setTitle(title);
                if (progressDialog instanceof LoadingDialog) {
                    ((LoadingDialog) progressDialog).setContent(content);
                }
                progressDialog.show();
            } else {
                progressDialog = new LoadingDialog.Builder(this)
                        .title(title)
                        .content(content)
                        .cancelable(cancelAble)
                        .show();
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        isShowingDialog = false;
                    }
                });
            }
        } catch (Exception | Error e) {
            Logger.d("dialog error", "show dialog" + e.getMessage());
        }
    }

    public void showPermissionRationale(@StringRes int titleResId) {
        showPermissionRationale(titleResId, null, null);
    }

    public void showPermissionRationale(@StringRes int titleResId, DialogInterface.OnClickListener negativeListener, AppSettingsDialog.AfterGotoAppSettingsHook afterGotoAppSettingsHook) {
        showPermissionRationale(titleResId, R.string.rationale_ask_again, negativeListener, afterGotoAppSettingsHook);
    }

    protected void showPermissionRationale(@StringRes int titleResId, @StringRes int rationaleResId, DialogInterface.OnClickListener negativeListener, AppSettingsDialog.AfterGotoAppSettingsHook afterGotoAppSettingsHook) {
        showPermissionRationale(titleResId, getString(rationaleResId), negativeListener, afterGotoAppSettingsHook);
    }

    protected void showPermissionRationale(@StringRes int titleResId, String Content, DialogInterface.OnClickListener negativeListener, AppSettingsDialog.AfterGotoAppSettingsHook afterGotoAppSettingsHook) {
        new AppSettingsDialog.Builder(this)
                .setRationale(Content)
                .setTitle(getString(titleResId))
                .setPositiveButton(R.string.go_setting)
                .setNegativeButton(R.string.cancel)
                .setNegativeListener(negativeListener)
                .setAfterGotoAppSettingsHook(afterGotoAppSettingsHook)
                .build()
                .show();
    }


    /**
     * 延时弹框，0.5s内网络请求完毕时不再弹框，否则0.5s后弹框
     *
     * @param title
     * @param content
     * @param cancelAble
     */
    private void lazyShowDialog(final String title, final String content, final boolean cancelAble) {
        isNetCallbackComplete = false;
        mNetDialogSubscription = RxUtils.fromCallable(new Callable<Void>() {
            @Override
            public Void call() {
                return null;
            }
        }).delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (!isShowingDialog && !isNetCallbackComplete) {
                            showProgressDialogInternal(title, content, cancelAble);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    public void hideProgressDialog() {
        isShowingDialog = false;
        isNetCallbackComplete = true;
        if (null != mNetDialogSubscription && !mNetDialogSubscription.isUnsubscribed()) {
            mNetDialogSubscription.unsubscribe();
        }
        dismissDialog();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean isActivityEnabled() {
        return !isFinishing() && !(Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT && isDestroyed());
    }

    private void dismissDialog() {
        if (!isActivityEnabled()) {
            return;
        }
//        getDialogUtil().dismissDialog();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
//        try {
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//                progressDialog = null;
//            }
//        } catch (Exception | Error e) {
//            LogUtil.d("dialog error", "dismiss dialog" + e.getMessage());
//        }
    }

    protected void showToast(String text) {
        ToastUtils.showShortToastSafe(this, text);
    }

    protected void showToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 子类如果需要注册eventbus，则重写此方法
     */
    protected void registEventBus() {
        //EventBus.getDefault().register(this);
    }

    /**
     * 子类如果需要注销eventbus，则重写此方法
     */
    protected void unRegistEventBus() {
        //EventBus.getDefault().unregister(this);
    }

    public void hideSoftInputMethod() {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 设置透明状态栏
     */
    public void translucentStatus() {
        setTranslucentStatus(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void fitsSystemWindows() {
        Window window = getWindow();
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        if (mContentView != null) {
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                mChildView.setFitsSystemWindows(true);
            }
        }
    }

    //================================================================================
    // status bar
    //================================================================================
    protected void updateStatusBarColor(@ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(ContextCompat.getColor(this, colorRes));

            ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                ViewCompat.setFitsSystemWindows(mChildView, true);
            }
        }
    }

    @Override
    public void showLoading(boolean cancelAble) {
        showProgressDialog(cancelAble);
    }

    @Override
    public void showLoading(String content, boolean cancelAble) {
        showProgressDialog(content, cancelAble);
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    protected void toastMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void showLoadingView() {
        if (null == mStateView) {
            injectStateView();
        }
        if (null != mStateView) {
            mStateView.showLoading();
        }
    }

    @Override
    public void showErrorView(String errorMessage) {
        if (null == mStateView) {
            injectStateView();
        }
        if (null != mStateView) {
            mStateView.showRetry(R.id.button_retry, R.id.text_hint, errorMessage);
        }
    }

    @Override
    public void showEmptyView() {
        if (null == mStateView) {
            injectStateView();
        }
        if (null != mStateView) {
            mStateView.showEmpty();
        }
    }

    @Override
    public void showEmptyView(int clickableChildResId) {
        if (null == mStateView) {
            injectStateView();
        }
        if (null != mStateView) {
            mStateView.showEmpty(clickableChildResId);
        }
    }

    @Override
    public void showContentView() {
        if (null == mStateView) {
            injectStateView();
        }
        if (null != mStateView) {
            mStateView.showContent();
        }
    }

    @Override
    public void onBackPressed() {
        if (mStateSaved) {
            return;
        }
        handleBack();
        super.onBackPressed();
    }

    protected void handleBack() {

    }

    /**
     * 获取DialogUtil类
     *
     * @return
     */
    protected DialogUtil getDialogUtil() {
        if (null == mDialogUtil) {
            mDialogUtil = new DialogUtil(this);
        }
        return mDialogUtil;
    }

    /**
     * 注入加载状态的view
     */
    private void injectStateView() {
        mStateView = StateView.inject(this, true);
        if (null == mStateView) {
            return;
        }
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                clickRetryView();
            }

            @Override
            public void onEmptyClick() {
                clickEmptyView();
            }
        });
    }

    /**
     * 重试网络请求,需要该功能的子activity重写该方法
     */
    protected void clickRetryView() {
    }

    /**
     * 如果需要对空界面做操作，比如点击按钮跳转界面，可以调用该方法
     */
    protected void clickEmptyView() {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 解除presenter和view的绑定，将view置为null，防止页面关闭在网络接口回调之前，导致crash的问题
     */
    protected void unBindPresenterFromView() {

    }
}
