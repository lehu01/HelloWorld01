package com.zmsoft.ccd.module.main.message.detail.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailComboFoodsTitleRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class MessageDetailComboTitleViewholder extends MessageDetailBaseViewholder {
    @BindView(R.id.text_combo_name)
    TextView mTvComboName;
    @BindView(R.id.text_price)
    TextView mTvPrice;
    @BindView(R.id.text_foodnum)
    TextView mTvFoodNum;
    private DeskMsgDetailComboFoodsTitleRecyclerItem mComboFoodsTitle;

    public MessageDetailComboTitleViewholder(Context context, View itemView, RecyclerView recyclerView
            , RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, DeskMsgDetailRecyclerItem msgDetailRecyclerItemObj
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
        if (!TextUtils.isEmpty(mComboFoodsTitle.getFoodPrice())) {
            mTvPrice.setText(mComboFoodsTitle.getFoodPrice());
        } else {
            mTvPrice.setText("");
        }
        if (!TextUtils.isEmpty(mComboFoodsTitle.getFoodNum())) {
            mTvFoodNum.setText(mComboFoodsTitle.getFoodNum());
        } else {
            mTvFoodNum.setText("");
        }
    }

    protected void initListener(final int position) {
    }
}
