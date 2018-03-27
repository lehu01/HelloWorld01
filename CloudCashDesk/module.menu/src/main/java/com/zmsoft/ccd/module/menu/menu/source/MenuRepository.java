package com.zmsoft.ccd.module.menu.menu.source;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.menu.bean.BoMenu;
import com.zmsoft.ccd.module.menu.menu.bean.MenuRetailQuery;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuHitRule;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.MenuCategory;
import com.zmsoft.ccd.module.menu.menu.bean.MenuUnit;
import com.zmsoft.ccd.module.menu.menu.bean.PassThrough;
import com.zmsoft.ccd.module.menu.menu.bean.ResponseSeatStatus;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenu;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/17.
 */
@ModelScoped
public class MenuRepository implements IMenuSource {

    private final IMenuSource menuRemoteSource;

    @Inject
    MenuRepository(@Remote IMenuSource menuRemoteSource) {
        this.menuRemoteSource = menuRemoteSource;
    }

    @Override
    public void getMenuList(String entityId, Callback<List<Menu>> callback) {
        menuRemoteSource.getMenuList(entityId, callback);
    }

    @Override
    public Observable<List<Menu>> getMenuList(String entityId) {
        return menuRemoteSource.getMenuList(entityId);
    }

    @Override
    public void getMenuCategories(String entityId, int[] types, Callback<List<MenuCategory>> callback) {
        menuRemoteSource.getMenuCategories(entityId, types, callback);
    }

    @Override
    public void getMenuUnits(String entityId, Callback<List<MenuUnit>> callback) {
        menuRemoteSource.getMenuUnits(entityId, callback);
    }

    @Override
    public void getMenuPassThrough(String entityId, Callback<List<PassThrough>> callback) {
        menuRemoteSource.getMenuPassThrough(entityId, callback);
    }

    @Override
    public void saveCustomMenuToCart(String entity_id, String user_id, String ownerCustomerId,
                                     String seatCode, int peopleCount,
                                     String itemListJson, Callback<DinningTableVo> callback) {
        menuRemoteSource.saveCustomMenuToCart(entity_id, user_id, ownerCustomerId, seatCode,
                peopleCount, itemListJson, callback);
    }

    @Override
    public void getSeatStatus(String entityId, String seatCode, Callback<ResponseSeatStatus> callback) {
        menuRemoteSource.getSeatStatus(entityId, seatCode, callback);
    }

    @Override
    public void getSuitDetail(String entityId, String suitMenuId, String source, Callback<SuitMenu> callback) {
        menuRemoteSource.getSuitDetail(entityId, suitMenuId, source, callback);
    }

    @Override
    public void queryFoodDetail(String menuId, Callback<BaseMenuVo> callback) {
        menuRemoteSource.queryFoodDetail(menuId, callback);
    }

    @Override
    public Observable<BaseMenuVo> queryFoodDetail(String menuId) {
        return menuRemoteSource.queryFoodDetail(menuId);
    }

    @Override
    public Observable<List<SuitMenuHitRule>> getRulesBySuitMenuId(String suitMenuId) {
        return menuRemoteSource.getRulesBySuitMenuId(suitMenuId);
    }

    @Override
    public void getRulesBySuitMenuId(String suitMenuId, Callback<List<SuitMenuHitRule>> callback) {
        menuRemoteSource.getRulesBySuitMenuId(suitMenuId, callback);
    }

    @Override
    public Observable<List<BoMenu>> queryMenuListByEntityId(MenuRetailQuery menuRetailQuery) {
        return menuRemoteSource.queryMenuListByEntityId(menuRetailQuery);
    }
}
