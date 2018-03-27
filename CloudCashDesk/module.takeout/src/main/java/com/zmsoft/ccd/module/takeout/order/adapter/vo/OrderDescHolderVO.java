package com.zmsoft.ccd.module.takeout.order.adapter.vo;

import com.zmsoft.ccd.takeout.bean.Takeout;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/24.
 */

public class OrderDescHolderVO extends BaseTakeoutHolderVO{

    private String orderGoodNum;
    private String personNum;
    private String memo;

    public OrderDescHolderVO(Takeout takeout) {
        super(takeout);
    }


    public String getOrderGoodNum() {
        return orderGoodNum;
    }

    public void setOrderGoodNum(String orderGoodNum) {
        this.orderGoodNum = orderGoodNum;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
