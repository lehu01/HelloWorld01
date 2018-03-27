package com.zmsoft.ccd.module.menu.menu.source;

/**
 * Description：网关接口请求参数的Key
 * <br/>
 * Created by kumu on 2017/4/15.
 */

interface MenuHttpConstant {

    /**
     * 公共请求参数Key
     */
    interface Base {
        String ENTITY_ID = "entity_id";
    }

    /**
     * 菜单列表
     */
    interface MenuList extends Base {
        String METHOD = "com.dfire.soa.cloudcash.getMenuList";//"com.dfire.item.IGetMenuService.getMenusByQuery";
        String KEYWORD = "keyWord";
        String NAME = "name";
        String KIND_MENU_ID = "kindMenuId";
    }

    /**
     * 菜类列表
     */
    interface MenuCategory extends Base {
        String METHOD = "com.dfire.item.IGetMenuService.queryKindMenuList";
        String TYPES = "types";
    }

    /**
     * 点菜单位
     */
    interface MenuUnit extends Base {
        String METHOD = "com.dfire.item.IGetMenuService.queryUnit";
    }

    /**
     * 传送方案
     */
    interface PassThroughWay extends Base {
        String METHOD = "com.dfire.cashconfig.listProducePlans";
    }

    /**
     * 自定义菜到购物车
     */
    interface CustomToCart extends Base {
        String METHOD = "com.dfire.soa.cloudcash.addUserDefinedDish";
        String USER_ID = "user_id";
        String OWNER_CUSTOMER_ID = "owner_customer_id";
        String SEAT_CODE = "seat_code";
        String MEMO = "memo";
        String PEOPLE_COUNT = "people_count";
        String TRANSFER_PLAN_IDS = "transferPlanIds";
        String ITEM_LIST = "item_list";
    }

    /**
     * 桌位状态
     */
    interface SeatStatus extends Base {
        String METHOD = "com.dfire.msstate.getSeatStatusBySeatCode";
        String SEAT_CODE = "seat_code";
    }

    /**
     * 套餐详情
     */
    interface SuitDetail extends Base {
        String METHOD = "com.dfire.item.IGetSuitMenuService.getSuitMenuDetail2Client";
        String SUIT_MENU_ID = "suit_menu_id";
        String SOURCE = "source";
    }

    /**
     * 套餐的组合计价规则（撞餐）
     */
    interface SuitHitRule extends Base {
        String METHOD = "com.dfire.item.ISuitMenuHitRuleService.getRulesBySuitMenuId";
        String SUIT_MENU_ID = "suit_menu_id";
    }

    /**
     * 菜编码查询MenuList
     */
    interface MenuListByCode extends Base {
        String METHOD = "com.dfire.item.getMenuListByCode";
    }
}
