package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

import java.io.Serializable;

/**
 * 基础口味.
 *
 * @author DangGui
 * @create 2017/4/21.
 */
public abstract class BaseTaste implements Serializable{
    /**
     * <code>序列ID</code>.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <code>表名</code>.
     * 只给收银使用
     */
    public static final String TABLE_NAME = "TASTE";

    /**
     * 字段名：创建时间
     */
    public static String CREATETIME = "create_time";

    /**
     * 字段名：顺序码
     */
    public static String SORTCODE = "sort_code";

    /**
     * <code>顺序码</code>.
     */
    private int sortCode;
    /**
     * <code>口味</code>.
     */
    private String name;
    /**
     * <code>拼写</code>.
     */
    private String spell;
    /**
     * 口味分类id
     */
    private String kindTasteId;

    /**
     * <code>数据来源标记</code>.
     */
    private int ownerType = 1;

    /**
     * 得到顺序码.
     *
     * @return 顺序码.
     */
    public int getSortCode() {
        return sortCode;
    }

    /**
     * 设置顺序码.
     *
     * @param sortCode 顺序码.
     */
    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    /**
     * 得到口味.
     *
     * @return 口味.
     */
    public String getName() {
        return name;
    }

    /**
     * 设置口味.
     *
     * @param name 口味.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 得到拼写.
     *
     * @return 拼写.
     */
    public String getSpell() {
        return spell;
    }

    /**
     * 设置拼写.
     *
     * @param spell 拼写.
     */
    public void setSpell(String spell) {
        this.spell = spell;
    }

    /**
     * 得到[kindTasteId].
     *
     * @return [kindTasteId].
     */
    public String getKindTasteId() {
        return kindTasteId;
    }

    /**
     * 设置[kindTasteId].
     *
     * @param kindTasteId [kindTasteId].
     */
    public void setKindTasteId(String kindTasteId) {
        this.kindTasteId = kindTasteId;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }
}
