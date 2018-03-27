package com.zmsoft.ccd.module.menu.cart.view;

import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.presenter.cartdetail.CartDetailPresenter;
import com.zmsoft.ccd.module.menu.cart.presenter.cartdetail.dagger.CartDetailPresenterModule;
import com.zmsoft.ccd.module.menu.cart.presenter.cartdetail.dagger.DaggerCartDetailComponent;
import com.zmsoft.ccd.module.menu.dagger.ComponentManager;
import com.zmsoft.ccd.module.menu.menu.bean.ParamSuitSubMenu;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitGroupVO;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * 购物车菜品详情
 *
 * @author DangGui
 * @create 2017/4/18.
 */
@Route(path = RouterPathConstant.PATH_CART_DETAIL)
public class CartDetailActivity extends ToolBarActivity {
    /**
     * 商品详情页分类
     *
     * @see com.zmsoft.ccd.module.menu.helper.CartHelper.FoodDetailType
     */
    public static final String EXTRA_MENU_ID = "extra_menu_id";
    public static final String EXTRA_DETAIL_TYPE = "extra_detail_type";
    public static final String EXTRA_DETAIL_ITEMVO = "extra_detail_itemvo";
    public static final String EXTRA_DETAIL_BASEVO = "extra_detail_basevo";
    public static final String EXTRA_DETAIL_KIND = "extra_detail_kind";

    /**
     * 菜肴ID
     */
    @Autowired(name = EXTRA_MENU_ID)
    String mMenuId;

    @Autowired(name = EXTRA_DETAIL_TYPE)
    int mDetailType;

    @Autowired(name = EXTRA_DETAIL_ITEMVO)
    ItemVo mItemVo;

    /**
     * 菜详情 基础类
     */
    private BaseMenuVo mBaseMenuVo;
    /**
     * 桌位
     */
    @Autowired(name = RouterPathConstant.CartDetail.EXTRA_SEATCODE)
    String mSeatCode;

    /**
     * 订单id
     */
    @Autowired(name = RouterPathConstant.CartDetail.EXTRA_ORDERID)
    String mOrderId;

    /**
     * 套餐子菜详情 掌柜上设置的默认规格ID
     */
    @Autowired(name = RouterPathConstant.CartDetail.EXTRA_SPECID)
    String mSpecId;

    /**
     * 套餐必选子菜详情 已点份数
     */
    @Autowired(name = RouterPathConstant.CartDetail.EXTRA_FOOD_NUM)
    int mFoodNum;

    /**
     * 套餐子菜，菜本身数量限制
     */
    @Autowired(name = RouterPathConstant.CartDetail.EXTRA_FOOD_LIMITNUM)
    int mLimitNum;

    /**
     * 菜名称
     */
    @Autowired(name = RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU_NAME)
    String mMenuName;

    @Inject
    CartDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        CartDetailFragment cartDetailFragment = (CartDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        //获取传递过来的ItemVo
        Serializable serializable = getIntent().getSerializableExtra(EXTRA_DETAIL_ITEMVO);
        if (null != serializable) {
            mItemVo = (ItemVo) serializable;
        }
        //获取传递过来的BaseMenuVo
        Serializable baseMenuVoSerializable = getIntent().getSerializableExtra(EXTRA_DETAIL_BASEVO);
        if (null != baseMenuVoSerializable) {
            mBaseMenuVo = (BaseMenuVo) baseMenuVoSerializable;
        }
        //套餐子菜详情，菜所在分组数量限制所需属性
        SuitGroupVO suitGroupVO = getIntent().getParcelableExtra(RouterPathConstant.CartDetail.EXTRA_SUITGROUPVO);
        //套餐必选子菜详情
        ParamSuitSubMenu paramSuitSubMenu = null;
        //获取传递过来的ParamSuitSubMenu
        Serializable paramSuitSerializable = getIntent().getSerializableExtra(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU);
        if (null != paramSuitSerializable) {
            paramSuitSubMenu = (ParamSuitSubMenu) paramSuitSerializable;
        }
        int suitSubMenuPosition = getIntent().getIntExtra(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU_POSITION, -1);

        if (cartDetailFragment == null) {
            cartDetailFragment = CartDetailFragment.newInstance(mMenuId, mDetailType, mItemVo, mSeatCode
                    , mOrderId, mBaseMenuVo, mSpecId, suitGroupVO, mFoodNum, mLimitNum, paramSuitSubMenu
                    , mMenuName, suitSubMenuPosition);
            ActivityHelper.showFragment(getSupportFragmentManager(), cartDetailFragment, R.id.content);
        }

        DaggerCartDetailComponent.builder()
                .cartSourceComponent(ComponentManager.get().getCartSourceComponent())
                .cartDetailPresenterModule(new CartDetailPresenterModule(cartDetailFragment))
                .build()
                .inject(this);
    }
}
