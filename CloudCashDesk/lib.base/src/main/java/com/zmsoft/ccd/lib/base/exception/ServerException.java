package com.zmsoft.ccd.lib.base.exception;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/6/16.
 */

public class ServerException extends Throwable {

    private static final String INNER_ERROR_CODE = "INNER_ERROR_CODE";
    private static final String INNEE_ERROR_MESSAGE = "Network Error Data Exception";
    private String errorCode;
    private String message;

    public ServerException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ServerException convertIfSame(Throwable throwable) {
        if (throwable instanceof ServerException) {
            return (ServerException) throwable;
        } else {
            return new ServerException(INNER_ERROR_CODE, INNEE_ERROR_MESSAGE);
        }
    }
}
