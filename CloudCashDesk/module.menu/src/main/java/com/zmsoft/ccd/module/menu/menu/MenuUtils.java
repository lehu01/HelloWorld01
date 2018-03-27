package com.zmsoft.ccd.module.menu.menu;

import android.text.TextUtils;

import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.menu.cart.model.CustomerVo;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.helper.CartHelper;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/6/7.
 */

public class MenuUtils {

    /**
     * 修改购物车某个菜的对uid参数进行
     */
    public static String getUidByItemVO(ItemVo itemVo) {
        if (null != itemVo) {
            if (!TextUtils.isEmpty(itemVo.getCustomerRegisterId())) {
                return (itemVo.getCustomerRegisterId());
            } else {
                CustomerVo customerVo = itemVo.getCustomerVo();
                if (null != customerVo && !TextUtils.isEmpty(customerVo.getId())) {
                    return (customerVo.getId());
                } else {
                    return (UserHelper.getMemberId());
                }
            }
        } else {
            return (UserHelper.getMemberId());
        }
    }

    /**
     * 因为菜单列表的菜类和购物车菜类常量不一致，所以需要映射
     *
     * @param isInclude 菜单列表菜类
     * @return 修改购物车对应的菜类
     */
    public static int mapKindType(int isInclude) {
        if (isInclude == 0) {
            return CartHelper.CartFoodKind.KIND_NORMAL_FOOD;
        } else if (isInclude == 1) {
            return CartHelper.CartFoodKind.KIND_COMBO_FOOD;
        } else if (isInclude == 2) {
            return CartHelper.CartFoodKind.KIND_FEED_FOOD;
        }
        return CartHelper.CartFoodKind.KIND_NORMAL_FOOD;
    }
}
