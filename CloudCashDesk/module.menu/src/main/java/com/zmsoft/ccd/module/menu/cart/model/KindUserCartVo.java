package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class KindUserCartVo implements Serializable {
    /**
     * 菜肴类型VO对象
     */
    private KindVo kindVo;

    /**
     * 菜肴对象VO
     */
    private List<ItemVo> itemVos = new ArrayList<>();

    public KindVo getKindVo() {
        return kindVo;
    }

    public void setKindVo(KindVo kindVo) {
        this.kindVo = kindVo;
    }


    public List<ItemVo> getItemVos() {
        return itemVos;
    }

    public void setItemVos(List<ItemVo> itemVos) {
        this.itemVos = itemVos;
    }
}
