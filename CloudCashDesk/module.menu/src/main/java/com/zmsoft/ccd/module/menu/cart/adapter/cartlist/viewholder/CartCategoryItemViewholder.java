package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartCategoryRecyclerItem;
import com.zmsoft.ccd.module.menu.helper.CartHelper;

import java.util.List;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class CartCategoryItemViewholder extends CartBaseViewholder {
    @BindView(R2.id.text_category)
    TextView mTextCategory;
    @BindView(R2.id.text_remark)
    TextView mTextRemark;
    private CartCategoryRecyclerItem mCartCategoryRecyclerItem;

    public CartCategoryItemViewholder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartRecyclerItem) {
            mCartCategoryRecyclerItem = mCartRecyclerItem.getCartCategoryRecyclerItem();
            if (null != mCartCategoryRecyclerItem) {
                if (!TextUtils.isEmpty(mCartCategoryRecyclerItem.getCategoryName())) {
                    mTextCategory.setVisibility(View.VISIBLE);
                    mTextCategory.setText(mCartCategoryRecyclerItem.getCategoryName());
                } else {
                    mTextCategory.setVisibility(View.GONE);
                }
                if (mCartCategoryRecyclerItem.getFoodType() == CartHelper.CartFoodType.TYPE_MUST_SELECT) {
                    mTextRemark.setVisibility(View.VISIBLE);
                } else {
                    mTextRemark.setVisibility(View.GONE);
                }
            }
        }
    }
}