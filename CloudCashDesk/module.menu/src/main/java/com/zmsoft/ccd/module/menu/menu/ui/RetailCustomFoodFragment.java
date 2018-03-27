package com.zmsoft.ccd.module.menu.menu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.KeyboardUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.lib.widget.pickerview.OptionsPickerView;
import com.zmsoft.ccd.lib.widget.pickerview.PickerViewOptionsHelper;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.Item;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.dagger.ComponentManager;
import com.zmsoft.ccd.module.menu.events.BaseEvents;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.MenuCategory;
import com.zmsoft.ccd.module.menu.menu.bean.MenuKind;
import com.zmsoft.ccd.module.menu.menu.bean.vo.CartItemVO;
import com.zmsoft.ccd.module.menu.menu.presenter.RetailCustomFoodContract;
import com.zmsoft.ccd.module.menu.menu.presenter.RetailCustomFoodPresenter;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.DaggerRetailCustomFoodComponent;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.RetailCustomFoodPresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description：自定义商品
 * <br/>
 * Created by kumu on 2017/4/17.
 */

public class RetailCustomFoodFragment extends BaseFragment implements RetailCustomFoodContract.View {

    @Inject
    RetailCustomFoodPresenter presenter;

    @BindView(R2.id.edit_food_name)
    EditText mEditFoodName;
    @BindView(R2.id.text_category)
    TextView mTextCategory;
    @BindView(R2.id.edit_food_order_count)
    FoodNumberTextView mEditFoodNum;
    @BindView(R2.id.edit_food_price)
    EditFoodNumberView mEditFoodPrice;
    @BindView(R2.id.switch_can_discount)
    SwitchCompat mSwitchCanDiscount;

    private ArrayList<MenuCategory> mCategoryList;
    private MenuCategory mSelectedCategory;
    private OptionsPickerView mOptionsPickerView;

    private static int[] mTypes = new int[]{CartHelper.MenuListFoodKind.FOOD_KIND_NORMAL};

    private ItemVo mItemVO;

    private double defaultFoodNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.module_menu_fragment_retail_custom_food;
    }

    @Override
    protected void initParameters() {
        super.initParameters();
        DaggerRetailCustomFoodComponent.builder()
                .menuSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .retailCustomFoodPresenterModule(new RetailCustomFoodPresenterModule(this))
                .build()
                .inject(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mItemVO = (ItemVo) bundle.getSerializable(RouterPathConstant.CustomFood.EXTRA_CUSTOM_FOOD_ITEMVO);
            if (mItemVO != null) {
                mSelectedCategory = new MenuCategory(mItemVO.getKindMenuId(), mItemVO.getKindMenuName());
            }
        }
    }

    private void fillData() {
        if (mItemVO != null) {
            mEditFoodName.setText(mItemVO.getName());
            mTextCategory.setText(mItemVO.getKindMenuName());
            mEditFoodNum.setNumberText(mItemVO.getNum());

            mEditFoodPrice.setNumberText(mItemVO.getPrice());
            mSwitchCanDiscount.setChecked(mItemVO.getIsRatio() == 1);

        }
    }

    private void setNumberViewGravity(EditFoodNumberView numberView) {
        if (numberView != null && numberView.getEditText() != null) {
            numberView.getEditText().setGravity(GravityCompat.END | Gravity.CENTER_VERTICAL);
        }
    }

    private void setNumberViewGravity_EditText(FoodNumberTextView numberView) {
        if (numberView != null && numberView.getEditText() != null) {
            numberView.getEditText().setGravity(GravityCompat.END | Gravity.CENTER_VERTICAL);
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        if (mItemVO != null) {
            fillData();
        } else {
            mEditFoodNum.setNumberText(defaultFoodNum);
        }

        setNumberViewGravity_EditText(mEditFoodNum);
        setNumberViewGravity(mEditFoodPrice);

        view.findViewById(R.id.layout_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCategoryList == null || mCategoryList.isEmpty()) {
                    showLoading(true);
                    presenter.loadMenuCategories(UserHelper.getEntityId(), mTypes);
                }
            }
        });

        //菜的分类
        mTextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KeyboardUtils.hideSoftInput(getActivity());

                if (mCategoryList == null || mCategoryList.isEmpty()) {
                    showLoading(true);
                    presenter.loadMenuCategories(UserHelper.getEntityId(), mTypes);
                    return;
                }

                if (mOptionsPickerView == null) {
                    mOptionsPickerView = PickerViewOptionsHelper.createDefaultPrickerView(getActivity(), R.string.module_menu_menu_category);
                    mOptionsPickerView.setOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            //返回的分别是三个级别的选中位置
                            if (options1 > mCategoryList.size() - 1) {
                                return;
                            }
                            mSelectedCategory = mCategoryList.get(options1);
                            mTextCategory.setText(mSelectedCategory.getName());
                        }
                    });
                }
                if (mSelectedCategory != null) {
                    int index = mCategoryList.indexOf(mSelectedCategory);
                    if (index != -1) {
                        mOptionsPickerView.setSelectOptions(index);
                    }
                }
                mOptionsPickerView.setPicker(mCategoryList);
                mOptionsPickerView.show();
            }
        });


        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mEditFoodPrice.getEditText().setLayoutParams(ll);
        LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        CustomViewUtil.initEditViewFocousAll(mEditFoodPrice.getEditText());
        mEditFoodNum.getEditText().setLayoutParams(ll2);
        mEditFoodNum.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
            @Override
            public void onClick(int which, double numberValue) {
                switch (which) {
                    case FoodNumberTextView.CLICK_LEFT:
                        checkAndSetFoodNum(mEditFoodNum.getNumber() - 1);
                        break;
                    case FoodNumberTextView.CLICK_RIGHT:
                        checkAndSetFoodNum(mEditFoodNum.getNumber() + 1);
                        break;
                }
            }
        });

        mEditFoodNum.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
            @Override
            public void onClickEdit(double numberValue) {
                showEditValueDialog(mItemVO,
                        1,
                        numberValue,
                        getString(R.string.module_menu_custom_food_title),
                        getString(R.string.module_menu_food_unit),
                        CartHelper.DialogDateFrom.RETAIL_CUSTOM_FOOD_VIEW);
            }
        });
    }

    private void checkAndSetFoodNum(double num) {
        if (num <= 0) {
            ToastUtils.showShortToast(getContext(), R.string.module_menu_retail_check_input_count_must_than_zero);
            mEditFoodNum.requestFocus();
            num = defaultFoodNum;
        }
        mEditFoodNum.setNumberText(num);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        String entityId = UserHelper.getEntityId();
        //加载菜类列表
        presenter.loadMenuCategories(entityId, mTypes);
        //加载单位
        //presenter.loadMenuUnits(entityId);
        //加载传送方案
        //presenter.loadPassThrough(entityId);
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void unBindPresenterFromView() {
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }

    @Override
    public void loadMenuCategoriesSuccess(ArrayList<MenuCategory> list) {
        hideLoading();
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mCategoryList = filterCategories(list);
        if (mItemVO == null) {
            mSelectedCategory = mCategoryList.get(0);
            mTextCategory.setText(mSelectedCategory.getName());
        }
    }

    /**
     * 只展示子级分类（不包括套餐）
     */
    private ArrayList<MenuCategory> filterCategories(ArrayList<MenuCategory> list) {
        ArrayList<MenuCategory> filtered = new ArrayList<>();
        for (MenuCategory category : list) {
            if (!isUseIdAsParentId(category.getId(), list)) {
                filtered.add(category);
            }
        }
        return filtered;
    }

    /**
     * 有没有把该分类id作为某个分类的父分类
     *
     * @return boolean
     */
    private boolean isUseIdAsParentId(String id, ArrayList<MenuCategory> list) {
        for (MenuCategory cate : list) {
            if (id.equals(cate.getParentId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadMenuCategoriesFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
    }


    @Override
    public void saveCustomMenuToCartSuccess(DinningTableVo dinningTableVo) {
        hideLoading();
        RouterBaseEvent.CommonEvent modifyCartEvent = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART;
        modifyCartEvent.setObject(dinningTableVo);
        EventBusHelper.post(modifyCartEvent);
        Intent intent = new Intent();
        intent.putExtra(CartHelper.CartActivityRequestCode.ACTIVITY_RESULT_EXTRA_DINVO, dinningTableVo);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void saveCustomMenuToCartFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean checkParams() {
        if (mEditFoodName.getText().toString().trim().length() == 0) {
            ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_food_name);
            mEditFoodName.requestFocus();
            return false;
        }

        if (mSelectedCategory == null) {
            ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_category_name);
            return false;
        }
        if (!mEditFoodNum.hasInput()) {
            ToastUtils.showShortToast(getContext(), R.string.module_menu_retail_check_input_count);
            mEditFoodNum.requestFocus();
            return false;
        }
        if (mEditFoodNum.getNumber() <= 0) {
            ToastUtils.showShortToast(getContext(), R.string.module_menu_retail_check_input_count_must_than_zero);
            mEditFoodNum.requestFocus();
            return false;
        }

        if (!mEditFoodPrice.hasInput()) {
            ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_unit_price);
            mEditFoodPrice.requestFocus();
            return false;
        }
        if (mEditFoodPrice.getNumber() <= 0) {
            ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_unit_price_must_than_zero);
            mEditFoodPrice.requestFocus();
            return false;
        }

        return true;
    }

    public void saveCustomMenuToCart(OrderParam createOrderParam) {
        if (!checkParams()) {
            return;
        }
        List<Item> list = new ArrayList<>();
        Item item = new Item();
        if (mItemVO != null) {
            item.setMenuId(mItemVO.getMenuId());
        }
        //这个不用传递
        item.setLabels(null);
        //自定义菜
        item.setKind(MenuKind.CUSTOM);
        //商品名称
        item.setName(mEditFoodName.getText().toString());
        //分类id
        item.setKindMenuId(mSelectedCategory.getId());
        //分类name
        item.setKindMenuName(mSelectedCategory.getName());
        //点菜数量
        item.setNum(mEditFoodNum.getNumber());
        //单价
        item.setPrice(mEditFoodPrice.getNumber());
        //originalPrice和price保持一致
        item.setOriginalPrice(mEditFoodPrice.getNumber());
        //总价
        item.setFee(mEditFoodNum.getNumber() * mEditFoodPrice.getNumber());

        //设置index
        if (mItemVO != null) {
            item.setIndex(mItemVO.getIndex());
        } else {
            item.setIndex(CartItemVO.createIndex());
        }

        //doubleUnitStatus
//        if (mItemVO != null && mItemVO.isTwoAccount()) {
//            item.setDoubleUnitStatus(mItemVO.getAccountNum() == (mEditFoodAccountNum.getNumber()) ?
//                    CartHelper.DoubleUnitStatus.FALSE : CartHelper.DoubleUnitStatus.TRUE);
//        }


        //数据埋点
        item.setSource(CartHelper.CartSource.CUSTOM_FOOD);

        //结账单位与点菜单位相同 // TODO: 2017/10/25 accountNum如何设置
        item.setAccountNum(mEditFoodNum.getNumber());
        item.setUnit(getString(R.string.module_menu_retail_item));
        //if (mSwitchSameUnit.isChecked()) {
        //    item.setAccountNum(mEditFoodNum.getNumber());
        //    item.setAccountUnit(null);
        //} else {
        //    item.setAccountNum(mEditFoodAccountNum.getNumber());
        //    item.setAccountUnit(mTextAccountUnit.getText().toString());
        //}

        //可否打折
        item.setIsRatio((short) (mSwitchCanDiscount.isChecked() ? 1 : 0));
        //折扣率
        item.setRatio(mItemVO != null ? mItemVO.getRatio() : 100D);

        list.add(item);
        showLoading(false);
        presenter.saveCustomMenuToCart(UserHelper.getEntityId(),
                UserHelper.getMemberId(), UserHelper.getMemberId(), createOrderParam.getSeatCode(),
                createOrderParam.getNumber(), JsonMapper.toJsonString(list));
    }

    public boolean dismissPickerView() {
        if (mOptionsPickerView != null && mOptionsPickerView.isShowing()) {
            mOptionsPickerView.dismiss();
            return true;
        }
        return false;
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
                checkAndSetFoodNum(number);
            }
        });
    }
}
