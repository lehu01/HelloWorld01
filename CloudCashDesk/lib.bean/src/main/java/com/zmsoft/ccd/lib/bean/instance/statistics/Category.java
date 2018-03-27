package com.zmsoft.ccd.lib.bean.instance.statistics;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/20 11:21
 */
public class Category extends Base {

    private String categoryName; // 分类名称
    private int num; // 分类名称数量

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
