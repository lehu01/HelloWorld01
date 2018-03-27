package com.zmsoft.ccd.lib.bean.user;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/29 10:18
 *     desc  :
 * </pre>
 */
public class SmsCode extends Base {


    /**
     * message : 手机号已经注册！
     * isRegister : 0
     * status : 0
     * isPopMessage : 0
     * memberId : ajsajsgdasdslkj
     */

    private String message;
    private int isRegister;
    private int status;
    private int isPopMessage;
    private String memberId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(int isRegister) {
        this.isRegister = isRegister;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsPopMessage() {
        return isPopMessage;
    }

    public void setIsPopMessage(int isPopMessage) {
        this.isPopMessage = isPopMessage;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
