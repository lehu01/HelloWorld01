package com.zmsoft.ccd.lib.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiclaim.modularization.router.MRouter;
import com.dfire.mobile.network.Network;
import com.zmsoft.ccd.lib.base.BaseContractView;
import com.zmsoft.ccd.lib.base.R;
import com.zmsoft.ccd.lib.base.activity.BaseActivity;
import com.zmsoft.ccd.lib.utils.KeyboardUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.stateview.StateView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * @email：danshen@2dfire.com
 * @time : 2016/11/25 14:22
 */
public abstract class BaseFragment extends Fragment implements BaseContractView {
    private DialogUtil mDialogUtil;
    /**
     * 界面加载状态的view
     */
    protected StateView mStateView;
    /**
     * 界面的根布局
     */
    private View rootView;

    /**
     * 是否可见
     */
    private boolean isVisible;
    /**
     * 视图是否初始化完毕
     */
    private boolean isInvokedOnCreateView;

    /**
     * setUserVisibleHint()方法是否被调用
     */
    private boolean isSetUserVisibleHintInvoked;

    private Unbinder unbinder;

    private CompositeSubscription mCompositeSub;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isSetUserVisibleHintInvoked = true;
        if (isVisible = getUserVisibleHint()) {
            onVisible();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isInvokedOnCreateView = true;
        MRouter.getInstance().inject(this);
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = createView(inflater, container);
            unbinder = ButterKnife.bind(this, rootView);
            initView(rootView, savedInstanceState);
            initListener();
            initData();
            registerEventBus();

            //说明不是和Viewpager一起用
            if (!isSetUserVisibleHintInvoked) {
                isVisible = true;
            }
        }
        onVisible();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);//声明执行onCreateOptionsMenu方法
    }

    private void onVisible() {
        //当前界面可见状态，且执行了onCreateView方法才去加载数据
        if (isVisible && isInvokedOnCreateView) {
            lazyLoad();
        }
    }

    /**
     * 初始化参数. 该方法在onCreate方法里调用
     */
    protected void initParameters() {

    }

    /**
     * 加载数据的操作直接放在该方法里
     */
    protected void lazyLoad() {

    }


    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        unRegisterEventBus();
        Network.cancel(this);//取消当前正在执行的网络请求
        unBindPresenterFromView();
        if (mCompositeSub != null && !mCompositeSub.isUnsubscribed()) {
            mCompositeSub.unsubscribe();
        }
        isInvokedOnCreateView = false;
        super.onDestroyView();
    }

    /**
     * 获取fragment布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * view初始化
     *
     * @param view               view对象
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * @see #lazyLoad()
     */
    @Deprecated
    protected void initData() {

    }

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    protected void showToast(String text) {
        ToastUtils.showShortToast(getActivity(), text);
    }

    protected void showProgressDialog(boolean cancelAble) {
        showProgressDialog(null, cancelAble);
    }

    protected void showToast(@StringRes int text) {
        ToastUtils.showShortToast(getActivity(), text);
    }


    protected void showProgressDialog(String text, boolean cancelAble) {
        showProgressDialog(null, text, cancelAble);
    }

    protected void showProgressDialog(String title, String text, boolean cancelAble) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            baseActivity.showProgressDialog(title, text, cancelAble);
        }
    }

    protected void hideProgressDialog() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            baseActivity.hideProgressDialog();
        }
    }

    public void hideSoftInputMethod() {
        KeyboardUtils.hideSoftInput(getActivity());
    }

    public boolean onBackPressed() {
        return false;
    }

    /**
     * 判断宿主activity是否存在，建议在调用getActivity()方法前调用该方法进行判断
     *
     * @return true/false
     */
    protected boolean isHostActive() {
        return isAdded();
    }

    /**
     * 子类如果需要注册eventbus，则重写此方法
     */
    protected void registerEventBus() {
//        EventBus.getDefault().register(this);
    }

    /**
     * 子类如果需要注销eventbus，则重写此方法
     */
    protected void unRegisterEventBus() {
        //EventBus.getDefault().unregister(this);
    }

    /**
     * 获取DialogUtil类
     *
     * @return
     */
    protected DialogUtil getDialogUtil() {
        if (null == mDialogUtil) {
            mDialogUtil = new DialogUtil(getActivity());
        }
        return mDialogUtil;
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

    protected void toastMsg(String successMsg) {
        showToast(successMsg);
    }

    @Override
    public void showLoadingView() {
        if (null == mStateView) {
            injectStateView(rootView);
        }
        if (null != mStateView) {
            mStateView.showLoading();
        }
    }

    @Override
    public void showErrorView(String errorMessage) {
        if (null == mStateView) {
            injectStateView(rootView);
        }
        if (null != mStateView) {
            mStateView.showRetry(R.id.button_retry, R.id.text_hint, errorMessage);
        }
    }

    @Override
    public void showEmptyView() {
        if (null == mStateView) {
            injectStateView(rootView);
        }
        if (null != mStateView) {
            mStateView.showEmpty();
        }
    }

    @Override
    public void showEmptyView(int clickableChildResId) {
        if (null == mStateView) {
            injectStateView(rootView);
        }
        if (null != mStateView) {
            mStateView.showEmpty(clickableChildResId);
        }
    }

    @Override
    public void showContentView() {
        if (null == mStateView) {
            injectStateView(rootView);
        }
        if (null != mStateView) {
            mStateView.showContent();
        }
    }

    /**
     * 注入加载状态的view
     */
    protected void injectStateView(View view) {
        mStateView = StateView.inject(view, true);
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
     * 重试网络请求,需要该功能的子fragment重写该方法
     */
    protected void clickRetryView() {
    }

    /**
     * 如果需要对空界面做操作，比如点击按钮跳转界面，可以调用该方法
     */
    protected void clickEmptyView() {
    }

    /**
     * 解除presenter和view的绑定，将view置为null，防止页面关闭在网络接口回调之前，导致crash的问题
     */
    public abstract void unBindPresenterFromView();

    protected void addRxSubscription(Subscription s) {
        if (mCompositeSub == null) {
            mCompositeSub = new CompositeSubscription();
        }
        mCompositeSub.add(s);
    }

    protected View getRootView() {
        return rootView;
    }
}
