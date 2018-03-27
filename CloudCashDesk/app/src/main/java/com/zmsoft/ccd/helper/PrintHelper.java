package com.zmsoft.ccd.helper;

import com.ccd.lib.print.constants.PrintConfigConstant;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.bean.print.PrintRowCount;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：打印字符数
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/16 20:13
 */
public class PrintHelper {
    // 打印客户联
    public static final int TYPE_PRINT_ACCOUNT_ORDER = 1031;
    // 打印点菜单
    public static final int TYPE_PRINT_DISHES_ORDER = 1032;

    /**
     * 获取打印字符数
     */
    public static List<PrintRowCount> getPrintByteList() {
        int[] str = new int[]{32, 33, 38, 40, 42, 48, 64};
        List<PrintRowCount> list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            PrintRowCount rowCount = new PrintRowCount();
            rowCount.setValue(str[i]);
            rowCount.setDisplayName(StringUtils.appendStr(str[i], "mm"));
            list.add(rowCount);
        }
        return list;
    }

    /**
     * 获取选中的位置
     */
    public static int getCheckIndex(String name, List<PrintRowCount> data) {
        int index = 0;
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getDisplayName().equals(name)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }


    /**
     * 是否使用本地打印
     *
     * @param printType    打印机类型
     * @param isLocalPrint 网口，单独开关
     */
    public static void saveLocalCashPrint(int printType, boolean isLocalPrint) {
        switch (printType) {
            case PrintConfigConstant.PrinterType.PRINT_TYPE_NET:
                BaseSpHelper.saveLocalPrint(GlobalVars.context, isLocalPrint);
                break;
            case PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH:
            case PrintConfigConstant.PrinterType.PRINT_TYPE_LOCAL:
                BaseSpHelper.saveLocalPrint(GlobalVars.context, false);
                break;
            case PrintConfigConstant.PrinterType.PRINT_TYPE_USB:
                BaseSpHelper.saveLocalPrint(GlobalVars.context, true);
                break;

        }
    }
}
