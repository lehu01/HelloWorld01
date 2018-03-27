package com.zmsoft.ccd.network;

import com.dfire.sdk.util.MD5Util;

import java.util.Arrays;
import java.util.Map;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/8 13:50
 */
public class SignUtil {

    public static String sign(Map<String, Object> paramsMap, String secret) {
        String[] keys = paramsMap.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            if (key.equals(CommonConstant.PARA_SIGN)) {
                continue;
            }
            Object v = paramsMap.get(key);
            if (null == v) {
                continue;
            }
            sb.append(key).append(v);
        }
        sb.append(secret);

        //return MD5Util.MD5Encode(sb.toString());
        //使用 dfire.sdk里的MD5Util
        return MD5Util.encode(sb.toString());
    }

}
