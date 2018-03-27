package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexItem;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexboxLayout;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerChoiceItem;
import com.zmsoft.ccd.module.menu.events.BaseEvents;

import java.util.List;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailChoiceItemViewHolder extends BaseCartDetailViewHolder {
    @BindView(R2.id.view_divider)
    View mViewDivider;
    @BindView(R2.id.text_name)
    TextView mTextName;
    @BindView(R2.id.layout_custom_flexbox)
    CustomFlexboxLayout mLayoutCustomFlexbox;

    private CartDetailRecyclerChoiceItem mCartDetailRecyclerChoiceItem;

    public CartDetailChoiceItemViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartDetailRecyclerItem) {
            mCartDetailRecyclerChoiceItem = mCartDetailRecyclerItem.getCartDetailRecyclerChoiceItem();
            if (null != mCartDetailRecyclerChoiceItem) {
                SpannableStringUtils.Builder builder = SpannableStringUtils
                        .getBuilder(mContext, "");

                if (!TextUtils.isEmpty(mCartDetailRecyclerChoiceItem.getName())) {
                    builder.append(mCartDetailRecyclerChoiceItem.getName());
                    builder.setForegroundColor(ContextCompat.getColor(mContext, R.color.primaryTextColor));
                }
                if (mCartDetailRecyclerChoiceItem.isMustSelect()) {
                    builder.append(mContext.getString(R.string.module_menu_cartdetail_mustselect));
                    builder.setForegroundColor(ContextCompat.getColor(mContext, R.color.accentColor));
                }
                SpannableStringBuilder spannableStringBuilder = builder.create();
                if (spannableStringBuilder.length() > 0) {
                    mTextName.setVisibility(View.VISIBLE);
                    mViewDivider.setVisibility(View.VISIBLE);
                    mTextName.setText(spannableStringBuilder);
                } else {
                    mTextName.setVisibility(View.GONE);
                    mViewDivider.setVisibility(View.GONE);
                }
                List<CustomFlexItem> customFlexItemList = mCartDetailRecyclerChoiceItem.getCustomFlexItemList();
                if (null != customFlexItemList && !customFlexItemList.isEmpty()) {
                    mLayoutCustomFlexbox.setVisibility(View.VISIBLE);
                    boolean isSingleChoice = (mCartDetailRecyclerChoiceItem.getKey()
                            .equals(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_SPEC)
                            || mCartDetailRecyclerChoiceItem.getKey()
                            .equals(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_MAKE));
                    mLayoutCustomFlexbox.initSource(customFlexItemList, isSingleChoice);
                } else {
                    mLayoutCustomFlexbox.setVisibility(View.GONE);
                }
            }
        }
        initListener();
    }

    private void initListener() {
        mLayoutCustomFlexbox.setOnItemChangeListener(new CustomFlexboxLayout.OnItemChangeListener() {
            @Override
            public void onItemChanged(List<CustomFlexItem> checkedFlexItemList) {
                mCartDetailRecyclerChoiceItem.setCheckedFlexItemList(checkedFlexItemList);
                EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
            }
        });
    }
}
