package com.zmsoft.ccd.module.menu.menu.bean;

public interface MenuKind {

    /**
     * 普通菜
     */
    short NORMAL = 0;
    /**
     * 套菜
     */
    short SUIT = 1;
    /**
     * 加料菜
     */
    short BURDENING = 2;

    /**
     * 自定义普通菜
     */
    short CUSTOM = 3;
    /**
     * 自定义套菜
     */
    short CUSTOM_SUIT = 4;
}