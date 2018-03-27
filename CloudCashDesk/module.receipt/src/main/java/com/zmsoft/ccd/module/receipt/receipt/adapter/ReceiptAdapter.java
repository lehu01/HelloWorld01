package com.zmsoft.ccd.module.receipt.receipt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseHolderEmpty;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptRecyclerItem;
import com.zmsoft.ccd.module.receipt.receipt.adapter.viewholder.ReceiptHeaderViewholder;
import com.zmsoft.ccd.module.receipt.receipt.adapter.viewholder.ReceiptMethodViewholder;

/**
 * 收款RecyclerView适配器
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptAdapter extends BaseListAdapter {
    private Context mContext;
    private AdapterClick mAdapterClick;

    public ReceiptAdapter(Context context, FooterClick footerClick, AdapterClick adapterClick) {
        super(context, footerClick);
        this.mContext = context;
        this.mAdapterClick = adapterClick;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            //收款头部（消费金额、服务费、打折等模块）
            case ReceiptRecyclerItem.ItemType.TYPE_HEAD:
                return new ReceiptHeaderViewholder(mContext
                        , inflateLayout(R.layout.module_receipt_item_recyclerview_head, parent), mAdapterClick);
            //收款底部（收款方式）
            case ReceiptRecyclerItem.ItemType.TYPE_RECEIPT_METHOD:
                return new ReceiptMethodViewholder(mContext
                        , inflateLayout(R.layout.module_receipt_item_recyclerview_method, parent));
            //收款底部（收款方式）
            case ReceiptRecyclerItem.ItemType.TYPE_RECEIPT_EMPTY:
                View emptyView = new View(mContext);
                emptyView.setBackgroundResource(R.color.white);
                return new BaseHolderEmpty(mContext
                        , emptyView);
            default:
                return getUnKnowViewHolder(parent);
        }
    }

    @Override
    protected int getMyItemViewType(int position) {
        if (position < getList().size()) {
            Object item = getList().get(position);
            if (null != item && item instanceof ReceiptRecyclerItem) {
                ReceiptRecyclerItem recyclerItem = (ReceiptRecyclerItem) item;
                return recyclerItem.getItemType();
            }
        }
        return -1;
    }
}
