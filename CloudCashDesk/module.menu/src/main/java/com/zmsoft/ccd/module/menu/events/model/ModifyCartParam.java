package com.zmsoft.ccd.module.menu.events.model;

import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;

/**
 * @author DangGui
 * @create 2017/4/28.
 */

public class ModifyCartParam {
    /**
     * 修改购物车后返回的DinningTableVo
     */
    private DinningTableVo mDinningTableVo;
    /**
     * 内部维护的字段类
     */
    private OrderParam mCreateOrderParam;

    public ModifyCartParam(DinningTableVo dinningTableVo, OrderParam createOrderParam) {
        mDinningTableVo = dinningTableVo;
        mCreateOrderParam = createOrderParam;
    }

    public DinningTableVo getDinningTableVo() {
        return mDinningTableVo;
    }

    public void setDinningTableVo(DinningTableVo dinningTableVo) {
        mDinningTableVo = dinningTableVo;
    }

    public OrderParam getCreateOrderParam() {
        return mCreateOrderParam;
    }

    public void setCreateOrderParam(OrderParam createOrderParam) {
        mCreateOrderParam = createOrderParam;
    }
}
