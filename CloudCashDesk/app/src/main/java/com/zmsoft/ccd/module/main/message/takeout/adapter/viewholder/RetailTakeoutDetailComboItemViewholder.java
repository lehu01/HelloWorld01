package com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailComboFoodsItemInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailRecyclerItem;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class RetailTakeoutDetailComboItemViewholder extends RetailTakeoutDetailBaseViewholder {
    @BindView(R.id.text_foodname)
    TextView mTvFoodName;
    @BindView(R.id.text_fooddesc)
    TextView mTvFoodDesc;
    @BindView(R.id.text_foodnum)
    TextView mTvFoodNum;
    private TakeoutDetailComboFoodsItemInfoRecyclerItem mComboFoodsItemInfo;

    public RetailTakeoutDetailComboItemViewholder(Context context, View itemView, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, TakeoutDetailRecyclerItem msgDetailRecyclerItemObj, int position) {
        super.initView(holder, msgDetailRecyclerItemObj, position);
        mComboFoodsItemInfo = msgDetailRecyclerItemObj.getComboFoodsItemInfo();
        if (null == mComboFoodsItemInfo)
            return;
        initInfoView();
        initListener(position);
    }

    protected void initInfoView() {
        if (!TextUtils.isEmpty(mComboFoodsItemInfo.getFoodName())) {
            mTvFoodName.setText(mComboFoodsItemInfo.getFoodName());
        } else {
            mTvFoodName.setText("");
        }
        if (!TextUtils.isEmpty(mComboFoodsItemInfo.getFoodRemark())) {
            mTvFoodDesc.setVisibility(View.VISIBLE);
            mTvFoodDesc.setText(mComboFoodsItemInfo.getFoodRemark());
        } else {
            mTvFoodDesc.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mComboFoodsItemInfo.getFoodNum())) {
            mTvFoodNum.setText(mComboFoodsItemInfo.getFoodNum());
        } else {
            mTvFoodNum.setText("");
        }
        if (mComboFoodsItemInfo.getNum() > 1) {
            mTvFoodNum.setTextColor(ContextCompat.getColor(mContext, R.color.accentColor));
        } else {
            mTvFoodNum.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        }
    }

    protected void initListener(final int position) {
    }
}
