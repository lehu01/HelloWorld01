package com.zmsoft.ccd.lib.bean.filter;

import com.zmsoft.ccd.lib.utils.StringUtils;

import java.io.Serializable;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/28 17:27
 */
public class FilterItem implements Serializable {

    public static final int FILTER_ITEM_TITLE = 1; // 父标题
    public static final int FILTER_ITEM_CONTENT = 2; // 子内容

    public static final String MENU_0001 = "MENU_0001"; // 关注的对象
    public static final String MENU_ITEM_0001 = "MENU_ITEM_0001"; //  关注的座位
    public static final String MENU_ITEM_0002 = "MENU_ITEM_0002"; // 关注的外卖单
    public static final String MENU_ITEM_0003 = "MENU_ITEM_0003"; // 关注的零售单
    public static final String MENU_0002 = "MENU_0002"; // 关注的座位区域

    public static final String MENU_ITEM_END_PAY = "MENU_ITEM_END_PAY"; // 已结账单
    public static final String MENU_ITEM_NO_PAY = "MENU_ITEM_NO_PAY"; // 未结账单
    public static final String MENU_ITEM_MESSAGE_ALL = "MENU_ITEM_MESSAGE_ALL"; // 全部消息
    public static final String MENU_ITEM_MESSAGE_USER = "MENU_ITEM_MESSAGE_USER"; // 顾客新消息
    public static final String MENU_ITEM_MESSAGE_DEAL_WITH = "MENU_ITEM_MESSAGE_DEAL_WITH"; // 已处理消息

    private boolean isCheck; // 是否选中
    private int type; // 类型
    private String name; // 名称
    private String code; // code
    private String bizId; // 业务id，如果是桌位下的item，code为areaId

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        this.isCheck = check;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public boolean isWatched() {
        if (!StringUtils.isEmpty(code) && (MENU_ITEM_0001.equals(code) || MENU_ITEM_0003.equals(code))) {
            return true;
        }
        return false;
    }
}
