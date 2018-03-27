package com.zmsoft.ccd.module.main.home.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.home.HomeCount;

import java.util.List;
import com.zmsoft.ccd.lib.bean.shop.ShopLimitVo;

/**
 * @author DangGui
 * @create 2017/8/15.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter> {

        void getShopLimitDayFailure();

        void getShopLimitDaySuccess(ShopLimitVo shopLimitVo);

        void successGetHomeUnreadCount(HomeCount homeCount);

        void failGetHomeUnreadCount(String errorMsg);

        void successGetWorkMode(boolean isMixture);
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取工作模式（混合/独立）
         */
        void getWorkModel(String entityId, List<String> codeList);
        /**
         * 获取店铺是否是正式版本提示
         *
         * @param entityId
         */
        void getShopLimitDay(String entityId);

        /**
         * 获取首页各项未读数量
         */
        void getHomeUnreadCount();

        boolean isAppInstalled(String packageName);

        String getLauncherActivityClassName(String packageName);
    }
}
