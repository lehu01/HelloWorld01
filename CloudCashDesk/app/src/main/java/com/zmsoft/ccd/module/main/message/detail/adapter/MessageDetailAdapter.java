package com.zmsoft.ccd.module.main.message.detail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerAdapter;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.viewholder.MessageDetailComboItemViewholder;
import com.zmsoft.ccd.module.main.message.detail.adapter.viewholder.MessageDetailComboTitleViewholder;
import com.zmsoft.ccd.module.main.message.detail.adapter.viewholder.MessageDetailFoodItemViewholder;
import com.zmsoft.ccd.module.main.message.detail.adapter.viewholder.MessageDetailFoodTitleViewholder;
import com.zmsoft.ccd.module.main.message.detail.adapter.viewholder.MessageDetailOrderViewholder;
import com.zmsoft.ccd.module.main.message.detail.adapter.viewholder.MessageDetailPayViewholder;

import java.util.ArrayList;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class MessageDetailAdapter extends BaseRecyclerAdapter<DeskMsgDetailRecyclerItem> {
    private static final int ITEM_TYPE_FOOTER = 16;//footView
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<DeskMsgDetailRecyclerItem> mDatas;
    private RecyclerView mRecyclerView;

    public MessageDetailAdapter(Context context, RecyclerView view, ArrayList<DeskMsgDetailRecyclerItem> datas) {
        super(view, datas, 0);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mRecyclerView = view;
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DeskMsgDetailRecyclerItem.ItemType.TYPE_ORDER_INFO:
                return new MessageDetailOrderViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_msg_detail_order, parent, false)
                        , mRecyclerView, this);
            case DeskMsgDetailRecyclerItem.ItemType.TYPE_FOOD_TITLE:
                return new MessageDetailFoodTitleViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_msg_detail_foodtitle, parent, false)
                        , mRecyclerView, this);
            case DeskMsgDetailRecyclerItem.ItemType.TYPE_FOOD_ITEM:
                return new MessageDetailFoodItemViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_msg_detail_fooditem, parent, false)
                        , mRecyclerView, this);
            case DeskMsgDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_TITLE:
                return new MessageDetailComboTitleViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_msg_detail_combotitle, parent, false)
                        , mRecyclerView, this);
            case DeskMsgDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_ITEM:
                return new MessageDetailComboItemViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_msg_detail_comboitem, parent, false)
                        , mRecyclerView, this);
            case DeskMsgDetailRecyclerItem.ItemType.TYPE_PAY_ITEM:
                return new MessageDetailPayViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_msg_detail_pay, parent, false)
                        , mRecyclerView, this);
            default:
                return new BaseRecyclerHolder(mContext, new LinearLayout(mContext), this) {
                    @Override
                    public void initView(BaseRecyclerHolder holder, Object item, int position) {

                    }
                };
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mDatas.size()) {
            DeskMsgDetailRecyclerItem deskMessage = mDatas.get(position);
            if (null == deskMessage)
                return -1;
            return deskMessage.getItemType();
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
