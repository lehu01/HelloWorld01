package com.zmsoft.ccd.lib.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.zmsoft.ccd.lib.base.R;


/**
 * 自定义Dialog
 *
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 10:20
 */
public class LoadingDialog extends Dialog {
    private Context mContext;
    private TextView textView;
    private ProgressBar progressBar;

    public LoadingDialog(Context context) {
        this(context, R.style.LoadingDialogTheme);
        textView = (TextView) findViewById(R.id.tv_content);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        MDTintHelper.setTint(progressBar, Color.parseColor("#FFFFFF"));
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setContentView(R.layout.layout_loading);
    }

    @Override
    public final void setTitle(CharSequence title) {
        // do nothing
    }

    public LoadingDialog setContent(String text) {
        if (TextUtils.isEmpty(text)) {
            text = mContext.getString(R.string.loading);
        }
        textView.setText(text);
        textView.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        return this;
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public static class Builder {

        private Context context;
        private String title;
        private String content;
        private boolean cancelable;
        private OnCancelListener onCancelListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder onCancelListener(OnCancelListener listener) {
            this.onCancelListener = listener;
            return this;
        }

        public LoadingDialog show() {
            LoadingDialog dialog = new LoadingDialog(context);
            dialog.setTitle(title);
            dialog.setContent(content);
            dialog.setCancelable(cancelable);
            dialog.setOnCancelListener(onCancelListener);
            dialog.show();
            return dialog;
        }
    }
}


