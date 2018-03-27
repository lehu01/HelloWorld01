package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

import java.io.Serializable;
import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class BaseMenuVo implements Serializable {

    /**
     * [用于套餐]这个值为0，表示商品下架（存在必选商品下架或者沽清），为1表示商品上架
     */
    private int isSelf;

    /**
     * 0表示为套餐，1表示普通菜
     */
    private int isSetMenu;
    /**
     * 菜品详情json串,如果isSetMenu是1，则将menuJson序列化为NormalMenuVo
     *
     * @see NormalMenuVo
     */
    private String menuJson;

    /**
     * 备注（目前只有普通菜详情、修改页需要该字段，套餐详情和修改不需要）
     */
    private List<KindAndTasteVo> kindTasteList;

    public int getIsSetMenu() {
        return isSetMenu;
    }

    public void setIsSetMenu(int isSetMenu) {
        this.isSetMenu = isSetMenu;
    }

    public String getMenuJson() {
        return menuJson;
    }

    public void setMenuJson(String menuJson) {
        this.menuJson = menuJson;
    }

    public List<KindAndTasteVo> getKindTasteList() {
        return kindTasteList;
    }

    public void setKindTasteList(List<KindAndTasteVo> kindTasteList) {
        this.kindTasteList = kindTasteList;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }
}
