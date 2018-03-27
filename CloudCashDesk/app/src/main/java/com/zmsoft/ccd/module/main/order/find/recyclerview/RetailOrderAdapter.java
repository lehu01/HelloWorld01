package com.zmsoft.ccd.module.main.order.find.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.main.order.find.viewmodel.RetailOrderViewHolder;

/**
 * Created by huaixi on 2017/10/26.
 */

public class RetailOrderAdapter extends BaseListAdapter {

    private final Context mContext;
    private AdapterClick mAdapterClick;

    public RetailOrderAdapter(Context context, FooterClick footerClick, AdapterClick adapterClick, int emptyView) {
        super(context, footerClick, emptyView);
        mContext = context;
        this.mAdapterClick = adapterClick;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new RetailOrderViewHolder(mContext, inflateLayout(R.layout.item_retail_list_order, parent), mAdapterClick);
    }
}
