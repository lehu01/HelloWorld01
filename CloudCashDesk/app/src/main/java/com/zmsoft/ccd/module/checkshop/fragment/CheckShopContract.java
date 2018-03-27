package com.zmsoft.ccd.module.checkshop.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.shop.Shop;
import com.zmsoft.ccd.lib.bean.shop.request.BindShopRequest;
import com.zmsoft.ccd.lib.bean.user.User;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 10:47
 */
public class CheckShopContract {

    public interface Presenter extends BasePresenter {

        // 设置工作状态
        void setWorkingStatus(String entityId, int type, String userId, int status);

        /**
         * 绑定店铺（切店）
         */
        void bindShop(BindShopRequest request);

        // 获取工作模式是否选择接口
        void getConfigSwitchVal(String entityId, String systemTypeId, String code);

        // 获取绑定列表接口
        void getBindShopList(String memberId);

        // 绑定接口
        void bindShop(String memberId, String userId, String entityId, String originalUserId);

    }

    public interface View extends BaseView<CheckShopContract.Presenter> {

        // 显示错误界面
        void showLoadErrorView(String message);

        // 获取工作模式
        void workModelSuccess(String data);

        // 获取绑定店铺列表成功
        void refreshBindShopList(List<Shop> list);

        // 绑定店铺
        void bindShop(User user);

        // 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
        void loadDataError(String errorMessage);
    }


}
