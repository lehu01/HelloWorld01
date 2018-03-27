package com.zmsoft.ccd.module.menu.menu.bean.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuGroup;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/21.
 */

public class SuitGroupVO implements Parcelable {

    private String groupId;

    private SuitMenuGroup menuGroup;

    /**
     * 用于该分组为全选  子菜必选数量的总和
     */
    private double requiredNum;

    /**
     * 用户在当前分组下选择的子菜数量
     */
    private double currentNum;

    public SuitGroupVO(SuitMenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    /**
     * 获取可点份数
     *
     * @return
     */
    public double getNum() {
        if (menuGroup != null) {
            return menuGroup.getNum();
        }
        return -1;
    }

    /**
     * 是否分组设置为 数量不限制
     *
     * @return
     */
    public boolean isNoLimit() {
        return menuGroup != null && menuGroup.getNum() < 1;
    }

    /**
     * 分组名称
     *
     * @return
     */
    public String getGroupName() {
        if (menuGroup != null) {
            return menuGroup.getName();
        }
        return "";
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        if (!TextUtils.isEmpty(groupId)) {
            return groupId;
        }
        if (menuGroup != null) {
            return menuGroup.getSuitMenuDetailId();
        }
        return groupId;
    }

    public boolean isRequired() {
        return menuGroup.getIsRequired() == 1;
    }


    public double getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(double currentNum) {
        this.currentNum = currentNum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.menuGroup, flags);
        dest.writeDouble(this.requiredNum);
        dest.writeDouble(this.currentNum);
    }

    protected SuitGroupVO(Parcel in) {
        this.menuGroup = in.readParcelable(SuitMenuGroup.class.getClassLoader());
        this.requiredNum = in.readDouble();
        this.currentNum = in.readDouble();
    }

    public static final Creator<SuitGroupVO> CREATOR = new Creator<SuitGroupVO>() {
        @Override
        public SuitGroupVO createFromParcel(Parcel source) {
            return new SuitGroupVO(source);
        }

        @Override
        public SuitGroupVO[] newArray(int size) {
            return new SuitGroupVO[size];
        }
    };
}
