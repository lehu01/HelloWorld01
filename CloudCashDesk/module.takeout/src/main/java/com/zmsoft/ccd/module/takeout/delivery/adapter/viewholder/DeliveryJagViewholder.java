package com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryJagItem;

import java.util.List;

import butterknife.BindView;

/**
 * 配送订单信息
 *
 * @author DangGui
 * @create 2016/12/21.
 */

public class DeliveryJagViewholder extends BaseDeliveryViewholder {

    @BindView(R2.id.view_jag)
    View mViewJag;
    private DeliveryJagItem mDeliveryJagItem;

    public DeliveryJagViewholder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        mDeliveryJagItem = mDeliveryOrderListItem.getDeliveryJagItem();
        if (null != mDeliveryJagItem) {
            mViewJag.setBackgroundResource(mDeliveryJagItem.isUp() ? R.drawable.shape_item_up_jag : R.drawable.shape_item_bottom_jag);
            if (mDeliveryJagItem.isUp()) {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) mViewJag.getLayoutParams();
                lp.topMargin = 1;
            } else {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) mViewJag.getLayoutParams();
                lp.topMargin = 0;
            }
        }
    }
}
