package com.zmsoft.ccd.module.main.message.detail.adapter.items;

import java.io.Serializable;

/**
 * 桌位消息详情——商品信息列表的title信息
 * eg：“当归     2份      15:20”
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class DeskMsgDetailFoodsTitleRecyclerItem implements Serializable {
    /**
     * 点菜人的头像
     */
    private String customerAvatarUrl;
    /**
     * 点菜人的姓名
     */
    private String customerName;
    /**
     * 点的菜的数量，此处数量是本栏里面所有数量的和，如果有小数，需显示小数，单位统一都是“份”。
     */
    private String menuNum;
    /**
     * 下单时间，精确到小时和分钟
     */
    private String openTime;
    /**
     * 消息创建时间的时间戳
     */
    private String createTime;
    /**
     * 修改时间(消息处理时间)
     */
    private String modifiedTime;

    /**
     * 消息详情展示用的时间，未审核的消息用createTime，审核过的（处理过的）消息用openTime
     */
    private String showTime;

    public String getCustomerAvatarUrl() {
        return customerAvatarUrl;
    }

    public void setCustomerAvatarUrl(String customerAvatarUrl) {
        this.customerAvatarUrl = customerAvatarUrl;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMenuNum() {
        return menuNum;
    }

    public void setMenuNum(String menuNum) {
        this.menuNum = menuNum;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
}
