package com.zmsoft.ccd.menu.business;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/9/12.
 */

public class MenuConstant {

    public static class CartFoodKind {
        /**
         * 普通菜
         */
        public static final int KIND_NORMAL_FOOD = 1;
        /**
         * 套菜
         */
        public static final int KIND_COMBO_FOOD = 2;
        /**
         * 自定义菜
         */
        public static final int KIND_CUSTOM_FOOD = 3;
        /**
         * 自定义套菜
         */
        public static final int KIND_CUSTOM_COMBO = 4;
        /**
         * 加料菜
         */
        public static final int KIND_FEED_FOOD = 5;
    }

    /**
     * 加菜的状态
     */
    public static class TakeFoodType {
        public static final int FOOD = 0; //食物
        public static final int DOGGY_BOX = 1; //打包盒
    }
}
