package com.zmsoft.ccd.network;

import com.dfire.mobile.network.RequestModel;
import com.zmsoft.ccd.app.AppEnv;

import java.util.Map;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/5 15:19
 */
public class HttpHelper {

    public static RequestModel getDefaultRequestModel(Map<String, Object> map, String method) {
        RequestModel.Builder builder = RequestModel.post(AppEnv.getGateWayUrl())
                .addParameters(map)
                .addUrlParameter(CommonConstant.PARA_METHOD, method);// 将method参数放日get的url内，这样nds做动态分配需要，才能获取到
        return builder.build();
    }

    /**
     * @param map
     * @param method
     * @param timeOutMillSec 超时时间（connectTimeOut/readTimeOut）
     * @return
     */
    public static RequestModel getDefaultRequestModel(Map<String, Object> map, String method, long timeOutMillSec) {
        RequestModel.Builder builder = RequestModel.post(AppEnv.getGateWayUrl())
                .addParameters(map)
                .connectTimeout(timeOutMillSec)
                .readTimeout(timeOutMillSec)
                .addUrlParameter(CommonConstant.PARA_METHOD, method);// 将method参数放日get的url内，这样nds做动态分配需要，才能获取到
        return builder.build();
    }

    /**
     * 公共返回错误码，链接地址http://k.2dfire.net/pages/viewpage.action?pageId=265191648
     */
    public static class HttpErrorCode {
        public static final String ERR_PUB00001 = "ERR_PUB00001";//网络连接失败
        public static final String ERR_PUB200001 = "ERR_PUB200001";//访问令牌为空
        public static final String ERR_PUB200002 = "ERR_PUB200002";// 无效的访问令牌(被踢下线)
        public static final String ERR_PUB200003 = "ERR_PUB200003";//  访问令牌已过期
        //public static final String ERR_PERMISSION_ERROR = "600003";// 没有权限code
        public static final String ERR_PERMISSION_ERROR = "ERR_PUB600003";// 没有权限code

    }
}
