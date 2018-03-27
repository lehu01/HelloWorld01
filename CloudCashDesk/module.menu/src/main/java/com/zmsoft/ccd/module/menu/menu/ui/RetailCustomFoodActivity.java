package com.zmsoft.ccd.module.menu.menu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.lib.base.activity.BaseActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：自定义商品
 * <br/>
 * Created by kumu on 2017/4/17.
 */
@Route(path = RouterPathConstant.RetailCustomFood.PATH)
public class RetailCustomFoodActivity extends BaseActivity {

    @BindView(R2.id.text_custom_cancel)
    TextView mTextCustomCancel;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.text_custom_food)
    TextView mTextCustomFood;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.container)
    FrameLayout mContainer;

    private RetailCustomFoodFragment mFoodFragment;

    private OrderParam mCreateOrderParam;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_menu_activity_retail_custom_food);

        mCreateOrderParam = (OrderParam) getIntent()
                .getSerializableExtra(RouterPathConstant.RetailCustomFood.EXTRA_CREATE_ORDER_PARAM);
        ItemVo mItemVO = (ItemVo) getIntent().getSerializableExtra(RouterPathConstant.RetailCustomFood.EXTRA_CUSTOM_FOOD_ITEMVO);


        if (savedInstanceState != null) {
            mFoodFragment = (RetailCustomFoodFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        }
        if (mFoodFragment == null) {
            mFoodFragment = new RetailCustomFoodFragment();
        }

        if (mItemVO != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RouterPathConstant.RetailCustomFood.EXTRA_CUSTOM_FOOD_ITEMVO, mItemVO);
            mFoodFragment.setArguments(bundle);
        }
        ActivityHelper.showFragment(getSupportFragmentManager(), mFoodFragment, R.id.container, false);
    }


    @OnClick({R2.id.text_custom_cancel, R2.id.text_custom_food})
    void processClick(View view) {
        int i = view.getId();
        if (i == R.id.text_custom_cancel) {
            finish();
        } else if (i == R.id.text_custom_food) {
            if (mCreateOrderParam == null) {
                ToastUtils.showShortToast(this, "mCreateOrderParam is null");
                return;
            }
            mFoodFragment.saveCustomMenuToCart(mCreateOrderParam);
        }
    }


    @Override
    public void onBackPressed() {
        if (mFoodFragment != null && mFoodFragment.dismissPickerView()) {
            return;
        }
        super.onBackPressed();
    }
}
