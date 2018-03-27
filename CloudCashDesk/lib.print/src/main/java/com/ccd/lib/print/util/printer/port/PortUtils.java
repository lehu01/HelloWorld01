package com.ccd.lib.print.util.printer.port;

import com.ccd.lib.print.R;
import com.ccd.lib.print.helper.PrintChooseHelper;
import com.ccd.lib.print.util.printer.PrinterUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/2 11:00
 *     desc  : 网口打印工具类
 * </pre>
 */
public class PortUtils {

    /**
     * 端口号
     */
    public final static int PRINTER_PORT = 9100;

    /**
     * 网口打印
     *
     * @param printerIp  ip地址
     * @param port       端口号
     * @param content    内容byte
     * @param retryIndex 打印index
     * @param retryTimes 打印时间
     */
    public static boolean printByPort(String printerIp, int port, byte[] content, int retryIndex, int retryTimes) {
        boolean printResult = false;
        Socket client = new Socket();
        try {
            if (StringUtils.isEmpty(printerIp)) {
                if (!PrintChooseHelper.getNoPrompt()) {
                    PrinterUtils.showGuideDialog();
                } else {
                    ToastUtils.showToastInWorkThread(context, context.getString(R.string.please_set_printer));
                }
                return printResult;
            }
            client.connect(new InetSocketAddress(printerIp, port), 3000);
            OutputStream printerOut = client.getOutputStream();
            printerOut.write(content);
            printerOut.flush();
            return true;
        } catch (Exception e) {
            int retry = retryIndex;
            retry++;
            if (retry != retryTimes) {
                printByPort(printerIp, port, content, retry, retryTimes);
            }
            if (retry == retryTimes) {
                ToastUtils.showToastInWorkThread(context, context.getString(R.string.printer_send_error));
            }
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return printResult;
    }
}
