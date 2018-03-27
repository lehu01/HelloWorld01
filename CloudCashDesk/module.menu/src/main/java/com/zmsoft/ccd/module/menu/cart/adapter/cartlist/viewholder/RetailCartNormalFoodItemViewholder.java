package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.lib.widget.ShopSpecialityView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.RetailCartNormalFoodRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.RetailCartRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.view.RetailCartDetailActivity;
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

public class RetailCartNormalFoodItemViewholder extends RetailCartBaseViewholder {

    @BindView(R2.id.image_menu_icon)
    ImageView imageMenuIcon;
    @BindView(R2.id.view_shade)
    View viewShade;
    @BindView(R2.id.image_menu_icon_sell_out)
    ImageView imageMenuIconSellOut;
    @BindView(R2.id.frame_image_menu_icon)
    RelativeLayout frameImageMenuIcon;
    @BindView(R2.id.text_weigh_food_flag)
    TextView textWeighFoodFlag;
    @BindView(R2.id.text_food_name)
    TextView textFoodName;
    @BindView(R2.id.shop_speciality_view)
    ShopSpecialityView shopSpecialityView;
    @BindView(R2.id.text_bar_code)
    TextView textBarCode;
    @BindView(R2.id.text_food_unit_price)
    TextView textFoodUnitPrice;
    @BindView(R2.id.edit_food_number_view)
    FoodNumberTextView editFoodNumberView;
    @BindView(R2.id.edit_food_weight_view)
    EditFoodNumberView editFoodWeightView;
    @BindView(R2.id.text_clear_weigh)
    TextView textClearWeigh;
    @BindView(R2.id.rl_food_weight)
    RelativeLayout mRlFoodWeight;

    @BindView(R2.id.view_divider)
    View viewDivider;

    @BindView(R2.id.tv_account_unit)
    TextView mTvAccountUnit;

    private RetailCartNormalFoodRecyclerItem mCartNormalFoodRecyclerItem;
    private ItemVo mItemVo;

    public RetailCartNormalFoodItemViewholder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartRecyclerItem) {
            mCartNormalFoodRecyclerItem = mCartRecyclerItem.getRetailCartNormalFoodRecyclerItem();
            if (null != mCartNormalFoodRecyclerItem) {
                dismissFirstGoodsDivider(source, bean);
                mItemVo = mCartNormalFoodRecyclerItem.getItemVo();
                //菜配图
                ImageLoaderUtil.getInstance().loadImage(mItemVo.getImagePath(), imageMenuIcon
                        , ImageLoaderOptionsHelper.getCcdGoodsRoundCornerOptions());

                if (!TextUtils.isEmpty(mCartNormalFoodRecyclerItem.getFoodName())) {
                    textFoodName.setText(mCartNormalFoodRecyclerItem.getFoodName());
                } else {
                    textFoodName.setText("");
                }

                //条形码
                if (!TextUtils.isEmpty(mItemVo.getCode())) {
                    textBarCode.setText(mItemVo.getCode());
                } else {
                    textBarCode.setText(getString(R.string.module_menu_retail_no_barcode_label));
                }

                if (isWeight())
                    textWeighFoodFlag.setVisibility(View.VISIBLE);
                else
                    textWeighFoodFlag.setVisibility(View.GONE);

                //设置价格
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(String.format(getContext().getResources().getString(R.string.module_menu_list_placeholder_price_unit)
                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , NumberUtils.getDecimalFee(mItemVo.getPrice(), 2))
                        , isWeight() ? mCartNormalFoodRecyclerItem.getAccountUnit() : ""));

                if (isWeight()) {
                    int divide = builder.toString().indexOf('/');
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.module_menu_price_red));
                    builder.setSpan(redSpan, 0, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ForegroundColorSpan graySpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.module_menu_black));
                    builder.setSpan(graySpan, divide, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textFoodUnitPrice.setText(builder);
                } else {
                    textFoodUnitPrice.setTextColor(getContext().getResources().getColor(R.color.module_menu_price_red));
                    textFoodUnitPrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , NumberUtils.getDecimalFee(mItemVo.getPrice(), 2)));
                }

                //数量
                if (!isWeight()) {
                    mRlFoodWeight.setVisibility(View.GONE);
                    editFoodNumberView.setVisibility(View.VISIBLE);
                    editFoodNumberView.setNumberText(mCartNormalFoodRecyclerItem.getNum());
                } else {
                    editFoodNumberView.setVisibility(View.GONE);
                    mRlFoodWeight.setVisibility(View.VISIBLE);
                    editFoodWeightView.setNumberText(mCartNormalFoodRecyclerItem.getAccountNum());
                    mTvAccountUnit.setText(mCartNormalFoodRecyclerItem.getAccountUnit());
                }
            }
        }
        initListener();
    }

    private void dismissFirstGoodsDivider(List source, Object bean) {
        if (null == source || null == bean) return;
        int position = source.indexOf(bean);
        if (position >= 1) {
            bean = source.get(position - 1);
            if (null != bean && bean instanceof RetailCartRecyclerItem) {
                if (((RetailCartRecyclerItem) bean).getItemType() == RetailCartRecyclerItem.ItemType.TYPE_ORDER_INFO) {
                    viewDivider.setVisibility(View.GONE); //第一个商品去除分割线
                } else {
                    viewDivider.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private boolean isWeight() {
        return mCartNormalFoodRecyclerItem.getDoubleUnits() == 1;
    }

    private void initListener() {
        RxView.clicks(textFoodName).throttleFirst(1, TimeUnit.SECONDS)
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
                                    .build(RouterPathConstant.RetailCustomFood.PATH)
                                    .putSerializable(RouterPathConstant.RetailCustomFood.EXTRA_CUSTOM_FOOD_ITEMVO, itemVo)
                                    .putSerializable(RouterPathConstant.RetailCustomFood.EXTRA_CREATE_ORDER_PARAM
                                            , mCartNormalFoodRecyclerItem.getCreateOrderParam())
                                    .navigation((Activity) mContext, CartHelper.CartActivityRequestCode.CODE_TO_CART_CUSTOM_FOOD);
                        } else {
                            MRouter.getInstance()
                                    .build(RouterPathConstant.RetailCartDetail.PATH_CART_DETAIL)
                                    .putString(RetailCartDetailActivity.EXTRA_MENU_ID, menuId)
                                    .putInt(RetailCartDetailActivity.EXTRA_DETAIL_TYPE, detailType)
                                    .putSerializable(RetailCartDetailActivity.EXTRA_DETAIL_ITEMVO, itemVo)
                                    .putString(RouterPathConstant.CartDetail.EXTRA_SEATCODE
                                            , mCartNormalFoodRecyclerItem.getSeatCode())
                                    .putString(RouterPathConstant.CartDetail.EXTRA_ORDERID
                                            , mCartNormalFoodRecyclerItem.getOrderId())
                                    .navigation((Activity) mContext, CartHelper.CartActivityRequestCode.CODE_TO_CART_DETAIL);
                        }

                    }
                });
        editFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
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

        editFoodNumberView.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
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

        weightFoodNumListen();
    }

    private void weightFoodNumListen() {
        editFoodWeightView.setOnInputDone(new EditFoodNumberView.OnInputDone() {
            @Override
            public void onDone(double numberValue) {
                if (null != mItemVo) {
                    if (mItemVo.getAccountNum() != numberValue) {
                        if (!CartHelper.checkStartNum(mItemVo.getStartNum(), numberValue)) {
                            if (numberValue != 0) {
                                editFoodWeightView.setNumberText
                                        (mItemVo.getStartNum() > mItemVo.getNum() ? mItemVo.getStartNum() : mItemVo.getNum());
                                ToastUtils.showShortToast(getContext(),
                                        getContext().getString(R.string.module_menu_check_start_num,
                                                mItemVo.getName(), mItemVo.getStartNum(), mItemVo.getUnit()));
                                return;
                            }
                        }
                        if (numberValue == 0) {
                            mItemVo.setNum(numberValue);
                        }
                        mItemVo.setAccountNum(numberValue);
                        BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY;
                        event.setObject(mItemVo);
                        EventBusHelper.post(event);
                    }
                }
            }
        });

        //按返回键，检查起点分数
        editFoodWeightView.setSoftBackListener(new EditFoodNumberView.SoftBackListener() {
            @Override
            public void onSoftBack(TextView textView, double inputNum) {
                if (null != mItemVo) {
                    if (mItemVo.getAccountNum() != inputNum) {
                        if (!CartHelper.checkStartNum(mItemVo.getStartNum(), inputNum)) {
                            if (inputNum != 0) {
                                editFoodWeightView.setNumberText
                                        (mItemVo.getStartNum() > mItemVo.getNum() ? mItemVo.getStartNum() : mItemVo.getNum());
                                ToastUtils.showShortToast(getContext(),
                                        getContext().getString(R.string.module_menu_check_start_num,
                                                mItemVo.getName(), mItemVo.getStartNum(), mItemVo.getUnit()));
                                return;
                            }
                        }
                        if (inputNum == 0) {
                            mItemVo.setNum(inputNum);
                        }
                        mItemVo.setAccountNum(inputNum);
                        BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY;
                        event.setObject(mItemVo);
                        EventBusHelper.post(event);
                    }
                }
            }
        });

        CustomViewUtil.initEditViewFocousAll(editFoodWeightView.getEditText());

        mRlFoodWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFoodWeightView.getEditText().setSelectAllOnFocus(true);
                editFoodWeightView.getEditText().clearFocus();
                editFoodWeightView.getEditText().requestFocus();
                showInputMethod();
            }
        });
    }

    private void showInputMethod() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 200);
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
                        BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY;
                        event.setObject(mItemVo);
                        EventBusHelper.post(event);
                    }
                }
            }
        });

    }
}
