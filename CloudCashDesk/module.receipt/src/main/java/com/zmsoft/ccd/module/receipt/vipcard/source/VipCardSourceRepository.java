package com.zmsoft.ccd.module.receipt.vipcard.source;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardDetailResult;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardListResult;

import javax.inject.Inject;

/**
 * Description：具体调用仓库
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 19:45
 */
public class VipCardSourceRepository implements IVipCardSource {

    private final IVipCardSource mIVipCardSource;

    @Inject
    public VipCardSourceRepository(@Remote IVipCardSource iVipCardSource) {
        this.mIVipCardSource = iVipCardSource;
    }

    @Override
    public void getVipCardList(String entityId, String keyword, Callback<VipCardListResult> callback) {
        mIVipCardSource.getVipCardList(entityId, keyword, callback);
    }

    @Override
    public void getVipCardDetail(String entityId, String cardId, String orderId, Callback<VipCardDetailResult> callback) {
        mIVipCardSource.getVipCardDetail(entityId, cardId, orderId, callback);
    }
}
