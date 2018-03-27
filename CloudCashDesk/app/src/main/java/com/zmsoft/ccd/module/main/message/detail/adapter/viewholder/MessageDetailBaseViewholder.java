package com.zmsoft.ccd.module.main.message.detail.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;
import com.zmsoft.ccd.lib.widget.couponview.LinearLayoutCouponView;
import com.zmsoft.ccd.lib.widget.couponview.RelativeLayoutCouponView;
import com.zmsoft.ccd.module.main.message.detail.adapter.MessageDetailAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class MessageDetailBaseViewholder extends BaseRecyclerHolder<DeskMsgDetailRecyclerItem> {
    protected ArrayList<DeskMsgDetailRecyclerItem> mDatas;
    protected RecyclerView mRecyclerView;
    protected MessageDetailAdapter mRecyclerItemAdapter;
    protected View mItemView;
    protected DeskMsgDetailRecyclerItem mDeskMsgDetailRecyclerItem;
    protected int mItemType;

    public MessageDetailBaseViewholder(Context context, View itemView, RecyclerView recyclerView
            , RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        if (null != adapter && adapter instanceof MessageDetailAdapter)
            mRecyclerItemAdapter = (MessageDetailAdapter) adapter;
        this.mDatas = (ArrayList<DeskMsgDetailRecyclerItem>) mRecyclerItemAdapter.getDatasList();
        this.mRecyclerView = recyclerView;
        this.mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, DeskMsgDetailRecyclerItem msgDetailRecyclerItemObj
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
        } else {
            mItemType = mDeskMsgDetailRecyclerItem.getItemType();
            switch (mItemType) {
                case DeskMsgDetailRecyclerItem.ItemType.TYPE_PAY_ITEM:
                    if (mDatas.get(position + 1).getItemType() != DeskMsgDetailRecyclerItem.ItemType.TYPE_PAY_ITEM) {
                        setSemicircle(false, true);
                    } else {
                        setSemicircle(false, false);
                    }
                    break;
                case DeskMsgDetailRecyclerItem.ItemType.TYPE_FOOD_TITLE:
                    setSemicircle(true, false);
                    break;
                case DeskMsgDetailRecyclerItem.ItemType.TYPE_FOOD_ITEM:
                    if (mDatas.get(position + 1).getItemType() != DeskMsgDetailRecyclerItem.ItemType.TYPE_FOOD_ITEM) {
                        if (mDatas.get(position + 1).getItemType() == DeskMsgDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_TITLE) {
                            setSemicircle(false, false);
                        } else {
                            setSemicircle(false, true);
                        }
                    } else {
                        setSemicircle(false, false);
                    }
                    break;
                case DeskMsgDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_TITLE:
                    setSemicircle(false, false);
                    break;
                case DeskMsgDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_ITEM:
                    if (mDatas.get(position + 1).getItemType() != DeskMsgDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_ITEM) {
                        if (mDatas.get(position + 1).getItemType() == DeskMsgDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_TITLE) {
                            setSemicircle(false, false);
                        } else if (mDatas.get(position + 1).getItemType() == DeskMsgDetailRecyclerItem.ItemType.TYPE_FOOD_ITEM) {
                            setSemicircle(false, false);
                        } else {
                            setSemicircle(false, true);
                        }
                    } else {
                        setSemicircle(false, false);
                    }
                    break;
                default:
                    break;
            }
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
