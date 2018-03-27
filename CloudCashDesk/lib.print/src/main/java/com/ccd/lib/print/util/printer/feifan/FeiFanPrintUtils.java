package com.ccd.lib.print.util.printer.feifan;

import com.ffan.printer10.PrintSdk;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/2 10:53
 *     desc  : 非凡打印机
 * </pre>
 */
public class FeiFanPrintUtils {

    public static boolean printByLocal(byte[] content) {
        return printByLocal(content, false);
    }

    public static boolean printByLocal(byte[] content, boolean shouldOpenMoneyBox) {
        try {
            if (shouldOpenMoneyBox) {
                addOpenMoneyBoxCode(content);
            }
            PrintSdk.getInstance().print(content);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加打开钱箱打印机编码
     *
     * @param finalData
     */
    private static void addOpenMoneyBoxCode(byte[] finalData) {
        finalData[finalData.length - 10] = 0x1b;
        finalData[finalData.length - 9] = 0x70;
        finalData[finalData.length - 8] = 0x00;
        finalData[finalData.length - 7] = 0x32;
        finalData[finalData.length - 6] = 0x32;

        finalData[finalData.length - 5] = 0x1b;
        finalData[finalData.length - 4] = 0x70;
        finalData[finalData.length - 3] = 0x01;
        finalData[finalData.length - 2] = 0x32;
        finalData[finalData.length - 1] = 0x32;
    }

    private static byte[] getOpenMoneyData() {
        byte[] data = new byte[10];
        data[0] = 0x1b;
        data[1] = 0x70;
        data[2] = 0x00;
        data[3] = 0x32;
        data[4] = 0x32;

        data[5] = 0x1b;
        data[6] = 0x70;
        data[7] = 0x01;
        data[8] = 0x32;
        data[9] = 0x32;
        return data;
    }

    public static void openMoneyBox() {
        try {
            PrintSdk.getInstance().print(getOpenMoneyData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
