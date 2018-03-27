package com.zmsoft.ccd.module.receipt.receiptway.onaccount.model;

import com.zmsoft.ccd.receipt.bean.SignInfoVo;

/**
 * @author DangGui
 * @create 2017/6/17.
 */

public class SignStaffItem {
    private String name;
    private boolean checked;
    private SignInfoVo mSignInfoVo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public SignInfoVo getSignInfoVo() {
        return mSignInfoVo;
    }

    public void setSignInfoVo(SignInfoVo signInfoVo) {
        mSignInfoVo = signInfoVo;
    }
}
