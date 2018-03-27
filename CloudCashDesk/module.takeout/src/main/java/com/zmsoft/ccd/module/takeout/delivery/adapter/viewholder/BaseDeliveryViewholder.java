package com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder;

import android.content.Context;
import android.view.View;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryOrderListItem;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 消息中心
 *
 * @author DangGui
 * @create 2016/12/21.
 */

public class BaseDeliveryViewholder extends BaseHolder {
    protected Context mContext;
    protected DeliveryOrderListItem mDeliveryOrderListItem;
    protected View mItemView;

    public BaseDeliveryViewholder(Context context, View itemView) {
        super(context, itemView);
        this.mContext = context;
        this.mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (null == bean)
            return;
        if (null == source)
            return;
        if (bean instanceof DeliveryOrderListItem)
            mDeliveryOrderListItem = (DeliveryOrderListItem) bean;
    }
}
