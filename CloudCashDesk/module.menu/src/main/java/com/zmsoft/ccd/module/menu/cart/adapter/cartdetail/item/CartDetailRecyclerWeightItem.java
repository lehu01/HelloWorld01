package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailRecyclerWeightItem {
    /**
     * 重量名称
     */
    private String weightName;

    private double accountNum;

    /**
     * 原始的accountNum，因为服务端无法判断双单位菜的accountNum是否修改过，所以客户端需要在CartItem里
     * 增加一个标志位doubleUnitStatus来区分,表示双单位菜是否修改过 枚举0：未修改 1：修改过
     * originAccountNum用来存储最原始的accountNum，用来和修改后的accountNum进行比较，判断是否有修改
     *
     * @see com.zmsoft.ccd.module.menu.cart.model.CartItem，
     */
    private double originAccountNum;

    private String accountUnit;

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountUnit() {
        return accountUnit;
    }

    public void setAccountUnit(String accountUnit) {
        this.accountUnit = accountUnit;
    }

    public double getOriginAccountNum() {
        return originAccountNum;
    }

    public void setOriginAccountNum(double originAccountNum) {
        this.originAccountNum = originAccountNum;
    }
}
