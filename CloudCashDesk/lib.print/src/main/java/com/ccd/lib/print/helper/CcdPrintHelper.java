package com.ccd.lib.print.helper;

import android.content.Context;
import android.content.Intent;

import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.ccd.lib.print.model.PrintData;
import com.ccd.lib.print.service.CcdPrintService;
import com.ccd.lib.print.service.LabelCcdPrintService;
import com.zmsoft.ccd.lib.base.helper.RetailOrderHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.util.List;

/**
 * Description：打印帮助类
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/22 11:09
 */
public class CcdPrintHelper {

    //==================================================================================
    // 小票打印
    //==================================================================================
    public static void manualPrintOrder(Context context, int bizType, String orderId) {
        printOrder(context, PrintData.TYPE_ORDER, bizType, orderId, "");
    }

    public static void reprintOrder(Context context, int bizType, String orderId) {
        printOrder(context, PrintData.TYPE_ORDER, bizType, orderId, "", Base.INT_TRUE);
    }

    public static void printOrder(Context context, int type, int bizType, String orderId, String seatCode) {
        printOrder(context, type, bizType, orderId, seatCode, Base.INT_FALSE);
    }

    public static void printOrder(Context context, int type, int bizType, String orderId, String seatCode, int reprint) {
        Intent intent = new Intent(context, CcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setType(type);
        printData.setBizType(bizType);
        printData.setOrderId(orderId);
        printData.setUserId(UserHelper.getUserId());
        printData.setEntityId(UserHelper.getEntityId());
        printData.setReprint(reprint);
        // 判断是否是零售单seatCode
        if (StringUtils.isEmpty(seatCode) || seatCode.contains(RetailOrderHelper.SEAT_CODE_BY_RETAIL)) {
            printData.setSeatCode("");
            printData.setOrderKind(1);
        } else {
            printData.setSeatCode(seatCode);
        }
        intent.putExtra(CcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }

    public static void printOrderByLoadFilePath(Context context, String loadFilePath, String seatCode
            , String orderId, int orderKind, int bizType, String areaIp, String taskId) {
        Intent intent = new Intent(context, CcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setLoadFilePath(loadFilePath);
        printData.setSeatCode(seatCode);
        printData.setOrderId(orderId);
        printData.setOrderKind(orderKind);
        printData.setBizType(bizType);
        printData.setAreaIp(areaIp);
        printData.setPrintTaskId(taskId);
        intent.putExtra(CcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }

    public static void printInstance(Context context, int type, int bizType, List<String> instanceIds) {
        printInstance(context, type, bizType, instanceIds, "", "");
    }

    public static void printInstance(Context context, int type, int bizType, List<String> instanceIds, String seatCode, String orderId) {
        printInstance(context, type, bizType, instanceIds, seatCode, orderId, Base.INT_FALSE);
    }

    public static void reprintInstance(Context context, int type, int bizType, List<String> instanceIds) {
        printInstance(context, type, bizType, instanceIds, "", "", Base.INT_TRUE);
    }

    public static void printInstance(Context context, int type, int bizType, List<String> instanceIds, String seatCode, String orderId, int reprint) {
        Intent intent = new Intent(context, CcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setType(type);
        printData.setBizType(bizType);
        printData.setUserId(UserHelper.getUserId());
        printData.setEntityId(UserHelper.getEntityId());
        printData.setInstanceIds(instanceIds);
        printData.setReprint(reprint);
        // 判断是否是零售单seatCode
        if (StringUtils.isEmpty(seatCode) || seatCode.contains(RetailOrderHelper.SEAT_CODE_BY_RETAIL)) {
            printData.setSeatCode("");
            printData.setOrderKind(1);
        } else {
            printData.setSeatCode(seatCode);
        }
        printData.setOrderId(orderId);
        intent.putExtra(CcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }

    public static void printSmallTicketTest(Context context, String ip, byte[] content, boolean isLocalPrint, int type, int rowCount
            , SmallTicketPrinterConfig config) {
        Intent intent = new Intent(context, CcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setIp(ip);
        printData.setRawData(content);
        printData.setLocalPrint(isLocalPrint);
        printData.setLocalType(type);
        printData.setRowCount(rowCount);
        printData.setPrinterConfig(config);
        intent.putExtra(CcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }

    //==================================================================================
    // 标签打印
    //==================================================================================
    public static void printLabelTest(Context context, String ip, byte[] content
            , int printType, SmallTicketPrinterConfig config) {
        Intent intent = new Intent(context, LabelCcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setIp(ip);
        printData.setRawData(content);
        printData.setLocalType(printType);
        printData.setPrinterConfig(config);
        intent.putExtra(LabelCcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }

    public static void printLabelOrderInstance(Context context, int bizType, String orderId) {
        Intent intent = new Intent(context, LabelCcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setType(PrintData.TYPE_SELF_ORDER);
        printData.setBizType(bizType);
        printData.setOrderId(orderId);
        printData.setUserId(UserHelper.getUserId());
        printData.setEntityId(UserHelper.getEntityId());
        intent.putExtra(LabelCcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }

    public static void printLabelInstance(Context context, int bizType, List<String> instanceIds) {
        Intent intent = new Intent(context, LabelCcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setType(PrintData.TYPE_ORDER);
        printData.setBizType(bizType);
        printData.setUserId(UserHelper.getUserId());
        printData.setEntityId(UserHelper.getEntityId());
        printData.setInstanceIds(instanceIds);
        intent.putExtra(LabelCcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }

    public static void printLabelByLoadFilePath(Context context, String loadFilePath, String seatCode, String orderId, int orderKind) {
        Intent intent = new Intent(context, LabelCcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setLoadFilePath(loadFilePath);
        printData.setSeatCode(seatCode);
        printData.setOrderId(orderId);
        printData.setOrderKind(orderKind);
        intent.putExtra(LabelCcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }

    //==================================================================================
    // 账单汇总
    //==================================================================================
    public static void printBill(Context context, int type) {
        Intent intent = new Intent(context, CcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setType(PrintData.TYPE_ORDER);
        printData.setBizType(PrintData.BIZ_TYPE_PRINT_BILL);
        printData.setBillType(type);
        intent.putExtra(CcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }

    //==================================================================================
    // 转桌
    //==================================================================================
    public static void printChangeSeat(Context context, String orderId, String oldSeatCode) {
        Intent intent = new Intent(context, CcdPrintService.class);
        PrintData printData = new PrintData();
        printData.setOrderId(orderId);
        printData.setSeatCode(oldSeatCode);
        printData.setType(PrintData.TYPE_ORDER);
        printData.setBizType(PrintData.BIZ_TYPE_PRINT_CHANGE_SEAT);
        intent.putExtra(CcdPrintService.PRINT_DATA, printData);
        context.startService(intent);
    }
}
