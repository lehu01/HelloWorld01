package com.zmsoft.ccd.module.menu.menu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dfire.mobile.util.JsonMapper;
import com.google.android.flexbox.FlexboxLayout;
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
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexItem;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexboxLayout;
import com.zmsoft.ccd.lib.widget.pickerview.OptionsPickerView;
import com.zmsoft.ccd.lib.widget.pickerview.PickerViewOptionsHelper;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.Item;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.dagger.ComponentManager;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.MenuCategory;
import com.zmsoft.ccd.module.menu.menu.bean.MenuKind;
import com.zmsoft.ccd.module.menu.menu.bean.MenuUnit;
import com.zmsoft.ccd.module.menu.menu.bean.PassThrough;
import com.zmsoft.ccd.module.menu.menu.bean.vo.CartItemVO;
import com.zmsoft.ccd.module.menu.menu.presenter.CustomFoodContract;
import com.zmsoft.ccd.module.menu.menu.presenter.CustomFoodPresenter;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.CustomFoodPresenterModule;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.DaggerCustomFoodComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description：自定义商品
 * <br/>
 * Created by kumu on 2017/4/17.
 */

public class CustomFoodFragment extends BaseFragment implements CustomFoodContract.View {


    @Inject
    CustomFoodPresenter presenter;

    @BindView(R2.id.edit_food_name)
    EditText mEditFoodName;
    @BindView(R2.id.text_category)
    TextView mTextCategory;
    @BindView(R2.id.edit_food_order_count)
    EditFoodNumberView mEditFoodNum;
    @BindView(R2.id.text_unit)
    EditText mTextUnit;
    @BindView(R2.id.switch_same_unit)
    SwitchCompat mSwitchSameUnit;
    @BindView(R2.id.edit_food_account_count)
    EditFoodNumberView mEditFoodAccountNum;
    @BindView(R2.id.layout_account_num)
    RelativeLayout mLayoutAccountNum;
    @BindView(R2.id.text_account_unit)
    EditText mTextAccountUnit;
    @BindView(R2.id.layout_account_unit)
    RelativeLayout mLayoutAccountUnit;
    @BindView(R2.id.edit_food_price)
    EditFoodNumberView mEditFoodPrice;
    @BindView(R2.id.switch_can_discount)
    SwitchCompat mSwitchCanDiscount;
    @BindView(R2.id.text_label_note)
    TextView mTextLabelNote;
    @BindView(R2.id.edit_food_note)
    EditText mEditFoodNote;
    @BindView(R2.id.layout_transfers)
    View viewTransfers;
    @BindView(R2.id.text_label_pass_through)
    TextView mTextLabelPassThrough;
    @BindView(R2.id.flex_box_pass_through)
    CustomFlexboxLayout mFlexBoxPassThrough;

    @BindView(R2.id.btn_transfer_retry)
    Button mTextTransferRetry;


    private ArrayList<MenuCategory> mCategoryList;
    private MenuCategory mSelectedCategory;
    private OptionsPickerView mOptionsPickerView;


    private static int[] mTypes = new int[]{CartHelper.MenuListFoodKind.FOOD_KIND_NORMAL};

    private ItemVo mItemVO;

    @Override
    protected int getLayoutId() {
        return R.layout.module_menu_fragment_custom_food;
    }

    @Override
    protected void initParameters() {
        super.initParameters();
        DaggerCustomFoodComponent.builder()
                .menuSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .customFoodPresenterModule(new CustomFoodPresenterModule(this))
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
            mTextUnit.setText(mItemVO.getUnit());

            //结账单位和点菜单位相同
            if (!mItemVO.isTwoAccount()) {
                mSwitchSameUnit.setChecked(true);
            } else {//双单位
                mSwitchSameUnit.setChecked(false);
                mEditFoodAccountNum.setNumberText(mItemVO.getAccountNum());
                mTextAccountUnit.setText(mItemVO.getAccountUnit());
                mLayoutAccountNum.setVisibility(View.VISIBLE);
                mLayoutAccountUnit.setVisibility(View.VISIBLE);
            }

            mEditFoodPrice.setNumberText(mItemVO.getPrice());
            mSwitchCanDiscount.setChecked(mItemVO.getIsRatio() == 1);
            if (!TextUtils.isEmpty(mItemVO.getMemo())) {
                mEditFoodNote.setText(mItemVO.getMemo());
                mEditFoodNote.setSelection(mItemVO.getMemo().length());
            }

        }
    }

    private void setNumberViewGravity(EditFoodNumberView numberView) {
        if (numberView != null && numberView.getEditText() != null) {
            numberView.getEditText().setGravity(GravityCompat.END | Gravity.CENTER_VERTICAL);
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        if (mItemVO != null) {
            fillData();
        }

        setNumberViewGravity(mEditFoodNum);
        setNumberViewGravity(mEditFoodAccountNum);
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

        mSwitchSameUnit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mLayoutAccountNum.setVisibility(isChecked ? View.GONE : View.VISIBLE);
                mLayoutAccountUnit.setVisibility(isChecked ? View.GONE : View.VISIBLE);
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

        if (mTextTransferRetry != null) {
            mTextTransferRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading(true);
                    presenter.loadPassThrough(UserHelper.getEntityId());
                }
            });
        }

        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mEditFoodPrice.getEditText().setLayoutParams(ll);
        CustomViewUtil.initEditViewFocousAll(mEditFoodPrice.getEditText());
        LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mEditFoodNum.getEditText().setLayoutParams(ll2);
        CustomViewUtil.initEditViewFocousAll(mEditFoodNum.getEditText());
        LinearLayout.LayoutParams ll3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mEditFoodAccountNum.getEditText().setLayoutParams(ll3);
        CustomViewUtil.initEditViewFocousAll(mEditFoodAccountNum.getEditText());
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
        presenter.loadPassThrough(entityId);
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
    public void loadMenuUnitsSuccess(List<MenuUnit> list) {
    }

    @Override
    public void loadMenuUnitsFailed(String errorCode, String errorMsg) {
        ToastUtils.showShortToast(getContext(), errorCode);
    }

    @Override
    public void loadPassThroughSuccess(List<PassThrough> list) {
        hideLoading();
        if (mTextTransferRetry != null) {
            mTextTransferRetry.setVisibility(View.GONE);
        }

        if (list == null || list.isEmpty()) {
            viewTransfers.setVisibility(View.GONE);
            return;
        }

        List<CustomFlexItem> items = new ArrayList<>();
        for (PassThrough passThrough : list) {
            boolean check = false;
            if (mItemVO != null) {
                List<String> ids = mItemVO.getTransferPlanIds();
                if (ids != null && !ids.isEmpty()) {
                    check = ids.contains(passThrough.getId());
                }
            }
            items.add(new CustomFlexItem(passThrough.getId(), passThrough.getName(), check));
        }
        mFlexBoxPassThrough.setFlexWrap(FlexboxLayout.FLEX_WRAP_WRAP);
        mFlexBoxPassThrough.initSource(items, true);

    }

    @Override
    public void loadPassThroughFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
        if (mTextTransferRetry != null) {
            mTextTransferRetry.setVisibility(View.VISIBLE);
        }
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
            ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_count);
            mEditFoodNum.requestFocus();
            return false;
        }
        if (mEditFoodNum.getNumber() <= 0) {
            ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_count_must_than_zero);
            mEditFoodNum.requestFocus();
            return false;
        }

        if (mTextUnit.getText().toString().trim().length() == 0) {
            ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_unit);
            mTextUnit.requestFocus();
            return false;
        }

        if (!mSwitchSameUnit.isChecked()) {

            if (!mEditFoodAccountNum.hasInput()) {
                ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_account_num);
                return false;
            }

            if (mEditFoodAccountNum.getNumber() <= 0) {
                ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_account_num_than_zero);
                return false;
            }

            if (mTextAccountUnit.getText().toString().trim().length() == 0) {
                ToastUtils.showShortToast(getContext(), R.string.module_menu_check_input_account_unit);
                return false;
            }

            if (mTextAccountUnit.getText().toString().equals(mTextUnit.getText().toString())) {
                ToastUtils.showShortToast(getContext(), R.string.module_menu_check_unit_account_unit_cannot_same);
                return false;
            }
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
        if (!mSwitchSameUnit.isChecked()) {
            item.setFee(mEditFoodAccountNum.getNumber() * mEditFoodPrice.getNumber());
        } else {
            item.setFee(mEditFoodNum.getNumber() * mEditFoodPrice.getNumber());
        }
        //点菜单位
        item.setUnit(mTextUnit.getText().toString());
        //设置index
        if (mItemVO != null) {
            item.setIndex(mItemVO.getIndex());
        } else {
            item.setIndex(CartItemVO.createIndex());
        }

        //doubleUnitStatus
        if (mItemVO != null && mItemVO.isTwoAccount()) {
            item.setDoubleUnitStatus(mItemVO.getAccountNum() == (mEditFoodAccountNum.getNumber()) ?
                    CartHelper.DoubleUnitStatus.FALSE : CartHelper.DoubleUnitStatus.TRUE);
        }


        //数据埋点
        item.setSource(CartHelper.CartSource.CUSTOM_FOOD);

        /*
         * （1）单单位菜， num 和 accountNul 是一样的。 unit 是份， accountUnit 则是null ，
         * （2） 双单位菜， num 和accountNum 可以是不一样的， accountUnit 不为null。
         */

        //结账单位与点菜单位相同
        if (mSwitchSameUnit.isChecked()) {
            item.setAccountNum(mEditFoodNum.getNumber());
            //item.setAccountUnit(mTextUnit.getText().toString());
            item.setAccountUnit(null);
        } else {
            item.setAccountNum(mEditFoodAccountNum.getNumber());
            item.setAccountUnit(mTextAccountUnit.getText().toString());
        }
        //可否打折
        item.setIsRatio((short) (mSwitchCanDiscount.isChecked() ? 1 : 0));
        //折扣率
        item.setRatio(mItemVO != null ? mItemVO.getRatio() : 100D);
        //备注
        item.setMemo(TextUtils.isEmpty(mEditFoodNote.getText().toString().trim())
                ? null : mEditFoodNote.getText().toString());

        List<CustomFlexItem> flexItems = mFlexBoxPassThrough.getCheckedItemList();
        if (flexItems != null && flexItems.size() > 0) {
            List<String> transferIds = new ArrayList<>();
            transferIds.add(flexItems.get(0).getId());
            //传送方案
            item.setTransferPlanIds(transferIds);
        }

        //也有可能进入修改界面，该自定义菜是有传送方案的，但是加载服务器上的传送方案列表失败，这个时候提交的话该自定义菜就变成无传送方案的了
        if (mItemVO != null && (item.getTransferPlanIds() == null || item.getTransferPlanIds().isEmpty())) {
            item.setTransferPlanIds(mItemVO.getTransferPlanIds());
        }

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
}
