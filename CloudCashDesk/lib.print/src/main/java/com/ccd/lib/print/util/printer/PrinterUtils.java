package com.ccd.lib.print.util.printer;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ccd.lib.print.bean.LabelPrinterConfig;
import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.ccd.lib.print.dialog.SetPrinterDialogFragment;
import com.ccd.lib.print.manager.LabelPrintManager;
import com.ccd.lib.print.manager.SmallTicketPrinterManager;
import com.ccd.lib.print.constants.PrintConfigConstant;
import com.ccd.lib.print.util.printer.bluetooth.BlueToothPrinterUtils;
import com.ccd.lib.print.util.printer.feifan.FeiFanPrintUtils;
import com.ccd.lib.print.util.printer.port.PortUtils;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.zmsoft.ccd.lib.utils.StringUtils;

/**
 * Description：打印工具类
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/3 10:59
 */
public class PrinterUtils {

    //=======================================================================================
    // 小票打印
    //=======================================================================================
    public static boolean print(final byte[] content) {
        SmallTicketPrinterConfig printConfig = SmallTicketPrinterManager.getPrinterSetting();
        return print(printConfig.getIp(), printConfig.getPrinterType(), content, printConfig);
    }

    public static boolean print(final String printIp, final int printType, final byte[] content, SmallTicketPrinterConfig printerConfig) {
        return print(printIp, printType, content, printerConfig, 3);
    }

    public static boolean print(final String printIp, final int printType, final byte[] content, SmallTicketPrinterConfig printerConfig, int retryCount) {
        if (content == null || content.length == 0 || printerConfig == null) {
            return false;
        }
        switch (printType) {
            case PrintConfigConstant.PrinterType.PRINT_TYPE_NET:
                return PortUtils.printByPort(printIp, PortUtils.PRINTER_PORT, content, 0, retryCount);
            case PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH:
                return BlueToothPrinterUtils.printByBluetooth(content, printerConfig);
            case PrintConfigConstant.PrinterType.PRINT_TYPE_LOCAL:
                return FeiFanPrintUtils.printByLocal(content);
        }
        return false;
    }

    //=======================================================================================
    // 标签打印
    //=======================================================================================
    public static boolean printByLabel(final byte[] content) {
        LabelPrinterConfig printerConfig = LabelPrintManager.getPrinterSetting();
        return printByLabel(StringUtils.notNull(printerConfig.getIp()), printerConfig.getPrinterType(), content, printerConfig);
    }

    public static boolean printByLabel(final String printIp, final int printType, final byte[] content, SmallTicketPrinterConfig printerConfig) {
        return printByLabel(printIp, printType, content, printerConfig, 3);
    }

    private static boolean printByLabel(final String printerIp, final int printType, final byte[] content, SmallTicketPrinterConfig printerConfig, final int retryCount) {
        if (content == null || content.length == 0 || printerConfig == null) {
            return false;
        }
        switch (printType) {
            case PrintConfigConstant.PrinterType.PRINT_TYPE_NET:
                return PortUtils.printByPort(printerIp, PortUtils.PRINTER_PORT, content, 0, retryCount);
            case PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH:
                return BlueToothPrinterUtils.printByBluetooth(content, printerConfig);
        }
        return false;
    }

    //=======================================================================================
    // 显示引导配置打印机弹窗
    //=======================================================================================
    public static void showGuideDialog() {
        AppCompatActivity currentActivity = (AppCompatActivity) RouterActivityManager.get().getActivity();
        FragmentTransaction fragmentTransaction = currentActivity.getSupportFragmentManager().beginTransaction();
        SetPrinterDialogFragment dialogFragment =
                (SetPrinterDialogFragment) currentActivity.getSupportFragmentManager().findFragmentByTag(SetPrinterDialogFragment.FRAGMENT_TAG);
        if (dialogFragment == null) {
            dialogFragment = new SetPrinterDialogFragment();
            dialogFragment.show(fragmentTransaction, SetPrinterDialogFragment.FRAGMENT_TAG);
        }
    }
}
