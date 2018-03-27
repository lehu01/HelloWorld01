package com.zmsoft.ccd.lib.base.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.zmsoft.ccd.lib.base.R;
import com.zmsoft.ccd.lib.base.activity.CcdWebViewAcitivity;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;

/**
 * @author DangGui
 * @create 2017/9/5.
 */

public class CcdWebViewFragment extends BaseFragment {
    private RelativeLayout mRootLayout;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String mUrl;

    public static CcdWebViewFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(CcdWebViewAcitivity.EXTRA_PARAM_URL, url);
        CcdWebViewFragment fragment = new CcdWebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_webview;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRootLayout = (RelativeLayout) view.findViewById(R.id.rootlayout);
        mWebView = (WebView) view.findViewById(R.id.webview);
        mProgressBar = (ProgressBar) view.findViewById(R.id.loading_progressbar);
        initWebView();
        Bundle bundle = getArguments();
        if (null != bundle) {
            mUrl = bundle.getString(CcdWebViewAcitivity.EXTRA_PARAM_URL);
        }
        loadUrl();
    }

    @Override
    protected void initListener() {
        //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        mWebView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (isHostActive()) {
                    getActivity().setTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                getDialogUtil().showDialog(R.string.webview_geolocation_alert_title,
                        String.format(getString(R.string.webview_geolocation_alert_content)
                                , origin),
                        R.string.webview_geolocation_alert_accept,
                        R.string.webview_geolocation_alert_reject,
                        true, new SingleButtonCallback() {
                            @Override
                            public void onClick(DialogUtilAction which) {
                                if (which == DialogUtilAction.POSITIVE) {
                                    callback.invoke(origin, true, true);
                                } else if (which == DialogUtilAction.NEGATIVE) {
                                    callback.invoke(origin, false, false);
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {

    }

    public WebView getWebView() {
        return mWebView;
    }

    private void initWebView() {
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
    }

    private void loadUrl() {
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onDestroyView() {
        if (null != mWebView) {
            mRootLayout.removeView(mWebView);
            mWebView.destroy();
        }
        super.onDestroyView();
    }
}
