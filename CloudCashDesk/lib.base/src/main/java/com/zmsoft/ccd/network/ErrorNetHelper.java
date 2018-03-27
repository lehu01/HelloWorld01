package com.zmsoft.ccd.network;

import com.dfire.mobile.network.exception.ConnectNetworkException;
import com.dfire.mobile.network.exception.NetworkException;
import com.dfire.mobile.network.exception.SSLNetworkException;
import com.dfire.mobile.network.exception.ServerNetworkException;
import com.dfire.mobile.network.exception.TimeoutNetworkException;
import com.dfire.mobile.util.JsonParseException;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.R;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * Description：网络异常
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/18 11:56
 */
public class ErrorNetHelper {

    private static Map<String, String> mErrorMap = new HashMap<>();

    static {
        mErrorMap.put("CLOUDCASH_1000", GlobalVars.context.getString(R.string.http_net_error));
        mErrorMap.put("SYSTEM_DEFAULT_ERROR", GlobalVars.context.getString(R.string.http_net_error));
        mErrorMap.put("ERR_DP0040", GlobalVars.context.getString(R.string.http_net_error));
    }

    public static String getErrorMessage(String errorCode, String errorMessage) {
        String result = mErrorMap.get(errorCode);
        return StringUtils.isEmpty(result) ? errorMessage : result;
    }

    public static String getNetMessage(Exception e) {
        String result;
        if (e instanceof ConnectNetworkException || e instanceof UnknownHostException || e instanceof ConnectException) {
            result = GlobalVars.context.getString(R.string.network_exception_connect);
        } else if (e instanceof TimeoutNetworkException || e instanceof InterruptedIOException) {
            result = GlobalVars.context.getString(R.string.network_exception_timeout);
        } else if (e instanceof ServerNetworkException) {
            ServerNetworkException serverNetworkException = (ServerNetworkException) e;
            if (null != serverNetworkException.networkResponse) {
                result = String.format(context.getResources().getString(R.string.network_exception_server)
                        , serverNetworkException.networkResponse.statusCode + "");
            } else {
                result = String.format(context.getResources().getString(R.string.network_exception_server)
                        , "Unknown");
            }
        } else if (e instanceof SSLNetworkException) {
            result = GlobalVars.context.getString(R.string.network_exception_ssl);
        } else if (e instanceof IOException) {
            result = String.format(context.getResources().getString(R.string.network_exception_server)
                    , "Unknown");
        } else if (e instanceof NetworkException) {
            result = GlobalVars.context.getString(R.string.network_exception_server_access);
        } else if (e instanceof JsonParseException) {
            result = GlobalVars.context.getString(R.string.data_parse_error);
        } else {
            result = GlobalVars.context.getString(R.string.http_net_error);
        }
        return result;
    }
}
