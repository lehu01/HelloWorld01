package com.zmsoft.ccd.module.workmodel.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/24 14:42
 */
public class WorkModelContract {

    public interface Presenter extends BasePresenter {

        // 获取工作模式
        void getWorkModelConfig(String entityId, List<String> codeList);

        // 检测收银本地版本
        void checkCashSupportVersion(String entityId);

        // 保存工作模式
        void saveWorkModelConfig(String entityId, boolean openCloudCash, boolean useLocalCash, String userId);
    }

    public interface View extends BaseView<Presenter> {

        // 显示弹窗提示
        void showDialogPrompt(String errorMessage);

        // 获取工作模式成功
        void getWorkModelConfigSuccess(Map<String, String> map);

        // 显示错误页面，重新加载
        void showLoadDataErrorView(String errorMessage);

        // 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
        void loadDataError(String errorMessage);

        // 本地收银版本支持
        void localCashVersionSupport();

        // 保存工作模式成功
        void saveWorkModelConfigSuccess(Boolean isSuccess);
    }
}
