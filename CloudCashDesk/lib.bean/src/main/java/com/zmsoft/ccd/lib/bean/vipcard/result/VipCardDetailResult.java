package com.zmsoft.ccd.lib.bean.vipcard.result;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.vipcard.VipCardDetail;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/14 17:58
 */
public class VipCardDetailResult extends Base {

    private VipCardDetail cardDetail;
    private String warningMsg;

    public VipCardDetail getCardDetail() {
        return cardDetail;
    }

    public void setCardDetail(VipCardDetail cardDetail) {
        this.cardDetail = cardDetail;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }
}
