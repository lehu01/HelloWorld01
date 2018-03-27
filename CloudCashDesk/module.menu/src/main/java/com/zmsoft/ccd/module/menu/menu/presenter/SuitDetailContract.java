package com.zmsoft.ccd.module.menu.menu.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuHitRule;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitMenuDetailVO;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/21.
 */

public interface SuitDetailContract {


    interface View extends BaseView<BasePresenter> {

        void loadSuitDetailSuccess(SuitMenuDetailVO suitMenuVO);

        void loadSuitDetailFailed(String errorCode, String errorMessage);

        void addSuitToCartSuccess(DinningTableVo data);
        void addSuitToCartFailed(String errorCode, String errorMessage);

        void loadSuitRuleSuccess(List<SuitMenuHitRule> suitMenuHitRule);
        void loadSuitRuleFailed(String errorCode, String errorMessage);

    }

    interface Presenter extends BasePresenter {

        void loadSuitDetail(String suitMenuId);

        void addSuitToCart(String seatCode, String orderId, String source, List<CartItem> cartItemList);

        void loadSuitRule(String menuId);
    }
}
