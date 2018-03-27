package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder;

import android.content.Context;
import android.view.View;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.utils.dialogutil.DialogUtil;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartRecyclerItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class CartBaseViewholder extends BaseHolder {
    @BindView(R2.id.layout_jag_up)
    View mJagUp;
    @BindView(R2.id.layout_jag_bottom)
    View mJagBottom;

    protected Context mContext;
    protected CartRecyclerItem mCartRecyclerItem;
    protected View mItemView;
    protected DialogUtil mDialogUtil;

    public CartBaseViewholder(Context context, View itemView) {
        super(context, itemView);
        this.mContext = context;
        this.mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (null != bean && bean instanceof CartRecyclerItem) {
            mCartRecyclerItem = (CartRecyclerItem) bean;
            initViewSemicircle(getAdapterPosition(), source);
        }
    }

    private void initViewSemicircle(int position, List source) {
        if (null == source || source.isEmpty() || position < 0 || position > source.size() - 1) {
            return;
        }
        if (source.size() == 1) {
            setSemicircle(true, true);
        } else if (position == source.size() - 1) {
            setSemicircle(false, true);
        } else {
            int itemType = mCartRecyclerItem.getItemType();
            switch (itemType) {
                //订单信息
                case CartRecyclerItem.ItemType.TYPE_ORDER_INFO:
                    setSemicircle(true, true);
                    break;
                //类目信息
                case CartRecyclerItem.ItemType.TYPE_CATEGORY:
                    setSemicircle(true, false);
                    break;
                //普通菜
                case CartRecyclerItem.ItemType.TYPE_NORMAL_FOOD:
                    //套餐菜
                case CartRecyclerItem.ItemType.TYPE_COMBO_FOOD:
                    Object object = source.get(position + 1);
                    if (null != object && object instanceof CartRecyclerItem) {
                        if (((CartRecyclerItem) object).getItemType() != CartRecyclerItem.ItemType.TYPE_NORMAL_FOOD
                                && ((CartRecyclerItem) object).getItemType() != CartRecyclerItem.ItemType.TYPE_COMBO_FOOD) {
                            setSemicircle(false, true);
                        } else {
                            setSemicircle(false, false);
                        }
                    }
                    break;
                default:
                    setSemicircle(false, false);
                    break;
            }
        }
    }

    private void setSemicircle(boolean top, boolean bottom) {
        mJagUp.setVisibility(top ? View.VISIBLE : View.GONE);
        mJagBottom.setVisibility(bottom ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取DialogUtil类
     *
     * @return
     */
    protected DialogUtil getDialogUtil() {
        if (null == mDialogUtil) {
            mDialogUtil = new DialogUtil(mContext);
        }
        return mDialogUtil;
    }
}