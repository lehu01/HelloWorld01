package com.zmsoft.ccd.module.printconfig.label.fragment;

import com.ccd.lib.print.bean.LabelPrinterConfig;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.SwitchRequest;

import java.util.List;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/21 10:44
 *     desc  :
 * </pre>
 */
public class LabelPrintConfigContract {

    public interface Presenter extends BasePresenter {

        /**
         * 批量保存开关：Function
         *
         * @param entityId 实体id
         * @param codeList codeList
         * @return
         */
        void saveFunctionSwitchList(String entityId, List<SwitchRequest> codeList);

        /**
         * 保存标签打印机配置
         *
         * @param entityId    实体id
         * @param userId      用户id
         * @param printerType 打印类型
         * @param ip          ip地址
         */
        void saveLabelPrintConfig(String entityId, String userId, int printerType, String ip);

        /**
         * 检测入参
         *
         * @param type          类型
         * @param ip            ip
         * @param blueToothName 蓝牙名称
         * @return
         */
        boolean check(int type, String ip, String blueToothName);

        /**
         * 获取标签打印配置信息
         *
         * @param entityId 实体id
         * @param userId   用户id
         */
        void getLabelPrintConfig(String entityId, String userId);

    }

    public interface View extends BaseView<Presenter> {

        // 保存标签开关成功
        void saveLabelSwitchSuccess();

        // 保存标签打印机成功
        void saveLabelPrintConfigSuccess();

        // 获取ip地址
        String getIp();

        // 获取蓝牙名称
        String getBlueToothName();

        // 获取标签打印配置成功
        void getLabelPrintConfigSuccess(LabelPrinterConfig data);

        // 界面加载失败
        void showErrorLoadView(String errorMessage);

        // 提示
        void showErrorToast(String errorMessage);

    }
}
