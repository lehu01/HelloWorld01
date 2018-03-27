package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailRecyclerRemarkItem {
    /**
     * 是否设置了暂不上菜
     */
    private boolean showStandBy;
    /**
     * 是否暂不上菜
     */
    private boolean isStandBy;
    /**
     * 是否设置了赠送这个菜
     */
    private boolean showPresenterFood;
    /**
     * 是否赠送这个菜
     */
    private boolean isPresenterFood;

    private String memo;
    public boolean isShowStandBy() {
        return showStandBy;
    }

    public void setShowStandBy(boolean showStandBy) {
        this.showStandBy = showStandBy;
    }

    public boolean isStandBy() {
        return isStandBy;
    }

    public void setStandBy(boolean standBy) {
        isStandBy = standBy;
    }

    public boolean isShowPresenterFood() {
        return showPresenterFood;
    }

    public void setShowPresenterFood(boolean showPresenterFood) {
        this.showPresenterFood = showPresenterFood;
    }

    public boolean isPresenterFood() {
        return isPresenterFood;
    }

    public void setPresenterFood(boolean presenterFood) {
        isPresenterFood = presenterFood;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
