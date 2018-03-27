package com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.lib.widget.couponview.LinearLayoutCouponView;
import com.zmsoft.ccd.lib.widget.couponview.RelativeLayoutCouponView;
import com.zmsoft.ccd.module.main.message.takeout.adapter.RetailTakeoutMsgDetailAdapter;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailRecyclerItem;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class RetailTakeoutDetailBaseViewholder extends BaseRecyclerHolder<TakeoutDetailRecyclerItem> {
    protected Context mContext;
    protected ArrayList<TakeoutDetailRecyclerItem> mDatas;
    protected RecyclerView mRecyclerView;
    protected RetailTakeoutMsgDetailAdapter mRecyclerItemAdapter;
    protected View mItemView;
    protected TakeoutDetailRecyclerItem mDeskMsgDetailRecyclerItem;
    protected int mItemType;

    public RetailTakeoutDetailBaseViewholder(Context context, View itemView, RecyclerView recyclerView
            , RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        this.mContext = context;
        if (null != adapter && adapter instanceof RetailTakeoutMsgDetailAdapter)
            mRecyclerItemAdapter = (RetailTakeoutMsgDetailAdapter) adapter;
        this.mDatas = (ArrayList<TakeoutDetailRecyclerItem>) mRecyclerItemAdapter.getDatasList();
        this.mRecyclerView = recyclerView;
        this.mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, TakeoutDetailRecyclerItem msgDetailRecyclerItemObj
            , int position) {
        if (null == holder)
            return;
        if (null == msgDetailRecyclerItemObj)
            return;
        mDeskMsgDetailRecyclerItem = msgDetailRecyclerItemObj;
        initViewSemicircle(position);
    }

    protected void initInfoView() {
    }

    protected void initListener(final int position) {
    }

    private void initViewSemicircle(int position) {
        if (position == 0) {
            //内部嵌套了另一个CouponView，设置了top、bottom是true
            setSemicircle(false, false);
        } else if (position == mDatas.size() - 1) {
            setSemicircle(false, true);
        }
    }

    private void setSemicircle(boolean top, boolean bottom) {
        if (mItemView instanceof LinearLayoutCouponView) {
            ((LinearLayoutCouponView) mItemView).setSemicircleTop(top);
            ((LinearLayoutCouponView) mItemView).setSemicircleBottom(bottom);
        } else if (mItemView instanceof RelativeLayoutCouponView) {
            ((RelativeLayoutCouponView) mItemView).setSemicircleTop(top);
            ((RelativeLayoutCouponView) mItemView).setSemicircleBottom(bottom);
        }
    }
}
