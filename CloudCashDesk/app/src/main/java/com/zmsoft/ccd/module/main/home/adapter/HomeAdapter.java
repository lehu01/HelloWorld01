package com.zmsoft.ccd.module.main.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerAdapter;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.home.adapter.items.HomeBizItem;
import com.zmsoft.ccd.module.main.home.adapter.viewholder.HomeViewholder;

import java.util.ArrayList;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class HomeAdapter extends BaseRecyclerAdapter<HomeBizItem> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<HomeBizItem> mDatas;
    private RecyclerView mRecyclerView;

    public HomeAdapter(Context context, RecyclerView view, ArrayList<HomeBizItem> datas) {
        super(view, datas, 0);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mRecyclerView = view;
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomeBizItem.ItemType.ITEM_ACCOUNTED:
            case HomeBizItem.ItemType.ITEM_TAKE_OUT:
            case HomeBizItem.ItemType.ITEM_HANG_UP_ORDER:
            case HomeBizItem.ItemType.ITEM_ELECTRONIC_CASH_DETAIL:
            case HomeBizItem.ItemType.ITEM_FOODS_BALANCE:
            case HomeBizItem.ItemType.ITEM_SHOPKEEPER:
                return new HomeViewholder(mContext
                        , mLayoutInflater.inflate(R.layout.item_home_recyclerview, parent, false)
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
            HomeBizItem homeBizItem = mDatas.get(position);
            if (null == homeBizItem)
                return -1;
            return homeBizItem.getItemType();
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
