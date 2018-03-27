package com.zmsoft.ccd.module.receipt.vipcard.source;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardDetailResult;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardListResult;

/**
 * Description：接口
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 19:44
 */
public interface IVipCardSource {

    /**
     * 根据手机号/卡号，获取会员卡列表
     *
     * @param entityId 实体id
     * @param keyword  关键字
     */
    void getVipCardList(String entityId, String keyword, Callback<VipCardListResult> callback);

    /**
     * 获取会员卡详情
     *
     * @param entityId 实体id
     * @param cardId   卡id
     * @param callback 回调
     */
    void getVipCardDetail(String entityId, String cardId, String orderId, Callback<VipCardDetailResult> callback);

}
