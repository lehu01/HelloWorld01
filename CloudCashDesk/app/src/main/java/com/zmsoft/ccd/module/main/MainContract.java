package com.zmsoft.ccd.module.main;

import com.dfire.mobile.cashupdate.bean.CashUpdateInfoDO;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.carryout.TakeoutMobile;
import com.zmsoft.ccd.lib.bean.shop.ShopLimitVo;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 14:46
 */
public class MainContract {

    public interface Presenter extends BasePresenter {

        // 获取店铺是否是正式版本提示
        void getShopLimitDay(String entityId);

        /**
         * 获取标签打印配置信息
         *
         * @param entityId 实体id
         * @param userId   用户id
         */
        void getLabelPrintConfig(String entityId, String userId);

        // 获取批量开关：FielCode
        Observable<Map<String, String>> getFielCodeByList(String entityId, List<String> codeList);

        // 获取小票打印配置
        void getPrintConfig(String entityId, String userId);

        // 检查更新
        void checkAppUpdate(String entityId, String appCode, int version);

        // 获取店铺开关，批量
        void getSwitchByList(String entityId, List<String> codeList);

        /**
         * 批量获取权限
         */
        void batchCheckPermisson(int systemType, List<String> actionCodeList);

        void loadCarryoutPhoneList(String entityId);


        // 获取工作状态
        void getWorkingStatus(String entityId, int type, String userId);

        // 设置工作状态
        void setWorkingStatus(String entityId, int type, String userId, int status);

        // 检查绑定外卖消息
        void checkTakeOutBindSeat(String entityId, String userId);

        // 获取货币符号
        void getConfigSwitchVal(String entityId, String systemTypeId, String code);
    }

    public interface View extends BaseView<Presenter> {

        // 获取店铺试用失败
        void getShopLimitDayFailure();

        // 获取成功
        void getShopLimitDaySuccess(ShopLimitVo shopLimitVo);

        // 检测更新成功
        void checkAppUpdateSuccess(CashUpdateInfoDO cashUpdateInfoDO);

        // 检查外卖消息绑定
        void checkTakeOutBindSeat();

        /**
         * 打开右侧个人中心侧栏
         */
        void showPersonalCenterMenu();

        void loadConfigSuccess(boolean hasOpenCarryoutPhoneCall);

        void loadConfigFailed(boolean hasOpenCarryoutPhoneCall);

        void loadCarryoutPhoneListSuccess(List<TakeoutMobile> list);

        void loadCarryoutPhoneListFailed(String errorCode, String errorMsg);

        //================================================================================
        // work status
        //================================================================================
        // 工作模式失败
        void loadWorkingStatusFailure();

        // 获取工作状态
        void loadWorkingStatusSuccess(boolean status);

        // 设置上下班成功
        void setWorkingSuccess(int data, int type);

        // 信息toast提示
        void showToastMessage(String message);
    }

}
