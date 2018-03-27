package com.zmsoft.ccd.module.main.order.hangup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.main.order.hangup.adapter.viewholder.HangUpOrderViewHolder;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/17 14:56
 *     desc  : 挂单适配器
 * </pre>
 */
public class HangUpOrderListAdapter extends BaseListAdapter {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public HangUpOrderListAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick, R.layout.layout_empty_hang_order);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new HangUpOrderViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_hang_up_order, parent, false));
    }

}
