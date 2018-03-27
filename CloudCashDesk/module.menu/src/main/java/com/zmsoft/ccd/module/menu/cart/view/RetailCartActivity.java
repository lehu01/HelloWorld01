package com.zmsoft.ccd.module.menu.cart.view;

import android.content.Intent;
import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.presenter.cartlist.RetailCartPresenter;
import com.zmsoft.ccd.module.menu.cart.presenter.cartlist.dagger.DaggerRetailCartListComponent;
import com.zmsoft.ccd.module.menu.cart.presenter.cartlist.dagger.RetailCartListPresenterModule;
import com.zmsoft.ccd.module.menu.dagger.ComponentManager;
import com.zmsoft.ccd.module.menu.helper.CartHelper;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * 购物车
 *
 * @author DangGui
 * @create 2017/4/10.
 */
@Route(path = RouterPathConstant.RetailCart.PATH_CART)
public class RetailCartActivity extends ToolBarActivity {
    @Inject
    RetailCartPresenter mPresenter;

    @Autowired(name = RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM)
    OrderParam mCreateOrderParam;

    public static final int RESULT_REMARK_CODE = 3001; // 订单备注

    /**
     * 上级页面传参，标识上级页面的来源
     *
     * @see RouterPathConstant.Cart
     */
    @Autowired(name = RouterPathConstant.Cart.EXTRA_FROM)
    int mFrom;

    /**
     * 购物车数据
     */
    @Autowired(name = RouterPathConstant.Cart.EXTRA_DINNING_VO)
    DinningTableVo mDinningTableVo;

    private RetailCartFragment mCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        mCartFragment = (RetailCartFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        //获取传递过来的参数
        Serializable serializable = getIntent().getSerializableExtra(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM);
        if (null != serializable) {
            mCreateOrderParam = (OrderParam) serializable;
        }
        if (mCartFragment == null) {
            mCartFragment = RetailCartFragment.newInstance(mCreateOrderParam, mFrom, mDinningTableVo);
            ActivityHelper.showFragment(getSupportFragmentManager(), mCartFragment, R.id.content);
        }

        DaggerRetailCartListComponent.builder()
                .cartSourceComponent(ComponentManager.get().getCartSourceComponent())
                .retailCartListPresenterModule(new RetailCartListPresenterModule(mCartFragment))
                .build()
                .inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CartHelper.CartActivityRequestCode.CODE_TO_CART_DETAIL:
            case CartHelper.CartActivityRequestCode.CODE_TO_CART_CUSTOM_FOOD:
                if (resultCode == RESULT_OK) {
                    handleResult(data);
                }
                break;
            case RESULT_REMARK_CODE:
                mCartFragment.refreshCart();
                break;
        }
    }

    private void handleResult(Intent data) {
        if (null != data) {
            Serializable serializable = data.getSerializableExtra(CartHelper.CartActivityRequestCode.ACTIVITY_RESULT_EXTRA_DINVO);
            if (null != serializable) {
                //如果是从购物车详情返回的数据，那么拿到DinningTableVo并刷新购物车列表界面
                DinningTableVo dinningTableVo = (DinningTableVo) serializable;
                if (null != mCartFragment) {
                    mCartFragment.showCart(dinningTableVo);
                }
            } else {
                if (null != mCartFragment) {
                    mCartFragment.refreshCart();
                }
            }
        } else {
            if (null != mCartFragment) {
                mCartFragment.refreshCart();
            }
        }
    }

    @Override
    protected void handleBack() {
        super.handleBack();
        if (mFrom == RouterPathConstant.Cart.EXTRA_FROM_HANG_UP_ORDER) {
            setResult(RESULT_OK);
        }
    }
}
