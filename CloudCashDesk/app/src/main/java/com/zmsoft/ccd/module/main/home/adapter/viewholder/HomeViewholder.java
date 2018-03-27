package com.zmsoft.ccd.module.main.home.adapter.viewholder;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.home.adapter.HomeAdapter;
import com.zmsoft.ccd.module.main.home.adapter.items.HomeBizItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class HomeViewholder extends BaseRecyclerHolder<HomeBizItem> {
    @BindView(R.id.text_name)
    TextView mTextName;

    private ArrayList<HomeBizItem> mDatas;
    private RecyclerView mRecyclerView;
    private HomeAdapter mRecyclerItemAdapter;
    private View mItemView;
    private HomeBizItem mHomeBizItem;
    private int mItemType;

    public HomeViewholder(Context context, View itemView, RecyclerView recyclerView
            , RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        if (null != adapter && adapter instanceof HomeAdapter)
            mRecyclerItemAdapter = (HomeAdapter) adapter;
        this.mDatas = (ArrayList<HomeBizItem>) mRecyclerItemAdapter.getDatasList();
        this.mRecyclerView = recyclerView;
        this.mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, HomeBizItem homeBizItem
            , int position) {
        if (null == holder)
            return;
        if (null == homeBizItem)
            return;
        mHomeBizItem = homeBizItem;
        mItemType = mHomeBizItem.getItemType();
        initInfoView();
    }

    protected void initInfoView() {
        if (!TextUtils.isEmpty(mHomeBizItem.getName())) {
            if (mItemType == HomeBizItem.ItemType.ITEM_HANG_UP_ORDER && mHomeBizItem.getUnReadNum() > 0) {
                mTextName.setText(mHomeBizItem.getName() + "(" + mHomeBizItem.getUnReadNum() + ")");
            } else {
                mTextName.setText(mHomeBizItem.getName());
            }
        } else {
            mTextName.setText("");
        }
        if (mHomeBizItem.getIconRes() == -1) {
            mHomeBizItem.setIconRes(0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mTextName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, mHomeBizItem.getIconRes(), 0, 0);
        } else {
            mTextName.setCompoundDrawablesWithIntrinsicBounds(0, mHomeBizItem.getIconRes(), 0, 0);
        }
    }
}
