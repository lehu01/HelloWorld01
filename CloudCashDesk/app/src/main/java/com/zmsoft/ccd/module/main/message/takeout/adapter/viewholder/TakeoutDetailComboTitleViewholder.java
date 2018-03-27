package com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailComboFoodsTitleRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailRecyclerItem;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class TakeoutDetailComboTitleViewholder extends TakeoutDetailBaseViewholder {
    @BindView(R.id.text_combo_name)
    TextView mTvComboName;
    @BindView(R.id.text_foodnum)
    TextView mTvFoodNum;
    private TakeoutDetailComboFoodsTitleRecyclerItem mComboFoodsTitle;

    public TakeoutDetailComboTitleViewholder(Context context, View itemView, RecyclerView recyclerView
            , RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, TakeoutDetailRecyclerItem msgDetailRecyclerItemObj
            , int position) {
        super.initView(holder, msgDetailRecyclerItemObj, position);
        mComboFoodsTitle = msgDetailRecyclerItemObj.getComboFoodsTitle();
        if (null == mComboFoodsTitle)
            return;
        initInfoView();
        initListener(position);
    }

    protected void initInfoView() {
        if (!TextUtils.isEmpty(mComboFoodsTitle.getFoodName())) {
            mTvComboName.setText(mComboFoodsTitle.getFoodName());
        } else {
            mTvComboName.setText("");
        }
        if (!TextUtils.isEmpty(mComboFoodsTitle.getFoodNum())) {
            mTvFoodNum.setText(mComboFoodsTitle.getFoodNum());
        } else {
            mTvFoodNum.setText("");
        }
        if (mComboFoodsTitle.getNum() > 1) {
            mTvFoodNum.setTextColor(ContextCompat.getColor(mContext, R.color.accentColor));
        } else {
            mTvFoodNum.setTextColor(ContextCompat.getColor(mContext, R.color.secondaryTextColor));
        }
    }

    protected void initListener(final int position) {
    }
}
