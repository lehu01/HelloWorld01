package com.zmsoft.ccd.module.menu.menu.bean.vo;

/**
 * Description：菜类、菜名的首字母、点的份数
 * <br/>
 * Created by kumu on 2017/4/13.
 */

public class MenuGroupVO {


    /**
     * 菜类ID
     */
    private String groupId;

    /**
     * 菜类名
     */
    private String groupName;


    /**
     * 该分类所点菜的项数总和，如果数量为0，则不显示数量。一个商品点多份的情况算1项，例如冷菜点了2份水煮花生、1份凉拌黄瓜，显示：2。
     */
    private int orderCount;

    public MenuGroupVO(String groupId) {
        this.groupId = groupId;
    }

    public MenuGroupVO(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public MenuGroupVO(String groupId, String groupName, int orderCount) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.orderCount = orderCount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }


    @Override
    public boolean equals(Object o) {
        if (groupId == null) {
            return false;
        }
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuGroupVO that = (MenuGroupVO) o;

        return groupId.equals(that.groupId);

    }

    @Override
    public int hashCode() {
        return groupId == null ? 0 : groupId.hashCode();
    }
}
