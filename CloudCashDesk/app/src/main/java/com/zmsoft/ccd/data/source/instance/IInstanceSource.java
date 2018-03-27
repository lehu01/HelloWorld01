package com.zmsoft.ccd.data.source.instance;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.instance.op.cancelorgiveinstance.CancelOrGiveInstance;
import com.zmsoft.ccd.lib.bean.instance.op.updateprice.UpdateInstancePrice;
import com.zmsoft.ccd.lib.bean.instance.op.updateweight.UpdateInstanceWeight;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 16:33
 */
public interface IInstanceSource {

    /**
     * 更新菜肴价格 [订单中心]
     *
     * @param entityId   实体id
     * @param instanceId 菜肴id
     * @param opUserId   用户id
     * @param modifyTime 修改时间
     * @param fee        修改价格
     */
    void updateInstancePrice(String entityId, String instanceId, String opUserId, long modifyTime, int fee, Callback<UpdateInstancePrice> callback);

    /**
     * 更改订单内单个菜肴重量 [订单中心]
     *
     * @param entityId   实体id
     * @param instanceId 菜肴id
     * @param opUserId   操作用户id
     * @param modifyTme  修改时间
     * @param weight     修改重量
     */
    void updateInstanceWeight(String entityId, String instanceId, String opUserId, long modifyTme, double weight, Callback<UpdateInstanceWeight> callback);

    /**
     * 退菜 [订单中心]
     *
     * @param entityId   实体id
     * @param opUserId   用户id
     * @param instanceId 菜肴id
     * @param reason     退菜原因
     * @param modifyTime 操作时间opTime:Instance内时间
     * @param callback   回调
     */
    void cancelInstance(String entityId, String opUserId, String instanceId, String reason, long modifyTime, double num, double accountNum, Callback<CancelOrGiveInstance> callback);

    /**
     * 赠送菜肴 [订单中心]
     *
     * @param entityId   实体id
     * @param opUserId   用户id
     * @param instanceId 菜肴id
     * @param reason     退菜原因
     * @param modifyTime 操作时间opTime:Instance内时间
     * @param callback   回调
     */
    void giveInstance(String entityId, String opUserId, String instanceId, String reason, long modifyTime,double num, double accountNum, Callback<CancelOrGiveInstance> callback);

    /**
     * 催菜
     *
     * @param entityId           实体id
     * @param userId             用户id，操作员id
     * @param menuIdList         菜肴数组，json字符串
     * @param customerRegisterId 会员id
     * @param seatCode           座位code
     * @param orderId            订单id
     * @param callback           回调
     */
    void pushInstance(String entityId, String userId, List<String> menuIdList, String customerRegisterId, String seatCode, String orderId, Callback<Boolean> callback);

}
