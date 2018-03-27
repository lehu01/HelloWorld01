package com.zmsoft.ccd.module.takeout.order.adapter.vo;

import android.text.SpannableString;

import com.zmsoft.ccd.takeout.bean.Takeout;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/24.
 */

public class OrderPayInfoHolderVO extends BaseTakeoutHolderVO{

    private SpannableString payDetailSpan;

    public OrderPayInfoHolderVO(Takeout takeout) {
        super(takeout);
    }

    public SpannableString getPayDetailSpan() {
        return payDetailSpan;
    }

    public void setPayDetailSpan(SpannableString payDetailSpan) {
        this.payDetailSpan = payDetailSpan;
    }
}
