package com.zmsoft.ccd.lib.bean.menubalance;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 1 on 2016/10/25.
 */

public class MenuBalanceVO implements Parcelable {
    public static final double ZERO = 10E-6;

    /**
     * <code>序列ID</code>.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <code>剩余数量不限</code>.
     */
    public static final double BALANCE_NUM_NOLIMIT = -1d;

    /**
     * <code>剩余数量为零</code>.
     */
    public static final double BALANCE_NUM_ZERO = 0d;

    /**
     * <code>点菜模式</code>.
     */
    public static final short MODE_BY_BUY = (short) 1;
    /**
     * <code>结账模式</code>.
     */
    public static final short MODE_BY_ACCOUNT = (short) 2;

    /**
     * <code>沽清方式:手动(=0,不可加减)</code>.
     */
    public static final short BALANCE_MODE_BY_HANDLER = (short) 1;
    /**
     * <code>沽清方式:自动(可小于0,可加回)</code>.
     */
    public static final short BALANCE_MODE_BY_AUTO = (short) 2;

    private String id;

    private String entityId;

    private long createtime;

    private long opTime;

    private int isValid;

    /**
     * <code>菜肴ID</code>.
     */
    private String menuId;
    /**
     * <code>计算依据（点菜数量/结账数量）</code>.
     */
    private int calMode;
    /**
     * <code>剩余数量</code>.
     */
    private double balanceNum;
    /**
     * <code>沽清模式</code>.
     */
    private int balanceMode;

    private String menuName;

    /**
     *
     */
    private int balanceId;

    /**
     * 状态
     */
    private int status;

    /**
     * 得到菜肴ID.
     *
     * @return 菜肴ID.
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * 设置菜肴ID.
     *
     * @param menuId 菜肴ID.
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * 得到计算依据（点菜数量/结账数量）.
     *
     * @return 计算依据（点菜数量/结账数量）.
     */
    public int getCalMode() {
        return calMode;
    }

    /**
     * 设置计算依据（点菜数量/结账数量）.
     *
     * @param calMode 计算依据（点菜数量/结账数量）.
     */
    public void setCalMode(int calMode) {
        this.calMode = calMode;
    }

    /**
     * 得到剩余数量.
     *
     * @return 剩余数量.
     */
    public double getBalanceNum() {
        return balanceNum;
    }

    /**
     * 设置剩余数量.
     *
     * @param balanceNum 剩余数量.
     */
    public void setBalanceNum(double balanceNum) {
        this.balanceNum = balanceNum;
    }

    /**
     * 得到沽清模式.
     *
     * @return 沽清模式.
     */
    public int getBalanceMode() {
        return balanceMode;
    }

    /**
     * 设置沽清模式.
     *
     * @param balanceMode 沽清模式.
     */
    public void setBalanceMode(int balanceMode) {
        this.balanceMode = balanceMode;
    }


    public int getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    /**
     * 清空.
     */
    public void clear() {
        setBalanceNum(BALANCE_NUM_NOLIMIT);
        setBalanceMode(BALANCE_MODE_BY_HANDLER);
    }

    /**
     * 是否已售完.
     *
     * @return
     */
    public boolean isOver() {
        if (BALANCE_MODE_BY_HANDLER == getBalanceMode()) {
            return isZero(getBalanceNum());
        } else {
            return getBalanceNum() <= 0;
        }
    }

    /**
     * 是否为不限数量.
     *
     * @return
     */
    public boolean isNoLimit() {
        if (BALANCE_MODE_BY_HANDLER == getBalanceMode()) {
            return isZero(getBalanceNum() + 1);
        } else {
            return false;
        }
    }

    /**
     * 判断double型数字是否为零(包括正负数).
     *
     * @return 结果.
     */
    public static boolean isZero(Double d) {
        if (d == null) {
            return false;
        }
        return Math.abs(d) < ZERO;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.entityId);
        dest.writeLong(this.createtime);
        dest.writeLong(this.opTime);
        dest.writeInt(this.isValid);
        dest.writeString(this.menuId);
        dest.writeInt(this.calMode);
        dest.writeValue(this.balanceNum);
        dest.writeInt(this.balanceMode);
        dest.writeString(this.menuName);
        dest.writeInt(this.balanceId);
        dest.writeInt(this.status);
    }

    public MenuBalanceVO() {
    }

    protected MenuBalanceVO(Parcel in) {
        this.id = in.readString();
        this.entityId = in.readString();
        this.createtime = in.readLong();
        this.opTime = in.readLong();
        this.isValid = in.readInt();
        this.menuId = in.readString();
        this.calMode = in.readInt();
        this.balanceNum = (Double) in.readValue(Double.class.getClassLoader());
        this.balanceMode = in.readInt();
        this.menuName = in.readString();
        this.balanceId = in.readInt();
        this.status = in.readInt();
    }

    public static final Creator<MenuBalanceVO> CREATOR = new Creator<MenuBalanceVO>() {
        @Override
        public MenuBalanceVO createFromParcel(Parcel source) {
            return new MenuBalanceVO(source);
        }

        @Override
        public MenuBalanceVO[] newArray(int size) {
            return new MenuBalanceVO[size];
        }
    };
}
