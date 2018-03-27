package com.zmsoft.ccd.network;

import android.text.TextUtils;

import com.dfire.mobile.network.Callback;
import com.dfire.mobile.network.exception.ConnectNetworkException;
import com.dfire.mobile.network.exception.NetworkException;
import com.dfire.mobile.network.exception.SSLNetworkException;
import com.dfire.mobile.network.exception.ServerNetworkException;
import com.dfire.mobile.network.exception.TimeoutNetworkException;
import com.dfire.mobile.util.JavaTypeToken;
import com.dfire.mobile.util.JsonMapper;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.R;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/3/3.
 */

public abstract class CcdNetCallBack<T> extends Callback<String> {
    @Override
    protected void onSuccess(String data) {
        //根据TypeToken找到泛型对应的实体类
        Type ParameterizedTypeInner = JavaTypeToken.getParameterizedType(HttpBean.class, getLocalType());
        Type ParameterizedTypeOuter = JavaTypeToken.getParameterizedType(HttpResult.class, ParameterizedTypeInner);
        HttpResult<HttpBean<?>> httpResult = null;
        try {
            httpResult = JsonMapper.fromJson(data, ParameterizedTypeOuter);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(e.toString());
            onError(HttpHelper.HttpErrorCode.ERR_PUB00001, GlobalVars.context.getString(R.string.json_parse_exception));
            return;
        }

        //如果服务端返回的数据是Null，或者网络是断开状态，调用onFailure
        if (null == httpResult) {
            onFailure(new RuntimeException());
        } else {
            String errorCode = httpResult.getErrorCode();
            //过滤需要特殊处理的错误码
            if (!TextUtils.isEmpty(errorCode)) {
                switch (errorCode) {
                    case HttpHelper.HttpErrorCode.ERR_PUB200001: //访问令牌为空
                    case HttpHelper.HttpErrorCode.ERR_PUB200002:// 无效的访问令牌
                    case HttpHelper.HttpErrorCode.ERR_PUB200003://访问令牌已过期(被踢下线)
                        logOut(errorCode);
                        return;
                    default:
                        break;
                }
            }
            //接口调用是否成功，0：失败，1：成功
            int code = httpResult.getCode();
            //如果接口调用失败，调用onError
            if (code == HttpResult.FAILURE) {
                onError(httpResult.getErrorCode(), ErrorNetHelper.getErrorMessage(httpResult.getErrorCode(), httpResult.getMessage()));
                return;
            }
            //如果HttpResult下层的HttpBean是Null，调用onError
            if (null == httpResult.getData()) {
                onError(httpResult.getErrorCode(), ErrorNetHelper.getErrorMessage(httpResult.getErrorCode(), httpResult.getMessage()));
            } else {
                //无论HttpBean下层的bean实体类是否是Null，都将结果通过onData回调给调用者
                if (null == httpResult.getData().getData()) {
                    onData(null);
                } else {
                    onData((T) httpResult.getData().getData());
                }
            }
        }
    }

    @Override
    protected void onFailure(Throwable t) {
        t.printStackTrace();
        String errorMsg;
        if (t instanceof ConnectNetworkException) {
            errorMsg = GlobalVars.context.getString(R.string.network_exception_connect);
        } else if (t instanceof TimeoutNetworkException) {
            errorMsg = GlobalVars.context.getString(R.string.network_exception_timeout);
        } else if (t instanceof ServerNetworkException) {
            ServerNetworkException serverNetworkException = (ServerNetworkException) t;
            if (null != serverNetworkException.networkResponse) {
                errorMsg = String.format(context.getResources().getString(R.string.network_exception_server)
                        , serverNetworkException.networkResponse.statusCode + "");
            } else {
                errorMsg = String.format(context.getResources().getString(R.string.network_exception_server)
                        , "Unknown");
            }
        } else if (t instanceof SSLNetworkException) {
            errorMsg = GlobalVars.context.getString(R.string.network_exception_ssl);
        } else if (t instanceof NetworkException) {
            errorMsg = GlobalVars.context.getString(R.string.network_exception_server_access);
        } else {
            errorMsg = GlobalVars.context.getString(R.string.http_net_error);
        }
        ToastUtils.showShortToast(GlobalVars.context, errorMsg);
        onError(HttpHelper.HttpErrorCode.ERR_PUB00001, errorMsg);
    }

    /**
     * 成功的数据回调，将需要的实体类回调给调用者
     *
     * @param t 实体类
     */
    protected abstract void onData(T t);

    /**
     * 失败的数据回调，将错误码、错误消息回调给调用者
     *
     * @param errorCode 错误码
     * @param errorMsg  错误消息
     */
    protected abstract void onError(String errorCode, String errorMsg);

    private Type getLocalType() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        Type paramType = types[0];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getUpperBounds()[0];
        }
        return paramType;
    }

    /**
     * 退出登录
     */
    private void logOut(String errorCode) {
        RouterBaseEvent.CommonEvent logoutEvent = RouterBaseEvent.CommonEvent.EVENT_LOG_OUT;
        logoutEvent.setObject(errorCode);
        EventBusHelper.post(logoutEvent);
    }
}
