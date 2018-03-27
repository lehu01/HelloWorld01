package com.zmsoft.lib.pos.common.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.allinpay.usdk.core.data.ResponseData;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.lib.pos.R;
import com.zmsoft.lib.pos.allin.AllinConstant;
import com.zmsoft.lib.pos.allin.helper.AllInDataHelper;
import com.zmsoft.lib.pos.common.PosCallBack;
import com.zmsoft.lib.pos.common.constant.PosRequestCodeConstant;
import com.zmsoft.lib.pos.newland.NewLandConstant;
import com.zmsoft.lib.pos.newland.bean.NewLandResponse;
import com.zmsoft.lib.pos.newland.helper.NewLandDataHelper;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/27 15:29
 *     desc  : pos回调数据处理
 * </pre>
 */
public class PosReceiveDataHelper {

    private PosCallBack mPosCallBack;
    private static PosReceiveDataHelper mInstance;

    private PosReceiveDataHelper() {

    }

    public static PosReceiveDataHelper getInstance() {
        if (mInstance == null) {
            synchronized (PosReceiveDataHelper.class) {
                if (mInstance == null) {
                    mInstance = new PosReceiveDataHelper();
                }
            }
        }
        return mInstance;
    }

    public void doReceive(int requestCode, int resultCode, Intent data, PosCallBack posCallBack) {
        mPosCallBack = posCallBack;
        if (requestCode == PosRequestCodeConstant.PAY_CODE_BY_NEW_LAND || requestCode == PosRequestCodeConstant.CANCEL_PAY_CODE_BY_NEW_LAND) { // 新大陆支付
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        String msgTp = bundle.getString(NewLandConstant.Key.MSG_TP);
                        if (TextUtils.equals(msgTp, NewLandConstant.Code.PAY_REPLY)) {
                            if (requestCode == PosRequestCodeConstant.PAY_CODE_BY_NEW_LAND) {
                                NewLandResponse response = NewLandDataHelper.receiveData(bundle);
                                if (response != null) {
                                    if (mPosCallBack != null) {
                                        mPosCallBack.onSuccessPayByNewLand(response);
                                    }
                                }
                            } else {
                                if (mPosCallBack != null) {
                                    mPosCallBack.onSuccessCancelPayByNewLand();
                                }
                            }
                        }
                        break;
                }
            }
        } else if (requestCode == PosRequestCodeConstant.PAY_CODE_BY_ALL_IN || requestCode == PosRequestCodeConstant.CANCEL_PAY_CODE_BY_ALL_IN) { // 通联
            ResponseData responseData = AllInDataHelper.receiveData(requestCode, resultCode, data);
            if (responseData == null) {
                return;
            }
            String rejCode = responseData.getValue(ResponseData.REJCODE);
            if (AllinConstant.TransType.SUCCESS.equals(rejCode)) {
                if (requestCode == PosRequestCodeConstant.PAY_CODE_BY_ALL_IN) {
                    if (mPosCallBack != null) {
                        mPosCallBack.onSuccessPayByAllIn(responseData);
                    }
                } else {
                    if (mPosCallBack != null) {
                        mPosCallBack.onSuccessCancelPayByAllIn(responseData);
                    }
                }
            } else if (AllinConstant.TransType.CANCEL.equals(rejCode)) {
                if (mPosCallBack != null) {
                    mPosCallBack.onSuccessCancelPayByAllIn(responseData);
                }
            } else {
                if (mPosCallBack != null) {
                    if (AllinConstant.RejCode.NO_RECORD.equalsIgnoreCase(rejCode)
                            || AllinConstant.RejCode.NO_PAY_RECORD.equalsIgnoreCase(rejCode)) {
                        mPosCallBack.onErrorMessage(GlobalVars.context.getString(R.string.all_in_cancel_pay_failure));
                    } else {
                        mPosCallBack.onErrorMessage(StringUtils.notNull(responseData.getValue(ResponseData.REJCODE_CN)));
                    }
                }
            }
        }
    }
}
