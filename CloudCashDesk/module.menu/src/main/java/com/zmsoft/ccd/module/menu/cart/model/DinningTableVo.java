package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/13.
 */
public class DinningTableVo implements Serializable{
    /**
     * 扫码用户ID
     */
    private String owner;
    /**
     * 菜单分类统计数量
     */
    private LinkedHashMap<String, KindMenuCount> kindMenuDic;
    /**
     * 我点的菜种类统计数量
     */
    private int myDicNum;
    /**
     * 同桌其他菜种类的统计数量
     */
    private int otherDicNum;
    /**
     * 整桌所有菜种类的统计数量
     */
    private int totalDicNum;
    /**
     * 桌位购物车菜品数据（按种类统计） 因为H5没有做判空 需要给一个空集合<br />
     * <b>页面是按菜类来分开展示的话，使用这个字段就行，List<UserCartVo> userCarts可以不用管</b>
     */
    private List<KindUserCartVo> kindUserCarts = new ArrayList<>(0);
    /**
     * 估清菜
     */
    private List<SoldOutMenuVo> soldOutMenus;
    /**
     * 用餐人数
     */
    private int people;
    /**
     * 整桌备注
     */
    private String memo;
    /**
     * 是否允许修改人数与备注<br />
     * <b>该字段是true的话表示已开单</b>
     */
    private boolean modifyPeopleMemo;
    /**
     * 是否展示智能推荐菜
     */
    private boolean smart;
    /**
     * 备注标签
     */
    private List<String> memoLabels;

    /**
     * 购物车修改时间戳（修改商品）
     */
    private long cartTime;

    /**
     * 标注整桌菜是否打包
     */
    private boolean takeOut;

    /**
     * 标注整桌菜是否暂时不上
     */
    private boolean wait;

    /**
     * 标注整桌菜是否热菜暂时不上
     */
    private boolean hotDishWait;

    public boolean isTakeOut() {
        return takeOut;
    }

    public void setTakeOut(boolean takeOut) {
        this.takeOut = takeOut;
    }

    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public boolean isHotDishWait() {
        return hotDishWait;
    }

    public void setHotDishWait(boolean hotDishWait) {
        this.hotDishWait = hotDishWait;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LinkedHashMap<String, KindMenuCount> getKindMenuDic() {
        return kindMenuDic;
    }

    public void setKindMenuDic(LinkedHashMap<String, KindMenuCount> kindMenuDic) {
        this.kindMenuDic = kindMenuDic;
    }

    public int getMyDicNum() {
        return myDicNum;
    }

    public void setMyDicNum(int myDicNum) {
        this.myDicNum = myDicNum;
    }

    public int getOtherDicNum() {
        return otherDicNum;
    }

    public void setOtherDicNum(int otherDicNum) {
        this.otherDicNum = otherDicNum;
    }

    public int getTotalDicNum() {
        return totalDicNum;
    }

    public void setTotalDicNum(int totalDicNum) {
        this.totalDicNum = totalDicNum;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isModifyPeopleMemo() {
        return modifyPeopleMemo;
    }

    public void setIsModifyPeopleMemo(boolean isModifyPeopleMemo) {
        this.modifyPeopleMemo = isModifyPeopleMemo;
    }

    public boolean isSmart() {
        return smart;
    }

    public void setIsSmart(boolean isSmart) {
        this.smart = isSmart;
    }

    public List<KindUserCartVo> getKindUserCarts() {
        return kindUserCarts;
    }

    public void setKindUserCarts(List<KindUserCartVo> kindUserCarts) {
        this.kindUserCarts = kindUserCarts;
    }

    public List<String> getMemoLabels() {
        return memoLabels;
    }

    public void setMemoLabels(List<String> memoLabels) {
        this.memoLabels = memoLabels;
    }

    public long getCartTime() {
        return cartTime;
    }

    public void setCartTime(long cartTime) {
        this.cartTime = cartTime;
    }

    public List<SoldOutMenuVo> getSoldOutMenus() {
        return soldOutMenus;
    }

    public void setSoldOutMenus(List<SoldOutMenuVo> soldOutMenus) {
        this.soldOutMenus = soldOutMenus;
    }
}
