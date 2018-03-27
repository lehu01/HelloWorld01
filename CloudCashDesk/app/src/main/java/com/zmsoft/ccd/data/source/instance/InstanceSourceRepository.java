package com.zmsoft.ccd.data.source.instance;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.instance.op.cancelorgiveinstance.CancelOrGiveInstance;
import com.zmsoft.ccd.lib.bean.instance.op.updateprice.UpdateInstancePrice;
import com.zmsoft.ccd.lib.bean.instance.op.updateweight.UpdateInstanceWeight;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 16:44
 */
@Singleton
public class InstanceSourceRepository implements IInstanceSource {

    private final IInstanceSource mIInstanceSource;
    private final ICommonSource mICommonSource;

    @Inject
    public InstanceSourceRepository(@Remote IInstanceSource iInstanceSource) {
        mIInstanceSource = iInstanceSource;
        mICommonSource = new CommonRemoteSource();
    }

    @Override
    public void updateInstancePrice(String entityId, String instanceId, String opUserId, long modifyTime, int fee, Callback<UpdateInstancePrice> callback) {
        mIInstanceSource.updateInstancePrice(entityId, instanceId, opUserId, modifyTime, fee, callback);
    }

    @Override
    public void updateInstanceWeight(String entityId, String instanceId, String opUserId, long modifyTme, double weight, Callback<UpdateInstanceWeight> callback) {
        mIInstanceSource.updateInstanceWeight(entityId, instanceId, opUserId, modifyTme, weight, callback);
    }

    @Override
    public void cancelInstance(String entityId, String orderId, String menuId, String reason, long modifyTime, double num, double accountNum, Callback<CancelOrGiveInstance> callback) {
        mIInstanceSource.cancelInstance(entityId, orderId, menuId, reason, modifyTime, num, accountNum, callback);
    }

    @Override
    public void giveInstance(String entityId, String orderId, String menuId, String reason, long modifyTime, double num, double accountNum, Callback<CancelOrGiveInstance> callback) {
        mIInstanceSource.giveInstance(entityId, orderId, menuId, reason, modifyTime, num, accountNum, callback);
    }

    @Override
    public void pushInstance(String entityId, String userId, List<String> menuIdList, String customerRegisterId, String seatCode, String orderId, Callback<Boolean> callback) {
        mIInstanceSource.pushInstance(entityId, userId, menuIdList, customerRegisterId, seatCode, orderId, callback);
    }

    public void getReasonList(String entityId, String dicCode, int systemType, final Callback<List<Reason>> callback) {
        mICommonSource.getReasonList(entityId, dicCode, systemType, callback);
    }
}
