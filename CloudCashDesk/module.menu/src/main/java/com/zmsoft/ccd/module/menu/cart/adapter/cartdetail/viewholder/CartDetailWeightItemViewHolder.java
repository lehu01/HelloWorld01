package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.KeyboardUtils;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerWeightItem;
import com.zmsoft.ccd.module.menu.events.BaseEvents;
import com.zmsoft.ccd.module.menu.helper.CartHelper;

import java.util.List;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailWeightItemViewHolder extends BaseCartDetailViewHolder {
    @BindView(R2.id.text_name)
    TextView mTextName;
    @BindView(R2.id.edit_weight)
    EditFoodNumberView mEditWeight;
    private CartDetailRecyclerWeightItem mCartDetailRecyclerWeightItem;

    public CartDetailWeightItemViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        CustomViewUtil.initEditViewFocousAll(mEditWeight.getEditText());
        if (null != mCartDetailRecyclerItem) {
            mCartDetailRecyclerWeightItem = mCartDetailRecyclerItem.getCartDetailRecyclerWeightItem();
            if (null != mCartDetailRecyclerWeightItem) {
//                if (!TextUtils.isEmpty(mCartDetailRecyclerWeightItem.getWeightName())) {
//                    mTextName.setVisibility(View.VISIBLE);
//                    mTextName.setText(mCartDetailRecyclerWeightItem.getWeightName());
//                } else {
//                    mTextName.setVisibility(View.GONE);
//                }
                String accountNum;
                if (mCartDetailRecyclerWeightItem.getAccountNum() > 0) {
                    accountNum = FeeHelper.getDecimalFee(mCartDetailRecyclerWeightItem.getAccountNum());
                    mEditWeight.setNumberText(Double.parseDouble(accountNum));
                } else {
                    mEditWeight.setNumberText(CartHelper.CART_DETAIL_DOUBLE_UNIT_ACCOUNT_NUM);
                }

                if (!TextUtils.isEmpty(mCartDetailRecyclerWeightItem.getAccountUnit())) {
                    mEditWeight.setUnitText(mCartDetailRecyclerWeightItem.getAccountUnit());
                } else {
                    mEditWeight.setUnitText("");
                }
            }
        }
        initListener();
    }

    private void initListener() {
        mEditWeight.setOnEditTextChangeListener(new EditFoodNumberView.OnEditTextChangeListener() {
            @Override
            public void OnEditTextChange(double numberValue) {
                handleEditTextChange(numberValue);
                EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
            }
        });
        mEditWeight.setOnInputDone(new EditFoodNumberView.OnInputDone() {
            @Override
            public void onDone(double numberValue) {
                handleEditTextChange(numberValue);
            }
        });
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditWeight.getEditText().requestFocus();
                mEditWeight.getEditText().setSelection(mEditWeight.getEditText().length());
                KeyboardUtils.showSoftInput(mContext, mEditWeight.getEditText());
            }
        });
    }

    private void handleEditTextChange(double numberValue) {
        //双单位菜，num >0 的情况下，accountNum必须 > 0,由于num肯定 > 0 ，所以这里只需保证accountNum > 0的逻辑即可
        if (numberValue <= CartHelper.FoodNum.MIN_VALUE) {
            numberValue = mCartDetailRecyclerWeightItem.getAccountNum();
        }
        if (numberValue > CartHelper.FoodNum.MAX_VALUE) {
            numberValue = CartHelper.FoodNum.MAX_VALUE;
        }
//        mEditWeight.setNumberText(numberValue);
        mCartDetailRecyclerWeightItem.setAccountNum(numberValue);
    }
}
