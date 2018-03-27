package com.zmsoft.ccd.module.menu.helper;

/**
 * 购物车帮助类
 *
 * @author DangGui
 * @create 2017/4/10.
 */

public class CartHelper {
    /**
     * 商品如果有做法、规格、备注、加料在商品的下一行显示，超过一行时换行显示，例如大杯，加冰，椰果1份
     * 加料需展示每份加料的数量
     * 依次显示规格、做法、加料、备注，用逗号隔开
     */
    public static final String COMMA_SEPARATOR = "，";

    /**
     * 购物车菜的类型
     */
    public static class CartFoodType {
        public static final int TYPE_MUST_SELECT = 1; // 必选商品
    }

    /**
     * 购物车菜的类型(套餐、普通菜等)<br />
     * 1：普通菜 2：套菜 5：加料菜
     */
    public static class CartFoodKind {
        public static final int KIND_NORMAL_FOOD = 1; // 普通菜
        public static final int KIND_COMBO_FOOD = 2; // 套菜
        public static final int KIND_CUSTOM_FOOD = 3; // 自定义菜
        public static final int KIND_FEED_FOOD = 5; // 加料菜
    }

    /**
     * 加载菜分类需要用到
     */
    public static class MenuListFoodKind {
        /**
         * 普通菜
         */
        public static final int FOOD_KIND_NORMAL = 0;
        /**
         * 套餐
         */
        public static final int FOOD_KIND_SUIT = 1;
        /**
         * 加料菜
         */
        public static final int FOOD_KIND_BURDENING = 2;
        /**
         * 所有
         */
        public static final int FOOD_KIND_ALL = -1;

    }

    /**
     * 上菜选项<br />
     * 是否先上菜，1表示暂不上菜，0表示立即上菜
     */
    public static class ServeFoodKind {
        public static final int KIND_NOW = 0; // 立即上菜
        public static final int KIND_STANDBY = 1; // 暂不上菜
    }

    /**
     * 商品详情页分类 <br />
     * 目前有四个页面 商品详情、套餐子菜详情、商品修改、必选商品修改 共用同一个界面，需要根据
     * type进行细节上的区分展示
     */
    public static class FoodDetailType {
        public static final int FOOD_DETAIL = 1; // 商品详情
        public static final int COMBO_CHILD_DETAIL = 2; // 套餐子菜详情
        public static final int FOOD_MODIFY = 3; // 商品修改
        public static final int MUST_SELECT_MODIFY = 4; // 必选商品修改
        public static final int COMBO_MUST_SELECT_CHILD_DETAIL = 5; // 套餐内必点商品的详情
    }

    /**
     * 套餐/普通菜 <br />
     * 1表示套餐，0表示普通菜
     */
    public static class BaseMenuFoodType {
        public static final int NORMAL = 0; // 普通菜
        public static final int COMBO = 1; // 套餐
    }

    /**
     * 是否双单位菜肴 <br />
     * 0表示非双单位，1表示双单位
     */
    public static class TwoAccountKind {
        public static final int KIND_NOT_2Account = 0; // 非双单位
        public static final int KIND_2Account = 1; // 双单位
    }

    /**
     * 调价模式
     * <p>
     * 一次性加价：1
     * 每购买单位加价：2
     * 每结账单位加价：3
     * 不加价：0
     * </p>
     */
    public static class AddPriceMode {
        /**
         * 不加价
         */
        public static final int MODE_NONE = 0;
        /**
         * 一次性加价
         */
        public static final int MODE_ONCE = 1;
        /**
         * 每购买单位加价
         */
        public static final int MODE_BUY_UNIT = 2;
        /**
         * 每结账单位加价
         */
        public static final int MODE_ACCOUNT_UNIT = 3;
    }

    /**
     * 购物车页面来源
     *
     * @link http://k.2dfire.net/pages/viewpage.action?pageId=285081713
     */
    public static class CartSource {
        public static String CART_LIST = "CLOUD_CASH001";
        public static String CART_MODIFY = "CLOUD_CASH002";
        public static String CART_MUST_SELECT_MODIFY = "CLOUD_CASH003";
        public static String CART_DETAIL = "CLOUD_CASH004";
        public static String CART_COMBO_CHILD_DETAIL = "CLOUD_CASH005";
        public static String MENU_LIST = "CLOUD_CASH006";
        public static String COMBO_DETAIL = "CLOUD_CASH007";
        public static String CUSTOM_FOOD = "CLOUD_CASH008";
        public static String CUSTOM_SCAN_ADDFOOD = "CLOUD_CASH009";
    }

    /**
     * Dialog获取数据来源
     */
     public static class DialogDateFrom {
        public static int SUIT_DETAIL = 1;  // 套餐详情,有套餐组限额
        public static int OTHER_VIEW = 2;
        // 购物车中简单菜详情第二个view，只需要和最小值判断，但是不能为0
        public static int CART_NORMAL_FOOD_DETAIL_SENOND_VIEW = 3;
        // 零售自定义商品中数量，只需要和0判断，但是不能为0
        public static int RETAIL_CUSTOM_FOOD_VIEW = 4;
    }
    /**
     * 页面跳转的requestCode
     */
    public static class CartActivityRequestCode {
        //跳转到购物车详情
        public static final int CODE_TO_CART_DETAIL = 100;
        //跳转到自定义菜 详情
        public static final int CODE_TO_CART_CUSTOM_FOOD = 101;
        //onActivityResult拿到的intent中的DinningTableVo
        public static String ACTIVITY_RESULT_EXTRA_DINVO = "extra_dinvo";
    }

    /**
     * 是否赠送这个菜 0 不赠送，1 赠送
     */
    public static class PresentFood {
        public static final int NOT = 0; // 不赠送
        public static final int YES = 1; //  赠送
    }

    /**
     * 是否先不上菜，1表示暂不上菜，0表示立即上菜
     */
    public static class StandBy {
        public static final int NOT = 0; // 立即上菜
        public static final int YES = 1; //  暂不上菜
    }

    /**
     * 菜的数量，最大是10000
     */
    public static class FoodNum {
        public static final int MIN_VALUE = 0;
        public static final int MAX_VALUE = 10000;
    }

    public static class UnitKind {
        /**
         * 未设置
         */
        public static final int DOUBLE_UNIT_UNSETTING = -1;
        /**
         * 双单位
         */
        public static final int DOUBLE_UNIT_YES = 1;
        /**
         * 不是双单位
         */
        public static final int DOUBLE_UNIT_NO = 0;
    }

    /**
     * <code>是否下架</code>.<br />
     * 0已下架 1未下架
     */
    public static class FoodOffShelves {
        public static final int OFF = 0; // 已下架
        public static final int ON = 1; // 未下架
    }

    /**
     * 购物车详情，双单位菜accountNum默认是1.00
     */
    public static final double CART_DETAIL_DOUBLE_UNIT_ACCOUNT_NUM = 1.00;

    /**
     * 因为服务端无法判断双单位菜的accountNum是否修改过，所以客户端需要在CartItem里
     * 增加一个标志位doubleUnitStatus来区分,表示双单位菜是否修改过 枚举<br />
     * 0：未修改 1：修改过
     */
    public static class DoubleUnitStatus {
        public static final int FALSE = 0; // 未修改
        public static final int TRUE = 1; // 修改过
    }

    /**
     * 检查起点分数
     *
     * @return 如果合法则返回true
     */
    public static boolean checkStartNum(double startNum, double inputNum) {
        return startNum <= 1 || inputNum >= startNum;
    }

}
