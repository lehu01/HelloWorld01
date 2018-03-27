package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder;

import android.app.Activity;
import android.content.Context;
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
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartNormalFoodRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;
import com.zmsoft.ccd.module.menu.cart.view.CartDetailActivity;
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

public class CartNormalFoodItemViewholder extends CartBaseViewholder {
    @BindView(R2.id.image_userhead)
    ImageView mImageUserhead;
    @BindView(R2.id.text_username)
    TextView mTextUsername;
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
    @BindView(R2.id.edit_second_food_number_view)
    FoodNumberTextView mEditSecondFoodNumberView;
    @BindView(R2.id.view_makename_divider)
    View mViewMakenameDivider;
    @BindView(R2.id.text_makename)
    TextView mTextMakename;

    private CartNormalFoodRecyclerItem mCartNormalFoodRecyclerItem;
    private ItemVo mItemVo;

    public CartNormalFoodItemViewholder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartRecyclerItem) {
            mCartNormalFoodRecyclerItem = mCartRecyclerItem.getCartNormalFoodRecyclerItem();
            if (null != mCartNormalFoodRecyclerItem) {
                mItemVo = mCartNormalFoodRecyclerItem.getItemVo();
                //头像
                if (!TextUtils.isEmpty(mCartNormalFoodRecyclerItem.getCustomerAvatarUrl())) {
                    ImageLoaderUtil.getInstance().loadImage(mCartNormalFoodRecyclerItem.getCustomerAvatarUrl()
                            , mImageUserhead, ImageLoaderOptionsHelper.getCcdAvatarOptions());
                } else {
                    ImageLoaderUtil.getInstance().loadImage(R.drawable.icon_user_default
                            , mImageUserhead, ImageLoaderOptionsHelper.getCcdAvatarOptions());
                }
                //姓名
                if (!TextUtils.isEmpty(mCartNormalFoodRecyclerItem.getCustomerName())) {
                    mTextUsername.setVisibility(View.VISIBLE);
                    mTextUsername.setText(mCartNormalFoodRecyclerItem.getCustomerName());
                } else {
                    mTextUsername.setVisibility(View.GONE);
                }
                //菜名
                SpannableStringUtils.Builder builder = SpannableStringUtils
                        .getBuilder(mContext, "");
                if (mCartNormalFoodRecyclerItem.isStandby()) {
                    builder.append(mContext.getString(R.string.module_menu_cart_food_standby))
                            .setForegroundColor(ContextCompat.getColor(mContext, R.color.accentColor));
                } else {
                    builder.append("");
                }
                if (!TextUtils.isEmpty(mCartNormalFoodRecyclerItem.getFoodName())) {
                    builder.append(mCartNormalFoodRecyclerItem.getFoodName());
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
                if (!TextUtils.isEmpty(mCartNormalFoodRecyclerItem.getFoodPrice())) {
                    mTextPrice.setText(mCartNormalFoodRecyclerItem.getFoodPrice());
                } else {
                    mTextPrice.setText("");
                }
                //数量
                //点菜数量
                if (mCartNormalFoodRecyclerItem.getFoodType() == CartHelper.CartFoodType.TYPE_MUST_SELECT) {
                    mEditFirstFoodNumberView.setVisibility(View.GONE);
                    mEditSecondFoodNumberView.setVisibility(View.GONE);
                    String unit = mCartNormalFoodRecyclerItem.getUnit();
                    String accountUnit = mCartNormalFoodRecyclerItem.getAccountUnit();
                    if (!TextUtils.isEmpty(unit)) {
                        mTextFoodsCount.setVisibility(View.VISIBLE);
                        String numStr;
                        double num = mCartNormalFoodRecyclerItem.getNum();
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
                    if (mCartNormalFoodRecyclerItem.getAccountNum() > 0 && !TextUtils.isEmpty(accountUnit)
                            && !TextUtils.isEmpty(unit) && !accountUnit.equals(unit)) {
                        mTextFoodsAccountCount.setVisibility(View.VISIBLE);
                        String accountNumStr;
                        double accountNum = mCartNormalFoodRecyclerItem.getAccountNum();
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
                    String unit = mCartNormalFoodRecyclerItem.getUnit();
                    String accountUnit = mCartNormalFoodRecyclerItem.getAccountUnit();
                    if (!TextUtils.isEmpty(unit)) {
                        mEditFirstFoodNumberView.setVisibility(View.VISIBLE);
                        mEditFirstFoodNumberView.setNumberText(mCartNormalFoodRecyclerItem.getNum());
                        mEditFirstFoodNumberView.setUnitText(unit);
                    } else {
                        mEditFirstFoodNumberView.setVisibility(View.GONE);
                    }
                    //结账数量
                    if (!TextUtils.isEmpty(accountUnit) && !TextUtils.isEmpty(unit) && !accountUnit.equals(unit)) {
                        mEditSecondFoodNumberView.setVisibility(View.VISIBLE);
                        mEditSecondFoodNumberView.setNumberText(mCartNormalFoodRecyclerItem.getAccountNum());
                        mEditSecondFoodNumberView.setUnitText(accountUnit);
                    } else {
                        mEditSecondFoodNumberView.setVisibility(View.GONE);
                    }
                }

                //做法
                if (!TextUtils.isEmpty(mCartNormalFoodRecyclerItem.getMakeMethod())) {
                    mViewMakenameDivider.setVisibility(View.VISIBLE);
                    mTextMakename.setVisibility(View.VISIBLE);
                    mTextMakename.setText(mCartNormalFoodRecyclerItem.getMakeMethod());
                } else {
                    mViewMakenameDivider.setVisibility(View.GONE);
                    mTextMakename.setVisibility(View.GONE);
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
                        int detailType = CartHelper.FoodDetailType.FOOD_MODIFY;
                        if (mCartNormalFoodRecyclerItem.getFoodType() == CartHelper.CartFoodType.TYPE_MUST_SELECT) {
                            detailType = CartHelper.FoodDetailType.MUST_SELECT_MODIFY;
                        }
                        ItemVo itemVo = mCartNormalFoodRecyclerItem.getItemVo();
                        String menuId = null;
                        //菜类，普通菜、套餐、自定义菜等
                        int kind = 0;
                        if (null != itemVo) {
                            menuId = itemVo.getMenuId();
                            kind = itemVo.getKind();
                        }
                        //如果是自定义菜，就跳转到自定义菜修改页，否则进入普通菜修改页
                        if (kind == CartHelper.CartFoodKind.KIND_CUSTOM_FOOD) {
                            MRouter.getInstance()
                                    .build(RouterPathConstant.CustomFood.PATH)
                                    .putSerializable(RouterPathConstant.CustomFood.EXTRA_CUSTOM_FOOD_ITEMVO, itemVo)
                                    .putSerializable(RouterPathConstant.CustomFood.EXTRA_CREATE_ORDER_PARAM
                                            , mCartNormalFoodRecyclerItem.getCreateOrderParam())
                                    .navigation((Activity) mContext, CartHelper.CartActivityRequestCode.CODE_TO_CART_CUSTOM_FOOD);
                        } else {
                            MRouter.getInstance()
                                    .build(RouterPathConstant.PATH_CART_DETAIL)
                                    .putString(CartDetailActivity.EXTRA_MENU_ID, menuId)
                                    .putInt(CartDetailActivity.EXTRA_DETAIL_TYPE, detailType)
                                    .putSerializable(CartDetailActivity.EXTRA_DETAIL_ITEMVO, itemVo)
                                    .putString(RouterPathConstant.CartDetail.EXTRA_SEATCODE
                                            , mCartNormalFoodRecyclerItem.getSeatCode())
                                    .putString(RouterPathConstant.CartDetail.EXTRA_ORDERID
                                            , mCartNormalFoodRecyclerItem.getOrderId())
                                    .navigation((Activity) mContext, CartHelper.CartActivityRequestCode.CODE_TO_CART_DETAIL);
                        }

                    }
                });
        mEditFirstFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
            @Override
            public void onClick(int which, double numberValue) {
                switch (which) {
                    case FoodNumberTextView.CLICK_LEFT:
                        double num = numberValue - 1;

                        //如果有起点份数，且当前数量小于等于起点份数，点减号直接=0
                        if (mItemVo != null && mItemVo.getStartNum() > 1 && numberValue <= mItemVo.getStartNum()) {
                            num = 0;
                        }

                        if (num < CartHelper.FoodNum.MIN_VALUE) {
                            num = CartHelper.FoodNum.MIN_VALUE;
                        }
                        if (null != mItemVo) {
                            mItemVo.setNum(num);
                        }
                        if (num != numberValue) {
                            BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY;
                            event.setObject(mItemVo);
                            EventBusHelper.post(event);
                        }
                        break;
                    case FoodNumberTextView.CLICK_RIGHT:
                        double addNum = numberValue + 1;

                        //如果有起点份数，且当前数量小于起点份数，点加号数量直接设置为起点数量
                        if (mItemVo != null && mItemVo.getStartNum() > 1 && numberValue < mItemVo.getStartNum()) {
                            addNum = mItemVo.getStartNum();
                        }

                        if (addNum > CartHelper.FoodNum.MAX_VALUE) {
                            addNum = CartHelper.FoodNum.MAX_VALUE;
                        }
                        if (null != mItemVo) {
                            mItemVo.setNum(addNum);
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
        mEditSecondFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
            @Override
            public void onClick(int which, double numberValue) {
                switch (which) {
                    case FoodNumberTextView.CLICK_LEFT:
                        double accountNum = numberValue - 1;
                        if (accountNum <= CartHelper.FoodNum.MIN_VALUE) {
                            //双单位菜，num >0 的情况下，accountNum必须 > 0,由于num <=0 时，该菜直接被移除了，所以这里只需保证accountNum > 0的逻辑即可
                            accountNum = numberValue;
                        }
                        if (null != mItemVo) {
                            mItemVo.setAccountNum(accountNum);
                        }
                        if (accountNum != numberValue) {
                            BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY;
                            event.setObject(mItemVo);
                            EventBusHelper.post(event);
                        }
                        break;
                    case FoodNumberTextView.CLICK_RIGHT:
                        double addAccountNum = numberValue + 1;
                        if (addAccountNum > CartHelper.FoodNum.MAX_VALUE) {
                            addAccountNum = CartHelper.FoodNum.MAX_VALUE;
                        }
                        if (null != mItemVo) {
                            mItemVo.setAccountNum(addAccountNum);
                        }
                        if (addAccountNum != numberValue) {
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
                showEditValueDialog(mItemVo,
                        mItemVo.getStartNum(),
                        numberValue,
                        mItemVo.getName(),
                        mItemVo.getUnit(),
                        CartHelper.DialogDateFrom.OTHER_VIEW);
            }
        });

       // 只需要和最小值进行限额判断
       mEditSecondFoodNumberView.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
           @Override
           public void onClickEdit(double numberValue) {
               showEditValueDialog(mItemVo,1,
                       numberValue,
                       mItemVo.getName(),
                       mItemVo.getAccountUnit(),
                       CartHelper.DialogDateFrom.CART_NORMAL_FOOD_DETAIL_SENOND_VIEW);
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
                        if(from == CartHelper.DialogDateFrom.OTHER_VIEW) {
                            mItemVo.setNum(number);
                        }else if (from == CartHelper.DialogDateFrom.CART_NORMAL_FOOD_DETAIL_SENOND_VIEW) {
                            mItemVo.setAccountNum(number);
                        }
                        BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY;
                        event.setObject(mItemVo);
                        EventBusHelper.post(event);

                    }
                }
            }
        });

    }
}
