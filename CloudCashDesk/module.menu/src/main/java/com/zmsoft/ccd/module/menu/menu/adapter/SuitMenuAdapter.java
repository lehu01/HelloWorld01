package com.zmsoft.ccd.module.menu.menu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.lib.widget.ShopSpecialityView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.ParamSuitSubMenu;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitGroupVO;
import com.zmsoft.ccd.module.menu.menu.ui.SuitDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：套餐详情适配器
 * <br/>
 * Created by kumu on 2017/4/21.
 */

public class SuitMenuAdapter extends BaseListAdapter {

    private static final int LAYOUT_TYPE_GROUP = 1;
    private static final int LAYOUT_TYPE_MENU = 2;
    private static final int LAYOUT_TYPE_SPECIFICATION = 3;


    public static final int CLICK_TYPE_ADD_CART = 1;
    public static final int CLICK_TYPE_PLUS = 2;
    public static final int CLICK_TYPE_MINUS = 3;
    public static final int CLICK_TYPE_INPUT_DONE = 4;
    public static final int CLICK_TYPE_EDIT_DIALOG = 5;

    private AdapterClick mAdapterClick;

    private OrderParam mCreateOrderParam;

    public void setCreateOrderParam(OrderParam createOrderParam) {
        mCreateOrderParam = createOrderParam;
    }

    public OrderParam getCreateOrderParam() {
        return mCreateOrderParam;
    }

    public SuitMenuAdapter(Context context, AdapterClick adapterClick) {
        super(context, null);
        mAdapterClick = adapterClick;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LAYOUT_TYPE_GROUP:
                return new GroupHolder(mContext, inflateLayout(R.layout.module_menu_item_suit_group, parent));
            case LAYOUT_TYPE_MENU:
                return new MenuHolder(mContext, inflateLayout(R.layout.module_menu_item_suit_menu, parent), mAdapterClick, this);
            case LAYOUT_TYPE_SPECIFICATION:
                return new SpecificationHolder(getContext(),
                        inflateLayout(R.layout.module_menu_item_suit_sub_menu_specification, parent), mAdapterClick);
        }
        return getUnKnowViewHolder(parent);
    }

    @Override
    protected int getMyItemViewType(int position) {
        Object data = getModel(position);
        if (data instanceof SuitGroupVO) {
            return LAYOUT_TYPE_GROUP;
        } else if (data instanceof MenuVO) {
            MenuVO menuVO = (MenuVO) data;
            return menuVO.isSelectedMenuNewLine() ? LAYOUT_TYPE_SPECIFICATION : LAYOUT_TYPE_MENU;
        }
        return -1;
    }

    static class GroupHolder extends BaseHolder {
        @BindView(R2.id.text_group_name)
        TextView mTextGroupName;
        @BindView(R2.id.text_number)
        TextView mTextNumber;
        @BindView(R2.id.text_require_num)
        TextView mTextRequireNum;


        GroupHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (bean instanceof SuitGroupVO) {
                SuitGroupVO groupVO = (SuitGroupVO) bean;
                mTextGroupName.setText(groupVO.getGroupName());

                if (groupVO.isRequired()) {
                    mTextNumber.setVisibility(View.GONE);
                    mTextRequireNum.setVisibility(View.VISIBLE);
                    mTextRequireNum.setText(getString(R.string.module_menu_suit_menu_require_num,
                            NumberUtils.trimPointIfZero(groupVO.getCurrentNum())));
                } else {
                    mTextRequireNum.setVisibility(View.GONE);
                    mTextNumber.setVisibility(View.VISIBLE);

                    mTextNumber.setText(getString(R.string.module_menu_suit_detail_group_num,
                            NumberUtils.trimPointIfZero(groupVO.getCurrentNum()),
                            groupVO.isNoLimit() ? getString(R.string.module_menu_suit_detail_group_num_no_limit)
                                    : NumberUtils.trimPointIfZero(groupVO.getNum())));
                }

            }
        }
    }

    /**
     * 菜基本信息ViewHolder
     */
    static class MenuHolder extends BaseHolder {

        @BindView(R2.id.image_menu_icon)
        ImageView mImageMenuIcon;
        @BindView(R2.id.image_menu_icon_sell_out)
        ImageView mImageMenuIconSellOut;
        @BindView(R2.id.view_divider)
        View viewDivider;
        @BindView(R2.id.text_food_name)
        TextView mTextFoodName;
        @BindView(R2.id.text_food_extra_price)
        TextView mTextFoodExtraPrice;
        @BindView(R2.id.edit_food_number_view)
        FoodNumberTextView mEditFoodNumberView;
        @BindView(R2.id.view_shade)
        View mViewShade;
        @BindView(R2.id.text_require_num)
        TextView mTextRequireNum;
        @BindView(R2.id.shop_speciality_view)
        ShopSpecialityView mShopSpecialityView;

        private AdapterClick mAdapterClick;
        private SuitMenuAdapter mSuitMenuAdapter;

        MenuHolder(Context context, View itemView, AdapterClick adapterClick, SuitMenuAdapter suitMenuAdapter) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
            mAdapterClick = adapterClick;
            mSuitMenuAdapter = suitMenuAdapter;
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

                mShopSpecialityView.setData(menu.getAcridLevel(), menu.getRecommendLevel(), menu.getSpecialTagString());

                ImageLoaderUtil.getInstance().loadImage(menu.getMenuPicUrl(),
                        R.drawable.module_menu_ic_default_menu_image,
                        R.drawable.module_menu_ic_default_menu_image, mImageMenuIcon);

                if (mImageMenuIconSellOut != null) {
                    if (menu.isSoldOut()) {
                        mImageMenuIconSellOut.setVisibility(View.VISIBLE);
                        mViewShade.setVisibility(View.VISIBLE);
                    } else {
                        mImageMenuIconSellOut.setVisibility(View.GONE);
                        mViewShade.setVisibility(View.GONE);
                    }
                }

                mImageMenuIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (menuVO.getMenu().isSoldOut()) {
                            return;
                        }
                        ParamSuitSubMenu subMenu = null;
                        if (menuVO.isRequired() && menuVO.hasSelectedMenu()) {
                            MenuVO m = menuVO.getSelectedMenus().get(0);
                            subMenu = new ParamSuitSubMenu(m.getAccountNum(), m.getMakeId(), m.getMemo(), m.getLabels());
                        }
                        MenuListAdapter.goSuitMenuSubDetail((Activity) getContext(),
                                menuVO, subMenu, mSuitMenuAdapter.getCreateOrderParam(),
                                menuVO.isRequired() ? CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL : CartHelper.FoodDetailType.COMBO_CHILD_DETAIL,
                                menuVO.getPerNum(), menuVO.getLimitNum(), SuitDetailActivity.REQUEST_CODE_SUIT_SUB_MENU, getAdapterPosition());
                    }
                });
                boolean flag = menuVO.getSelectedMenus() != null && menuVO.getSelectedMenus().size() == 1 && !menuVO.getSelectedMenus().get(0).isSelectedMenuNewLine();
                if (menuVO.isRequired() && flag) {
                    mTextRequireNum.setVisibility(View.VISIBLE);
                    mTextRequireNum.setText(getString(R.string.module_menu_suit_menu_require_num_unit,
                            menu.getPerNum(), menu.getBuyAccount()));
                } else {
                    mTextRequireNum.setVisibility(View.GONE);
                }

                if (menuVO.getSelectedMenuNoSpec() != null
                        && menuVO.getSelectedMenuNoSpec().getTmpNum() > 0
                        && !menuVO.isRequired()) {
                    if (getAdapterPosition() < source.size() - 1) {
                        Object obj = source.get(getAdapterPosition() + 1);
                        if (obj instanceof MenuVO && ((MenuVO) obj).getMenuId().equals(menuVO.getMenuId())) {
                            mEditFoodNumberView.setVisibility(View.GONE);
                        } else {
                            mEditFoodNumberView.setVisibility(View.VISIBLE);
                            mEditFoodNumberView.setNumberText(menuVO.getSelectedMenuNoSpec().getTmpNum());
                        }
                    } else {
                        mEditFoodNumberView.setVisibility(View.VISIBLE);
                        mEditFoodNumberView.setNumberText(menuVO.getSelectedMenuNoSpec().getTmpNum());
                    }
                } else {
                    mEditFoodNumberView.setVisibility(View.GONE);
                }

                //设置菜名: 套餐的子菜如果是有规格的，则掌柜上设置好规格后，在商品名称后面有规格标记：千岛湖鱼头（大份）
                mTextFoodName.setText(menu.getName());
                if (menu.hasSpec() && !TextUtils.isEmpty(menu.getSpecDetailName())) {
                    mTextFoodName.append(getString(R.string.module_menu_suit_menu_name_suffix, menu.getSpecDetailName()));
                }
                //加价信息
                if (menu.getAddPrice() > 0) {
                    mTextFoodExtraPrice.setVisibility(View.VISIBLE);
                    mTextFoodExtraPrice.setText(getString(R.string.module_menu_suit_menu_add_price,
                            FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                    , NumberUtils.trimPointIfZero(menu.getAddPrice()))));
                } else {
                    mTextFoodExtraPrice.setVisibility(View.GONE);
                }


                //如果售罄菜名、价格需要加上横线、图片显示已售完
                if (menu.isSoldOut()) {
                    mTextFoodName.setPaintFlags(mTextFoodName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    mTextFoodExtraPrice.setPaintFlags(mTextFoodExtraPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    mTextFoodName.setPaintFlags(mTextFoodName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    mTextFoodExtraPrice.setPaintFlags(mTextFoodExtraPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }


                mEditFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
                    @Override
                    public void onClick(int which, double numberValue) {
                        mAdapterClick.onAdapterClick(which == FoodNumberTextView.CLICK_LEFT
                                ? CLICK_TYPE_MINUS : CLICK_TYPE_PLUS, mEditFoodNumberView, menuVO.getSelectedMenuNoSpec());
                    }
                });

                mEditFoodNumberView.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
                    @Override
                    public void onClickEdit(double numberValue) {
                        if (menuVO.getSelectedMenuNoSpec() != null) {
                            menuVO.getSelectedMenuNoSpec().setSuitSubInputNum(numberValue);
                            mAdapterClick.onAdapterClickEdit(CLICK_TYPE_EDIT_DIALOG, mEditFoodNumberView, menuVO.getSelectedMenuNoSpec());
                        }
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClick(menuVO);
                    }
                });

            }
        }

        private void itemClick(MenuVO menuVO) {
            if (menuVO.getMenu().isSoldOut()) {
                return;
            }
            if (menuVO.isRequired()) {
                if (menuVO.hasSelectedMenu()) {
                    MenuVO m = menuVO.getSelectedMenus().get(0);
                    MenuListAdapter.goSuitMenuSubDetail((Activity) getContext(), m,
                            new ParamSuitSubMenu(m.getAccountNum(), m.getMakeId(), m.getMemo(), m.getLabels()),
                            mSuitMenuAdapter.getCreateOrderParam(),
                            CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL,
                            m.getPerNum(), m.getLimitNum(), SuitDetailActivity.REQUEST_CODE_SUIT_SUB_MENU, getAdapterPosition());
                }
                return;
            }

            if (menuVO.getMenu().hasMake() || menuVO.getMenu().isTwoAccount()) {
                MenuListAdapter.goSuitMenuSubDetail((Activity) getContext(), menuVO, null,
                        mSuitMenuAdapter.getCreateOrderParam(),
                        CartHelper.FoodDetailType.COMBO_CHILD_DETAIL,
                        menuVO.getPerNum(), menuVO.getLimitNum(), SuitDetailActivity.REQUEST_CODE_SUIT_SUB_MENU, getAdapterPosition());
                return;
            }

            mAdapterClick.onAdapterClick(CLICK_TYPE_ADD_CART, mEditFoodNumberView, menuVO);
        }
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
        @BindView(R2.id.text_require_num)
        TextView mTextRequireNum;


        AdapterClick mAdapterClick;


        SpecificationHolder(Context context, View itemView, AdapterClick adapterClick) {
            super(context, itemView);
            mAdapterClick = adapterClick;
            ButterKnife.bind(this, itemView);
        }


        @Override
        protected void bindView(List source, Object bean) {
            if (bean instanceof MenuVO) {
                final MenuVO selectedMenu = (MenuVO) bean;
                if (getAdapterPosition() == source.size() - 1) {
                    mViewDivider.setVisibility(View.GONE);
                } else {
                    if (getAdapterPosition() < source.size() - 1) {
                        Object obj = source.get(getAdapterPosition() + 1);
                        if (obj instanceof MenuVO && ((MenuVO) obj).isSelectedMenuNewLine()) {
                            mViewDivider.setVisibility(View.VISIBLE);
                        } else {
                            mViewDivider.setVisibility(View.GONE);
                        }
                    } else {
                        mViewDivider.setVisibility(View.VISIBLE);
                    }
                }

                //如："大份，红烧，加香菜，加辣椒，加青菜"
                mTextMenuSpecification.setText(selectedMenu.getSpecification());
                if (selectedMenu.getExtraPrice() > 0) {
                    mTextMenuExtraPrice.setVisibility(View.VISIBLE);
                    mTextMenuExtraPrice.setText(getString(R.string.module_menu_extra_price,
                            FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), NumberUtils.trimPointIfZero(selectedMenu.getExtraPrice()))));
                } else {
                    mTextMenuExtraPrice.setVisibility(View.GONE);
                }

                if (selectedMenu.isRequired()) {
                    mTextRequireNum.setText((getString(R.string.module_menu_suit_menu_require_num_unit,
                            selectedMenu.getPerNum(), selectedMenu.getBuyAccount())));
                    mTextRequireNum.setVisibility(View.VISIBLE);
                    mEditFoodNumberView.setVisibility(View.GONE);
                } else {
                    mTextRequireNum.setVisibility(View.GONE);
                    mEditFoodNumberView.setVisibility(View.VISIBLE);
                    mEditFoodNumberView.setNumberText(selectedMenu.getTmpNum());

                    mEditFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
                        @Override
                        public void onClick(int which, double numberValue) {
                            mAdapterClick.onAdapterClick(which == FoodNumberTextView.CLICK_LEFT
                                    ? CLICK_TYPE_MINUS : CLICK_TYPE_PLUS, mEditFoodNumberView, selectedMenu);
                        }
                    });

                    mEditFoodNumberView.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
                        @Override
                        public void onClickEdit(double numberValue) {
                            selectedMenu.setSuitSubInputNum(numberValue);
                            mAdapterClick.onAdapterClickEdit(CLICK_TYPE_EDIT_DIALOG, mEditFoodNumberView, selectedMenu);
                        }
                    });

                }
            }
        }
    }
}
