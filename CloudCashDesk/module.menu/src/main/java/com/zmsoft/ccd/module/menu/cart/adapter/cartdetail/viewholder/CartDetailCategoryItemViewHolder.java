package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerCategoryItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerItem;

import java.util.List;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailCategoryItemViewHolder extends BaseCartDetailViewHolder {
    @BindView(R2.id.view_divider)
    View mViewDivider;
    @BindView(R2.id.view_dash_divider)
    View mViewDashDivider;
    @BindView(R2.id.text_header_name)
    TextView mTextHeaderName;
    @BindView(R2.id.text_category_name)
    TextView mTextCategoryName;
    private CartDetailRecyclerCategoryItem mCartDetailRecyclerCategoryItem;

    public CartDetailCategoryItemViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartDetailRecyclerItem) {
            mCartDetailRecyclerCategoryItem = mCartDetailRecyclerItem.getCartDetailRecyclerCategoryItem();
            if (null != mCartDetailRecyclerCategoryItem) {
                if (!TextUtils.isEmpty(mCartDetailRecyclerCategoryItem.getCategoryHeaderName())) {
                    mTextHeaderName.setVisibility(View.VISIBLE);
                    mViewDivider.setVisibility(View.VISIBLE);
                    mViewDashDivider.setVisibility(View.GONE);
                    mTextHeaderName.setText(mCartDetailRecyclerCategoryItem.getCategoryHeaderName());
                } else {
                    mTextHeaderName.setVisibility(View.GONE);
                    mViewDivider.setVisibility(View.GONE);
                    //如果分类模块属于“备注”内的，不展示分割线
                    if (mCartDetailRecyclerCategoryItem.getCategoryType() == CartDetailRecyclerItem.ItemType.TYPE_REMARK) {
                        mViewDashDivider.setVisibility(View.GONE);
                    } else {
                        mViewDashDivider.setVisibility(View.VISIBLE);
                    }
                }
                if (!TextUtils.isEmpty(mCartDetailRecyclerCategoryItem.getCategoryName())) {
                    mTextCategoryName.setText(mCartDetailRecyclerCategoryItem.getCategoryName());
                } else {
                    mTextCategoryName.setText("");
                }


            }
        }
    }
}
