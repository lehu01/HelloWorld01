package com.zmsoft.ccd.lib.bean.instance.statistics;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/20 11:15
 */
public class CategoryInfo extends Base {

    private int totalNum; // 菜肴分类合计数量
    private List<Category> categoryVoList; // 菜肴分类统计信息

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<Category> getCategoryVoList() {
        return categoryVoList;
    }

    public void setCategoryVoList(List<Category> categoryVoList) {
        this.categoryVoList = categoryVoList;
    }
}
