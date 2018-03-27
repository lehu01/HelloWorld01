package com.zmsoft.ccd.lib.bean.vipcard.result;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.vipcard.VipCard;

import java.util.List;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/12 14:45
 */
public class VipCardListResult extends Base {

    private List<VipCard> cardList;

    public List<VipCard> getCardList() {
        return cardList;
    }

    public void setCardList(List<VipCard> cardList) {
        this.cardList = cardList;
    }
}
