package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

import java.io.Serializable;
import java.util.List;

/**
 * 普通菜详情
 *
 * @author DangGui
 * @create 2017/4/19.
 */
public class NormalMenuVo implements Serializable{

    /**
     * 菜肴详情
     */
    private Menu menu;

    /**
     * 菜肴相关联的做法数据列表
     */
    private List<MakeDataDto> makeDataList;

    /**
     * 菜肴相关联的规格数据列表
     */
    private List<SpecDataDto> specDataList;

    /**
     * 菜肴相关联的图片路径列表
     */
    private List<String> menuImageList;

    /**
     * 加料菜类列表
     */
    private List<AdditionKindMenuVo> additionKindMenuList;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<MakeDataDto> getMakeDataList() {
        return makeDataList;
    }

    public void setMakeDataList(List<MakeDataDto> makeDataList) {
        this.makeDataList = makeDataList;
    }

    public List<SpecDataDto> getSpecDataList() {
        return specDataList;
    }

    public void setSpecDataList(List<SpecDataDto> specDataList) {
        this.specDataList = specDataList;
    }

    public List<String> getMenuImageList() {
        return menuImageList;
    }

    public void setMenuImageList(List<String> menuImageList) {
        this.menuImageList = menuImageList;
    }

    public List<AdditionKindMenuVo> getAdditionKindMenuList() {
        return additionKindMenuList;
    }

    public void setAdditionKindMenuList(List<AdditionKindMenuVo> additionKindMenuList) {
        this.additionKindMenuList = additionKindMenuList;
    }
}
