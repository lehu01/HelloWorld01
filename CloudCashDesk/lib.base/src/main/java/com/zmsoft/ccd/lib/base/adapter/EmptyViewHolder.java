package com.zmsoft.ccd.lib.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * EmptyViewHolder 空的item，容错处理
 *
 * @author DangGui
 * @create 2016/12/19.
 */
public class EmptyViewHolder extends BaseRecyclerHolder {

    public EmptyViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, Object item, int position) {

    }
}
