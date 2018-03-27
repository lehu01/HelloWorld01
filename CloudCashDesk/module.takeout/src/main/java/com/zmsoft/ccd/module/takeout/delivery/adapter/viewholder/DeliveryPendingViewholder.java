package com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryOrderPendingItem;

import java.util.List;

import butterknife.BindView;

/**
 * 配送订单信息
 *
 * @author DangGui
 * @create 2016/12/21.
 */

public class DeliveryPendingViewholder extends BaseDeliveryViewholder {

    @BindView(R2.id.checkbox_scope)
    CheckBox mCheckboxScope;
    @BindView(R2.id.text_takeout_order_taker)
    TextView mTextTakeoutOrderTaker;
    @BindView(R2.id.text_takeout_order_code)
    TextView mTextTakeoutOrderCode;
    @BindView(R2.id.text_takeout_order_foods)
    TextView mTextTakeoutOrderFoods;
    @BindView(R2.id.text_takeout_delivery_order_foods_total_num)
    TextView mTextTakeoutDeliveryOrderFoodsTotalNum;

    private DeliveryOrderPendingItem mPendingItem;

    public DeliveryPendingViewholder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        mPendingItem = mDeliveryOrderListItem.getPendingItem();
        if (null != mPendingItem) {
            if (!TextUtils.isEmpty(mPendingItem.getUserName()) || !TextUtils.isEmpty(mPendingItem.getUserPhone())) {
                StringBuilder stringBuilder = new StringBuilder();
                if (!TextUtils.isEmpty(mPendingItem.getUserName())) {
                    stringBuilder.append(mPendingItem.getUserName());
                }
                if (!TextUtils.isEmpty(mPendingItem.getUserPhone())) {
                    stringBuilder.append(" (");
                    stringBuilder.append(mPendingItem.getUserPhone());
                    stringBuilder.append(")");
                }
                mTextTakeoutOrderTaker.setText(String.format(mContext.getResources().getString(R.string.module_takeout_delivery_receiver_name_placeholder)
                        , stringBuilder.toString()));
            } else {
                mTextTakeoutOrderTaker.setText(String.format(mContext.getResources().getString(R.string.module_takeout_delivery_receiver_name_placeholder)
                        , "-"));
            }
            if (!TextUtils.isEmpty(mPendingItem.getOrderCode())) {
                mTextTakeoutOrderCode.setVisibility(View.VISIBLE);
                mTextTakeoutOrderCode.setText(String.format(mContext.getResources().getString(R.string.order_no)
                        , mPendingItem.getOrderCode()));
            } else {
                mTextTakeoutOrderCode.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mPendingItem.getAddress())) {
                mTextTakeoutOrderFoods.setVisibility(View.VISIBLE);
                mTextTakeoutOrderFoods.setText(mPendingItem.getAddress());
            } else {
                mTextTakeoutOrderFoods.setVisibility(View.GONE);
            }
            mCheckboxScope.setChecked(mPendingItem.isChecked());
            initListener();
        }
    }

    private void initListener() {
        mCheckboxScope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemView.performClick();
            }
        });
    }
}
