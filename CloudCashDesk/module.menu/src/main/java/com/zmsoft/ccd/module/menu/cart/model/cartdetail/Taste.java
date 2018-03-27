package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

/**
 * 口味.
 *
 * @author DangGui
 * @create 2017/4/21.
 */
public class Taste extends BaseTaste {
    public static String simpleName = Taste.class.getSimpleName();

    /**
     * <code>序列ID</code>.
     */
    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 关键字
     */
    private String keyword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
