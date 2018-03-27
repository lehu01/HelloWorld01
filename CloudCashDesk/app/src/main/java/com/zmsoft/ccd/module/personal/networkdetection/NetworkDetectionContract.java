package com.zmsoft.ccd.module.personal.networkdetection;

import android.content.Context;
import android.widget.ImageView;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * @author mantianxing
 * @create 2017/12/15.
 */

public interface NetworkDetectionContract {

    interface View extends BaseView<presenter> {

        /**
         * 检测网络成功
         */
        void detectionSuccess(long duration);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         */
        void loadDataError(String errorMessage);
    }

    interface presenter extends BasePresenter {

        /**
         * 网络检测
         */
        int networkDetection(String entityId, String appCode, int version,int count, long sumDuration);

        /**
         * 检测网络是否连接
         */
        boolean isNetworkAvailable();

    }
}
