package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail;

import android.content.Context;
import android.view.ViewGroup;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder.CartDetailCategoryItemViewHolder;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder.CartDetailChoiceItemViewHolder;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder.CartDetailFeedItemViewHolder;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder.CartDetailPriceItemViewHolder;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder.CartDetailRemarkItemViewHolder;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder.CartDetailWeightItemViewHolder;

/**
 * 购物车详情RecyclerView适配器
 *
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailAdapter extends BaseListAdapter {
    private Context mContext;

    public CartDetailAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        this.mContext = context;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            //单价ITEM
            case CartDetailRecyclerItem.ItemType.TYPE_PRICE:
                return new CartDetailPriceItemViewHolder(mContext
                        , inflateLayout(R.layout.module_menu_item_recyclerview_cartdetail_price, parent));
            //重量ITEM
            case CartDetailRecyclerItem.ItemType.TYPE_WEIGHT:
                return new CartDetailWeightItemViewHolder(mContext
                        , inflateLayout(R.layout.module_menu_item_recyclerview_cartdetail_weight, parent));
            //选择ITEM
            case CartDetailRecyclerItem.ItemType.TYPE_CHOICE:
                return new CartDetailChoiceItemViewHolder(mContext
                        , inflateLayout(R.layout.module_menu_item_recyclerview_cartdetail_choice, parent));
            //分类ITEM
            case CartDetailRecyclerItem.ItemType.TYPE_CATEGORY:
                return new CartDetailCategoryItemViewHolder(mContext
                        , inflateLayout(R.layout.module_menu_item_recyclerview_cartdetail_category, parent));
            //加料菜子菜ITEM
            case CartDetailRecyclerItem.ItemType.TYPE_FEED:
                return new CartDetailFeedItemViewHolder(mContext
                        , inflateLayout(R.layout.module_menu_item_recyclerview_cartdetail_feeditem, parent));
            //备注ITEM
            case CartDetailRecyclerItem.ItemType.TYPE_REMARK:
                return new CartDetailRemarkItemViewHolder(mContext
                        , inflateLayout(R.layout.module_menu_item_recyclerview_cartdetail_remark, parent));
            default:
                return getUnKnowViewHolder(parent);
        }
    }

    @Override
    protected int getMyItemViewType(int position) {
        if (position < getList().size()) {
            Object item = getList().get(position);
            if (null != item && item instanceof CartDetailRecyclerItem) {
                CartDetailRecyclerItem cartDetailRecyclerItem = (CartDetailRecyclerItem) item;
                return cartDetailRecyclerItem.getItemType();
            }
        }
        return -1;
    }
}
