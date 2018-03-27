package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerFeedItem;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;
import com.zmsoft.ccd.module.menu.events.BaseEvents;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.ui.EditFoodNumberDialog;

import java.util.List;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailFeedItemViewHolder extends BaseCartDetailViewHolder {
    @BindView(R2.id.text_name)
    TextView mTextName;
    @BindView(R2.id.text_price)
    TextView mTextPrice;
    @BindView(R2.id.edit_food_number_view)
    FoodNumberTextView mEditFoodNumberView;
    @BindView(R2.id.text_soldout)
    TextView mTextSoldout;
    private CartDetailRecyclerFeedItem mCartDetailRecyclerFeedItem;

    public CartDetailFeedItemViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartDetailRecyclerItem) {
            mCartDetailRecyclerFeedItem = mCartDetailRecyclerItem.getCartDetailRecyclerFeedItem();
            if (null != mCartDetailRecyclerFeedItem) {
                if (!TextUtils.isEmpty(mCartDetailRecyclerFeedItem.getFeedName())) {
                    mTextName.setVisibility(View.VISIBLE);
                    if (mCartDetailRecyclerFeedItem.isSoldOut()) {
                        mTextName.setText(SpannableStringUtils.getBuilder(mContext, mCartDetailRecyclerFeedItem.getFeedName())
                                .setStrikethrough()
                                .create());
                    } else {
                        mTextName.setText(mCartDetailRecyclerFeedItem.getFeedName());
                    }
                } else {
                    mTextName.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(mCartDetailRecyclerFeedItem.getPrice())) {
                    mTextPrice.setVisibility(View.VISIBLE);
                    if (mCartDetailRecyclerFeedItem.isSoldOut()) {
                        mTextPrice.setText(SpannableStringUtils.getBuilder(mContext, mCartDetailRecyclerFeedItem.getPrice())
                                .setStrikethrough()
                                .create());
                    } else {
                        mTextPrice.setText(mCartDetailRecyclerFeedItem.getPrice());
                    }

                } else {
                    mTextPrice.setVisibility(View.GONE);
                }
                if (mCartDetailRecyclerFeedItem.isSoldOut()) {
                    mTextSoldout.setVisibility(View.VISIBLE);
                    mEditFoodNumberView.setVisibility(View.GONE);
                } else {
                    mTextSoldout.setVisibility(View.GONE);
                    mEditFoodNumberView.setVisibility(View.VISIBLE);
                    if (mCartDetailRecyclerFeedItem.getNum() >= 0) {
                        mEditFoodNumberView.setNumberText(mCartDetailRecyclerFeedItem.getNum());
                    } else {
                        mEditFoodNumberView.setNumberText(0);
                    }
                }
            }
        }
        initListener();
    }

    private void initListener() {
        mEditFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
            @Override
            public void onClick(int which, double numberValue) {
                switch (which) {
                    case FoodNumberTextView.CLICK_LEFT:
                        double num = numberValue - 1;
                        if (num < CartHelper.FoodNum.MIN_VALUE) {
                            num = CartHelper.FoodNum.MIN_VALUE;
                        }
                        mEditFoodNumberView.setNumberText(num);
                        mCartDetailRecyclerFeedItem.setNum(num);
                        EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
                        break;
                    case FoodNumberTextView.CLICK_RIGHT:
                        double addNum = numberValue + 1;
                        if (addNum > CartHelper.FoodNum.MAX_VALUE) {
                            addNum = CartHelper.FoodNum.MAX_VALUE;
                        }
                        mEditFoodNumberView.setNumberText(addNum);
                        mCartDetailRecyclerFeedItem.setNum(addNum);
                        EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
                        break;
                }
            }
        });

        mEditFoodNumberView.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
            @Override
            public void onClickEdit(double numberValue) {
                showEditValueDialog(mCartDetailRecyclerFeedItem,
                        1,
                        numberValue,
                        mCartDetailRecyclerFeedItem.getFeedName(),
                        (String) getString(R.string.module_menu_food_unit),
                        CartHelper.DialogDateFrom.OTHER_VIEW);
            }
        });
     /*   mEditFoodNumberView.setOnInputDone(new EditFoodNumberView.OnInputDone() {
            @Override
            public void onDone(double numberValue) {
                if (numberValue < CartHelper.FoodNum.MIN_VALUE) {
                    numberValue = CartHelper.FoodNum.MIN_VALUE;
                }
                if (numberValue > CartHelper.FoodNum.MAX_VALUE) {
                    numberValue = CartHelper.FoodNum.MAX_VALUE;
                }
                mEditFoodNumberView.setNumberText(numberValue);
                mCartDetailRecyclerFeedItem.setNum(numberValue);
                EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
            }
        });*/
    }

    /**
     * 弹出数量输入框
     */
    private void showEditValueDialog(final Object data, double startNum, double number, String menuName, String unit, int from) {

        EditFoodNumberDialog dialog = new EditFoodNumberDialog(getContext());
        dialog.initDialog(data, startNum, number, menuName, unit, from);
        dialog.setNegativeListener(new EditFoodNumberDialog.DialogNegativeListener() {
            @Override
            public void onClick() {

            }
        });
        dialog.setPositiveListener(new EditFoodNumberDialog.DialogPositiveListener() {
            @Override
            public void onClick(View view, Object data, double number) {
                if ((data instanceof CartDetailRecyclerFeedItem)) {
                    mEditFoodNumberView.setNumberText(number);
                    mCartDetailRecyclerFeedItem.setNum(number);
                    EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
                }
            }
        });

    }
}
