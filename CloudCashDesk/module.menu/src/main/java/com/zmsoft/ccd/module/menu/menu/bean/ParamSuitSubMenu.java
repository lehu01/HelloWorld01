package com.zmsoft.ccd.module.menu.menu.bean;

import com.zmsoft.ccd.module.menu.cart.model.MemoLabel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Description：套餐子菜传给
 * <br/>
 * Created by kumu on 2017/6/15.
 */

public class ParamSuitSubMenu implements Serializable {

    /**
     * 结账数量
     */
    private double accountNum;

    /**
     * 做法
     */
    private String makeId;

    /**
     * 备注
     */
    private String memo;

    /**
     * 口味
     */
    private Map<String, List<MemoLabel>> labels;

    public ParamSuitSubMenu() {
    }

    public ParamSuitSubMenu(double accountNum, String makeIdl, String memo, Map<String, List<MemoLabel>> labels) {
        this.accountNum = accountNum;
        this.makeId = makeIdl;
        this.memo = memo;
        this.labels = labels;
    }

    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Map<String, List<MemoLabel>> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, List<MemoLabel>> labels) {
        this.labels = labels;
    }
}
