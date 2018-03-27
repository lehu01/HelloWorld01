package com.zmsoft.ccd.data.source.home;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.home.HomeCount;
import com.zmsoft.ccd.lib.bean.shop.ShopLimitVo;

/**
 * 主页
 *
 * @author DangGui
 * @create 2017/8/15.
 */

public interface IHomeSource {

    /**
     * 获取店铺是否是正式版本提示
     *
     * @param entityId
     */
    void getShopLimitDay(String entityId, Callback<ShopLimitVo> callback);

    /**
     * 获取首页各项未读数量
     */
    void getHomeUnReadCount(Callback<HomeCount> callback);
}
