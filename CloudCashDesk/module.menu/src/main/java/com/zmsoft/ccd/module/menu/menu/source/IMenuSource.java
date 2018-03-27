package com.zmsoft.ccd.module.menu.menu.source;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.menu.bean.BoMenu;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.MenuCategory;
import com.zmsoft.ccd.module.menu.menu.bean.MenuRetailQuery;
import com.zmsoft.ccd.module.menu.menu.bean.MenuUnit;
import com.zmsoft.ccd.module.menu.menu.bean.PassThrough;
import com.zmsoft.ccd.module.menu.menu.bean.ResponseSeatStatus;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenu;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuHitRule;

import java.util.List;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/17.
 */

public interface IMenuSource {

    /**
     * 获取菜单列表
     */
    void getMenuList(String entityId, Callback<List<Menu>> callback);

    Observable<List<Menu>> getMenuList(final String entityId);

    /**
     * 获取菜单分类
     */
    void getMenuCategories(String entityId, int[] types, Callback<List<MenuCategory>> callback);

    /**
     * 获取点菜单位
     */
    void getMenuUnits(String entityId, Callback<List<MenuUnit>> callback);

    /**
     * 获取传菜方案
     */
    void getMenuPassThrough(String entityId, Callback<List<PassThrough>> callback);

    /**
     * 保存自定义菜到购物车
     *
     * @param ownerCustomerId 购物车拥有者id(这个接口和userId相同)
     */
    void saveCustomMenuToCart(String entityId,
                              String userId,
                              String ownerCustomerId,
                              String seatCode,
                              int peopleCount,
                              String itemListJson,
                              Callback<DinningTableVo> callback);

    /**
     * 根据seatCode查询桌位状态
     */
    void getSeatStatus(String entityId, String seatCode, Callback<ResponseSeatStatus> callback);


    /**
     * 套餐详情
     *
     * @param source 0：堂食，1：外卖
     */
    void getSuitDetail(String entityId, String suitMenuId, String source, Callback<SuitMenu> callback);

    /**
     * 获取商品详情
     *
     * @param callback
     */
    void queryFoodDetail(String menuId, final Callback<BaseMenuVo> callback);

    Observable<BaseMenuVo> queryFoodDetail(final String menuId);


    Observable<List<SuitMenuHitRule>> getRulesBySuitMenuId(final String suitMenuId);

    /**
     * 套餐计价规则（撞餐）
     */
    void getRulesBySuitMenuId(String suitMenuId, Callback<List<SuitMenuHitRule>> callback);

    Observable<List<BoMenu>> queryMenuListByEntityId(final MenuRetailQuery menuRetailQuery);
}
