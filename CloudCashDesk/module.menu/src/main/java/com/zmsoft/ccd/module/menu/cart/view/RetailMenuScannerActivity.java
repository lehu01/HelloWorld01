package com.zmsoft.ccd.module.menu.cart.view;

import android.os.Bundle;
import android.text.TextUtils;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.scan.ScanMenu;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.Menu;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;
import com.zmsoft.ccd.module.menu.cart.presenter.scan.RetailScanContract;
import com.zmsoft.ccd.module.menu.cart.presenter.scan.RetailScanPresenter;
import com.zmsoft.ccd.module.menu.cart.presenter.scan.dagger.DaggerRetailMenuScanComponent;
import com.zmsoft.ccd.module.menu.cart.presenter.scan.dagger.RetailMenuScanPresenterModule;
import com.zmsoft.ccd.module.menu.dagger.ComponentManager;
import com.zmsoft.ccd.module.menu.events.model.ModifyCartParam;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.BoMenu;
import com.zmsoft.ccd.module.menu.menu.bean.MenuRetailQuery;
import com.zmsoft.ccd.module.menu.menu.ui.RetailScannerDialog;
import com.zmsoft.scan.lib.scan.BaseQrScanActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 菜单页 扫码
 *
 * @author DangGui
 * @create 2017/4/26.
 */
@Route(path = RouterPathConstant.RetailMenuScan.PATH_MENU_SCAN)
public class RetailMenuScannerActivity extends BaseQrScanActivity implements RetailScanContract.View {

    private RetailScannerDialog mRetailScannerDialog;

    @Inject
    RetailScanPresenter mPresenter;
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

    private String mMenuId;
    private ScanMenu mScanMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(DEFAULT_LAYOUT);
    }

    @Override
    protected void qrScanResult(Object result) {

        if (result instanceof String) {
            queryMenuListByCode((String) result);
            return;
        }

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
        DaggerRetailMenuScanComponent.builder()
                .menuSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .retailMenuScanPresenterModule(new RetailMenuScanPresenterModule(this))
                .build()
                .inject(this);
        initHitTxt();
    }

    @Override
    protected int bizType() {
        return TYPE_SCAN_MENU;
    }

    private void initHitTxt() {
        mTextScanPrompt.setText(getString(R.string.module_menu_retail_qr_find_instance_prompt));
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setPresenter(RetailScanContract.Presenter presenter) {
        this.mPresenter = (RetailScanPresenter) presenter;
    }

    private void queryMenuDetail(String menuId) {
        mPresenter.queryCartFoodDetail(menuId);
    }

    private void queryMenuListByCode(String code) {
        MenuRetailQuery menuRetailQuery = new MenuRetailQuery();
        menuRetailQuery.setEntityId(UserHelper.getEntityId());
        menuRetailQuery.setCode(code);
        menuRetailQuery.setPageSize(20);
        mPresenter.queryMenuListByCode(menuRetailQuery);
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
                        continueScanning();
                        return;
                    }
                    if (mPresenter.isSoldOut(normalMenuVo)) {
                        showToast(getString(R.string.module_menu_join_cart_fail_soldout));
                        continueScanning();
                        return;
                    }

//                    if (mPresenter.isNormalMenuHasSpecOrMake(normalMenuVo)) {
//                        //有做法或者规格或者双单位，直接进入普通菜详情
//                        Menu menu = normalMenuVo.getMenu();
//                        if (null != menu && menu.getIsTwoAccount() == CartHelper.TwoAccountKind.KIND_2Account) {
//                            BoMenu boMenu = new BoMenu();
//                            boMenu.setId(normalMenuVo.getMenu().getId());
//                            addWeightCommodity(boMenu);
//                        } else {
//                            goToNormalDetail(baseMenuVo);
//                            continueScanning();
//                        }
//                    } else {
                    //}

                    // 普通菜无规格或做法，直接加入购物车，零售没有做法规格
                    Menu menu = normalMenuVo.getMenu();
                    if (null != menu && menu.getIsTwoAccount() == CartHelper.TwoAccountKind.KIND_2Account) {
                        BoMenu boMenu = new BoMenu();
                        boMenu.setId(normalMenuVo.getMenu().getId());
                        addWeightCommodity(boMenu);
                    } else {
                        mPresenter.joinCart(mSeatCode, mOrderId, mMenuId, normalMenuVo);
                    }

                }
            }
        } else {
            mCodeScanView.restartScan();
        }
    }

    @Override
    public void showMenuList(List<BoMenu> boMenuList) {

        ArrayList<BoMenu> boMenus = new ArrayList<>();
        boMenus.addAll(boMenuList);

        if (boMenus.size() <= 0) {
            ToastUtils.showShortToast(this, getString(R.string.module_menu_retail_no_commodity));
            continueScanning();
        } else if (boMenus.size() == 1) {
            if (boMenus.get(0) != null) {
                if (boMenus.get(0).getIsTwoAccount() == 0) {
                    mMenuId = boMenus.get(0).getId();
                    queryMenuDetail(boMenus.get(0).getId());
                } else {
                    addWeightCommodity(boMenus.get(0));
                }
            }
        } else {
            if (mRetailScannerDialog == null) {
                mRetailScannerDialog = new RetailScannerDialog();
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("bo_menu", boMenus);

            mRetailScannerDialog.setArguments(bundle);
            mRetailScannerDialog.show(getSupportFragmentManager(), "RetailScannerDialog");
        }
    }

    public void continueScanning() {
        mCodeScanView.restartScan();
        mCodeScanView.pauseScan(1500);
    }

    public void addWeightCommodity(BoMenu boMenu) {
        RouterBaseEvent.CommonEvent modifyCartEvent = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_FOR_WEIGHT;
        modifyCartEvent.setObject(boMenu);
        EventBusHelper.post(modifyCartEvent);
        finish();
    }

    public void scannerShopClicked(BoMenu boMenu) {
        mRetailScannerDialog.dismissAllowingStateLoss();
        if (boMenu.getIsTwoAccount() == 0) {
            mMenuId = boMenu.getId();
            queryMenuDetail(boMenu.getId());
        } else {
            addWeightCommodity(boMenu);
        }
    }

    @Override
    public void showJoinCartResult(NormalMenuVo normalMenuVo, DinningTableVo dinningTableVo) {
        if (null != dinningTableVo) {
            String menuName = "";
            if (null != normalMenuVo && null != normalMenuVo.getMenu() && !TextUtils.isEmpty(normalMenuVo.getMenu().getName())) {
                menuName = normalMenuVo.getMenu().getName();
            }
            showToast(String.format(getString(R.string.module_menu_retail_join_cart_success)
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
                .build(RouterPathConstant.RetailCartDetail.PATH_CART_DETAIL)
                .putString(RetailCartDetailActivity.EXTRA_MENU_ID, mMenuId)
                .putInt(RetailCartDetailActivity.EXTRA_DETAIL_TYPE, CartHelper.FoodDetailType.FOOD_DETAIL)
                .putSerializable(RetailCartDetailActivity.EXTRA_DETAIL_BASEVO, baseMenuVo)
                .putString(RouterPathConstant.CartDetail.EXTRA_SEATCODE
                        , mSeatCode)
                .putString(RouterPathConstant.CartDetail.EXTRA_ORDERID
                        , mOrderId)
                .putInt(RetailCartDetailActivity.EXTRA_DETAIL_KIND, CartHelper.BaseMenuFoodType.NORMAL)
                .navigation(this);
    }

    /**
     * 跳转到套餐详情页
     */
    private void goToComboDetail(String menuId, BaseMenuVo baseMenuVo) {
        MRouter.getInstance()
                .build(RouterPathConstant.SuitDetail.PATH)
                .putSerializable(RouterPathConstant.SuitDetail.PARAM_SUIT_BASE_MENU_VO, baseMenuVo)
                .putString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_ID, menuId)
                .navigation(this);
    }

}
