package com.zmsoft.ccd.module.menu.menu.bean.vo;

import com.zmsoft.ccd.module.menu.cart.model.MemoLabel;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.ParamSuitSubMenu;

import java.util.List;
import java.util.Map;

/**
 * @author DangGui
 * @create 2017/6/6.
 */

public class SuitChildVO extends Menu {

    private int suitSubMenuPosition;

    private ParamSuitSubMenu paramSuitSubMenu;

    private String groupId;

    private String makeId;

    /**
     * 备注
     */
    private String memo;


    /**
     * 点菜数量
     */
    private Double num;
    /**
     * 结账数量
     */
    private double accountNum;
    /**
     * 备注
     */
    private Map<String, List<MemoLabel>> labels;

    /**
     * 是否先不上菜，1表示暂不上菜，0表示立即上菜
     */
    private short isWait = 0;

    /**
     * 双单位菜是否修改过 <br />
     * 因为服务端无法判断双单位菜的accountNum是否修改过，所以客户端需要在CartItem里
     * 增加一个标志位doubleUnitStatus来区分,表示双单位菜是否修改过 枚举
     * 0：未修改 1：修改过
     */
    private int doubleUnitStatus;

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }

    public Map<String, List<MemoLabel>> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, List<MemoLabel>> labels) {
        this.labels = labels;
    }

    public short getIsWait() {
        return isWait;
    }

    public void setIsWait(short isWait) {
        this.isWait = isWait;
    }

    public int getDoubleUnitStatus() {
        return doubleUnitStatus;
    }

    public void setDoubleUnitStatus(int doubleUnitStatus) {
        this.doubleUnitStatus = doubleUnitStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public ParamSuitSubMenu getParamSuitSubMenu() {
        return paramSuitSubMenu;
    }

    public void setParamSuitSubMenu(ParamSuitSubMenu paramSuitSubMenu) {
        this.paramSuitSubMenu = paramSuitSubMenu;
    }

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getSuitSubMenuPosition() {
        return suitSubMenuPosition;
    }

    public void setSuitSubMenuPosition(int suitSubMenuPosition) {
        this.suitSubMenuPosition = suitSubMenuPosition;
    }
}
