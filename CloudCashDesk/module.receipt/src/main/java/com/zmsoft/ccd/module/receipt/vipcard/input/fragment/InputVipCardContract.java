package com.zmsoft.ccd.module.receipt.vipcard.input.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.vipcard.VipCard;

import java.util.List;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 17:27
 */
public interface InputVipCardContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取会员卡列表
         */
        void getVipCardList(String entityId, String keyword);
    }

    interface View extends BaseView<Presenter> {

        // 获取会员卡列表
        void loadVipCardListSuccess(List<VipCard> list);

        // 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
        void loadDataError(String errorMessage);

    }

}
