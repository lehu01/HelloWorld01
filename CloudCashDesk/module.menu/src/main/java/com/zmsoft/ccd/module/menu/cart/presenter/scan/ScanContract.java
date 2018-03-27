package com.zmsoft.ccd.module.menu.cart.presenter.scan;


import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;

/**
 * 菜单 扫码
 *
 * @author DangGui
 * @create 2017/4/26.
 */

public interface ScanContract {
    interface View extends BaseView<Presenter> {

        void showMenuDetail(BaseMenuVo baseMenuVo);

        void showJoinCartResult(NormalMenuVo normalMenuVo, DinningTableVo dinningTableVo);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        /**
         * 查询购物车菜品详情
         */
        void queryCartFoodDetail(String menuId);

        /**
         * 加入购物车
         *
         * @param seatCode
         * @param orderId
         */
        void joinCart(String seatCode, String orderId, String menuId, NormalMenuVo normalMenuVo);
    }
}
