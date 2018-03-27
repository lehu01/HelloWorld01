package com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.message.takeout.adapter.RetailTakeoutMsgDetailAdapter;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailFoodsItemInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailRecyclerItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class RetailTakeoutDetailFoodItemViewholder extends RetailTakeoutDetailBaseViewholder {
    protected ArrayList<TakeoutDetailRecyclerItem> datas;
    protected RecyclerView recyclerView;
    protected RetailTakeoutMsgDetailAdapter recyclerItemAdapter;
    protected TakeoutDetailFoodsItemInfoRecyclerItem foodsItemInfo;

    //item 布局
    @BindView(R.id.text_foodname)
    TextView mTvFoodName;
    @BindView(R.id.text_food_remark)
    TextView mTvFoodRemark;
    @BindView(R.id.text_foodnum)
    TextView mTvFoodNum;
    @BindView(R.id.text_weigh_food_flag)
    TextView mTvWeighFoodFlag;
    @BindView(R.id.text_bar_code)
    TextView mTvBarCode;

    public RetailTakeoutDetailFoodItemViewholder(Context context, View itemView, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, TakeoutDetailRecyclerItem msgDetailRecyclerItemObj, int position) {
        super.initView(holder, msgDetailRecyclerItemObj, position);
        foodsItemInfo = msgDetailRecyclerItemObj.getFoodsItemInfo();
        if (null == foodsItemInfo)
            return;
        initInfoView();
        initListener(position);
    }

    protected void initInfoView() {
        if (!TextUtils.isEmpty(foodsItemInfo.getName())) {
            mTvFoodName.setVisibility(View.VISIBLE);
            mTvFoodName.setText(foodsItemInfo.getName());
        } else {
            mTvFoodName.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(foodsItemInfo.getMakeName())) {
            mTvFoodRemark.setVisibility(View.VISIBLE);
            mTvFoodRemark.setText(foodsItemInfo.getMakeName());
        } else {
            mTvFoodRemark.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(foodsItemInfo.getNum())) {
            mTvFoodNum.setVisibility(View.VISIBLE);
            mTvFoodNum.setText(foodsItemInfo.getNum());
        } else {
            mTvFoodNum.setVisibility(View.GONE);
        }
        if (foodsItemInfo.getFoodNum() > 1) {
            mTvFoodNum.setTextColor(ContextCompat.getColor(mContext, R.color.accentColor));
        } else {
            mTvFoodNum.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        }
        if (foodsItemInfo.isTwoAccount()) {
            mTvWeighFoodFlag.setVisibility(View.VISIBLE);
        } else {
            mTvWeighFoodFlag.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(foodsItemInfo.getCode())) {
            mTvBarCode.setText(foodsItemInfo.getCode());
        } else {
            mTvBarCode.setText(mContext.getString(R.string.retail_no_barcode_label));
        }
    }

    protected void initListener(final int position) {
    }
}
