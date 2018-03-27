package com.zmsoft.ccd.module.menu.menu.converter;

import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.helper.SpecificationDataMapLayer;
import com.zmsoft.ccd.module.menu.menu.MenuUtils;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitMenuVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/6/7.
 */

public class CartItemConverter {


    /**
     * 组装添加套餐到购物车需要的数据
     */
    public static List<CartItem> getCartItemsBySuitMenu(SuitMenuVO suitMenuVO, Collection<MenuVO> menuVOs) {

        List<CartItem> items = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setMenuId(suitMenuVO.getMenuId());
        cartItem.setMenuName(suitMenuVO.getMenuName());
        cartItem.setNum(suitMenuVO.getNum());
        cartItem.setUid(MenuUtils.getUidByItemVO(suitMenuVO.getItemVo()));
        //如果没有则创建index
        cartItem.setIndex(suitMenuVO.getIndex());
        //套餐
        cartItem.setKindType(CartHelper.CartFoodKind.KIND_COMBO_FOOD);
        cartItem.setSource(CartHelper.CartSource.CART_COMBO_CHILD_DETAIL);

        //如果是单单位，把accountNum设置和num一样（套餐没有双单位）
        //if (!specificationVO.isTwoAccount()) {
        cartItem.setAccountNum(suitMenuVO.getNum());
        //} else {
        //    cartItem.setAccountNum(specificationVO.getItemVo().getAccountNum());
        //}
        //规格id（套餐无规格，套餐子菜可能有）
        //cartItem.setSpecId(specificationVO.getSpecId());
        //做法id（套餐无做法，套餐子菜可能有）
        //cartItem.setMakeId(specificationVO.getMakeId());

        //组装套餐子菜
        if (menuVOs != null && menuVOs.size() > 0) {
            List<CartItem> subs = new ArrayList<>(menuVOs.size());
            for (MenuVO menuVO : menuVOs) {
                //if (menuVO.getItem() != null) {
                //    subs.add(SpecificationDataMapLayer.itemConvertToCartItem(menuVO.getItem()));
                //} else {
                subs.add(SpecificationDataMapLayer.suitSubMenuConvertToCartItem(menuVO));
                //}
            }
            cartItem.setChildCartVos(subs);
        }

        //用户输入的备注
        cartItem.setMemo(suitMenuVO.getMemo());
        //用户选择的备注（套餐没有选择的入口）
        //cartItem.setLabels(specificationVO.getLabels());
        //暂不上菜
        cartItem.setIsWait(suitMenuVO.getIsWait());
        items.add(cartItem);
        return items;
    }


}
