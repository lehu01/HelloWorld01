package com.zmsoft.ccd.lib.base.adapter;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/3/4.
 */

public abstract class BaseHolder extends RecyclerView.ViewHolder {

    private Context context;

    public BaseHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public CharSequence getString(@StringRes int resId) {
        return context.getString(resId);
    }

    public CharSequence getString(@StringRes int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    public int getColor(@ColorRes int colorId) {
        return getContext().getResources().getColor(colorId);
    }


    protected abstract void bindView(List source, Object bean);
}
