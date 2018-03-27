package com.zmsoft.ccd.module.receipt.vipcard.detail.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardDetailResult;
import com.zmsoft.ccd.receipt.bean.CashPromotionBillResponse;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.receipt.bean.Promotion;

import java.util.List;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 17:27
 */
public interface VipCardDetailContract {

    interface Presenter extends BasePresenter {

        // 账单优惠（整单打折，卡优惠）
        void promoteBill(String orderId, List<Promotion> promotionList);

        // 云收银收款
        void collectPay(String orderId, List<Fund> fundList, boolean notCheckPromotion);

        // 获取会员卡详情
        void getVipCardDetail(String entityId, String cardId, String orderId);

        // check 参数
        boolean check(double balanceMoney, String needPayMoney);
    }

    interface View extends BaseView<Presenter> {

        // 已经使用优惠
        void alreadyOtherDiscount(String errorMessage);

        // 整单打折确认结果
        void successPromoteBill(CashPromotionBillResponse cashPromotionBillResponse);

        // 支付成功
        void successCollectPay(CloudCashCollectPayResponse cloudCashCollectPayResponse);

        // 支付失败
        void failCollectPay(String errorMessage);

        // 获取会员卡密码
        String getVipCardPassWord();

        // 获取应收金额
        String getNeedPayMoney();

        // 获取会员卡详情
        void loadVipCardDetailSuccess(VipCardDetailResult vipCardDetail, String errorMessage);

        // 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
        void loadDataError(String errorMessage);
    }

}
