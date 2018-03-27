package com.zmsoft.ccd.module.main.order.bill.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.bean.order.BillDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huaixi on 2017/11/01.
 */

public class RetailBillDetailAdapter extends BaseListAdapter {

    private static final int LAYOUT_TYPE_DATE = 1;
    private static final int LAYOUT_TYPE_BILL_DETAIL = 2;

    private Context mContext;
    private AdapterClick mAdapterClick;

    public RetailBillDetailAdapter(Context context, FooterClick footerClick, AdapterClick adapterClick, int emptyView) {
        super(context, footerClick, emptyView);
        mContext = context;
        this.mAdapterClick = adapterClick;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LAYOUT_TYPE_DATE:
                return new RetailDateViewHolder(getContext(), inflateLayout(R.layout.item_retail_order_date, parent), mAdapterClick);
            case LAYOUT_TYPE_BILL_DETAIL:
                return new RetailBillDetailViewHolder(getContext(), inflateLayout(R.layout.item_retail_completed_list_order, parent), mAdapterClick);
        }
        return new RetailDateViewHolder(getContext(), inflateLayout(R.layout.item_retail_order_date, parent), mAdapterClick);
    }

    @Override
    protected int getMyItemViewType(int position) {
        Object obj  = getModel(position);
        if (obj instanceof String) return LAYOUT_TYPE_DATE;
        else if(obj instanceof BillDetail) return LAYOUT_TYPE_BILL_DETAIL;
        return -1;
    }

    static class RetailDateViewHolder extends BaseHolder {
        @BindView(R.id.tv_date)
        TextView mTextDate;

        public RetailDateViewHolder(Context context, View itemView, BaseListAdapter.AdapterClick adapterClick) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {
            mTextDate.setText((String)bean);
        }
    }
}
