package com.ccd.lib.print.data;

import com.ccd.lib.print.bean.UploadPrintNum;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.print.PrintAreaVo;
import com.zmsoft.ccd.lib.bean.print.PrintOrderVo;
import com.zmsoft.ccd.lib.bean.print.PrintTestVo;

import java.util.List;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/12 10:22
 */
public interface IPrintSource {

    // 更新打印任务状态
    Observable<Boolean> updateTaskStatus(String entityId, String userId, String id, short status, String memo);

    // 锁定的打印任务
    Observable<Boolean> lockPrintTask(String entityId, String userId, String taskId);

    //================================================================================================
    // 发送消息给本地收银：打印订单
    //================================================================================================

    /**
     * 打印财务联 - 本地收银
     *
     * @param entityId
     * @param type
     * @param opUserId
     * @return
     */
    Observable<CommonResult> sendMessageToLocalCashPrintBill(String entityId, int type, String opUserId);

    /**
     * 打印财务联 - 本地收银
     *
     * @param entityId
     * @param orderId
     * @param opUserId
     * @return
     */
    Observable<CommonResult> sendMessageToLocalCashPrintFinanceOrder(String entityId, String orderId, String opUserId);

    /**
     * 打印本地收银测试页面 - 本地收银
     *
     * @param entityId 实体id
     * @param opUserId 操作员id
     * @return
     */
    Observable<CommonResult> sendMessageToLocalCashPrintTest(String entityId, String opUserId, String ip, int type, int rowCount);

    /**
     * 打印点菜单 - 本地收银
     *
     * @param entityId 实体id
     * @param orderId  订单id
     * @param type     类型
     */
    Observable<CommonResult> sendMessageToLocalCashPrintDishesOrder(String entityId, String orderId, int type, String opUserId);

    /**
     * 发送打印客户联 - 本地收银
     *
     * @param entityId 实体id
     * @param orderId  订单id
     * @param opUserId 操作员id
     */
    Observable<CommonResult> sendMessageToLocalCashPrintAccountOrder(String entityId, String orderId, String opUserId);

    //================================================================================================
    // 云收银：打印相关订单
    //================================================================================================

    /**
     * 打印账单汇总
     *
     * @param entityId 实体id
     * @param userId   操作员id
     * @param userId   用户id
     * @return
     */
    Observable<PrintOrderVo> printBillOrder(String entityId, String userId, int billType);

    /**
     * 更新打印数量
     *
     * @param entityId 实体id
     * @param orderId  订单id
     * @param type     0-客户联打印  1-财务联打印  2-客户联、财务联打印
     */
    Observable<UploadPrintNum> uploadPrintNum(String entityId, String orderId, int type);

    /**
     * 打印订单[点菜单/客户联/财务联]
     *
     * @param type     类型：点菜单/客户联/财务联
     * @param entityId 实体id
     * @param orderId  订单id
     * @param userId   操作员id
     */
    Observable<PrintOrderVo> printOrder(int type, String entityId, String orderId, String userId, int reprint);

    /**
     * 加菜[菜肴纬度]
     *
     * @param type        类型：点菜单/客户联/财务联
     * @param entityId    实体id
     * @param instanceIds 菜肴ids
     * @param userId      操作员id
     */
    Observable<PrintOrderVo> printInstance(int type, String entityId, List<String> instanceIds, String userId, int reprint);

    /**
     * 测试打印
     */
    void printTest(String entityId, Callback<PrintTestVo> callback);

    /**
     * 通过seatCode获取区域打印信息
     *
     * @param entityId
     * @param seatCode
     */
    Observable<PrintAreaVo> getPrintBySeatCode(String entityId, String seatCode, int orderKind);

    /**
     * 打印转桌单
     *
     * @param entityId    实体id
     * @param orderId     订单id
     * @param userId      操作员id
     * @param oldSeatCode 旧seatCode
     * @return
     */
    Observable<PrintOrderVo> printChangeSeat(String entityId, String orderId, String userId, String oldSeatCode);

}
