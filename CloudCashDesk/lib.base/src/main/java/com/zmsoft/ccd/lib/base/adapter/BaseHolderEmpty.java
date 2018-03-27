package com.zmsoft.ccd.lib.base.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/3/4.
 */

public class BaseHolderEmpty extends BaseHolder {
    public BaseHolderEmpty(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {

    }
}
