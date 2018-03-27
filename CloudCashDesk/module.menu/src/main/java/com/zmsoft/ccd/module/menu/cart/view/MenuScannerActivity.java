package com.zmsoft.ccd.module.menu.cart.view;

import android.os.Bundle;
import android.text.TextUtils;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.bean.scan.ScanMenu;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;
import com.zmsoft.ccd.module.menu.cart.presenter.scan.ScanContract;
import com.zmsoft.ccd.module.menu.cart.presenter.scan.ScanPresenter;
import com.zmsoft.ccd.module.menu.cart.presenter.scan.dagger.DaggerMenuScanComponent;
import com.zmsoft.ccd.module.menu.cart.presenter.scan.dagger.MenuScanPresenterModule;
import com.zmsoft.ccd.module.menu.dagger.ComponentManager;
import com.zmsoft.ccd.module.menu.events.model.ModifyCartParam;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.scan.lib.scan.BaseQrScanActivity;

import javax.inject.Inject;

/**
 * 菜单页 扫码
 *
 * @author DangGui
 * @create 2017/4/26.
 */
@Route(path = RouterPathConstant.MenuScan.PATH_MENU_SCAN)
public class MenuScannerActivity extends BaseQrScanActivity implements ScanContract.View {
    @Inject
    ScanPresenter mPresenter;

    @Autowired(name = RouterPathConstant.SuitDetail.PARAM_CREATE_ORDER_PARAM)
    OrderParam mCreateOrderParam;


    /**
     * 桌位
     */
    //@Autowired(name = RouterPathConstant.CartDetail.EXTRA_SEATCODE)
    //String mSeatCode;

    /**
     * 订单id
     */
    //@Autowired(name = RouterPathConstant.CartDetail.EXTRA_ORDERID)
    //String mOrderId;

    private String mMenuId;
    private ScanMenu mScanMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(DEFAULT_LAYOUT);
        MRouter.getInstance().inject(this);
    }

    @Override
    protected void qrScanResult(Object result) {
        if (result instanceof ScanMenu) {
            mScanMenu = (ScanMenu) result;
        }

        if (mScanMenu == null) {
            mCodeScanView.restartScan();
            showToast(getString(R.string.module_menu_toast_please_scan_menu));
            return;
        }

        String menuId = mScanMenu.getMenuId();
        if (!TextUtils.isEmpty(menuId)) {
            mMenuId = menuId;
            queryMenuDetail(menuId);
        } else {
            mCodeScanView.restartScan();
            showToast(getString(R.string.module_menu_toast_please_scan_menu));
        }
    }

    @Override
    protected void init() {
        DaggerMenuScanComponent.builder()
                .menuSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .menuScanPresenterModule(new MenuScanPresenterModule(this))
                .build()
                .inject(this);
        initHitTxt();
    }

    @Override
    protected int bizType() {
        return TYPE_SCAN_MENU;
    }

    private void initHitTxt() {
        mTextScanPrompt.setText(getString(R.string.module_menu_qr_find_instance_prompt));
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setPresenter(ScanContract.Presenter presenter) {
        this.mPresenter = (ScanPresenter) presenter;
    }

    private void queryMenuDetail(String menuId) {
        mPresenter.queryCartFoodDetail(menuId);
    }

    @Override
    public void showMenuDetail(BaseMenuVo baseMenuVo) {
        if (null != baseMenuVo) {
            Object realObj;
            if (baseMenuVo.getIsSetMenu() == CartHelper.BaseMenuFoodType.COMBO) {
                // 套餐详情
                goToComboDetail(mMenuId, baseMenuVo);
                mCodeScanView.restartScan();
                mCodeScanView.pauseScan(1500);
            } else if (baseMenuVo.getIsSetMenu() == CartHelper.BaseMenuFoodType.NORMAL) {
                //普通菜详情
                realObj = mPresenter.getRealMenuVo(baseMenuVo, NormalMenuVo.class);
                if (null != realObj && realObj instanceof NormalMenuVo) {
                    NormalMenuVo normalMenuVo = (NormalMenuVo) realObj;
                    if (mPresenter.isOffShelves(normalMenuVo)) {
                        showToast(getString(R.string.module_menu_join_cart_fail_offshelves));
                        mCodeScanView.restartScan();
                        mCodeScanView.pauseScan(1500);
                        return;
                    }
                    if (mPresenter.isSoldOut(normalMenuVo)) {
                        showToast(getString(R.string.module_menu_join_cart_fail_soldout));
                        mCodeScanView.restartScan();
                        mCodeScanView.pauseScan(1500);
                        return;
                    }

                    if (mPresenter.isNormalMenuHasSpecOrMake(normalMenuVo)) {
                        //有做法或者规格或者双单位，直接进入普通菜详情
                        goToNormalDetail(baseMenuVo);
                        mCodeScanView.restartScan();
                        mCodeScanView.pauseScan(1500);
                    } else {
                        // 普通菜无规格或做法，直接加入购物车
                        mPresenter.joinCart(mCreateOrderParam.getSeatCode(), mCreateOrderParam.getOrderId(), mMenuId, normalMenuVo);
                    }
                }
            }
        } else {
            mCodeScanView.restartScan();
        }
    }

    @Override
    public void showJoinCartResult(NormalMenuVo normalMenuVo, DinningTableVo dinningTableVo) {
        if (null != dinningTableVo) {
            String menuName = "";
            if (null != normalMenuVo && null != normalMenuVo.getMenu() && !TextUtils.isEmpty(normalMenuVo.getMenu().getName())) {
                menuName = normalMenuVo.getMenu().getName();
            }
            showToast(String.format(getString(R.string.module_menu_join_cart_success)
                    , menuName));
            //购物车修改，通知菜单列表
            RouterBaseEvent.CommonEvent modifyCartEvent = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART;
            ModifyCartParam modifyCartParam = new ModifyCartParam(dinningTableVo, null);
            modifyCartEvent.setObject(modifyCartParam);
            EventBusHelper.post(modifyCartEvent);
        }
        mCodeScanView.restartScan();
        mCodeScanView.pauseScan(1500);
    }

    @Override
    public void loadDataError(String errorMessage) {
        mCodeScanView.restartScan();
        showToast(errorMessage);
    }

    /**
     * 跳转到普通菜详情页
     */
    private void goToNormalDetail(BaseMenuVo baseMenuVo) {
        MRouter.getInstance()
                .build(RouterPathConstant.PATH_CART_DETAIL)
                .putString(CartDetailActivity.EXTRA_MENU_ID, mMenuId)
                .putInt(CartDetailActivity.EXTRA_DETAIL_TYPE, CartHelper.FoodDetailType.FOOD_DETAIL)
                .putSerializable(CartDetailActivity.EXTRA_DETAIL_BASEVO, baseMenuVo)
                .putString(RouterPathConstant.CartDetail.EXTRA_SEATCODE
                        , mCreateOrderParam.getSeatCode())
                .putString(RouterPathConstant.CartDetail.EXTRA_ORDERID
                        , mCreateOrderParam.getOrderId())
                .putInt(CartDetailActivity.EXTRA_DETAIL_KIND, CartHelper.BaseMenuFoodType.NORMAL)
                .navigation(this);
    }

    /**
     * 跳转到套餐详情页
     */
    private void goToComboDetail(String menuId, BaseMenuVo baseMenuVo) {
        MRouter.getInstance()
                .build(RouterPathConstant.SuitDetail.PATH)
                .putSerializable(RouterPathConstant.SuitDetail.PARAM_SUIT_BASE_MENU_VO, baseMenuVo)
                .putSerializable(RouterPathConstant.SuitDetail.PARAM_CREATE_ORDER_PARAM, mCreateOrderParam)
                .putString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_ID, menuId)
                .navigation(this);
    }

}
