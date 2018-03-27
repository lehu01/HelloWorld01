package com.ccd.lib.print.constants;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/12/14 15:02
 *     desc  : 打印类型type相关管理
 * </pre>
 */
public interface PrintConfigConstant {

    interface PrinterType {
        // 网口
        int PRINT_TYPE_NET = 2;
        // 蓝牙打印
        int PRINT_TYPE_BLUE_TOOTH = 1;
        // 本机打印
        int PRINT_TYPE_LOCAL = 3;
        // usb打印
        int PRINT_TYPE_USB = 4;
    }

    interface BlueToothType {
        // 默认
        int TYPE_DEFAULT = 0;
        // 商米
        int TYPE_SUN_MI = 1;
        // 通联
        int TYPE_ALL_IN = 2;
    }

    interface TicketType {
        // 小票
        int TYPE_SMALL_TICKET = 1;
        // 标签
        int TYPE_LABEL_TICKET = 2;
    }
}
