package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder;

import android.content.Context;
import android.view.View;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerItem;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class BaseCartDetailViewHolder extends BaseHolder {
    protected Context mContext;
    protected View mItemView;
    protected CartDetailRecyclerItem mCartDetailRecyclerItem;

    public BaseCartDetailViewHolder(Context context, View itemView) {
        super(context, itemView);
        this.mContext = context;
        this.mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (null != bean && bean instanceof CartDetailRecyclerItem) {
            mCartDetailRecyclerItem = (CartDetailRecyclerItem) bean;
        }
    }
}
