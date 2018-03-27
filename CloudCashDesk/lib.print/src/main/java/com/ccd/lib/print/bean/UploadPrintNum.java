package com.ccd.lib.print.bean;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/12 10:24
 */
public class UploadPrintNum extends Base {

    public static final int PRINT_ACCOUNT = 0; // 客户联
    public static final int PRINT_FINANCE = 1; // 财务联
    public static final int PRINT_ACOUNT_FINANCE = 2;// 客户联，财务联

    private int successNum;

    public int getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(int successNum) {
        this.successNum = successNum;
    }
}
