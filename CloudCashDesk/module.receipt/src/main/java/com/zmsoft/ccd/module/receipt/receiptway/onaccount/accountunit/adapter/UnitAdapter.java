package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.items.UnitRecyclerItem;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.viewholder.UnitViewholder;

/**
 * 选择挂账单位（人）RecyclerView适配器
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class UnitAdapter extends BaseListAdapter {
    private Context mContext;

    public UnitAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        this.mContext = context;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case UnitRecyclerItem.ITEM_TYPE:
                return new UnitViewholder(mContext
                        , inflateLayout(R.layout.module_receipt_normal_receipt_sign_staff_subitem, parent));
            default:
                return getUnKnowViewHolder(parent);
        }
    }

    @Override
    protected int getMyItemViewType(int position) {
        if (position < getList().size()) {
            Object item = getList().get(position);
            if (null != item && item instanceof UnitRecyclerItem) {
                UnitRecyclerItem recyclerItem = (UnitRecyclerItem) item;
                return recyclerItem.getItemType();
            }
        }
        return -1;
    }
}
