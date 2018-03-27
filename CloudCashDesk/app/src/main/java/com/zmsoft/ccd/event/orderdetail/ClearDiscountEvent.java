package com.zmsoft.ccd.event.orderdetail;

import com.zmsoft.ccd.lib.bean.order.detail.PromotionVo;

/**
 * Description：清空折扣
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 10:24
 */
public class ClearDiscountEvent {

    private PromotionVo promotionVo;

    public PromotionVo getPromotionVo() {
        return promotionVo;
    }

    public void setPromotionVo(PromotionVo promotionVo) {
        this.promotionVo = promotionVo;
    }
}
