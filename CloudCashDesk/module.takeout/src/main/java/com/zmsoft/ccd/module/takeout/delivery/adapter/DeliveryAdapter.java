package com.zmsoft.ccd.module.takeout.delivery.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryOrderListItem;
import com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder.BaseDeliveryViewholder;
import com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder.DeliveryJagViewholder;
import com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder.DeliveryOrderViewholder;
import com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder.DeliveryPendingViewholder;
import com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder.DeliverySelectAllViewholder;

/**
 * @author DangGui
 * @create 2017/8/18.
 */
public class DeliveryAdapter extends BaseListAdapter {
    private final Context mContext;
    private AdapterClick mAdapterClick;

    public DeliveryAdapter(Context context, FooterClick footerClick, AdapterClick adapterClick) {
        super(context, footerClick);
        mContext = context;
        this.mAdapterClick = adapterClick;
    }

    @Override
    protected int getMyItemViewType(int position) {
        if (position < getList().size()) {
            Object item = getList().get(position);
            if (null != item && item instanceof DeliveryOrderListItem) {
                DeliveryOrderListItem orderListItem = (DeliveryOrderListItem) item;
                return orderListItem.getItemType();
            }
        }
        return -1;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemType.ITEM_TYPE_ORDER:
                return new DeliveryOrderViewholder(mContext, inflateLayout(R.layout.module_takeout_item_delivery_order, parent)
                        , mAdapterClick);
            case ItemType.ITEM_TYPE_PENDING_DELEVIRY:
                return new DeliveryPendingViewholder(mContext, inflateLayout(R.layout.module_takeout_item_delivery_pending_order, parent));
            case ItemType.ITEM_TYPE_SELECT_ALL_ORDERS:
                return new DeliverySelectAllViewholder(mContext, inflateLayout(R.layout.module_takeout_item_delivery_select_all_order, parent));
            case ItemType.ITEM_TYPE_JAG_SLIDE:
                return new DeliveryJagViewholder(mContext, inflateLayout(R.layout.item_divider_jag, parent));
            case ItemType.ITEM_TYPE_JAG_WHOLE:
                return new BaseDeliveryViewholder(mContext, inflateLayout(R.layout.item_divider_jag_whole, parent));
            default:
                return getUnKnowViewHolder(parent);
        }
    }

    public class ItemType {
        /**
         * 头部订单信息
         */
        public static final int ITEM_TYPE_ORDER = 1;
        /**
         * 其他待配送订单ITEM
         */
        public static final int ITEM_TYPE_PENDING_DELEVIRY = 2;
        /**
         * 全部选中其他待配送订单
         */
        public static final int ITEM_TYPE_SELECT_ALL_ORDERS = 3;
        /**
         * 锯齿分割线(单边，上/下)
         */
        public static final int ITEM_TYPE_JAG_SLIDE = 4;
        /**
         * 锯齿分割线（整个）
         */
        public static final int ITEM_TYPE_JAG_WHOLE = 5;

    }
}
