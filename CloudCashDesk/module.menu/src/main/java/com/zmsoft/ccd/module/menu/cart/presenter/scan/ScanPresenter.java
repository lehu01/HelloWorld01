package com.zmsoft.ccd.module.menu.cart.presenter.scan;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.AdditionKindMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.AdditionMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.MakeDataDto;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.Menu;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.SpecDataDto;
import com.zmsoft.ccd.module.menu.cart.source.CartRemoteSource;
import com.zmsoft.ccd.module.menu.cart.source.ICartSource;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.source.MenuRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author DangGui
 * @create 2017/4/26.
 */

public class ScanPresenter implements ScanContract.Presenter {

    private ScanContract.View mView;

    private MenuRepository mRepository;

    private ICartSource mCartRemoteSource;

    @Inject
    public ScanPresenter(ScanContract.View view, MenuRepository repository) {
        mView = view;
        mRepository = repository;
        this.mCartRemoteSource = new CartRemoteSource();
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void queryCartFoodDetail(String menuId) {
        mView.showLoading(GlobalVars.context.getString(R.string.dialog_waiting), false);
        mRepository.queryFoodDetail(menuId, new Callback<BaseMenuVo>() {
            @Override
            public void onSuccess(BaseMenuVo data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.showMenuDetail(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void joinCart(String seatCode, String orderId, String menuId, final NormalMenuVo normalMenuVo) {
        mView.showLoading(GlobalVars.context.getString(R.string.module_menu_cart_dialog_joining), false);
        List<CartItem> cartItemList = getCartItems(menuId, normalMenuVo);
        mCartRemoteSource.modifyCart(seatCode, orderId, CartHelper.CartSource.CUSTOM_SCAN_ADDFOOD
                , cartItemList, new Callback<DinningTableVo>() {
                    @Override
                    public void onSuccess(DinningTableVo data) {
                        if (null == mView) {
                            return;
                        }
                        mView.hideLoading();
                        mView.showJoinCartResult(normalMenuVo, data);
                    }

                    @Override
                    public void onFailed(ErrorBody body) {
                        if (null == mView) {
                            return;
                        }
                        mView.hideLoading();
                        mView.loadDataError(body.getMessage());
                    }
                });
    }

    /**
     * 通过json获取到真实的实体类
     *
     * @param baseMenuVo
     * @param clazz
     * @return
     */
    public Object getRealMenuVo(BaseMenuVo baseMenuVo, Class clazz) {
        Object object = null;
        try {
            object = JsonMapper.fromJson(baseMenuVo.getMenuJson(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 菜是否已售完
     *
     * @param normalMenuVo
     * @return
     */
    public boolean isSoldOut(NormalMenuVo normalMenuVo) {
        if (null == normalMenuVo) {
            return false;
        }
        Menu menu = normalMenuVo.getMenu();
        return (null != menu && menu.getIsSoldOut());
    }

    /**
     * 菜是否已下架
     *
     * @param normalMenuVo
     * @return
     */
    public boolean isOffShelves(NormalMenuVo normalMenuVo) {
        if (null == normalMenuVo) {
            return false;
        }
        Menu menu = normalMenuVo.getMenu();
        return (null != menu && menu.getIsSelf() == CartHelper.FoodOffShelves.OFF);
    }

    /**
     * 判断普通菜有没有规格/做法
     *
     * @param normalMenuVo
     * @return
     */
    public boolean isNormalMenuHasSpecOrMake(NormalMenuVo normalMenuVo) {
        if (null == normalMenuVo) {
            return false;
        }
        Menu menu = normalMenuVo.getMenu();
        boolean isTwoCount = (null != menu && menu.getIsTwoAccount() == CartHelper.TwoAccountKind.KIND_2Account);
        List<MakeDataDto> makeDataList = normalMenuVo.getMakeDataList();
        List<SpecDataDto> specDataList = normalMenuVo.getSpecDataList();
        boolean isHasMake = (makeDataList != null && !makeDataList.isEmpty());
        boolean isHasSpec = (specDataList != null && !specDataList.isEmpty());
        return isHasMake || isHasSpec || isTwoCount;
    }

    private List<CartItem> getCartItems(String menuId, NormalMenuVo normalMenuVo) {
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem mCartItem = new CartItem();
        if (null != normalMenuVo.getMenu()) {
            mCartItem.setUid(UserHelper.getMemberId());
            Menu menu = normalMenuVo.getMenu();
            mCartItem.setIndex(UserHelper.getEntityId() + StringUtils.getRandomString(24));
            mCartItem.setMenuId(menuId);
            mCartItem.setMenuName(menu.getName());
            mCartItem.setNum(1.0); //扫码加菜，每次肯定是1份
            mCartItem.setKindMenuId(menu.getKindMenuId());
            mCartItem.setAccountNum(mCartItem.getNum());
            mCartItem.setAccountUnit(menu.getAccount());
            mCartItem.setKindType(CartHelper.CartFoodKind.KIND_NORMAL_FOOD);
            mCartItem.setAddPriceMode(menu.getAddPriceMode());
            mCartItem.setAddPrice(menu.getAddPrice());
            mCartItem.setAddType(0); //云收银默认都是0
            mCartItem.setSource(CartHelper.CartSource.CUSTOM_SCAN_ADDFOOD);
            mCartItem.setPresent((short) 0); //扫码点菜，默认都是不赠送这个菜
            //普通菜子菜列表
            List<AdditionKindMenuVo> additionKindMenuList = normalMenuVo.getAdditionKindMenuList();
            if (null != additionKindMenuList) {
                mCartItem.setChildCartVos(getChildFoodList(additionKindMenuList));
            }
            //口味
            mCartItem.setMemo(menu.getMemo());
        }
        cartItemList.add(mCartItem);
        return cartItemList;
    }

    /**
     * 获取普通菜子菜列表数据
     */
    private List<CartItem> getChildFoodList(List<AdditionKindMenuVo> additionKindMenuList) {
        List<CartItem> childCartItemList = new ArrayList<>();
        for (int i = 0; i < additionKindMenuList.size(); i++) {
            AdditionKindMenuVo additionKindMenuVo = additionKindMenuList.get(i);
            if (null != additionKindMenuVo) {
                List<AdditionMenuVo> additionMenuList = additionKindMenuVo.getAdditionMenuList();
                if (null != additionMenuList && !additionMenuList.isEmpty()) {
                    for (int j = 0; j < additionMenuList.size(); j++) {
                        AdditionMenuVo additionMenuVo = additionMenuList.get(j);
                        if (null != additionMenuVo) {
                            CartItem cartItem = new CartItem();
                            cartItem.setMenuName(additionMenuVo.getMenuName());
                            cartItem.setMenuId(additionMenuVo.getMenuId());
                            cartItem.setKindMenuId(additionKindMenuVo.getKindMenuId());
                            cartItem.setNum(additionMenuVo.getNum());
                            cartItem.setAccountNum(additionMenuVo.getNum());
                            cartItem.setKindType(CartHelper.CartFoodKind.KIND_FEED_FOOD);
                            cartItem.setIndex(UserHelper.getEntityId() + StringUtils.getRandomString(24));
                            childCartItemList.add(cartItem);
                        }
                    }
                }
            }
        }
        return childCartItemList;
    }
}
