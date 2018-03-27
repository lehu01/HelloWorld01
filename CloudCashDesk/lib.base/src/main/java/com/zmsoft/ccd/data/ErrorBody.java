package com.zmsoft.ccd.data;

import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.R;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 17/02/2017.
 */
public class ErrorBody {

    private String mErrorCode = "";
    private String mMessage;

    public ErrorBody(String message) {
        mMessage = message;
    }


    public ErrorBody(String errorCode, String message) {
        mErrorCode = errorCode;
        mMessage = message;
    }

    public String getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(String errorCode) {
        mErrorCode = errorCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public static ErrorBody buildEmptyBody() {
        return new ErrorBody(GlobalVars.context.getString(R.string.empty_data));
    }

    public static ErrorBody buildBody(String message) {
        return new ErrorBody(message);
    }
}
