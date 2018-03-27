package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartComboFoodRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.events.BaseEvents;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.ui.EditFoodNumberDialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * @author DangGui
 * @create 2017/4/14.
 */

public class CartComboFoodItemViewholder extends CartBaseViewholder {

    @BindView(R2.id.image_userhead)
    ImageView mImageUserhead;
    @BindView(R2.id.text_username)
    TextView mTextUsername;
    @BindView(R2.id.text_combo_label)
    TextView mTextComboLabel;
    @BindView(R2.id.text_food_name)
    TextView mTextFoodName;
    @BindView(R2.id.text_price)
    TextView mTextPrice;
    @BindView(R2.id.text_foods_count)
    TextView mTextFoodsCount;
    @BindView(R2.id.text_foods_account_count)
    TextView mTextFoodsAccountCount;
    @BindView(R2.id.edit_first_food_number_view)
    FoodNumberTextView mEditFirstFoodNumberView;
    @BindView(R2.id.view_makename_divider)
    View mViewMakenameDivider;
    @BindView(R2.id.text_makename)
    TextView mTextMakename;
    @BindView(R2.id.text_alert)
    TextView mTvAlert;
    @BindView(R2.id.text_combo_select_submenu)
    TextView mTextComboSelectSubmenu;
    private CartComboFoodRecyclerItem mCartComboFoodRecyclerItem;
    private ItemVo mItemVo;

    public CartComboFoodItemViewholder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartRecyclerItem) {
            mCartComboFoodRecyclerItem = mCartRecyclerItem.getCartComboFoodRecyclerItem();
            if (null != mCartComboFoodRecyclerItem) {
                mItemVo = mCartComboFoodRecyclerItem.getItemVo();
                //头像
                if (!TextUtils.isEmpty(mCartComboFoodRecyclerItem.getCustomerAvatarUrl())) {
                    ImageLoaderUtil.getInstance().loadImage(mCartComboFoodRecyclerItem.getCustomerAvatarUrl()
                            , mImageUserhead, ImageLoaderOptionsHelper.getCcdAvatarOptions());
                } else {
                    ImageLoaderUtil.getInstance().loadImage(R.drawable.icon_user_default
                            , mImageUserhead, ImageLoaderOptionsHelper.getCcdAvatarOptions());
                }
                //姓名
                if (!TextUtils.isEmpty(mCartComboFoodRecyclerItem.getCustomerName())) {
                    mTextUsername.setVisibility(View.VISIBLE);
                    mTextUsername.setText(mCartComboFoodRecyclerItem.getCustomerName());
                } else {
                    mTextUsername.setVisibility(View.GONE);
                }
                //菜名
                SpannableStringUtils.Builder builder = SpannableStringUtils
                        .getBuilder(mContext, "");
                if (mCartComboFoodRecyclerItem.isStandby()) {
                    builder.append(mContext.getString(R.string.module_menu_cart_food_standby))
                            .setForegroundColor(ContextCompat.getColor(mContext, R.color.accentColor));
                } else {
                    builder.append("");
                }
                if (!TextUtils.isEmpty(mCartComboFoodRecyclerItem.getFoodName())) {
                    builder.append(mCartComboFoodRecyclerItem.getFoodName());
                } else {
                    builder.append("");
                }
                SpannableStringBuilder spannableStringBuilder = builder.create();
                if (spannableStringBuilder.length() > 0) {
                    mTextFoodName.setText(spannableStringBuilder);
                } else {
                    mTextFoodName.setText("");
                }
                //价格
                if (!TextUtils.isEmpty(mCartComboFoodRecyclerItem.getFoodPrice())) {
                    mTextPrice.setText(mCartComboFoodRecyclerItem.getFoodPrice());
                } else {
                    mTextPrice.setText("");
                }
                //数量
                //点菜数量
                if (mCartComboFoodRecyclerItem.getFoodType() == CartHelper.CartFoodType.TYPE_MUST_SELECT) {
                    mEditFirstFoodNumberView.setVisibility(View.GONE);
                    String unit = mCartComboFoodRecyclerItem.getUnit();
                    String accountUnit = mCartComboFoodRecyclerItem.getAccountUnit();
                    if (!TextUtils.isEmpty(unit)) {
                        mTextFoodsCount.setVisibility(View.VISIBLE);
                        String numStr;
                        double num = mCartComboFoodRecyclerItem.getNum();
                        if (NumberUtils.doubleIsInteger(num)) {
                            numStr = (int) num + unit;
                        } else {
                            numStr = num + unit;
                        }
                        mTextFoodsCount.setText(numStr);
                    } else {
                        mTextFoodsCount.setVisibility(View.GONE);
                    }
                    //结账数量
                    if (mCartComboFoodRecyclerItem.getAccountNum() > 0 && !TextUtils.isEmpty(accountUnit)
                            && !TextUtils.isEmpty(unit) && !accountUnit.equals(unit)) {
                        mTextFoodsAccountCount.setVisibility(View.VISIBLE);
                        String accountNumStr;
                        double accountNum = mCartComboFoodRecyclerItem.getAccountNum();
                        if (NumberUtils.doubleIsInteger(accountNum)) {
                            accountNumStr = (int) accountNum + accountUnit;
                        } else {
                            accountNumStr = accountNum + accountUnit;
                        }
                        mTextFoodsAccountCount.setText(accountNumStr);
                    } else {
                        mTextFoodsAccountCount.setVisibility(View.GONE);
                    }
                } else {
                    mTextFoodsCount.setVisibility(View.GONE);
                    mTextFoodsAccountCount.setVisibility(View.GONE);
                    String unit = mCartComboFoodRecyclerItem.getUnit();
                    String accountUnit = mCartComboFoodRecyclerItem.getAccountUnit();
                    if (!TextUtils.isEmpty(unit)) {
                        mEditFirstFoodNumberView.setVisibility(View.VISIBLE);
                        mEditFirstFoodNumberView.setNumberText(mCartComboFoodRecyclerItem.getNum());
                        mEditFirstFoodNumberView.setUnitText(unit);
                    } else {
                        mEditFirstFoodNumberView.setVisibility(View.GONE);
                    }
                }
                //是否有子菜
                if (mCartComboFoodRecyclerItem.isHasSubMenu()) {
                    mTvAlert.setVisibility(View.GONE);
                    mTextComboSelectSubmenu.setVisibility(View.GONE);
                    //做法
                    if (!TextUtils.isEmpty(mCartComboFoodRecyclerItem.getMakeMethod())) {
                        mViewMakenameDivider.setVisibility(View.VISIBLE);
                        mTextMakename.setVisibility(View.VISIBLE);
                        mTextMakename.setText(mCartComboFoodRecyclerItem.getMakeMethod());
                    } else {
                        mViewMakenameDivider.setVisibility(View.GONE);
                        mTextMakename.setVisibility(View.GONE);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        mTextFoodName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.module_menu_ic_modify, 0);
                    } else {
                        mTextFoodName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.module_menu_ic_modify, 0);
                    }
                } else {
                    mTextMakename.setVisibility(View.GONE);
                    if (mCartComboFoodRecyclerItem.getFoodType() == CartHelper.CartFoodType.TYPE_MUST_SELECT) {
                        mTvAlert.setVisibility(View.VISIBLE);
                        mTextComboSelectSubmenu.setVisibility(View.VISIBLE);
                        mViewMakenameDivider.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            mTextFoodName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                        } else {
                            mTextFoodName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        }
                    } else {
                        mTvAlert.setVisibility(View.GONE);
                        mTextComboSelectSubmenu.setVisibility(View.GONE);
                        mViewMakenameDivider.setVisibility(View.GONE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            mTextFoodName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.module_menu_ic_modify, 0);
                        } else {
                            mTextFoodName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.module_menu_ic_modify, 0);
                        }
                    }
                }
            }
        }
        initListener();
    }

    private void initListener() {
        RxView.clicks(mTextFoodName).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (null != mItemVo) {
                            //如果套餐是必选菜，并且没有选择好的子菜，不能点击菜名进入修改页
                            if (mCartComboFoodRecyclerItem.isHasSubMenu()
                                    || mCartComboFoodRecyclerItem.getFoodType() != CartHelper.CartFoodType.TYPE_MUST_SELECT) {
                                MRouter.getInstance()
                                        .build(RouterPathConstant.SuitDetail.PATH)
                                        .putString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_NAME, mItemVo.getName())
                                        .putString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_ID, mItemVo.getMenuId())
                                        .putSerializable(RouterPathConstant.SuitDetail.PARAM_CREATE_ORDER_PARAM, mCartComboFoodRecyclerItem.getCreateOrderParam())
                                        .putSerializable(RouterPathConstant.SuitDetail.PARAM_ITEMVO, mItemVo)
                                        .navigation(mContext);
                            }
                        }
                    }
                });
        RxView.clicks(mTextComboSelectSubmenu).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        MRouter.getInstance()
                                .build(RouterPathConstant.SuitDetail.PATH)
                                .putString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_NAME, mItemVo.getName())
                                .putString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_ID, mItemVo.getMenuId())
                                .putSerializable(RouterPathConstant.SuitDetail.PARAM_CREATE_ORDER_PARAM, mCartComboFoodRecyclerItem.getCreateOrderParam())
                                .putSerializable(RouterPathConstant.SuitDetail.PARAM_ITEMVO, mItemVo)
                                .navigation(mContext);
                    }
                });
        mEditFirstFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
            @Override
            public void onClick(int which, double numberValue) {
                switch (which) {
                    case FoodNumberTextView.CLICK_LEFT:
                        double num = numberValue - 1;
                        if (num < CartHelper.FoodNum.MIN_VALUE) {
                            num = CartHelper.FoodNum.MIN_VALUE;
                        }
                        if (null != mItemVo) {
                            mItemVo.setNum(num);
                            mItemVo.setAccountNum(num);
                        }
                        if (num != numberValue) {
                            BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY;
                            event.setObject(mItemVo);
                            EventBusHelper.post(event);
                        }
                        break;
                    case FoodNumberTextView.CLICK_RIGHT:
                        double addNum = numberValue + 1;
                        if (addNum > CartHelper.FoodNum.MAX_VALUE) {
                            addNum = CartHelper.FoodNum.MAX_VALUE;
                        }
                        if (null != mItemVo) {
                            mItemVo.setNum(addNum);
                            mItemVo.setAccountNum(addNum);
                        }
                        if (addNum != numberValue) {
                            BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY;
                            event.setObject(mItemVo);
                            EventBusHelper.post(event);
                        }
                        break;
                }
            }
        });

        mEditFirstFoodNumberView.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
            @Override
            public void onClickEdit(double numberValue) {
                showEditValueDialog(mItemVo,1,numberValue,mItemVo.getName(),mItemVo.getUnit(),
                        CartHelper.DialogDateFrom.OTHER_VIEW);
            }
        });
    }
    /**
     * 弹出数量输入框
     */
    private void showEditValueDialog(final Object data, double startNum, double number, String menuName, String unit, final int from) {

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
                if ((data instanceof ItemVo)) {
                    if (null != mItemVo) {
                        mItemVo.setNum(number);
                        mItemVo.setAccountNum(number);
                        BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY;
                        event.setObject(mItemVo);
                        EventBusHelper.post(event);
                    }
                }
            }
        });

    }
}
