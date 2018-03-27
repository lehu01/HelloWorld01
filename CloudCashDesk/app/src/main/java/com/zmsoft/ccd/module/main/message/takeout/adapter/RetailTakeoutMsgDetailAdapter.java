package com.zmsoft.ccd.module.main.message.takeout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerAdapter;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder.RetailTakeoutDetailComboItemViewholder;
import com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder.RetailTakeoutDetailComboTitleViewholder;
import com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder.RetailTakeoutDetailFoodItemViewholder;
import com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder.RetailTakeoutDetailPayDetailViewholder;
import com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder.RetailTakeoutMsgDetailOrderViewHolder;

import java.util.ArrayList;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class RetailTakeoutMsgDetailAdapter extends BaseRecyclerAdapter<TakeoutDetailRecyclerItem> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<TakeoutDetailRecyclerItem> mDatas;
    private RecyclerView mRecyclerView;

    public RetailTakeoutMsgDetailAdapter(Context context, RecyclerView view, ArrayList<TakeoutDetailRecyclerItem> datas) {
        super(view, datas, 0);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mRecyclerView = view;
    }


    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TakeoutDetailRecyclerItem.ItemType.TYPE_ORDER_INFO:
                return new RetailTakeoutMsgDetailOrderViewHolder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_retail_takeout_detail_order, parent, false)
                        , mRecyclerView, this);
            case TakeoutDetailRecyclerItem.ItemType.TYPE_PAY_DETAIL:
                return new RetailTakeoutDetailPayDetailViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_takeout_detail_pay, parent, false)
                        , mRecyclerView, this);
            case TakeoutDetailRecyclerItem.ItemType.TYPE_FOOD_ITEM:
                return new RetailTakeoutDetailFoodItemViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_retail_takeout_detail_fooditem, parent, false)
                        , mRecyclerView, this);
            case TakeoutDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_TITLE:
                return new RetailTakeoutDetailComboTitleViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_takeout_detail_combotitle, parent, false)
                        , mRecyclerView, this);
            case TakeoutDetailRecyclerItem.ItemType.TYPE_COMBO_FOOD_ITEM:
                return new RetailTakeoutDetailComboItemViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_recyclerview_takeout_detail_comboitem, parent, false)
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
            TakeoutDetailRecyclerItem deskMessage = mDatas.get(position);
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
