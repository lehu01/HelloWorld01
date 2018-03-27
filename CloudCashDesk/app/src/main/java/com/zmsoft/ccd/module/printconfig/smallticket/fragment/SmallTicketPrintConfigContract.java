package com.zmsoft.ccd.module.printconfig.smallticket.fragment;

import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
public class SmallTicketPrintConfigContract {

    public interface Presenter extends BasePresenter {

        void savePrintConfig(String entityId, String userId, int printerType, String ip, int rowCharMaxMun, Short isLocalCashPrint, Short type);

        void getPrintConfig(String entityId, String userId);

        boolean check(int type, String ip, String blueToothName, String byteCount);

    }

    public interface View extends BaseView<Presenter> {

        // 错误提示
        void showKnowDialog(String message);

        // 保存成功
        void savePrintConfigSuccess(SmallTicketPrinterConfig printerSetting);

        // 显示错误页面
        void showLoadDataErrorView(String message);

        // 获取配置成功
        void getPrintConfigSuccess(SmallTicketPrinterConfig printerSetting);

        // 获取蓝牙名称
        String getBlueToothName();

        // 获取打印字符数
        String getPrintByteCount();

        // 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
        void loadDataError(String errorMessage);

        // 获取ip地址
        String getIp();
    }
}
