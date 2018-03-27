package com.zmsoft.ccd.module.main.message.detail.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.MessageDetailHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailComboFoodsItemInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class MessageDetailComboItemViewholder extends MessageDetailBaseViewholder {
    @BindView(R.id.text_foodname)
    TextView mTvFoodName;
    @BindView(R.id.text_fooddesc)
    TextView mTvFoodDesc;
    @BindView(R.id.text_foodnum)
    TextView mTvFoodNum;
    @BindView(R.id.image_status)
    ImageView mImageStatus;
    private DeskMsgDetailComboFoodsItemInfoRecyclerItem mComboFoodsItemInfo;

    public MessageDetailComboItemViewholder(Context context, View itemView, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, DeskMsgDetailRecyclerItem msgDetailRecyclerItemObj, int position) {
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
        mTvFoodNum.setText(mComboFoodsItemInfo.getFoodNum());
        if (mComboFoodsItemInfo.getStatus() == MessageDetailHelper.FoodStatus.STATUS_ORDER_FAIL) {
            mImageStatus.setVisibility(View.VISIBLE);
            mImageStatus.setImageResource(R.drawable.icon_instance_rejected);
        } else {
            mImageStatus.setVisibility(View.GONE);
        }
    }

    protected void initListener(final int position) {
    }
}
