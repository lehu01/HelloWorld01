package com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliverySelectAllItem;

import java.util.List;

import butterknife.BindView;

/**
 * 配送订单信息
 *
 * @author DangGui
 * @create 2016/12/21.
 */

public class DeliverySelectAllViewholder extends BaseDeliveryViewholder {

    @BindView(R2.id.checkbox_scope)
    CheckBox mCheckboxScope;
    @BindView(R2.id.text_title)
    TextView mTextTitle;
    private DeliverySelectAllItem mSelectAllItem;

    public DeliverySelectAllViewholder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        mSelectAllItem = mDeliveryOrderListItem.getSelectAllItem();
        if (null != mSelectAllItem) {
            mCheckboxScope.setChecked(mSelectAllItem.isCheckAll());
            mTextTitle.setText(String.format(mContext.getResources().getString(R.string.module_takeout_delivery_pending_order)
                    , mSelectAllItem.getTotalNum()));
        }
        initListener();
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
