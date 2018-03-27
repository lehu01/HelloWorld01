package com.zmsoft.ccd.data.source.home;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.home.HomeCount;
import com.zmsoft.ccd.lib.bean.shop.ShopLimitVo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 主页
 *
 * @author DangGui
 * @create 2017/8/15.
 */
@Singleton
public class HomeRepository implements IHomeSource {

    private final IHomeSource remoteSource;

    @Inject
    HomeRepository(@Remote IHomeSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @Override
    public void getShopLimitDay(String entityId, Callback<ShopLimitVo> callback) {
        remoteSource.getShopLimitDay(entityId, callback);
    }

    @Override
    public void getHomeUnReadCount(Callback<HomeCount> callback) {
        remoteSource.getHomeUnReadCount(callback);
    }
}
