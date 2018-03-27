package com.zmsoft.ccd.module.menu.menu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.lib.widget.ShopSpecialityView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.view.CartDetailActivity;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.ParamSuitSubMenu;
import com.zmsoft.ccd.module.menu.menu.bean.ResponseSeatStatus;
import com.zmsoft.ccd.module.menu.menu.bean.vo.CartItemVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuGroupVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuVO;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Description：菜单列表适配器
 * <br/>
 * Created by kumu on 2017/4/7.
 */

public class RetailMenuListAdapter extends BaseListAdapter {


    private static final int LAYOUT_TYPE_GROUP = 1;
    private static final int LAYOUT_TYPE_MENU = 2;
    private static final int LAYOUT_TYPE_SPECIFICATION = 3;
    private static final int LAYOUT_TYPE_LETTER = 4;

    private boolean isShowImage = true;

    public static final int CLICK_TYPE_ADD_CART = 1;
    public static final int CLICK_TYPE_ITEM_PLUS = 2;
    public static final int CLICK_TYPE_ITEM_MINUS = 3;
    public static final int CLICK_TYPE_INPUT_DONE = 4;
    public static final int CLICK_TYPE_EDIT_DIALOG = 5;
    public static final int CLICK_RETAIL_WEIGH = 10;
    public static final int CLICK_RETAIL_WEIGH_CLEAR = 11;

    private AdapterClick mAdapterClick;

    private int mSeatCodeStatus;

    private OrderParam mCreateOrderParam;

    public void setCreateOrderParam(OrderParam createOrderParam) {
        mCreateOrderParam = createOrderParam;
    }

    public OrderParam getCreateOrderParam() {
        return mCreateOrderParam;
    }


    public static RetailMenuListAdapter createImageAdapter(Context context, AdapterClick adapterClick) {
        return new RetailMenuListAdapter(context, true, adapterClick);
    }

    private RetailMenuListAdapter(Context context, boolean isShowImage, AdapterClick adapterClick) {
        super(context, null);
        this.isShowImage = isShowImage;
        mAdapterClick = adapterClick;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LAYOUT_TYPE_LETTER:
                return new GroupLetterHolder(getContext(), inflateLayout(R.layout.module_menu_item_group_letter, parent));
            case LAYOUT_TYPE_GROUP:
                return new GroupCategoryHolder(mContext, inflateLayout(R.layout.module_menu_item_retail_group_category, parent));
            case LAYOUT_TYPE_MENU:
                return new MenuHolder(mContext, inflateLayout(isShowImage ?
                        R.layout.module_menu_item_retail_menu_with_image : R.layout.module_menu_item_retail_menu_without_image, parent)
                        , mSeatCodeStatus, mAdapterClick, this);
            case LAYOUT_TYPE_SPECIFICATION:
                return new SpecificationHolder(getContext(),
                        inflateLayout(R.layout.module_menu_item_retail_menu_specification, parent), mAdapterClick);
        }
        return getUnKnowViewHolder(parent);
    }

    @Override
    protected int getMyItemViewType(int position) {
        Object data = getModel(position);
        if (data instanceof MenuGroupVO) {
            return LAYOUT_TYPE_GROUP;
        } else if (data instanceof MenuVO) {
            return LAYOUT_TYPE_MENU;
        } else if (data instanceof CartItemVO) {
            return LAYOUT_TYPE_SPECIFICATION;
        } else if (data instanceof String) {
            return LAYOUT_TYPE_LETTER;
        }
        return -1;
    }

    public boolean getShowType() {
        return isShowImage;
    }

    public void setShowType(boolean isShowImage) {
        this.isShowImage = isShowImage;
    }

    public void setSeatCodeStatus(int status) {
        this.mSeatCodeStatus = status;
    }


    static class GroupLetterHolder extends BaseHolder {
        @BindView(R2.id.text_letter)
        TextView mTextLetter;

        GroupLetterHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {
            mTextLetter.setText(bean.toString());
        }
    }

    static class GroupCategoryHolder extends BaseHolder {
        @BindView(R2.id.text_category)
        TextView mTextCategory;
        @BindView(R2.id.text_order_count)
        TextView mTextOrderCount;


        GroupCategoryHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (bean instanceof MenuGroupVO) {
                MenuGroupVO menuGroupVO = (MenuGroupVO) bean;
                mTextCategory.setText(menuGroupVO.getGroupName());
                if (menuGroupVO.getOrderCount() == 0) {
                    mTextOrderCount.setVisibility(View.GONE);
                } else {
                    mTextOrderCount.setText(String.valueOf(menuGroupVO.getOrderCount()));
                    mTextOrderCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 菜基本信息ViewHolder
     */
    static class MenuHolder extends BaseHolder {

        ImageView mImageMenuIcon;
        ImageView mImageMenuIconSellOut;

        @BindView(R2.id.view_divider)
        View viewDivider;
        @BindView(R2.id.text_weigh_food_flag)
        TextView mTextSuitFoodFlag;
        @BindView(R2.id.text_food_name)
        TextView mTextFoodName;
        @BindView(R2.id.text_bar_code)
        TextView mTextBarCode;
        @BindView(R2.id.text_food_unit_price)
        TextView mTextFoodUnitPrice;
        @BindView(R2.id.edit_food_number_view)
        FoodNumberTextView mEditFoodNumberView;
        @BindView(R2.id.shop_speciality_view)
        ShopSpecialityView mShopSpecialityView;
        @BindView(R2.id.text_clear_weigh)
        TextView mTextClearWeigh;

        View mViewShade;


        private AdapterClick mAdapterClick;
        private int mSeatCodeStatus;
        private RetailMenuListAdapter mAdapter;

        MenuHolder(Context context, View itemView, int seatCodeStatus, AdapterClick adapterClick, RetailMenuListAdapter menuListAdapter) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
            mImageMenuIcon = (ImageView) itemView.findViewById(R.id.image_menu_icon);
            if (mImageMenuIcon != null) {
                mViewShade = itemView.findViewById(R.id.view_shade);
                mImageMenuIconSellOut = (ImageView) itemView.findViewById(R.id.image_menu_icon_sell_out);
            }
            mAdapterClick = adapterClick;
            mSeatCodeStatus = seatCodeStatus;
            mAdapter = menuListAdapter;
        }

        private boolean showForceMenuFlag(Menu menu) {
            //掌柜那边设置的；并且没售罄的；没下单的（没开桌）
            return menu.isForceMenu() && !menu.isSoldOut() && mSeatCodeStatus != ResponseSeatStatus.STATUS_USED;
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (bean instanceof MenuVO) {
                viewDivider.setVisibility(getAdapterPosition() == 0 ? View.GONE : View.VISIBLE);
                final MenuVO menuVO = (MenuVO) bean;
                final Menu menu = menuVO.getMenu();
                if (menu == null) {
                    return;
                }

                if (TextUtils.isEmpty(menu.getCode())) {
                    mTextBarCode.setVisibility(View.VISIBLE);
                    mTextBarCode.setText(getString(R.string.module_menu_retail_no_barcode_label));
                } else {
                    mTextBarCode.setVisibility(View.VISIBLE);
                    mTextBarCode.setText(menu.getCode());
                }

                if (mImageMenuIcon != null) {
                    ImageLoaderUtil.getInstance().loadImage(menu.getMenuPicUrl(), mImageMenuIcon
                            , ImageLoaderOptionsHelper.getCcdGoodsRoundCornerOptions());
                    //零售列表不能进入商品详情
//                    mImageMenuIcon.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (menu.isSoldOut()) {
//                                return;
//                            }
//                            if (menu.isSuit()) {
//                                goSuitMenuDetail(getContext(), menu, mAdapter.getCreateOrderParam());
//                            } else {
//                                goNormalMenuDetail(getContext(), menuVO, mAdapter.getCreateOrderParam(), CartHelper.FoodDetailType.FOOD_DETAIL);
//                            }
//                        }
//                    });

                    if (mImageMenuIconSellOut != null) {
                        if (menu.isSoldOut()) {
                            mImageMenuIconSellOut.setVisibility(View.VISIBLE);
                            mViewShade.setVisibility(View.VISIBLE);
                        } else {
                            mImageMenuIconSellOut.setVisibility(View.GONE);
                            mViewShade.setVisibility(View.GONE);
                        }
                    }
                }

                if (menuVO.getSpecificationVOs() != null
                        && menuVO.getSpecificationVOs().size() > 0) {

                    if (menuVO.getSpecificationVOs().size() > 1) {
                        Log.e("RetailMenuListAdapter", "===================零售出现多人点，请及时处理");
                    }

                    //零售不存在多人点的情况，只有自己点
                    if (menu.isTwoAccount()) {
                        mEditFoodNumberView.setVisibility(View.GONE);
                        mTextClearWeigh.setVisibility(View.VISIBLE);
                        //mTextClearWeigh.setText(NumberUtils.trimPointIfZero(menuVO.getSpecificationVOs().get(0).getAccountNum()));
                        mTextClearWeigh.setText(NumberUtils.getDecimalFee(menuVO.getSpecificationVOs().get(0).getAccountNum(), 2));
                        mTextClearWeigh.append(menuVO.getSpecificationVOs().get(0).getAccountUnit());
                        mTextClearWeigh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAdapterClick.onAdapterClick(CLICK_RETAIL_WEIGH_CLEAR, view, menuVO);
                            }
                        });
                    } else {
                        mEditFoodNumberView.setVisibility(View.VISIBLE);
                        mEditFoodNumberView.setNumberText(menuVO.getSpecificationVOs().get(0).getNum());
                        mTextClearWeigh.setVisibility(View.GONE);
                    }
//                    if (menuVO.getSpecificationVOs().get(0).hasSpecification()) {
//                        mEditFoodNumberView.setVisibility(View.GONE);
//                        mTextOtherClientReserve.setVisibility(View.GONE);
//                    } else {
//                        //自己点的
//                        if (menuVO.getSpecificationVOs().get(0).isSelf()) {
//                            mEditFoodNumberView.setVisibility(View.VISIBLE);
//                            mTextOtherClientReserve.setVisibility(View.GONE);
//                            mEditFoodNumberView.setNumberText(menuVO.getSpecificationVOs().get(0).getNum());
//                        } else {
//                            mEditFoodNumberView.setVisibility(View.GONE);
//                            mTextOtherClientReserve.setVisibility(View.VISIBLE);
//                            mTextOtherClientReserve.setText(menuVO.getSpecificationVOs().get(0).getShowNum());
//                            mTextOtherClientReserve.append(menuVO.getSpecificationVOs().get(0).getAccountUnit());
//                        }
//                    }
                } else {
                    mEditFoodNumberView.setVisibility(View.GONE);
                    mTextClearWeigh.setVisibility(View.GONE);
                }

                //是否是称重商品
                mTextSuitFoodFlag.setVisibility(menu.isTwoAccount() ? View.VISIBLE : View.GONE);
                //是否显示必选商品标识
                //mTextRequireTip.setVisibility(showForceMenuFlag(menu) ? View.VISIBLE : View.GONE);
                //设置菜名
                mTextFoodName.setText(menu.getName());
                //设置价格
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(String.format(getContext().getResources().getString(R.string.module_menu_list_placeholder_price_unit)
                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , NumberUtils.getDecimalFee(menu.getPrice(), 2))
                        , menu.getAccount()));

                if (menu.isTwoAccount()) {
                    int divide = builder.toString().indexOf('/');
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.module_menu_price_red));
                    builder.setSpan(redSpan, 0, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ForegroundColorSpan graySpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.module_menu_black));
                    builder.setSpan(graySpan, divide, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTextFoodUnitPrice.setText(builder);
                } else {
                    SpannableStringBuilder builderNormal = new SpannableStringBuilder();
                    builderNormal.append(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , NumberUtils.getDecimalFee(menu.getPrice(), 2)));
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.module_menu_price_red));
                    builderNormal.setSpan(redSpan, 0, builderNormal.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTextFoodUnitPrice.setText(builderNormal);
                }

                //如果售罄菜名、价格需要加上横线、图片显示已售完
                if (menu.isSoldOut()) {
                    mTextFoodName.setPaintFlags(mTextFoodName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    mTextFoodUnitPrice.setPaintFlags(mTextFoodUnitPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    mTextFoodName.setPaintFlags(mTextFoodName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    mTextFoodUnitPrice.setPaintFlags(mTextFoodUnitPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }

                mShopSpecialityView.setData(menu.getAcridLevel(), menu.getRecommendLevel(), menu.getSpecialTagString());


                mTextSuitFoodFlag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapterClick.onAdapterClick(CLICK_RETAIL_WEIGH, v, menuVO.getMenu());
                        //ToastUtils.showLongToast(v.getContext(), "弹出对话框")
                    }
                });


                //数量按钮处理
                mEditFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
                    @Override
                    public void onClick(int which, double numberValue) {
                        mAdapterClick.onAdapterClick(
                                which == FoodNumberTextView.CLICK_LEFT ? CLICK_TYPE_ITEM_MINUS : CLICK_TYPE_ITEM_PLUS,
                                mEditFoodNumberView,
                                menuVO.hasOnlyOneSpec() ? menuVO.getFirstSpec() : null);
                    }
                });

                mEditFoodNumberView.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
                    @Override
                    public void onClickEdit(double numberValue) {
                        menuVO.getMySpecification().setTmpNum(numberValue);
                        mAdapterClick.onAdapterClickEdit(CLICK_TYPE_EDIT_DIALOG, mEditFoodNumberView,
                                menuVO.hasOnlyOneSpec() ? menuVO.getFirstSpec() : null);
                    }
                });

            /*    mEditFoodNumberView.setOnInputDone(new EditFoodNumberView.OnInputDone() {
                    @Override
                    public void onDone(double numberValue) {
                        menuVO.getMySpecification().setTmpNum(numberValue);
                        mAdapterClick.onAdapterClick(CLICK_TYPE_INPUT_DONE, mEditFoodNumberView,
                                menuVO.hasOnlyOneSpec() ? menuVO.getFirstSpec() : null);
                    }
                });*/

                //按返回键关闭输入法
            /*   mEditFoodNumberView.setSoftBackListener(new EditFoodNumberView.SoftBackListener() {
                    @Override
                    public void onSoftBack(TextView textView, double inputNum) {
                        textView.clearFocus();
                        menuVO.getMySpecification().setTmpNum(inputNum);
                        mAdapterClick.onAdapterClick(CLICK_TYPE_INPUT_DONE, mEditFoodNumberView,
                                menuVO.hasOnlyOneSpec() ? menuVO.getFirstSpec() : null);
                    }
                });*/

                //itemClickQuickly(menu,menuVO);
                itemClick(menu, menuVO);
            }

        }

        void itemClick(final Menu menu, final MenuVO menuVO) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //售罄
                    if (menu.isSoldOut()) {
                        return;
                    }
                    //套餐
                    if (menu.isSuit()) {
                        return;
                    }
                    if (mAdapterClick == null) {
                        return;
                    }
                    //如果不是称重商品，直接加入购物车
                    if (!menu.isTwoAccount()) {
                        mAdapterClick.onAdapterClick(CLICK_TYPE_ADD_CART, mEditFoodNumberView, menuVO);
                    } else {
                        mAdapterClick.onAdapterClick(CLICK_RETAIL_WEIGH, v, menuVO);
                    }
                }
            });

        }

        public void itemClickQuickly(final Menu menu, final MenuVO menuVO) {
            RxView.clicks(itemView)
                    .flatMap(new Func1<Void, Observable<MenuVO>>() {
                        @Override
                        public Observable<MenuVO> call(Void aVoid) {

                            //售罄
                            if (menu.isSoldOut()) {
                                return null;
                            }

                            //套餐
                            if (menu.isSuit()) {
                                //ToastUtils.showShortToast(getContext(), R.string.module_menu_cart_not_support_combo_hint);
                                goSuitMenuDetail(getContext(), menu, mAdapter.getCreateOrderParam());
                                return null;
                            }

                            //双单位
                            if (menu.isTwoAccount()) {
                                goNormalMenuDetail(getContext(), menuVO, mAdapter.getCreateOrderParam(), CartHelper.FoodDetailType.FOOD_DETAIL);
                                return null;
                            }

                            if (!menu.hasMake() && !menu.hasSpec()) {

                                menuVO.setAggregateNum(menuVO.getAggregateNum() + 1);

                                menuVO.setTmpNum(menuVO.getAggregateNum());

                                Log.e("Adapter", "tmp:" + menuVO.getTmpNum());

                                mEditFoodNumberView.setNumberText(menuVO.getAggregateNum());

                                if (!menuVO.shouldNewLine()) {
                                    mEditFoodNumberView.setVisibility(View.VISIBLE);
                                }
                                return Observable.just(menuVO);
                            }
                            //如果商品没有规格也无做法，点击非图片区域，直接加入购物车，同时菜类分组右侧以及购物车右上角出现数量标记

                            goNormalMenuDetail(getContext(), menuVO, mAdapter.getCreateOrderParam(), CartHelper.FoodDetailType.FOOD_DETAIL);
                            return null;
                        }
                    })
                    .debounce(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<MenuVO>() {
                        @Override
                        public void call(MenuVO o) {
                            if (mAdapterClick != null) {
                                mAdapterClick.onAdapterClick(CLICK_TYPE_ADD_CART, mEditFoodNumberView, menuVO);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });

        }

    }

    /**
     * 跳转到套餐详情
     */
    private static void goSuitMenuDetail(Context context, Menu menu, OrderParam createOrderParam) {
        MRouter.getInstance()
                .build(RouterPathConstant.SuitDetail.PATH)
                .putString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_NAME, menu.getName())
                .putString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_ID, menu.getId())
                .putSerializable(RouterPathConstant.SuitDetail.PARAM_CREATE_ORDER_PARAM, createOrderParam)
                .navigation(context);
    }

    /**
     * 普通菜详情
     *
     * @param type CartHelper.FoodDetailType.*
     * @see CartHelper.FoodDetailType
     */
    private static void goNormalMenuDetail(Context context, MenuVO menuVO, OrderParam createOrderParam, int type) {
        if (menuVO == null || createOrderParam == null) {
            return;
        }
        MRouter.getInstance().build(RouterPathConstant.PATH_CART_DETAIL)
                .putString(CartDetailActivity.EXTRA_MENU_ID, menuVO.getMenuId())
                .putInt(CartDetailActivity.EXTRA_DETAIL_TYPE, type)
                .putString(RouterPathConstant.CartDetail.EXTRA_SEATCODE, createOrderParam.getSeatCode())
                .putString(RouterPathConstant.CartDetail.EXTRA_ORDERID, createOrderParam.getOrderId())
                .putString(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU_NAME, menuVO.getMenuName())
                .navigation(context);
    }

    /**
     * 套餐子菜详情
     *
     * @param type           CartHelper.FoodDetailType.*
     * @param requireMenuNum int （分组为全部必选）某个菜的必选数量
     * @param menuLimitNum   int 某个菜数量限制
     * @see CartHelper.FoodDetailType
     */
    static void goSuitMenuSubDetail(Activity context, MenuVO menuVO, ParamSuitSubMenu paramSuitSubMenu,
                                    OrderParam createOrderParam, int type,
                                    int requireMenuNum, int menuLimitNum, int requestCode, int position) {
        if (menuVO == null || createOrderParam == null) {
            return;
        }
        MRouter.getInstance().build(RouterPathConstant.PATH_CART_DETAIL)
                .putString(CartDetailActivity.EXTRA_MENU_ID, menuVO.getMenuId())
                .putInt(CartDetailActivity.EXTRA_DETAIL_TYPE, type)
                .putString(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU_NAME, menuVO.getMenuName())
                .putString(RouterPathConstant.CartDetail.EXTRA_SEATCODE, createOrderParam.getSeatCode())
                .putString(RouterPathConstant.CartDetail.EXTRA_SPECID, menuVO.getSpecDetailId())
                .putParcelable(RouterPathConstant.CartDetail.EXTRA_SUITGROUPVO, menuVO.getSuitGroupVO())
                .putInt(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU_POSITION, position)
                .putInt(RouterPathConstant.CartDetail.EXTRA_FOOD_NUM, requireMenuNum)
                .putInt(RouterPathConstant.CartDetail.EXTRA_FOOD_LIMITNUM, menuLimitNum)
                .putSerializable(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU, paramSuitSubMenu)
                .navigation(context, requestCode);
    }

    /**
     * 用户点的菜信息（点的份数、规格等）
     */
    static class SpecificationHolder extends BaseHolder {

        @BindView(R2.id.text_menu_specification)
        TextView mTextMenuSpecification;
        @BindView(R2.id.text_menu_extra_price)
        TextView mTextMenuExtraPrice;
        @BindView(R2.id.edit_food_number_view)
        FoodNumberTextView mEditFoodNumberView;
        @BindView(R2.id.text_other_client_reserve)
        TextView mTextOtherClientReserve;
        @BindView(R2.id.view_divider)
        View mViewDivider;

        AdapterClick mAdapterClick;


        SpecificationHolder(Context context, View itemView, AdapterClick adapterClick) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
            mAdapterClick = adapterClick;
        }


        @Override
        protected void bindView(List source, Object bean) {
            if (bean instanceof CartItemVO) {
                final CartItemVO specificationVO = (CartItemVO) bean;
                if (getAdapterPosition() == source.size() - 1) {
                    mViewDivider.setVisibility(View.GONE);
                } else {
                    mViewDivider.setVisibility(View.VISIBLE);
                }
                //"大份，红烧，加香菜，加辣椒，加青菜"
                mTextMenuSpecification.setText(specificationVO.getSpecification());
                if (specificationVO.getExtraPrice() <= 0) {
                    mTextMenuExtraPrice.setText("");
                } else {
                    mTextMenuExtraPrice.setText(getContext().getString(R.string.module_menu_extra_price,
                            FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), specificationVO.getShowExtraPrice())));
                }
                //判断是否是自己点的
                if (specificationVO.isSelf()) {
                    mEditFoodNumberView.setVisibility(View.VISIBLE);
                    mEditFoodNumberView.setNumberText(specificationVO.getNum());
                    mTextOtherClientReserve.setVisibility(View.GONE);
                } else {
                    mEditFoodNumberView.setVisibility(View.GONE);
                    mTextOtherClientReserve.setVisibility(View.VISIBLE);
                    mTextOtherClientReserve.setText((specificationVO.getShowNum()));
                    mTextOtherClientReserve.append(specificationVO.getAccountUnit());
                }

                if (getAdapterPosition() < source.size() - 1) {
                    if (source.get(getAdapterPosition() + 1) instanceof CartItemVO) {
                        mViewDivider.setVisibility(View.VISIBLE);
                    } else {
                        mViewDivider.setVisibility(View.GONE);
                    }
                } else {
                    mViewDivider.setVisibility(View.VISIBLE);
                }

                mEditFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
                    @Override
                    public void onClick(int which, double numberValue) {
                        switch (which) {
                            case FoodNumberTextView.CLICK_LEFT:
                                mAdapterClick.onAdapterClick(CLICK_TYPE_ITEM_MINUS, mEditFoodNumberView, specificationVO);
                                break;
                            case FoodNumberTextView.CLICK_RIGHT:
                                mAdapterClick.onAdapterClick(CLICK_TYPE_ITEM_PLUS, mEditFoodNumberView, specificationVO);
                                break;
                        }
                    }
                });

                mEditFoodNumberView.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
                    @Override
                    public void onClickEdit(double numberValue) {
                        specificationVO.setTmpNum(numberValue);
                        mAdapterClick.onAdapterClickEdit(CLICK_TYPE_EDIT_DIALOG, mEditFoodNumberView, specificationVO);
                    }
                });
              /*  mEditFoodNumberView.setOnInputDone(new FoodNumberTextView.OnInputDone() {
                    @Override
                    public void onDone(double numberValue) {
                        specificationVO.setTmpNum(numberValue);
                        mAdapterClick.onAdapterClick(CLICK_TYPE_INPUT_DONE, mEditFoodNumberView, specificationVO);
                    }
                });

                //按返回键关闭输入法
                mEditFoodNumberView.setSoftBackListener(new FoodNumberTextView.SoftBackListener() {
                    @Override
                    public void onSoftBack(TextView textView, double inputNum) {
                        textView.clearFocus();
                        specificationVO.setTmpNum(inputNum);
                        mAdapterClick.onAdapterClick(CLICK_TYPE_INPUT_DONE, mEditFoodNumberView, specificationVO);
                    }
                });*/
            }
        }
    }

}
