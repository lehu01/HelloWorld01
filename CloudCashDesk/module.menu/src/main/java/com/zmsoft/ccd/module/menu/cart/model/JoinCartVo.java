package com.zmsoft.ccd.module.menu.cart.model;

/**
 * 加入购物车
 *
 * @author DangGui
 * @create 2017/4/26.
 */

public class JoinCartVo {
    private String resultCode;
    private String resultMsg;
    private String exMsg;
    private String userCartId;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getExMsg() {
        return exMsg;
    }

    public void setExMsg(String exMsg) {
        this.exMsg = exMsg;
    }

    public String getUserCartId() {
        return userCartId;
    }

    public void setUserCartId(String userCartId) {
        this.userCartId = userCartId;
    }
}
