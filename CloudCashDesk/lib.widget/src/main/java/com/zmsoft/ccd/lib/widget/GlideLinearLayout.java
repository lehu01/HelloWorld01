package com.zmsoft.ccd.lib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/20 15:58
 */
public class GlideLinearLayout extends LinearLayout {

    private ViewTarget<GlideLinearLayout, GlideDrawable> viewTarget;

    public GlideLinearLayout(Context context) {
        super(context);
        viewTarget = new ViewTarget<GlideLinearLayout, GlideDrawable>(this) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                GlideLinearLayout glideLinearLayout = getView();
                glideLinearLayout.setImageAsBackground(resource);
            }
        };
    }

    public GlideLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GlideLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewTarget<GlideLinearLayout, GlideDrawable> getViewTarget() {
        return viewTarget;
    }

    public void setViewTarget(ViewTarget<GlideLinearLayout, GlideDrawable> viewTarget) {
        this.viewTarget = viewTarget;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setImageAsBackground(GlideDrawable resource) {
        setBackground(resource);
    }
}
