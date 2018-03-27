package com.zmsoft.ccd.data.spdata;

import com.ccd.lib.print.util.printer.LocalPrinterUtils;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/18 15:53
 *     desc  : 打印机类型数据管理器
 * </pre>
 */
public class PrintTypeDataManager {


    public static String[] getLabelPrintType() {
        List<String> list = new ArrayList<>();
        list.add(GlobalVars.context.getString(R.string.net_printer));
        list.add(GlobalVars.context.getString(R.string.blue_booth_printer));
        return list.toArray(new String[list.size()]);
    }

    public static String[] getSmallTicketPrintType() {
        List<String> list = new ArrayList<>();
        list.add(GlobalVars.context.getString(R.string.net_printer));
        list.add(GlobalVars.context.getString(R.string.blue_booth_printer));
        if (LocalPrinterUtils.isSupportLocalPrinter()) {
            list.add(GlobalVars.context.getString(R.string.local_printer));
        }
        if (BaseSpHelper.isHybrid(GlobalVars.context)) {
            list.add(GlobalVars.context.getString(R.string.usb_print));
        }
        return list.toArray(new String[list.size()]);
    }

}
