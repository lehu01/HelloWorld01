package com.zmsoft.ccd.lib.base.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.lib.base.R;


/**
 * 这个类跟ToolBarActivity几乎一样，唯一的区别是生成Toolbar的方式不同，ToolBarActivity是通过代码动态生成一个Toolbar
 * ，该类使用的前提是已经在界面layout里注册过了Toolbar
 *
 * @author DangGui
 * @create 2016/12/15.
 * @see ToolBarActivity
 */
public class NormalToolBarActivity extends BaseActivity {

    public static final String LOG_TAG = NormalToolBarActivity.class.getSimpleName();

    public static final int TITLE_MODE_LEFT = -1;
    public static final int TITLE_MODE_CENTER = -2;
    public static final int TITLE_MODE_NONE = -3;

    private int titleMode = TITLE_MODE_CENTER;

    private TextView tvTitle;
    private Toolbar toolbar;
    private FrameLayout toolbarLayout;
    private LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setToolbarCustomView(int layoutResId) {
        try {
            LayoutInflater.from(this).inflate(layoutResId, toolbar, true);
        } catch (Exception e) {
            Logger.w(LOG_TAG, "set toolbar customview exception");
        }
        tvTitle.setVisibility(View.GONE);
    }

    protected void setToolbarCustomView(View view) {
        if (toolbarLayout != null && view != null) {
            toolbar.addView(view, view.getLayoutParams());
        }
        tvTitle.setVisibility(View.GONE);
    }


    protected View getContentView() {
        return contentView;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
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
    public void setContentView(View view) {
        setNewContentView(view, view.getLayoutParams());
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        setNewContentView(view, params);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    private void setNewContentView(View view, ViewGroup.LayoutParams params) {
        // 添加toolbar布局
        contentView.addView(toolbarLayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // content FrameLayout
        FrameLayout contentLayout = new FrameLayout(this);
        if (params == null) {
            params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        contentLayout.addView(view, params);

        // 添加content布局
        contentView.addView(contentLayout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        super.setContentView(contentView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setTitle(CharSequence title, int mode) {
        this.titleMode = mode;
        setTitle(title);
    }

    public void setTitleMode(int mode) {
        this.titleMode = mode;
        setTitle(getTitle());
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (titleMode == TITLE_MODE_NONE) {
            if (toolbar != null) {
                toolbar.setTitle("");
            }
            if (tvTitle != null) {
                tvTitle.setText("");
            }
        } else if (titleMode == TITLE_MODE_LEFT) {
            if (toolbar != null) {
                toolbar.setTitle(title);
            }
            if (tvTitle != null) {
                tvTitle.setText("");
            }
        } else if (titleMode == TITLE_MODE_CENTER) {
            if (toolbar != null) {
                toolbar.setTitle("");
            }
            if (tvTitle != null) {
                tvTitle.setText(title);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void handleBack() {
        super.handleBack();
    }

    /**
     * -------------Toolbar的操作方法-----------------
     */

    protected void setNavigationIcon(int resId) {
        if (toolbar != null) {
            toolbar.setNavigationIcon(resId);
        }
    }

    protected void setNavigationIcon(Drawable icon) {
        if (toolbar != null) {
            toolbar.setNavigationIcon(icon);
        }
    }
}
