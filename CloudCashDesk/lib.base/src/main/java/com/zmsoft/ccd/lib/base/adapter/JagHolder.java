package com.zmsoft.ccd.lib.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zmsoft.ccd.lib.base.R;

import java.util.List;

/**
 * 锯齿ITEM
 */
public class JagHolder extends BaseHolder {
    private View mViewJag;
    private boolean mIsUp;
    private int mTopMargin;

    public JagHolder(Context context, View itemView, boolean isUp, int topMarginDimenRes) {
        super(context, itemView);
        this.mViewJag = itemView.findViewById(R.id.view_jag);
        this.mIsUp = isUp;
        this.mTopMargin = context.getResources().getDimensionPixelSize(topMarginDimenRes);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (mIsUp) {
            mViewJag.setBackgroundResource(R.drawable.shape_item_up_jag);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) mViewJag.getLayoutParams();
            lp.topMargin = mTopMargin;
        } else {
            mViewJag.setBackgroundResource(R.drawable.shape_item_bottom_jag);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) mViewJag.getLayoutParams();
            lp.topMargin = 0;
        }
    }
}