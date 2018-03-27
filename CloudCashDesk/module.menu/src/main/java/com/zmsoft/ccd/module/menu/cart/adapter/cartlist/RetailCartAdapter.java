package com.zmsoft.ccd.module.menu.cart.adapter.cartlist;

import android.content.Context;
import android.view.ViewGroup;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.RetailCartRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder.RetailCartCategoryItemViewholder;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder.RetailCartComboFoodItemViewholder;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder.RetailCartNormalFoodItemViewholder;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder.RetailCartOrderItemViewholder;

/**
 * 购物车RecyclerView适配器
 *
 * @author DangGui
 * @create 2017/4/10.
 */

public class RetailCartAdapter extends BaseListAdapter {
    private Context mContext;

    public RetailCartAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        this.mContext = context;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            //订单信息
            case RetailCartRecyclerItem.ItemType.TYPE_ORDER_INFO:
                return new RetailCartOrderItemViewholder(mContext
                        , inflateLayout(R.layout.module_menu_item_retail_recyclerview_cart_order, parent));
            //类目信息
            case RetailCartRecyclerItem.ItemType.TYPE_CATEGORY:
                return new RetailCartCategoryItemViewholder(mContext
                        , inflateLayout(R.layout.module_menu_item_recyclerview_cart_category, parent));
            //普通菜
            case RetailCartRecyclerItem.ItemType.TYPE_NORMAL_FOOD:
                return new RetailCartNormalFoodItemViewholder(mContext
                        , inflateLayout(R.layout.module_menu_item_retail_recyclerview_cart_normal_food, parent));
            //套餐菜
            case RetailCartRecyclerItem.ItemType.TYPE_COMBO_FOOD:
                return new RetailCartComboFoodItemViewholder(mContext
                        , inflateLayout(R.layout.module_menu_item_recyclerview_cart_combo_food, parent));
            default:
                return getUnKnowViewHolder(parent);
        }
    }

    @Override
    protected int getMyItemViewType(int position) {
        if (position < getList().size()) {
            Object item = getList().get(position);
            if (null != item && item instanceof RetailCartRecyclerItem) {
                RetailCartRecyclerItem cartRecyclerItem = (RetailCartRecyclerItem) item;
                return cartRecyclerItem.getItemType();
            }
        }
        return -1;
    }
}
