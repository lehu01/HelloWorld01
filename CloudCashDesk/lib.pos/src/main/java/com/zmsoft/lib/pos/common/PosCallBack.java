package com.zmsoft.lib.pos.common;

import com.allinpay.usdk.core.data.ResponseData;
import com.zmsoft.lib.pos.newland.bean.NewLandResponse;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/27 15:29
 *     desc  : pos回调接口定义
 * </pre>
 */
public interface PosCallBack {

    void onSuccessPayByNewLand(NewLandResponse response);

    void onSuccessCancelPayByNewLand();

    void onSuccessPayByAllIn(ResponseData responseData);

    void onSuccessCancelPayByAllIn(ResponseData responseData);

    void onErrorMessage(String message);
}