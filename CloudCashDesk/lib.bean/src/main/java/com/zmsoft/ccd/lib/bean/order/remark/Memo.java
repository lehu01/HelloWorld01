package com.zmsoft.ccd.lib.bean.order.remark;

import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;

/**
 * Description：客单备注列表vo
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/5 19:28
 */
public class Memo extends Reason {

    private boolean isCheck; // 是否选中

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
