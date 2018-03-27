package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.lib.widget.ShopSpecialityView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerPriceItem;
import com.zmsoft.ccd.module.menu.helper.CartHelper;

import java.util.List;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailPriceItemViewHolder extends BaseCartDetailViewHolder {

    @BindView(R2.id.text_name)
    TextView mTextName;
    @BindView(R2.id.shop_speciality_view)
    ShopSpecialityView mShopSpecialityView;
    @BindView(R2.id.text_price)
    TextView mTextPrice;

    private CartDetailRecyclerPriceItem mCartDetailRecyclerPriceItem;

    public CartDetailPriceItemViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartDetailRecyclerItem) {
            mCartDetailRecyclerPriceItem = mCartDetailRecyclerItem.getCartDetailRecyclerPriceItem();
            if (null != mCartDetailRecyclerPriceItem) {
                if (!TextUtils.isEmpty(mCartDetailRecyclerPriceItem.getFoodName())) {
                    if (mCartDetailRecyclerItem.getDetailType() == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                            || mCartDetailRecyclerItem.getDetailType() == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                        mTextName.setVisibility(View.GONE);
                    } else {
                        mTextName.setVisibility(View.VISIBLE);
                    }
//                    mTextName.setText(mCartDetailRecyclerPriceItem.getFoodName());
                } else {
                    mTextName.setVisibility(View.GONE);
                }
                boolean isValid = (mCartDetailRecyclerPriceItem.getChili() > 0 || mCartDetailRecyclerPriceItem.getRecommendation() > 0
                        || !TextUtils.isEmpty(mCartDetailRecyclerPriceItem.isSpecialty()));
                if (isValid) {
                    mShopSpecialityView.setVisibility(View.VISIBLE);
                    mShopSpecialityView.setData(mCartDetailRecyclerPriceItem.getChili(),
                            mCartDetailRecyclerPriceItem.getRecommendation(), mCartDetailRecyclerPriceItem.isSpecialty());
                } else {
                    mShopSpecialityView.setVisibility(View.GONE);
                }
                //套餐子菜详情，不展示价格
                if (mCartDetailRecyclerItem.getDetailType() == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                        || mCartDetailRecyclerItem.getDetailType() == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                    mTextPrice.setVisibility(View.GONE);
                } else {
                    mTextPrice.setVisibility(View.VISIBLE);
                    SpannableStringUtils.Builder builder = SpannableStringUtils
                            .getBuilder(mContext, mCartDetailRecyclerPriceItem.getPrice());
                    builder.setForegroundColor(ContextCompat.getColor(mContext, R.color.accentColor));
                    if (!TextUtils.isEmpty(mCartDetailRecyclerPriceItem.getUnit())) {
                        builder.append("/").setForegroundColor(ContextCompat.getColor(mContext, R.color.primaryTextColor));
                        builder.append(mCartDetailRecyclerPriceItem.getUnit())
                                .setForegroundColor(ContextCompat.getColor(mContext, R.color.primaryTextColor));
                    } else {
                        builder.append("");
                    }
                    SpannableStringBuilder spannableStringBuilder = builder.create();
                    if (spannableStringBuilder.length() > 0) {
                        mTextPrice.setText(spannableStringBuilder);
                    } else {
                        mTextPrice.setText("");
                    }
                }
            }
        }
    }
}
