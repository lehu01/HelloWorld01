package com.zmsoft.ccd.lib.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author DangGui
 * @create 2016/12/19.
 */
public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    public Context context;
    public RecyclerView.Adapter adapter;

    public BaseRecyclerHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(itemView);
        this.context = context;
        this.mViews = new SparseArray<>();
        this.adapter = adapter;
    }

    public SparseArray<View> getAllView() {
        return mViews;
    }

    public abstract void initView(BaseRecyclerHolder holder, T item, int position);

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseRecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public BaseRecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }
}
