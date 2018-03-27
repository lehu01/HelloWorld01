package com.zmsoft.ccd.lib.base.helper;

import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.bean.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/9 10:58
 *     desc  : 开关批量管理工具
 * </pre>
 */
public class BatchSwitchHelper {

    public static List<String> getBusinessModelSwitchList() {
        List<String> resultList = new ArrayList<>();
        resultList.add(SystemDirCodeConstant.FunctionFielCode.PRINT_LABEL);
        resultList.add(SystemDirCodeConstant.FunctionFielCode.DOUBLE_UPDATE_PROMPT);
        resultList.add(SystemDirCodeConstant.FunctionFielCode.SELF_PRINT_ACCOUNT_ORDER);
        resultList.add(SystemDirCodeConstant.FunctionFielCode.OPEN_ORDER_MUST_SEAT);
        resultList.add(SystemDirCodeConstant.FunctionFielCode.QUICK_OPEN_ORDER);
        resultList.add(SystemDirCodeConstant.FunctionFielCode.TRANS_SEAT_OR_PUSH_INSTANCE);
        resultList.add(SystemDirCodeConstant.FunctionFielCode.PRINT_CANCEL_INSTANCE);
        resultList.add(SystemDirCodeConstant.FunctionFielCode.ENOUGH_MONEY_END_AFTER_ORDER);
        return resultList;
    }

    public static List<String> getCommonSwitchList() {
        List<String> resultList = new ArrayList<>();
        resultList.add(SystemDirCodeConstant.ACCOUNT_BILL);
        resultList.add(SystemDirCodeConstant.IS_PRINT_ORDER);
        resultList.add(SystemDirCodeConstant.CLOUD_CASH_CALL);
        resultList.add(SystemDirCodeConstant.CASH_CLEAN);
        resultList.add(SystemDirCodeConstant.IS_USE_LOCAL_CASH);
        resultList.add(SystemDirCodeConstant.IS_SET_MINCONSUMEMODE);
        resultList.add(SystemDirCodeConstant.IS_LIMIT_TIME);
        resultList.add(SystemDirCodeConstant.LIMIT_TIME_END);
        return resultList;
    }

    public static void saveBusinessModelSwitchList(Map<String, String> data) {
        if (data != null) {
            // 自动打印客户联时间
            BaseSpHelper.saveSelfPrintAccountOrderSwitchTime(GlobalVars.context, System.currentTimeMillis());
            // 自动打印客户联开关
            BaseSpHelper.saveSelfPrintAccountOrderSwitch(GlobalVars.context,
                    data.get(SystemDirCodeConstant.FunctionFielCode.SELF_PRINT_ACCOUNT_ORDER));
            // 保存标签打印开关
            BaseSpHelper.saveUseLabelPrinter(GlobalVars.context,
                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.FunctionFielCode.PRINT_LABEL)));
            // 保存双单位修改提醒
            BaseSpHelper.saveDoubleUnitSwitch(GlobalVars.context,
                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.FunctionFielCode.DOUBLE_UPDATE_PROMPT)));
            // 快速开单
            BaseSpHelper.saveQuickOpenOrder(GlobalVars.context,
                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.FunctionFielCode.QUICK_OPEN_ORDER)));
            // 开单时是否必须选桌
            BaseSpHelper.saveOpenOrderMustSeat(GlobalVars.context,
                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.FunctionFielCode.OPEN_ORDER_MUST_SEAT)));
            // 转桌、并单、催菜时打印单据
            BaseSpHelper.saveTransSeatOrPushInstance(GlobalVars.context,
                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.FunctionFielCode.TRANS_SEAT_OR_PUSH_INSTANCE)));
            // 退菜时打印退菜单
            BaseSpHelper.savePrintCancelInstance(GlobalVars.context,
                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.FunctionFielCode.PRINT_CANCEL_INSTANCE)));
            // 当有足够的付款金额时是否自动结账完毕
            BaseSpHelper.saveEnoughMoneyEndAfterOrder(GlobalVars.context,
                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.FunctionFielCode.ENOUGH_MONEY_END_AFTER_ORDER)));
        }
    }

    public static void saveCommonSwitchList(Map<String, String> data) {
        if (data != null) {
            // 财务联开关
            BaseSpHelper.saveSelfPrintFinanceOrderSwitchTime(GlobalVars.context, System.currentTimeMillis());
            BaseSpHelper.saveSelfPrintFinanceOrderSwitch(GlobalVars.context, data.get(SystemDirCodeConstant.ACCOUNT_BILL));
            // 点菜单开关
            BaseSpHelper.saveSelfPrintDishesOrderSwitchTime(GlobalVars.context, System.currentTimeMillis());
            BaseSpHelper.saveSelfPrintDishesOrderSwitch(GlobalVars.context, data.get(SystemDirCodeConstant.IS_PRINT_ORDER));
            // 是否使用本地收银
            BaseSpHelper.saveWorkMode(GlobalVars.context, Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.IS_USE_LOCAL_CASH)));
            // 现金清账 外卖电话
            BaseSpHelper.saveCashCleanSwitch(GlobalVars.context, data.get(SystemDirCodeConstant.CASH_CLEAN));
            BaseSpHelper.saveCarryOutSwitch(GlobalVars.context, data.get(SystemDirCodeConstant.CLOUD_CASH_CALL));
            // 能否修改服务费开关
            BaseSpHelper.saveUpdateServiceFee(GlobalVars.context,
                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.IS_SET_MINCONSUMEMODE)));
            // 是否设置限时用餐
            BaseSpHelper.saveIsLimitTime(GlobalVars.context, Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.IS_LIMIT_TIME)));
            // 用餐超时时间
            BaseSpHelper.saveLimitTimeEnd(GlobalVars.context, data.get(SystemDirCodeConstant.LIMIT_TIME_END));
        }
    }
}
