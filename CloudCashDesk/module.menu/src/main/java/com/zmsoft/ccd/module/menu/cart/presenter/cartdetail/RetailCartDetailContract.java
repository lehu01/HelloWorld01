package com.zmsoft.ccd.module.menu.cart.presenter.cartdetail;


import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.KindAndTasteVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitChildVO;

import java.util.List;

/**
 * 购物车详情
 *
 * @author DangGui
 * @create 2017/4/18.
 */

public interface RetailCartDetailContract {
    interface View extends BaseView<Presenter> {
        void showCartFoodDetail(BaseMenuVo baseMenuVo);

        void showCart(DinningTableVo dinningTableVo);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        void toastCartMsg(String message);

        /**
         * 回退“赠送菜”开关状态
         */
        void backPresentFoodPermission();
    }

    interface Presenter extends BasePresenter {
        /**
         * 查询购物车菜品详情
         */
        void queryCartFoodDetail(String menuId);

        /**
         * 修改购物车，根据详情页recyclerview数据源获取到当前用户选择的各种属性，组装成一个CartItem提交给服务端
         *
         * @param menuId            菜肴ID
         * @param cartRecyclerItems 详情页recyclerview数据源
         * @param normalMenuVo      菜肴的原始shuju
         * @param num               菜的数量
         * @param detailType        详情界面类型（商品详情、商品修改等）
         * @param mKindTasteList    口味列表
         */
        void modifyCart(String seatCode, String orderId, String menuId, List<CartDetailRecyclerItem> cartRecyclerItems
                , ItemVo itemVo, NormalMenuVo normalMenuVo, List<KindAndTasteVo> mKindTasteList
                , double num, int detailType);

        /**
         * 权限校验
         */
        void checkPermission(int systemType, String actionCode, List<CartDetailRecyclerItem> cartRecyclerItems);

        /**
         * 获取套餐子菜修改后的信息，返回给套餐列表界面
         *
         * @param menuId
         * @param normalMenuVo
         * @param cartRecyclerItems
         * @param num
         * @param mKindTasteList
         * @return
         */
        SuitChildVO getComboMenu(String menuId, NormalMenuVo normalMenuVo, List<CartDetailRecyclerItem> cartRecyclerItems
                , double num, List<KindAndTasteVo> mKindTasteList);
    }
}
