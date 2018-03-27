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
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.module.main.message.detail.adapter.MessageDetailAdapter;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailFoodsItemInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class MessageDetailFoodItemViewholder extends MessageDetailBaseViewholder {
    protected ArrayList<DeskMsgDetailRecyclerItem> datas;
    protected RecyclerView recyclerView;
    protected MessageDetailAdapter recyclerItemAdapter;
    protected DeskMsgDetailFoodsItemInfoRecyclerItem foodsItemInfo;

    //item 布局
    @BindView(R.id.text_foodname)
    TextView mTvFoodName;
    @BindView(R.id.text_food_remark)
    TextView mTvFoodRemark;
    @BindView(R.id.text_price)
    TextView mTvPrice;
    @BindView(R.id.text_foodnum)
    TextView mTvFoodNum;
    @BindView(R.id.image_status)
    ImageView mImageStatus;

    public MessageDetailFoodItemViewholder(Context context, View itemView, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, DeskMsgDetailRecyclerItem msgDetailRecyclerItemObj, int position) {
        super.initView(holder, msgDetailRecyclerItemObj, position);
        foodsItemInfo = msgDetailRecyclerItemObj.getFoodsItemInfo();
        if (null == foodsItemInfo)
            return;
        initInfoView();
        initListener(position);
    }

    protected void initInfoView() {
        String foodName = "";
        String foodRemark = "";
        String foodPrice = "";
        if (!TextUtils.isEmpty(foodsItemInfo.getName())) {
            foodName = foodsItemInfo.getName();
        }
        if (!TextUtils.isEmpty(foodsItemInfo.getMakeName())) {
            foodRemark = foodsItemInfo.getMakeName();
        }
        if (!TextUtils.isEmpty(foodsItemInfo.getPrice())) {
            foodPrice = foodsItemInfo.getPrice();
        }
        //已同意的消息，如果某个菜被沽清（被拒绝），要显示“已拒绝”状态
        if (foodsItemInfo.getOrderStatus() == MessageDetailHelper.OrderState.STATE_AGREED
                && foodsItemInfo.getStatus() == MessageDetailHelper.FoodStatus.STATUS_ORDER_FAIL) {
            mImageStatus.setVisibility(View.VISIBLE);
            mImageStatus.setImageResource(R.drawable.icon_instance_rejected);
            mTvFoodName.setText(SpannableStringUtils.getBuilder(context, foodName)
                    .setStrikethrough()
                    .create());
            mTvFoodRemark.setText(SpannableStringUtils.getBuilder(context, foodRemark)
                    .setStrikethrough()
                    .create());
            mTvPrice.setText(SpannableStringUtils.getBuilder(context, foodPrice)
                    .setStrikethrough()
                    .create());
        } else {
            mImageStatus.setVisibility(View.GONE);
            mTvFoodName.setText(foodName);
            mTvFoodRemark.setText(foodRemark);
            mTvPrice.setText(foodPrice);
        }
        mTvFoodNum.setText(foodsItemInfo.getNum());
    }

    protected void initListener(final int position) {
    }
}
