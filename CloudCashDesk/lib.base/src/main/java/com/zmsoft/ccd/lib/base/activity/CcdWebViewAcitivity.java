package com.zmsoft.ccd.lib.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.zmsoft.ccd.lib.base.R;
import com.zmsoft.ccd.lib.base.fragment.CcdWebViewFragment;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @author DangGui
 * @create 2017/9/5.
 */

public class CcdWebViewAcitivity extends ToolBarActivity {
    public static final String EXTRA_PARAM_URL = "url";
    private CcdWebViewFragment mFragment;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        mUrl = getIntent().getStringExtra(EXTRA_PARAM_URL);
        mFragment = (CcdWebViewFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (mFragment == null) {
            mFragment = CcdWebViewFragment.newInstance(mUrl);
            ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.content);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mFragment.getWebView().canGoBack()) {
            mFragment.getWebView().goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public static void launchActivity(Context context, String url) {
        Intent intent = new Intent(context, CcdWebViewAcitivity.class);
        intent.putExtra(EXTRA_PARAM_URL, url);
        context.startActivity(intent);
    }
}
